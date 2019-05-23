package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class CollectionInterfaceFile implements ClassFile {

    private IJavaFileBuilder builder;

    public CollectionInterfaceFile() {
        this.builder = new InterfaceBuilder("", "Collection", "T")
                            .appendMethod(new Method("boolean", "isEmpty"));
    }

    @Override
    public IJavaFileBuilder getBuilder() {
        return builder;
    }

    @Override
    public String getClassName() {
        return ((InterfaceBuilder) getBuilder()).getIdentifier();
    }
}
