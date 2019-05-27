package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class RealMinComparatorClassFile implements ClassFile {
    private ClassBuilder builder;

    public RealMinComparatorClassFile() {
        this.builder = new ClassBuilder("", "RealMinComparator")
                .appendImplementation(new InterfaceBuilder("Comparator<Double>"));
        appendMethods();
    }

    private void appendMethods() {
        builder.appendMethod(new Method("int", "compare").asPublic()
                .appendAttribute(new Attribute("Double", "o1"))
                .appendAttribute(new Attribute("Double", "o2"))
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
