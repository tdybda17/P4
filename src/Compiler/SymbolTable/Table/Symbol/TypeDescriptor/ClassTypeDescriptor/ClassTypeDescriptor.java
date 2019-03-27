package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.List;

public abstract class ClassTypeDescriptor extends TypeDescriptor {
    public abstract List<Method> getMethods();
    public abstract List<Field> getFields();
}
