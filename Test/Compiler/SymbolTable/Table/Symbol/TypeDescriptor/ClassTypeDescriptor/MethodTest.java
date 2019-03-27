package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {
    private Method method;

    @BeforeEach
    void beforeEach() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new IntegerTypeDescriptor());
        parameters.add(new BooleanTypeDescriptor());
        method = new Method("testMethod", new IntegerTypeDescriptor(), parameters);
    }


    @Test
    void getMethodName() {
        assertEquals("testMethod", method.getMethodName());
    }

    @Test
    void getReturnType() {
        assertEquals(new IntegerTypeDescriptor(), method.getReturnType());
    }

    @Test
    void getParameterTypes() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new IntegerTypeDescriptor());
        parameters.add(new BooleanTypeDescriptor());
        assertEquals(parameters, method.getParameterTypes());
    }

    @Test
    void equals1() {
        List<TypeDescriptor> parameters = new ArrayList<>();
        parameters.add(new IntegerTypeDescriptor());
        parameters.add(new BooleanTypeDescriptor());
        Method other = new Method("testMethod", new IntegerTypeDescriptor(), parameters);
        assertEquals(other, method);
    }
}