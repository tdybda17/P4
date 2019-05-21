package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

public class VertexClassFile implements ClassFile {

    @Override
    public IJavaFileBuilder getBuilder() {
        return new ClassBuilder("", "Vertex")
                .appendField(new Attribute("Color", "color").withValue("Color.WHITE"))
                .appendField(new Attribute("String", "label").withValue("\"\""))
                .appendField(new Attribute("double", "distance"));
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
