package Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder;

import Compiler.CodeGeneration.JavaFileBuilder.IJavaFileBuilder;

import java.util.List;

public class InterfaceBuilder implements IJavaFileBuilder {

    private String accessModifier;
    private String identifier;

    public InterfaceBuilder(String accessModifier, String identifier) {
        this.accessModifier = accessModifier;
        this.identifier = identifier;
    }



    @Override
    public String getFile() {
        return null;
    }
}
