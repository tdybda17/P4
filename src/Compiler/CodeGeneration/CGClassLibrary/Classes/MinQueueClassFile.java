package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Constructor;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

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
                                "        else if (p0 instanceof Integer)\n" +
                                "            ((List<Integer>) p).sort(new IntegerMinComparator());\n" +
                                "        else if (p0 instanceof Double)\n" +
                                "            ((List<Double>) p).sort(new RealMinComparator());\n" +
                                "        else if (p0 instanceof String)\n" +
                                "            ((List<String>) p).sort(new LabelMinComparator());\n" +
                                "        else if (p0 instanceof Color)\n" +
                                "            ((List<Color>) p).sort(new ColorMinComparator());\n" +
                                "        else if (p0 instanceof Boolean)\n" +
                                "            ((List<Boolean>) p).sort(new BooleanMinComparator());\n" +
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
                                "        else if (t instanceof Integer)\n" +
                                "            ((List<Integer>) p).sort(new IntegerMinComparator());\n" +
                                "        else if (t instanceof Double)\n" +
                                "            ((List<Double>) p).sort(new RealMinComparator());\n" +
                                "        else if (t instanceof String)\n" +
                                "            ((List<String>) p).sort(new LabelMinComparator());\n" +
                                "        else if (t instanceof Color)\n" +
                                "            ((List<Color>) p).sort(new ColorMinComparator());\n" +
                                "        else if (t instanceof Boolean)\n" +
                                "            ((List<Boolean>) p).sort(new BooleanMinComparator());\n" +
                                "        else\n" +
                                "            throw new IllegalArgumentException(\"Cannot extract from MinQueue with element type \" + t);\n" +
                                "        return t;"))
                .appendMethod(new Method("boolean", "decreaseKey")
                        .asGeneric()
                        .appendAttribute(new Attribute("T", "p0"))
                        .appendAttribute(new Attribute("E", "p1"))
                        .appendBody("if (p0 instanceof Edge) {\n" +
                                "            ((Edge) p0).weight -= (Double) p1;\n" +
                                "            ((List<Edge>) p).sort(new EdgeMinComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Vertex) {\n" +
                                "            ((Vertex) p0).distance -= (Double) p1;\n" +
                                "            ((List<Vertex>) p).sort(new VertexMinComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Integer) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) (Integer)((Integer)p0 - (Integer)p1));\n" +
                                "            ((List<Integer>) p).sort(new IntegerMinComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Double) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) (Double)((Double)p0 - (Double)p1));\n" +
                                "            ((List<Double>) p).sort(new RealMinComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof String) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) p1);\n" +
                                "            ((List<String>) p).sort(new LabelMinComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Color) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) p1);\n" +
                                "            ((List<Color>) p).sort(new ColorMinComparator());\n" +
                                "            return true;\n" +
                                "        }\n" +
                                "        else if (p0 instanceof Boolean) {\n" +
                                "            if (p.remove(p0))\n" +
                                "                p.add((T) p1);\n" +
                                "            ((List<Boolean>) p).sort(new BooleanMinComparator());\n" +
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
