package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

public class MainClassFile implements ClassFile {

    private ClassBuilder builder;

    public MainClassFile() {
        this.builder = new ClassBuilder("", "Main");
    }

    @Override
    public IJavaFileBuilder getBuilder() {
        return builder;
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}

