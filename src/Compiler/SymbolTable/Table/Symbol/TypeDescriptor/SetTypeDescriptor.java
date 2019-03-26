package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

public class SetTypeDescriptor implements TypeDescriptor {
    private String typeName;
    private TypeDescriptor elementType;


    public SetTypeDescriptor(TypeDescriptor elementType) {
        this.typeName = "Set";
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
