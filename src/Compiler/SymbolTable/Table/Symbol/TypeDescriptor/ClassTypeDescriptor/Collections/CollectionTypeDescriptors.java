package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;

import java.util.ArrayList;

public abstract class CollectionTypeDescriptors extends ClassTypeDescriptor {
    public CollectionTypeDescriptors() {
        super();
        this.addMethod(isEmpty());
    }

    private Method isEmpty(){
        return new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
    }
}
