package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class VertexMinComparatorClassFile implements ClassFile {

    private ClassBuilder builder;

    public VertexMinComparatorClassFile() {
        this.builder = new ClassBuilder("", "VertexMinComparator")
                .appendImplementation(new InterfaceBuilder("Comparator<Vertex>"));
        appendMethods();
    }

    @Override
    public IJavaFileBuilder getBuilder() {
        return builder;
    }

    private void appendMethods() {
        builder.appendMethod(new Method("int", "compare").asPublic()
                    .appendAttribute(new Attribute("Vertex", "o1"))
                    .appendAttribute(new Attribute("Vertex", "o2"))
                    .appendBody("double diff = o1.distance - o2.distance;\n" +
                            "        if (diff > 0)\n" +
                            "            return 1;\n" +
                            "        else if (diff < 0)\n" +
                            "            return -1;\n" +
                            "        else\n" +
                            "            return 0;"));
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
