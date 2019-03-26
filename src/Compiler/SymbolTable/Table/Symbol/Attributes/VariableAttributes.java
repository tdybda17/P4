package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public class VariableAttributes implements Attributes {
    private TypeDescriptor type;
    private String kind;

    public VariableAttributes(TypeDescriptor type) {
        this.type = type;
        this.kind = "Variable";
    }

    @Override
    public String getAttributeKind() {
        return this.kind;
    }

    public TypeDescriptor getType() {
        return this.type;
    }
}
