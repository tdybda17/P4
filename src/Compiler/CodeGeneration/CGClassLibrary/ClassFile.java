package Compiler.CodeGeneration.CGClassLibrary;

import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

public interface ClassFile {

    IJavaFileBuilder getBuilder();
    String getClassName();

}
