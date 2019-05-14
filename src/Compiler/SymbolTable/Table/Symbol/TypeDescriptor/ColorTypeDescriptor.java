package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

public class ColorTypeDescriptor extends TypeDescriptor {
    @Override
    public String getTypeName() {
        return "Color";
    }

    @Override
    public String getJavaName() {
        return getTypeName();
    }
}
