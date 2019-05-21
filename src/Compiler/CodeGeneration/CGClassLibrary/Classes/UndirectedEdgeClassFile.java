package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Constructor;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

import java.util.List;

public class UndirectedEdgeClassFile implements ClassFile {

    @Override
    public IJavaFileBuilder getBuilder() {
        var builder = new ClassBuilder("", "UndirectedEdge")
                .appendExtendClass("Edge")
                .appendField(new Attribute("Set<Vertex>", "vertices"));
        appendMethods(builder);

        return builder;
    }

    private void appendMethods(ClassBuilder builder) {
        builder.appendConstructor(new Constructor(List.of(
                new Attribute("Vertex", "v1"),
                new Attribute("Vertex", "v2")
        ), "vertices = new HashSet<>();vertices.add(v1);vertices.add(v2);"));
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
