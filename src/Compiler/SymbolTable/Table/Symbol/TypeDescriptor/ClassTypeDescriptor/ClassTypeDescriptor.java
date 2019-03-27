package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.Set;

public abstract class ClassTypeDescriptor extends TypeDescriptor {
    public abstract Set<Method> getMethods();
    public abstract Set<Field> getFields();
}
