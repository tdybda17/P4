package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.*;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;

import java.util.List;

public class GraphClassFile implements ClassFile {

    @Override
    public IJavaFileBuilder getBuilder() {
        ClassBuilder builder = new ClassBuilder("", "Graph")
                .appendField(new Attribute("private", "int", "printIndex").withValue("0"))
                .appendField(new Attribute("Set<Vertex>", "vertices"))
                .appendField(new Attribute("Set<UndirectedEdge>", "edges"));

        appendMethod(builder);

        return builder;
    }

    private void appendMethod(ClassBuilder builder) {
        builder.appendConstructor(new Constructor(List.of(), "vertices = new HashSet<>();edges = new HashSet<>();"))
                .appendMethod(new Method("boolean", "addEdge")
                        .appendAttribute(new Attribute("Vertex", "p0"))
                        .appendAttribute(new Attribute("Vertex", "p1"))
                        .appendBody("vertices.add(p0);vertices.add(p1);return edges.add(new UndirectedEdge(p0, p1));"))
                .appendMethod(new Method("boolean", "removeEdge")
                        .appendAttribute(new Attribute("UndirectedEdge", "edge"))
                        .appendBody("return edges.remove(edge);"))
                .appendMethod(new Method("UndirectedEdge", "getEdge")
                        .appendAttribute(new Attribute("Vertex", "v1"))
                        .appendAttribute(new Attribute("Vertex", "v2"))
                        .appendBody("        for (UndirectedEdge edge : edges) {\n" +
                                "            if (edge.vertices.contains(v1) && edge.vertices.contains(v2) && !v1.equals(v2))\n" +
                                "                return edge;\n" +
                                "        }\n" +
                                "        throw new RuntimeException(\"Tried to get nonexistent edge between two vertices, '\" + v1.label + \"' and '\" + v2.label + \"'\");"))
                .appendMethod(new Method("Vertex", "getVertex")
                        .appendAttribute(new Attribute("String", "label"))
                        .appendBody("for (Vertex vertex : vertices) {\n" +
                                "            if (vertex.label.equals(label))\n" +
                                "                return vertex;\n" +
                                "        }\n" +
                                "        throw new RuntimeException(\"Tried to get nonexistent vertex from label '\" + label + \"'\");"))
                .appendMethod(new Method("boolean", "addVertex")
                        .appendAttribute(new Attribute("Vertex", "v"))
                        .appendBody("return vertices.add(v);"))
                .appendMethod(new Method("boolean", "removeVertex")
                        .appendAttribute(new Attribute("Vertex", "v"))
                        .appendBody("return vertices.remove(v);"))
                .appendMethod(new Method("Set<UndirectedEdge>", "getOutgoingEdges")
                        .appendAttribute(new Attribute("Vertex", "v"))
                        .appendBody("Set<UndirectedEdge> outgoingEdges = new HashSet<>();\n" +
                                "        for (UndirectedEdge edge : edges) {\n" +
                                "            if (edge.vertices.contains(v))\n" +
                                "                outgoingEdges.add(edge);\n" +
                                "        }\n" +
                                "        return outgoingEdges;"))
                .appendMethod(new Method("Set<Vertex>", "getNeighbours")
                        .appendAttribute(new Attribute("Vertex", "v"))
                        .appendBody("Set<Vertex> neighbours = new HashSet<>();\n" +
                                "        for (UndirectedEdge edge : edges) {\n" +
                                "            if (edge.vertices.contains(v)) {\n" +
                                "                for (Vertex vertex : edge.vertices) {\n" +
                                "                    if (!vertex.equals(v))\n" +
                                "                        neighbours.add(vertex);\n" +
                                "                }\n" +
                                "            }\n" +
                                "        }\n" +
                                "        return neighbours;"))
                .appendMethod(new Method("void", "print")
                        .appendBody("StringBuilder sb = new StringBuilder();\n" +
                                "        sb.append(\"graph {\\n\");\n" +
                                "        for (Vertex vertex : vertices)\n" +
                                "            sb.append(vertex.label).append(\" [style=\\\"filled\\\", fillcolor=\").append(vertex.color.name()).append(\"]\\n\");\n" +
                                "\n" +
                                "        for (UndirectedEdge edge : edges) {\n" +
                                "            Iterator<Vertex> vertexIterator = edge.vertices.iterator();\n" +
                                "            sb.append(vertexIterator.next().label).append(\" -- \").append(vertexIterator.next().label);\n" +
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
