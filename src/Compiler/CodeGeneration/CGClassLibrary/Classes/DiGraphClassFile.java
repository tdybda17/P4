package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.*;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;

import java.util.List;

public class DiGraphClassFile implements ClassFile {

    private ClassBuilder builder;

    public DiGraphClassFile() {
        this.builder = new ClassBuilder("", "DiGraph")
                .appendField(new Attribute("private", "int", "printIndex").withValue("0"))
                .appendField(new Attribute("Set<Vertex>", "vertices"))
                .appendField(new Attribute("Set<DirectedEdge>", "edges"));

        appendMethods();
    }

    @Override
    public IJavaFileBuilder getBuilder() {
        return builder;
    }

    private void appendMethods() {
        builder.appendConstructor(new Constructor(List.of(), "vertices = new HashSet<>();edges = new HashSet<>();"))
                .appendMethod(new Method("boolean", "addEdge")
                        .appendAttribute(new Attribute("Vertex", "p0"))
                        .appendAttribute(new Attribute("Vertex", "p1"))
                        .appendBody("vertices.add(p0);vertices.add(p1);return edges.add(new DirectedEdge(p0, p1));"))
                .appendMethod(new Method("boolean", "removeEdge")
                        .appendAttribute(new Attribute("DirectedEdge", "edge"))
                        .appendBody("return edges.remove(edge);"))
                .appendMethod(new Method("DirectedEdge", "getEdge")
                        .appendAttribute(new Attribute("Vertex", "v1"))
                        .appendAttribute(new Attribute("Vertex", "v2"))
                        .appendBody("for (DirectedEdge edge : edges) {\n" +
                                "            if ((edge.source.equals(v1) && edge.target.equals(v2)) || (edge.target.equals(v1) && edge.source.equals(v2)))\n" +
                                "                return edge;\n" +
                                "        }\n" +
                                "        throw new RuntimeException(\"Tried to get nonexistent edge between two vertices, '\" + v1.label + \"' and '\" + v2.label + \"'\");"))
                .appendMethod(new Method("Vertex", "getVertex")
                        .appendAttribute(new Attribute("String", "label"))
                        .appendBody("for (Vertex vertex : vertices) {if (vertex.label.equals(label)) return vertex; }\n" +
                                "throw new RuntimeException(\"Tried to get nonexistent vertex from label '\" + label + \"'\");"))
                .appendMethod(new Method("boolean", "addVertex")
                        .appendAttribute(new Attribute("Vertex", "v"))
                        .appendBody("return vertices.add(v);"))
                .appendMethod(new Method("boolean", "removeVertex")
                        .appendAttribute(new Attribute("Vertex", "v"))
                        .appendBody("return vertices.remove(v);"))
                .appendMethod(new Method("Set<DirectedEdge>", "getOutgoingEdges")
                        .appendAttribute(new Attribute("Vertex", "v"))
                        .appendBody("Set<DirectedEdge> outgoingEdges = new HashSet<>();\n" +
                                "        for (DirectedEdge edge : edges) {\n" +
                                "            if (edge.source.equals(v))\n" +
                                "                outgoingEdges.add(edge);\n" +
                                "        }\n" +
                                "        return outgoingEdges;"))
                .appendMethod(new Method("Set<Vertex>", "getNeighbours")
                        .appendAttribute(new Attribute("Vertex", "v"))
                        .appendBody("Set<Vertex> neighbours = new HashSet<>();\n" +
                                "        for (DirectedEdge edge : edges) {\n" +
                                "            if (edge.source.equals(v))\n" +
                                "                neighbours.add(edge.target);\n" +
                                "        }\n" +
                                "        return neighbours;"))
                .appendMethod(new Method("void", "print")
                        .appendBody("StringBuilder sb = new StringBuilder();\n" +
                                "        sb.append(\"digraph {\\n\");\n" +
                                "        for (Vertex vertex : vertices)\n" +
                                "            sb.append(vertex.label).append(\" [style=\\\"filled\\\", fillcolor=\").append(vertex.color.name()).append(\"]\\n\");\n" +
                                "\n" +
                                "        for (DirectedEdge edge : edges) {\n" +
                                "            sb.append(edge.source.label).append(\" -> \").append(edge.target.label);\n" +
                                "            sb.append(\" [color=\\\"\").append(edge.color.name()).append(\"\\\", label=\\\"\").append(edge.weight).append(\"\\\"]\\n\");\n" +
                                "        }\n" +
                                "        sb.append(\"}\\n\");\n" +
                                "        DotFileGenerator.createDotFile(sb.toString(), printIndex);\n" +
                                "        printIndex++;"));

    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
