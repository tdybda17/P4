package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.EnumBuilder.EnumBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.JavaFileBuilder;

public class ColorEnumFile implements ClassFile {

    private EnumBuilder builder;

    public ColorEnumFile() {
        this.builder = new EnumBuilder("Color")
                .appendEnum("GRAY")
                .appendEnum("BLACK")
                .appendEnum("WHITE")
                .appendEnum("RED")
                .appendEnum("GREEN")
                .appendEnum("BLUE")
                .appendEnum("YELLOW");
    }

    @Override
    public IJavaFileBuilder getBuilder() {
        return builder;
    }

    @Override
    public String getClassName() {
        return ((EnumBuilder) getBuilder()).getIdentifier();
    }
}
