package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetTypeDescriptor extends CollectionTypeDescriptor {
    public SetTypeDescriptor(TypeDescriptor elementType) {
        super(elementType);
        this.addMethods();
    }

    @Override
    public void setElementType(TypeDescriptor elementType) {
        super.setElementType(elementType);
        this.addMethods();
    }

    private void addMethods(){
        this.addMethod(add());
        this.addMethod(remove());
        this.addMethod(contains());
        this.addMethod(size());
    }

    private Method add(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());
        return new Method("add", new BooleanTypeDescriptor(), parameters);
    }

    private Method remove(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());
        return new Method("remove", new BooleanTypeDescriptor(), parameters);
    }

    private Method contains(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());
        return new Method("contains", new BooleanTypeDescriptor(), parameters);
    }

    private Method size() {
        return new Method("size", new IntegerTypeDescriptor(), new ArrayList<>());
    }

    @Override
    public String getTypeName() {
        return "Set";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; //Checking their names up against each other
        SetTypeDescriptor that = (SetTypeDescriptor) o;
        return getElementType().equals(that.getElementType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getElementType());
    }

    @Override
    public String toString() {
        return "Set<" + getElementType() + '>';
    }

    @Override
    public String getJavaName() {
        return "HashSet<" + getElementType().getJavaName() + ">";
    }
}
