package Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder;

import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.Constructor;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.JavaFileBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.StringOperator;

import java.util.ArrayList;
import java.util.List;

public class ClassBuilder extends JavaFileBuilder {
    private List<Attribute> fields;
    private String extendClass;
    private List<InterfaceBuilder> implementations;
    private List<Constructor> constructors;
    private boolean isAbstract;

    public ClassBuilder(String accessModifier, String identifier) {
        super(accessModifier, identifier, new ArrayList<>());
        implementations = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.constructors = new ArrayList<>();
        this.isAbstract = false;
    }

    public ClassBuilder(String accessModifier, String identifier, String ... genericVariables) {
        super(accessModifier, identifier, genericVariables);
        implementations = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.constructors = new ArrayList<>();
        this.isAbstract = false;
    }

    public ClassBuilder appendImplementation(InterfaceBuilder iFace) {
        this.implementations.add(iFace);
        return this;
    }

    public ClassBuilder appendField(Attribute attribute) {
        this.fields.add(attribute);
        return this;
    }

    public ClassBuilder appendConstructor(Constructor constructor) {
        this.constructors.add(constructor);
        return this;
    }

    public ClassBuilder appendExtendClass(String className) {
        if(extendClass != null) throw new UnsupportedOperationException("A class can only extend a single class");
        this.extendClass = className;
        return this;
    }

    public ClassBuilder asAbstract() {
        this.isAbstract = true;
        return this;
    }

    @Override
    public String getFileContent() {
        return super.createAccessModifier() +
                createClass() +
                super.createIdentifier() +
                super.createGenericVariables() +
                createExtends() +
                createImplementations() +
                " {" +
                createFields() +
                createConstructors() +
                super.createMethods() +
                "}";
    }

    private String createClass() {
        if(isAbstract) return "abstract class ";
        else return "class ";
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
            implementations.forEach(iFace -> {
                sb.append(iFace.getIdentifier());
                sb.append(", ");
            });
            if(implementations.size() > 0) {
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

    private String createConstructors() {
        List<String> strings = new ArrayList<>();
        constructors.forEach(c -> {
            strings.add(c.getConstructor(this.getIdentifier().trim()));
        });
        return StringOperator.explode(strings, ", ");
    }

}
