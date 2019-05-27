package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.*;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;

import java.util.List;

public class MaxQueueClassFile implements ClassFile {

    private ClassBuilder builder;

    public MaxQueueClassFile() {
        this.builder = new ClassBuilder("", "MaxQueue", "T")
                .appendImplementation(new InterfaceBuilder("Collection"))
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
                                "            ((List<Vertex>) p).sort(new VertexMaxComparator());\n" +
                                "        else if (p0 instanceof Edge)\n" +
                                "            ((List<Edge>) p).sort(new EdgeMaxComparator());\n" +
                                "        else if (p0 instanceof Integer)\n" +
                                "            ((List<Integer>) p).sort(new IntegerMaxComparator());\n" +
                                "        else if (p0 instanceof Double)\n" +
                                "            ((List<Double>) p).sort(new RealMaxComparator());\n" +
                                "        else if (p0 instanceof String)\n" +
                                "            ((List<String>) p).sort(new LabelMaxComparator());\n" +
                                "        else if (p0 instanceof Color)\n" +
                                "            ((List<Color>) p).sort(new ColorMaxComparator());\n" +
                                "        else if (p0 instanceof Boolean)\n" +
                                "            ((List<Boolean>) p).sort(new BooleanMaxComparator());\n" +
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
                                "        else if (t instanceof Integer)\n" +
                                "            ((List<Integer>) p).sort(new IntegerMaxComparator());\n" +
                                "        else if (t instanceof Double)\n" +
                                "            ((List<Double>) p).sort(new RealMaxComparator());\n" +
                                "        else if (t instanceof String)\n" +
                                "            ((List<String>) p).sort(new LabelMaxComparator());\n" +
                                "        else if (t instanceof Color)\n" +
                                "            ((List<Color>) p).sort(new ColorMaxComparator());\n" +
                                "        else if (t instanceof Boolean)\n" +
                                "            ((List<Boolean>) p).sort(new BooleanMaxComparator());\n" +
                                "        else\n" +
                                "            throw new IllegalArgumentException(\"Cannot extract from MaxQueue with element type \" + t);\n" +
                                "        return t;"))
                .appendMethod(new Method("boolean", "increaseKey")
                        .asGeneric()
                        .appendAttribute(new Attribute("T", "p0"))
                        .appendAttribute(new Attribute("E", "p1"))
                        .appendBody("if (p0 instanceof Edge) {\n" +
                                "            ((Edge) p0).weight += (Double) p1;\n" +
                                "            ((List<Edge>) p).sort(new EdgeMaxComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Vertex) {\n" +
                                "            ((Vertex) p0).distance += (Double) p1;\n" +
                                "            ((List<Vertex>) p).sort(new VertexMaxComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Integer) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) (Integer)((Integer)p0 + (Integer)p1));\n" +
                                "            ((List<Integer>) p).sort(new IntegerMaxComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Double) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) (Double)((Double)p0 + (Double)p1));\n" +
                                "            ((List<Double>) p).sort(new RealMaxComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof String) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) p1);\n" +
                                "            ((List<String>) p).sort(new LabelMaxComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Color) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) p1);\n" +
                                "            ((List<Color>) p).sort(new ColorMaxComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Boolean) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) p1);\n" +
                                "            ((List<Boolean>) p).sort(new BooleanMaxComparator());\n" +
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
