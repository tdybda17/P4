package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import Compiler.Exceptions.NotImplementedException;

public abstract class TypeDescriptor {
    public abstract String getTypeName();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeDescriptor typeDescriptor = (TypeDescriptor) o;
        return this.getTypeName().equals(typeDescriptor.getTypeName());
    }
}
