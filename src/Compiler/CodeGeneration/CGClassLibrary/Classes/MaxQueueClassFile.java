package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.*;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;

import java.util.List;

public class MaxQueueClassFile implements ClassFile {

    @Override
    public IJavaFileBuilder getBuilder() {
        ClassBuilder builder = new ClassBuilder("", "MaxQueue", "T")
                .appendImplementation(new InterfaceBuilder("Collection"))
                .appendField(new Attribute("List<T>", "p"));
        appendMethods(builder);

        return builder;
    }

    private void appendMethods(ClassBuilder builder) {
        builder.appendConstructor(new Constructor(List.of(), "p = new ArrayList<>();"))
                .appendMethod(new Method("boolean", "insert")
                        .appendAttribute(new Attribute("T", "p0"))
                        .appendBody("p.add(p0);\n" +
                                "        if (p0 instanceof Vertex)\n" +
                                "            ((List<Vertex>) p).sort(new VertexMaxComparator());\n" +
                                "        else if (p0 instanceof Edge)\n" +
                                "            ((List<Edge>) p).sort(new EdgeMaxComparator());\n" +
                                "        else\n" +
                                "            return false;\n" +
                                "        return true;"))
                .appendMethod(new Method("T", "maximum")
                        .appendBody("return p.get(0);"))
                .appendMethod(new Method("T", "extractMax")
                        .appendBody("T t =  p.remove(0);\n" +
                                "        if (t instanceof Vertex)\n" +
                                "            ((List<Vertex>) p).sort(new VertexMaxComparator());\n" +
                                "        else if (t instanceof Edge)\n" +
                                "            ((List<Edge>) p).sort(new EdgeMaxComparator());\n" +
                                "        else\n" +
                                "            throw new IllegalArgumentException(\"Cannot extract from MaxQueue with element type \" + t);\n" +
                                "        return t;"))
                .appendMethod(new Method("boolean", "increaseKey")
                        .appendAttribute(new Attribute("T", "p0"))
                        .appendAttribute(new Attribute("double", "p1"))
                        .appendBody("if (p0 instanceof Edge) {\n" +
                                "            ((Edge) p0).weight += p1;\n" +
                                "            ((List<Edge>) p).sort(new EdgeMaxComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Vertex) {\n" +
                                "            ((Vertex) p0).distance += p1;\n" +
                                "            ((List<Vertex>) p).sort(new VertexMaxComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        return false;"))
                .appendMethod(new Method("boolean", "isEmpty").asPublic()
                        .appendBody("return p.isEmpty();"));
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
