package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.List;
import java.util.Objects;

public class Method {
    private String methodName;
    private TypeDescriptor returnType;
    private List<TypeDescriptor> parameterTypes;

    public Method(String methodName, TypeDescriptor returnType, List<TypeDescriptor> parameterTypes) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;
    }

    public String getMethodName() {
        return methodName;
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
        Method method = (Method) o;
        return Objects.equals(methodName, method.methodName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName);
    }
}
