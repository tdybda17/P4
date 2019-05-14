package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueueTypeDescriptor extends CollectionTypeDescriptor {
    public QueueTypeDescriptor(TypeDescriptor elementType) {
        super(elementType);
        this.addMethods();
    }

    private void addMethods(){
        this.addMethod(enqueue());
        this.addMethod(dequeue());
    }

    @Override
    public void setElementType(TypeDescriptor elementType) {
        super.setElementType(elementType);
        this.addMethods();
    }

    @Override
    public String getTypeName() {
        return "Queue";
    }

    private Method enqueue(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());

        return new Method("enqueue", new BooleanTypeDescriptor(), parameters);
    }

    private Method dequeue(){
        return new Method("dequeue", getElementType(), new ArrayList<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QueueTypeDescriptor that = (QueueTypeDescriptor) o;
        return Objects.equals(getElementType(), that.getElementType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getElementType());
    }

    @Override
    public String getJavaName() {
        return "Queue<" + getElementType().getJavaName() + ">";
    }
}
