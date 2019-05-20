package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

public class EdgeMinComparatorClassFile implements ClassFile {

    @Override
    public IJavaFileBuilder getBuilder() {
        ClassBuilder builder = new ClassBuilder("", "EdgeMinComparator")
                .appendImplementation(new InterfaceBuilder("Comparator<Edge>"));
        appendMethods(builder);

        return builder;
    }

    private void appendMethods(ClassBuilder builder) {
        builder.appendMethod(new Method("int", "compare").asPublic()
                    .appendAttribute(new Attribute("Edge", "o1"))
                    .appendAttribute(new Attribute("Edge", "o2"))
                    .appendBody("double diff = o1.weight - o2.weight;\n" +
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
