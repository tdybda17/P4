package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;

public abstract class CollectionTypeDescriptor extends ClassTypeDescriptor {
    public CollectionTypeDescriptor() {
        super();
        this.addMethod(isEmpty());
    }

    public abstract void setElementType(TypeDescriptor elementType);

    private Method isEmpty(){
        return new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
    }
}
