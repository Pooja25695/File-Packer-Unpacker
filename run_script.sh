#!/bin/bash

# ✅ Navigate to project directory
cd "/Users/apple/Documents/Java Project/FilePackerUnpacker" || { echo "❌ Error: Directory not found!"; exit 1; }

# ✅ Ensure 'bin' directory exists
mkdir -p bin

# ✅ Compile Java files
echo "🔧 Compiling Java files..."
javac -d bin *.java

# ✅ Run the Java program if compilation is successful
if [ $? -eq 0 ]; then
    echo "✅ Compilation successful!"
    echo "🚀 Running FilePackerUnpackerMain..."
    java -cp bin FilePackerUnpackerMain
else
    echo "❌ Compilation failed!"
fi

