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
    public String getKind() {
        return this.kind;
    }

    @Override
    public TypeDescriptor getType() {
        return this.type;
    }
}
