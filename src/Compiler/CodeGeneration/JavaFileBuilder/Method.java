package Compiler.CodeGeneration.JavaFileBuilder;

import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.Attributes.FunctionAttributes;

import java.util.List;

public class Method {

    private String identifier;
    private List<Attribute> attributes;
    private StringBuilder body;

    public Method(String identifier) {
        this.body = new StringBuilder();
        this.identifier = identifier;
    }

    public Method withAttribute(Attribute attribute) {
        attributes.add(attribute);
        return this;
    }

    public Method withBody(String body) {
        this.body.append(body);
        return this;
    }

    public String createMethod() {

        return "";
    }



}
