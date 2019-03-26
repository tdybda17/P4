package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import java.util.Objects;

public class StackTypeDescriptor implements TypeDescriptor {
    private String typeName;
    private TypeDescriptor elementType;


    public StackTypeDescriptor(TypeDescriptor elementType) {
        this.typeName = "Stack";
        this.elementType = elementType;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public TypeDescriptor getElementType() {
        return elementType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StackTypeDescriptor that = (StackTypeDescriptor) o;
        return Objects.equals(typeName, that.typeName) &&
                Objects.equals(elementType, that.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, elementType);
    }
}
