package Compiler.CodeGeneration.JavaFileBuilder;

public class Attribute {

    private String type;
    private String name;

    public Attribute(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getAttribute() {
        return this.type + " " + this.name;
    }

}
