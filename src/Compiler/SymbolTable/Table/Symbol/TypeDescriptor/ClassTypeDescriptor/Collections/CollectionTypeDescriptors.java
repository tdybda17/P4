package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;

import java.util.ArrayList;
import java.util.List;

abstract class CollectionTypeDescriptors extends ClassTypeDescriptor {
    List<Method> methods;
    List<Field> fields;

    CollectionTypeDescriptors() {
        this.methods = new ArrayList<>();
        this.fields = new ArrayList<>();
        methods.add(isEmpty());
    }

    private Method isEmpty(){
        return new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
    }
}
