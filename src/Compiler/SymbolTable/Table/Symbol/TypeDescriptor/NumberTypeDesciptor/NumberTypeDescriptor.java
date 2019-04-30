package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor;


import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

public abstract class NumberTypeDescriptor extends TypeDescriptor {
    @Override
    public String getTypeName() {
        return "Number";
    }
}
