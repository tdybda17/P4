package Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder;

import Compiler.CodeGeneration.JavaFileBuilder.JavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Method;

import java.util.ArrayList;

public class InterfaceBuilder extends JavaFileBuilder {

    public InterfaceBuilder(String identifier) {
        super("", identifier, new ArrayList<>());
    }

    public InterfaceBuilder(String accessModifier, String identifier) {
        super(accessModifier, identifier, new ArrayList<>());
    }

    public InterfaceBuilder(String accessModifier, String identifier, String ... genericVariables) {
        super(accessModifier, identifier, genericVariables);
    }

    public InterfaceBuilder appendMethod(Method method) {
        super.appendMethod(method);
        return this;
    }

    @Override
    public String getFileContent() {
        return super.createAccessModifier() +
                "interface " +
                super.createIdentifier() +
                super.createGenericVariables() +
                " {" + super.createMethods() + "}";
    }

}
