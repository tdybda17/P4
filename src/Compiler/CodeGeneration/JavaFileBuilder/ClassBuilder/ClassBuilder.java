package Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder;

import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.JavaFileBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassBuilder extends JavaFileBuilder {

    private List<Attribute> fields;
    private String extendClass;
    private Map<InterfaceBuilder, String> implementations;

    public ClassBuilder(String accessModifier, String identifier) {
        super(accessModifier, identifier, new ArrayList<>());
        this.fields = new ArrayList<>();
    }

    public ClassBuilder(String accessModifier, String identifier, String ... genericVariables) {
        super(accessModifier, identifier, genericVariables);
        implementations = new HashMap<>();
        this.fields = new ArrayList<>();
    }

    public ClassBuilder appendImplementation(InterfaceBuilder iFace, String body) {
        this.implementations.put(iFace, body);
        return this;
    }

    public ClassBuilder appendField(Attribute attribute) {
        this.fields.add(attribute);
        return this;
    }

    public ClassBuilder appendExtendClass(String className) {
        if(extendClass != null) throw new UnsupportedOperationException("A class can only extend a single class");
        this.extendClass = className;
        return this;
    }

    @Override
    public String getFileContent() {
        return super.createAccessModifier() +
                "class " +
                super.createIdentifier() +
                super.createGenericVariables() +
                createExtends() +
                createImplementations() +
                " {" +
                createFields() +
                super.createMethods() +
                createImplementMethods() +
                "}";
    }

    private String createExtends() {
        if(extendClass == null || extendClass.isEmpty()) {
            return "";
        }
        return " extends " + extendClass + " ";
    }

    private String createImplementations() {
        if(implementations != null && !implementations.isEmpty()) {
            StringBuilder sb = new StringBuilder(" implements ");
            implementations.keySet().forEach(iFace -> {
                sb.append(iFace.getIdentifier());
                sb.append(", ");
            });
            if(implementations.size() > 1) {
                return sb.toString().substring(0, sb.length() - 2);
            } else {
                return sb.toString();
            }
        } else {
            return "";
        }
    }

    private String createFields() {
        StringBuilder sb = new StringBuilder();
        fields.forEach(field -> {
            sb.append(field.getAttribute());
            sb.append(";");
        });
        return sb.toString();
    }

    private String createImplementMethods() {
        return "";
    }
}
