import java.io.*;

public class FileUnpack {
    public FileUnpack(String src) throws Exception {
        unpack(src);
    }

    public void unpack(String filePath) throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // ✅ Read Magic Header
            String magicStr = reader.readLine();
            if (!magicStr.equals("Admin11")) {
                throw new IOException("❌ Invalid packed file format");
            }

            System.out.println("✅ Valid packed file detected, unpacking started...");

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("File: ")) {
                    String filename = line.substring(6).trim();

                    // ✅ Read file size
                    String sizeLine = reader.readLine();
                    if (!sizeLine.startsWith("Size: ")) {
                        throw new IOException("❌ Corrupt header: " + sizeLine);
                    }
                    int fileSize = Integer.parseInt(sizeLine.split(":")[1].trim());

                    System.out.println("📂 Extracting: " + filename + " (Size: " + fileSize + " bytes)");

                    // ✅ Skip blank line
                    reader.readLine();  

                    // ✅ Read and write the actual file content
                    try (FileOutputStream fout = new FileOutputStream(filename);
                         FileInputStream fileReader = new FileInputStream(filePath)) {

                        // Move FileInputStream to correct position after headers
                        long skipBytes = fileReader.skip(reader.skip(0));  

                        byte[] buffer = new byte[fileSize];
                        int bytesRead = fileReader.read(buffer, 0, fileSize);
                        fout.write(buffer, 0, bytesRead);
                    }

                    // ✅ Skip separator line
                    reader.readLine();
                    System.out.println("✅ Successfully unpacked: " + filename);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error during unpacking: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
