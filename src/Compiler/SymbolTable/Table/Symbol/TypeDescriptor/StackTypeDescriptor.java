package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

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
}
