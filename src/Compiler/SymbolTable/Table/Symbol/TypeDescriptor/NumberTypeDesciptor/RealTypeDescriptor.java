package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor;


public class RealTypeDescriptor extends NumberTypeDescriptor {
    @Override
    public String getTypeName() {
        return "Real";
    }

    @Override
    public String getJavaName() {
        return "double";
    }
}
