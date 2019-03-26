package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.Objects;

public class VariableAttributes implements Attributes {
    private TypeDescriptor type;
    private String kind;

    public VariableAttributes(TypeDescriptor type) {
        this.kind = "Variable";
        this.type = type;
    }

    @Override
    public String getAttributeKind() {
        return this.kind;
    }

    public TypeDescriptor getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariableAttributes that = (VariableAttributes) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(kind, that.kind);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, kind);
    }
}
