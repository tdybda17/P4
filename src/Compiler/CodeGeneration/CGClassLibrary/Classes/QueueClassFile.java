package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.*;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;

import java.util.List;

public class QueueClassFile implements ClassFile {

    @Override
    public IJavaFileBuilder getBuilder() {
        ClassBuilder builder =
                new ClassBuilder("", "Queue", "T")
                .appendImplementation(new InterfaceBuilder("Collection"));
        appendFields(builder);
        appendMethods(builder);
        return builder;
    }

    private void appendFields(ClassBuilder builder) {
        builder.appendField(new Attribute("LinkedList<T>", "p"));
    }

    private void appendMethods(ClassBuilder builder) {
        builder.appendConstructor(new Constructor(List.of(), "p = new LinkedList<>();"))
                .appendMethod(new Method("boolean", "isEmpty").asPublic()
                        .appendBody("return p.isEmpty();"))
                .appendMethod(new Method("boolean", "enqueue")
                        .appendAttribute(new Attribute("T", "p0"))
                        .appendBody("return p.add(p0);"))
                .appendMethod(new Method("T", "dequeue")
                        .appendBody("return p.removeFirst();"));
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
