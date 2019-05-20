package Compiler.CodeGeneration.JavaFileBuilder;

import java.util.List;

public class Constructor extends Method {

    private List<Attribute> arguments;
    private String body;

    public Constructor(List<Attribute> arguments, String body) {
        super("", "");
        this.body = body;
        for (Attribute attr : arguments) {
            super.appendAttribute(attr);
        }
    }

    public String getConstructor(String className) {
        return "public " +
                className +
                super.createAttributes() +
                " {" +
                body +
                "}";
    }

}
