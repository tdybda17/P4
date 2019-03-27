package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract class CollectionTypeDescriptors extends ClassTypeDescriptor {
    Set<Method> methods;
    Set<Field> fields;

    CollectionTypeDescriptors() {
        this.methods = new HashSet<>();
        this.fields = new HashSet<>();
        methods.add(isEmpty());
    }

    private Method isEmpty(){
        return new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
    }
}
