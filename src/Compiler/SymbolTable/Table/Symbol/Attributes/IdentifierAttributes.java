package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public class IdentifierAttributes implements Attributes {
    private TypeDescriptor type;

    public IdentifierAttributes(TypeDescriptor type) {
        this.type = type;
    }

    public TypeDescriptor getType() {
        return type;
    }
}
