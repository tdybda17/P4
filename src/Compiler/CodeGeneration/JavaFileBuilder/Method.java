package Compiler.CodeGeneration.JavaFileBuilder;

import java.util.ArrayList;
import java.util.List;

public class Method {

    private String accessModifier;
    private String returnType;
    private String identifier;
    private List<Attribute> attributes;
    private StringBuilder body;
    private boolean isStatic;
    private boolean isFinal;

    public Method(String returnType, String identifier) {
        this.returnType = returnType;
        this.body = new StringBuilder();
        this.identifier = identifier;
        this.attributes = new ArrayList<>();
        isStatic = false;
        isFinal = false;
    }

    public Method appendAttribute(Attribute attribute) {
        attributes.add(attribute);
        return this;
    }

    public Method appendBody(String body) {
        this.body.append(body);
        return this;
    }

    public Method asStatic() {
        isStatic = true;
        return this;
    }

    public Method asFinal() {
        isFinal = true;
        return this;
    }

    public Method asPublic() {
        this.accessModifier = "public";
        return this;
    }

    public Method asProtected() {
        this.accessModifier = "protected";
        return this;
    }

    public Method asPrivate() {
        this.accessModifier = "private";
        return this;
    }

    public String createMethod() {
        return createAccessModifier() +  createStatic() + returnType + " " + identifier + createAttributes() + createBody();
    }

    private String createAccessModifier() {
        if(accessModifier == null) return "";
        else return accessModifier + " ";
    }

    private String createStatic() {
        if(isStatic) return "static ";
        else if(isFinal) return "final ";
        else return "";
    }

    protected String createAttributes() {
        StringBuilder sb = new StringBuilder("(");
        attributes.forEach(attr -> {
            sb.append(attr.getAttribute());
            sb.append(", ");
        });
        return attributes.isEmpty() ?
                sb.append(")").toString() :
                deleteLast(sb, 2).append(")").toString();
    }

    private StringBuilder deleteLast(StringBuilder sb, int characters) {
        return sb.delete(sb.length() - characters, sb.length());
    }

    private String createBody() {
        if(body.length() > 0) {
            body.insert(0, "{");
            body.append("}");
            return body.toString();
        } else {
            return ";";
        }
    }

}
