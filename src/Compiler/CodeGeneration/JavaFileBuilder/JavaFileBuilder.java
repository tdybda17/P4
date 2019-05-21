package Compiler.CodeGeneration.JavaFileBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Abstract class for java file building, I.E classes and interfaces.
  * This class implements general methods for class and interface building
  * such as access modifier placement, identifier, generic variables and
  * implementation such as methods
  * */

public abstract class JavaFileBuilder implements IJavaFileBuilder {

    private String accessModifier;
    private String identifier;
    private List<String> genericVariables;
    private List<Method> methods;

    public JavaFileBuilder(String accessModifier, String identifier, List<String> genericVariables) {
        this.accessModifier = accessModifier;
        this.identifier = identifier;
        this.genericVariables = genericVariables;
        this.methods = new ArrayList<>();
    }

    public JavaFileBuilder(String accessModifier, String identifier, String... genericVariables) {
        this.accessModifier = accessModifier;
        this.identifier = identifier;
        this.genericVariables = new ArrayList<>();
        this.methods = new ArrayList<>();
        Collections.addAll(this.genericVariables, genericVariables);
    }

    public JavaFileBuilder appendMethod(Method method) {
        this.methods.add(method);
        return this;
    }

    protected String createAccessModifier() {
        return (accessModifier == null || accessModifier.isEmpty()) ? "" : accessModifier + " ";
    }

    protected String createIdentifier() {
        return identifier;
    }

    protected String createGenericVariables() {
        if (genericVariables == null || genericVariables.isEmpty()) {
            return "";
        } else {
            String variables = "<";
            variables += StringOperator.explode(genericVariables, ", ");
            variables += ">";
            return variables;
        }
    }

    protected String createMethods() {
        StringBuilder sb = new StringBuilder();
        methods.forEach(m -> {
            sb.append(m.createMethod());
        });
        return sb.toString();
    }

    @Override
    public abstract String getFileContent();

    public String getAccessModifier() {
        return accessModifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<String> getGenericVariables() {
        return genericVariables;
    }

    public List<Method> getMethods() {
        return methods;
    }
}
