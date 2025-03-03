import java.io.*;
import java.util.List;
import java.util.Arrays;

public class FilePacker {
    private FileOutputStream outstream;
    private final String ValidExt[] = {".txt", ".c", ".java", ".cpp"};

    public FilePacker(String src, String Dest) throws Exception {
        String Magic = "Admin11\n";  // ✅ Start with Magic Header on a new line
        outstream = new FileOutputStream(Dest);
        outstream.write(Magic.getBytes());  
        outstream.flush();  

        File folder = new File(src);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("❌ Invalid folder path!");
            return;
        }

        System.out.println("📦 Packing files from folder: " + src);
        listAllFiles(folder);
        outstream.close();
    }

    private void listAllFiles(File folder) throws IOException {
        File[] files = folder.listFiles();
        if (files == null) {
            System.out.println("❌ No files found in the folder.");
            return;
        }

        for (File file : files) {
            if (file.isFile()) {
                try {
                    String name = file.getName();
                    String ext = name.substring(name.lastIndexOf("."));

                    List<String> list = Arrays.asList(ValidExt);
                    if (list.contains(ext)) {
                        System.out.println("📂 Packing: " + file.getAbsolutePath());
                        Pack(file);
                    }
                } catch (Exception e) {
                    System.out.println("❌ Error processing file: " + file.getAbsolutePath());
                    e.printStackTrace();
                }
            }
        }
    }

    private void Pack(File file) throws IOException {
        if (!file.exists()) {
            System.out.println("❌ File not found: " + file.getAbsolutePath());
            return;
        }

        FileInputStream instream = new FileInputStream(file);

        // ✅ New header format
        String Header = "File: " + file.getName() + "\nSize: " + file.length() + "\n\n";
        outstream.write(Header.getBytes());
        outstream.flush();  // Ensure header is written before data

        // ✅ Write File Data
        byte[] buffer = new byte[1024];  
        int bytesRead;
        while ((bytesRead = instream.read(buffer)) > 0) {
            outstream.write(buffer, 0, bytesRead);
        }

        outstream.write("\n-----------------------------------------------\n".getBytes()); // ✅ Add separator
        instream.close();
        System.out.println("✅ Packed Successfully: " + file.getName());
    }
}
