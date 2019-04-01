package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
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
        this.addMethod(add());
        this.addMethod(remove());
        this.addMethod(contains());
    }

    private Method add(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        return new Method("add", new BooleanTypeDescriptor(), parameters);
    }

    private Method remove(){
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(elementType);
        return new Method("remove", new BooleanTypeDescriptor(), parameters);
    }

    private Method contains(){
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
        if (!super.equals(o)) return false; //Checking their names up against each other
        SetTypeDescriptor that = (SetTypeDescriptor) o;
        return elementType.equals(that.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), elementType);
    }
}
