package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Constructor;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

import java.util.List;

public class DirectedEdgeClassFile implements ClassFile {

    private ClassBuilder builder;

    public DirectedEdgeClassFile() {
        this.builder = new ClassBuilder("", "DirectedEdge")
                .appendExtendClass("Edge")
                .appendField(new Attribute("Vertex", "source"))
                .appendField(new Attribute("Vertex", "target"));
        appendMethods();
    }

    @Override
    public IJavaFileBuilder getBuilder() {
        return builder;
    }

    private void appendMethods() {
        builder.appendConstructor(
                new Constructor(List.of(
                    new Attribute("Vertex", "source"),
                    new Attribute("Vertex", "target")
                ), "this.source = source;this.target = target;"));
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
