package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapTypeDescriptor extends CollectionTypeDescriptors {
    private TypeDescriptor keyType;
    private TypeDescriptor elementType;

    MapTypeDescriptor(TypeDescriptor keyType, TypeDescriptor elementType) {
        super();
        this.keyType = keyType;
        this.elementType = elementType;
        this.addMethods();
    }

    private void addMethods(){
    }

    private Method containsKey(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(keyType);
        return new Method("containsKey", new BooleanTypeDescriptor(), parameters);
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
        return Objects.equals(keyType, that.keyType) && Objects.equals(elementType, that.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), keyType, elementType);
    }
}
