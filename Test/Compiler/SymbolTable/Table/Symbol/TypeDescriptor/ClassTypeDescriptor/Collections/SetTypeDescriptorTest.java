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

class SetTypeDescriptorTest {
    private SetTypeDescriptor setTypeDescriptor;
    private TypeDescriptor elementType;

    @BeforeEach
    void beforeEach(){
        //We create an set of integers
        elementType = new IntegerTypeDescriptor();
        setTypeDescriptor = new SetTypeDescriptor(elementType);
    }

    @Test
    void getMethodsIsEmpty(){
        Method expected = new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
        assertTrue(setTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsAdd() {
        List<TypeDescriptor> addParameters = new ArrayList<>();
        addParameters.add(elementType);
        Method expected = new Method("add", new BooleanTypeDescriptor(), addParameters);
        assertTrue(setTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsRemove() {
        List<TypeDescriptor> removeParameters = new ArrayList<>();
        removeParameters.add(elementType);
        Method expected = new Method("remove", new BooleanTypeDescriptor(), removeParameters);
        assertTrue(setTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsContains() {
        List<TypeDescriptor> containsParameters = new ArrayList<>();
        containsParameters.add(elementType);
        Method expected = new Method("remove", new BooleanTypeDescriptor(), containsParameters);
        assertTrue(setTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getFields() {
        Set<Field> expected = new HashSet<>();
        assertEquals(expected, setTypeDescriptor.getFields());
    }

    @Test
    void getTypeName() {
        assertEquals("Set", setTypeDescriptor.getTypeName());
    }

    @Test
    void equals1() {
        SetTypeDescriptor other = new SetTypeDescriptor(new IntegerTypeDescriptor());
        assertEquals(other, setTypeDescriptor);
    }

    //Testing that two sets with different element types are not equal
    @Test
    void equals2() {
        SetTypeDescriptor other = new SetTypeDescriptor(new BooleanTypeDescriptor());
        assertNotEquals(other, setTypeDescriptor);
    }

}