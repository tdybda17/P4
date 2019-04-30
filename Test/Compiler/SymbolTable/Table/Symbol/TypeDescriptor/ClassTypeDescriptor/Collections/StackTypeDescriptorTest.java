package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StackTypeDescriptorTest {
    private StackTypeDescriptor stackTypeDescriptor;
    private TypeDescriptor elementType;

    @BeforeEach
    void beforeEach(){
        //We create an stack of integers
        elementType = new IntegerTypeDescriptor();
        stackTypeDescriptor = new StackTypeDescriptor(elementType);
    }

    @Test
    void getMethodsIsEmpty(){
        Method expected = new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
        assertTrue(stackTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsPop() {
        Method expected = new Method("pop", elementType, new ArrayList<>());
        assertTrue(stackTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsPush() {
        List<TypeDescriptor> enqueueParameters = new ArrayList<>();
        enqueueParameters.add(elementType);
        Method expected = new Method("push", new BooleanTypeDescriptor(), enqueueParameters);
        assertTrue(stackTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getFields() {
        Set<Field> expected = new HashSet<>();

        assertEquals(expected, stackTypeDescriptor.getFields());
    }

    @Test
    void getTypeName() {
        assertEquals("Stack", stackTypeDescriptor.getTypeName());
    }

    @Test
    void equals1() {
        StackTypeDescriptor other = new StackTypeDescriptor(new IntegerTypeDescriptor());
        assertEquals(other, stackTypeDescriptor);
    }

    //Testing that two stacks with different element types are not equal
    @Test
    void equals2() {
        StackTypeDescriptor other = new StackTypeDescriptor(new BooleanTypeDescriptor());
        assertNotEquals(other, stackTypeDescriptor);
    }
}
