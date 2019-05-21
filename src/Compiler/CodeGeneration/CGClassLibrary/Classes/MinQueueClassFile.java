package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.*;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;

import java.util.List;

public class MinQueueClassFile implements ClassFile {

    private ClassBuilder builder;

    public MinQueueClassFile() {
        this.builder = new ClassBuilder("", "MinQueue", "T")
                .appendImplementation(new InterfaceBuilder("Collection<T>"))
                .appendField(new Attribute("List<T>", "p"));
        appendMethods();
    }

    @Override
    public IJavaFileBuilder getBuilder() {
        return builder;
    }

    private void appendMethods() {
        builder.appendConstructor(new Constructor(List.of(), "p = new ArrayList<>();"))
                .appendMethod(new Method("boolean", "insert")
                    .appendAttribute(new Attribute("T", "p0"))
                    .appendBody("p.add(p0);\n" +
                            "        if (p0 instanceof Vertex)\n" +
                            "            ((List<Vertex>) p).sort(new VertexMinComparator());\n" +
                            "        else if (p0 instanceof Edge)\n" +
                            "            ((List<Edge>) p).sort(new EdgeMinComparator());\n" +
                            "        else\n" +
                            "            return false;\n" +
                            "        return true;"))
                .appendMethod(new Method("T", "minimum")
                    .appendBody("return p.get(0);"))
                .appendMethod(new Method("T", "extractMin")
                    .appendBody("T t =  p.remove(0);\n" +
                            "        if (t instanceof Vertex)\n" +
                            "            ((List<Vertex>) p).sort(new VertexMinComparator());\n" +
                            "        else if (t instanceof Edge)\n" +
                            "            ((List<Edge>) p).sort(new EdgeMinComparator());\n" +
                            "        else\n" +
                            "            throw new IllegalArgumentException(\"Cannot extract from MinQueue with element type \" + t);\n" +
                            "        return t;"))
                .appendMethod(new Method("boolean", "decreaseKey")
                    .appendAttribute(new Attribute("T", "p0"))
                    .appendAttribute(new Attribute("double", "p1"))
                    .appendBody("if (p0 instanceof Edge) {\n" +
                            "            ((Edge) p0).weight -= p1;\n" +
                            "            ((List<Edge>) p).sort(new EdgeMinComparator());\n" +
                            "            return true;\n" +
                            "        }\n" +
                            "        else if (p0 instanceof Vertex) {\n" +
                            "            ((Vertex) p0).distance -= p1;\n" +
                            "            ((List<Vertex>) p).sort(new VertexMinComparator());\n" +
                            "            return true;\n" +
                            "        }\n" +
                            "        return false;"))
                .appendMethod(new Method("boolean", "isEmpty").asPublic()
                    .appendBody("return p.isEmpty();")
                );
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
