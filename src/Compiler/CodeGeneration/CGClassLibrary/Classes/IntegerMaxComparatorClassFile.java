package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class IntegerMaxComparatorClassFile implements ClassFile {
    private ClassBuilder builder;

    public IntegerMaxComparatorClassFile() {
        this.builder = new ClassBuilder("", "IntegerMaxComparator")
                .appendImplementation(new InterfaceBuilder("Comparator<Integer>"));
        appendMethods();
    }

    private void appendMethods() {
        builder.appendMethod(new Method("int", "compare").asPublic()
                .appendAttribute(new Attribute("Integer", "o1"))
                .appendAttribute(new Attribute("Integer", "o2"))
                .appendBody("return o2.compareTo(o1);"));
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
