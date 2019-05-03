package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.ClassTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;

public abstract class CollectionTypeDescriptor extends ClassTypeDescriptor {
    private TypeDescriptor elementType;

    public CollectionTypeDescriptor(TypeDescriptor elementType) {
        super();
        this.elementType = elementType;
        this.addMethods();
    }

    private void addMethods(){
        this.addMethod(isEmpty());
    }

    public void setElementType(TypeDescriptor elementType) {
        this.elementType = elementType;
        this.resetMethods();
        this.addMethods();
    }

    public TypeDescriptor getElementType() {
        return elementType;
    }

    private Method isEmpty(){
        return new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getTypeName()).append('<').append(getElementType().toString()).append('>');
        return stringBuilder.toString();
    }
}
