package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;

public class UndefinedTypeDescriptor extends TypeDescriptor {
    @Override
    public String getTypeName() {
        throw new TypeDescriptorException("You tried to get the type name of an undefined type descriptor");
    }

    @Override
    public String getJavaName() {
        throw new TypeDescriptorException("You tried to get the Java name of an undefined type descriptor");
    }
}
