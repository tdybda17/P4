package Compiler.CodeGeneration.JavaFileBuilder.Writer;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileWriter {

    private List<IJavaFileBuilder> builders;

    public FileWriter(IJavaFileBuilder fileBuilder) {
        this.builders = new ArrayList<>();
        builders.add(fileBuilder);
    }

    public FileWriter(List<ClassFile> files) {
        this.builders = new ArrayList<>();
        for (ClassFile file : files)
            builders.add(file.getBuilder());
    }

    public void writeFile(Path dest) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(dest);
        writer.write("// IMPORTANT: If you can't compile, mark the AutoGenCode directory as excluded\n");
        writer.write("package AutoGenCode;\n\n");
        writer.write("import import java.util.*;\n");
        writer.write("import Compiler.CodeGeneration.DotFileGenerator.DotFileGenerator;\n\n");
        for (IJavaFileBuilder builder : builders) {
            writer.write(builder.getFileContent());
        }
        writer.flush();
    }

}
