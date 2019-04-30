package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueueTypeDescriptor extends CollectionTypeDescriptor {
    private TypeDescriptor elementType;

    public QueueTypeDescriptor(TypeDescriptor elementType) {
        super();
        this.elementType = elementType;
        this.addMethods();
    }

    private void addMethods(){
        this.addMethod(enqueue());
        this.addMethod(dequeue());
    }

    @Override
    public void setElementType(TypeDescriptor elementType) {
        this.elementType = elementType;
    }

    @Override
    public TypeDescriptor getElementType() {
        return elementType;
    }

    @Override
    public String getTypeName() {
        return "Queue";
    }

    private Method enqueue(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);

        return new Method("enqueue", new BooleanTypeDescriptor(), parameters);
    }

    private Method dequeue(){
        return new Method("dequeue", elementType, new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QueueTypeDescriptor that = (QueueTypeDescriptor) o;
        return Objects.equals(elementType, that.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elementType);
    }
}
