package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import java.util.Objects;

public class IntegerTypeDescriptor implements TypeDescriptor {
    private String typeName;

    public IntegerTypeDescriptor() {
        typeName = "Integer";
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerTypeDescriptor that = (IntegerTypeDescriptor) o;
        return Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }
}
