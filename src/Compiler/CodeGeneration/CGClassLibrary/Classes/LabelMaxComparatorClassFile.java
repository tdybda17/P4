package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class LabelMaxComparatorClassFile implements ClassFile {
    private ClassBuilder builder;

    public LabelMaxComparatorClassFile() {
        this.builder = new ClassBuilder("", "LabelMaxComparator")
                .appendImplementation(new InterfaceBuilder("Comparator<String>"));
        appendMethods();
    }

    private void appendMethods() {
        builder.appendMethod(new Method("int", "compare").asPublic()
                .appendAttribute(new Attribute("String", "o1"))
                .appendAttribute(new Attribute("String", "o2"))
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
