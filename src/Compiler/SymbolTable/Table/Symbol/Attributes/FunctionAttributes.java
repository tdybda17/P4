package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.List;
import java.util.Objects;

public class FunctionAttributes implements Attributes {
    private TypeDescriptor returnType;
    private List<TypeDescriptor> parameterTypes;

    public FunctionAttributes(TypeDescriptor returnType, List<TypeDescriptor> parameterTypes) {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String getAttributeKind() {
        return "Function";
    }

    public TypeDescriptor getReturnType() {
        return returnType;
    }

    public List<TypeDescriptor> getParameterTypes() {
        return parameterTypes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FunctionAttributes that = (FunctionAttributes) o;
        return Objects.equals(returnType, that.returnType) &&
                Objects.equals(parameterTypes, that.parameterTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(returnType, parameterTypes);
    }
}
