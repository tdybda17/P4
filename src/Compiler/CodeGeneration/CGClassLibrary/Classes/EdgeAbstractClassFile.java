package Compiler.CodeGeneration.CGClassLibrary.Classes;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

public class EdgeAbstractClassFile implements ClassFile {

    @Override
    public IJavaFileBuilder getBuilder() {
        return new ClassBuilder("", "Edge").asAbstract()
                .appendField(new Attribute("double", "weight").withValue("1.0"))
                .appendField(new Attribute("Color", "color").withValue("Color.BLACK"));
    }

    @Override
    public String getClassName() {
        return ((ClassBuilder) getBuilder()).getIdentifier();
    }
}
