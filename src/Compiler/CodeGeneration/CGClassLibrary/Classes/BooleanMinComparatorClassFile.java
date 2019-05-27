package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class BooleanMinComparatorClassFile implements ClassFile {
    private ClassBuilder builder;

    public BooleanMinComparatorClassFile() {
        this.builder = new ClassBuilder("", "BooleanMinComparator")
                .appendImplementation(new InterfaceBuilder("Comparator<Boolean>"));
        appendMethods();
    }

    private void appendMethods() {
        builder.appendMethod(new Method("int", "compare").asPublic()
                .appendAttribute(new Attribute("Boolean", "o1"))
                .appendAttribute(new Attribute("Boolean", "o2"))
                .appendBody("return o1.compareTo(o2);"));
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
