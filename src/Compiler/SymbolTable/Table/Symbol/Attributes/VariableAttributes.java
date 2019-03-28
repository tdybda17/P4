package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.Objects;

public class VariableAttributes implements Attributes {
    private TypeDescriptor type;

    public VariableAttributes(TypeDescriptor type) {
        this.type = type;
    }

    @Override
    public String getAttributeKind() {
        return "Variable";
    }

    public TypeDescriptor getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableAttributes that = (VariableAttributes) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
