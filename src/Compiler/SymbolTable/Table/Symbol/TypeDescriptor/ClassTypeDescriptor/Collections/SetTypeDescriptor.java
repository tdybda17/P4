package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetTypeDescriptor extends CollectionTypeDescriptors {
    private TypeDescriptor elementType;

    public SetTypeDescriptor(TypeDescriptor elementType) {
        super();
        this.elementType = elementType;
        this.addMethods();
    }

    private void addMethods(){

    }

    private Method addMethod(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        return new Method("add", new BooleanTypeDescriptor(), parameters);
    }

    private Method removeMethod(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        return new Method("remove", new BooleanTypeDescriptor(), parameters);
    }

    private Method containsMethod(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        return new Method("contains", new BooleanTypeDescriptor(), parameters);
    }

    @Override
    public String getTypeName() {
        return "Set";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SetTypeDescriptor that = (SetTypeDescriptor) o;
        return elementType.equals(that.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elementType);
    }
}
