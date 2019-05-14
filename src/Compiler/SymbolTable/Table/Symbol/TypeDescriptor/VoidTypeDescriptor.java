package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public class VoidTypeDescriptor extends TypeDescriptor {
    @Override
    public String getTypeName() {
        return "void";
    }

    @Override
    public String getJavaName() {
        return "void";
    }
}
