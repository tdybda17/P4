package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class CollectionInterfaceFile implements ClassFile {

    @Override
    public IJavaFileBuilder getBuilder() {
        return new InterfaceBuilder("", "Collection", "T")
                .appendMethod(new Method("boolean", "isEmpty"));
    }

    @Override
    public String getClassName() {
        return ((InterfaceBuilder) getBuilder()).getIdentifier();
    }
}
