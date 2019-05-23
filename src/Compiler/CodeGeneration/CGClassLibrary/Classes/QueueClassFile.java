package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.*;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;

import java.util.List;

public class QueueClassFile implements ClassFile {

    private ClassBuilder builder;

    public QueueClassFile() {
        this.builder = new ClassBuilder("", "Queue", "T")
                .appendImplementation(new InterfaceBuilder("Collection"));
        appendFields();
        appendMethods();
    }

    @Override
    public IJavaFileBuilder getBuilder() {
        return builder;
    }

    private void appendFields() {
        builder.appendField(new Attribute("LinkedList<T>", "p"));
    }

    private void appendMethods() {
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
