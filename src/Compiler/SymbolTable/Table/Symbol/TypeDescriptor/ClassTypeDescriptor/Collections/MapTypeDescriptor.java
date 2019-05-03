package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapTypeDescriptor extends CollectionTypeDescriptor {
    private TypeDescriptor keyType;

    public MapTypeDescriptor(TypeDescriptor keyType, TypeDescriptor elementType) {
        super(elementType);
        this.keyType = keyType;
        this.addMethods();
    }

    private void addMethods(){
        this.addMethod(containsKey());
        this.addMethod(containsValue());
        this.addMethod(add());
        this.addMethod(remove());
        this.addMethod(get());
    }

    @Override
    public void setElementType(TypeDescriptor elementType) {
        super.setElementType(elementType);
        this.addMethods();
    }

    public TypeDescriptor getKeyType() {
        return keyType;
    }

    public void setKeyType(TypeDescriptor keyType) {
        this.keyType = keyType;
    }

    private Method containsKey(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(keyType);
        return new Method("containsKey", new BooleanTypeDescriptor(), parameters);
    }

    private Method containsValue(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(getElementType());
        return new Method("containsValue", new BooleanTypeDescriptor(), parameters);
    }

    private Method add() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(keyType);
        parameters.add(getElementType());
        return new Method("add", new BooleanTypeDescriptor(), parameters);
    }

    private Method remove() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(keyType);
        return new Method("remove", new BooleanTypeDescriptor(), parameters);
    }

    private Method get() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(keyType);
        return new Method("get", getElementType() , parameters);
    }


    @Override
    public String getTypeName() {
        return "Map";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MapTypeDescriptor that = (MapTypeDescriptor) o;
        return Objects.equals(keyType, that.keyType) && Objects.equals(getElementType(), that.getElementType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), keyType, getElementType());
    }

    @Override
    public String toString() {
        return "MapTypeDescriptor<" +
                keyType +
                ", " + getElementType() +
                '>';
    }
}
