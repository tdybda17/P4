package Compiler.CodeGeneration.JavaFileBuilder;

public class Attribute {

    private String accessModifier;
    private String type;
    private String name;
    private String value;

    // With this constructor it is possible to use the Attribute class as
    // parameter attribute to functions
    public Attribute(String type, String name) {
        this.accessModifier = "";
        this.type = type;
        this.name = name;
    }

    // With this constructor it is possible to use the Attribute class as
    // fields for classes
    public Attribute(String accessModifier, String type, String name) {
        this.accessModifier = accessModifier;
        this.type = type;
        this.name = name;
    }

    public Attribute withValue(String value) {
        this.value = value;
        return this;
    }

    public String getAttribute() {
        return (this.accessModifier + " " + this.type + " " + this.name + " " + createValue()).trim();
    }

    private String createValue() {
        if(value != null && !value.isEmpty()) {
            return "= " + value;
        } else {
            return "";
        }
    }

}
