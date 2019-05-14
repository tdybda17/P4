package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

public class LabelTypeDescriptor extends TypeDescriptor {
    @Override
    public String getTypeName() {
        return "Label";
    }

    @Override
    public String getJavaName() {
        return "String";
    }
}
