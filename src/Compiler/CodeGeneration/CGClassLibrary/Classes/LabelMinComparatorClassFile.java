package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class LabelMinComparatorClassFile implements ClassFile {
    private ClassBuilder builder;

    public LabelMinComparatorClassFile() {
        this.builder = new ClassBuilder("", "LabelMinComparator")
                .appendImplementation(new InterfaceBuilder("Comparator<String>"));
        appendMethods();
    }

    private void appendMethods() {
        builder.appendMethod(new Method("int", "compare").asPublic()
                .appendAttribute(new Attribute("String", "o1"))
                .appendAttribute(new Attribute("String", "o2"))
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
