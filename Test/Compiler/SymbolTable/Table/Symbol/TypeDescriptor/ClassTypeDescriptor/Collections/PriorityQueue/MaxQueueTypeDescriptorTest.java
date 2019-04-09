package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.PriorityQueue;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.*;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MaxQueueTypeDescriptorTest {
    private MaxQueueTypeDescriptor maxQueueTypeDescriptor;
    private TypeDescriptor elementType;

    @BeforeEach
    void beforeEach(){
        //We create a maxQueue of integers
        elementType = new IntegerTypeDescriptor();
        maxQueueTypeDescriptor = new MaxQueueTypeDescriptor(elementType);
    }


    @Test
    void getMethodsIsEmpty() {
        Method expected = new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
        assertTrue(maxQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsMaximum() {
        Method expected = new Method("maximum", elementType, new ArrayList<>());
        assertTrue(maxQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsExtractMax() {
        Method expected = new Method("extractMax", elementType, new ArrayList<>());
        assertTrue(maxQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsInsert() {
        List<TypeDescriptor> insertParameters = new ArrayList<>();
        insertParameters.add(elementType);
        Method expected = new Method("insert", new BooleanTypeDescriptor(), insertParameters);
        assertTrue(maxQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsIncreaseKey() {
        List<TypeDescriptor> decreaseKeyParameters = new ArrayList<>();
        decreaseKeyParameters.add(elementType);
        decreaseKeyParameters.add(new RealTypeDescriptor());
        Method expected = new Method("increaseKey", new BooleanTypeDescriptor(), decreaseKeyParameters);
        assertTrue(maxQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getFields() {
        Set<Field> expected = new HashSet<>();
        assertEquals(expected, maxQueueTypeDescriptor.getFields());
    }

    @Test
    void getTypeName() {
        assertEquals("MaxQueue", maxQueueTypeDescriptor.getTypeName());
    }

    @Test
    void equals1() {
        MaxQueueTypeDescriptor other = new MaxQueueTypeDescriptor(new IntegerTypeDescriptor());
        assertEquals(other, maxQueueTypeDescriptor);
    }

    //Testing that two maxQueues with different element types are not equal
    @Test
    void equals2() {
        MaxQueueTypeDescriptor other = new MaxQueueTypeDescriptor(new BooleanTypeDescriptor());
        assertNotEquals(other, maxQueueTypeDescriptor);
    }

    //Testing that MinQueue and MaxQueue are not equal
    @Test
    void equals3() {
        MinQueueTypeDescriptor minQueueTypeDescriptor = new MinQueueTypeDescriptor(new IntegerTypeDescriptor());
        assertNotEquals(minQueueTypeDescriptor, this.maxQueueTypeDescriptor);
    }
}