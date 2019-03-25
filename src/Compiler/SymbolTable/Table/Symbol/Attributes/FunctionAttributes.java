package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.List;

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
}
