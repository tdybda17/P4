package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

public class IntegerTypeDescriptor implements TypeDescriptor {
    private String typeName;

    public IntegerTypeDescriptor() {
        typeName = "Integer";
    }

    @Override
    public String getTypeName() {
        return typeName;
    }
}
