package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;

import java.util.Objects;

public class Field {
    private TypeDescriptor type;
    private String fieldName;

    public Field(String fieldName, TypeDescriptor type) {
        this.fieldName = fieldName;
        this.type = type;

    }

    public TypeDescriptor getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(fieldName, field.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName);
    }

    @Override
    public String toString() {
        return "Field{" +
                "type=" + type +
                ", fieldName='" + fieldName + '\'' +
                '}';
    }
}
