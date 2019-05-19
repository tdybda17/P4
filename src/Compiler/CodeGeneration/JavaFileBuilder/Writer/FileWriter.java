package Compiler.CodeGeneration.JavaFileBuilder.Writer;

import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileWriter {

    private IJavaFileBuilder fileBuilder;

    public FileWriter(IJavaFileBuilder fileBuilder) {
        this.fileBuilder = fileBuilder;
    }

    public void writeFile(Path dest) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(dest);
        writer.write("// IMPORTANT: If you can't compile, mark the AutoGenCode directory as excluded\n");
        writer.write("package AutoGenCode;\n\n");
        writer.write(fileBuilder.getFileContent());
        writer.flush();
    }

}
