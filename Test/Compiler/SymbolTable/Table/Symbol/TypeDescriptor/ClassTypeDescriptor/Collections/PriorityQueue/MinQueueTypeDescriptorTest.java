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

class MinQueueTypeDescriptorTest {
    private MinQueueTypeDescriptor minQueueTypeDescriptor;
    private TypeDescriptor elementType;

    @BeforeEach
    void beforeEach(){
        //We create a minQueue of integers
        elementType = new IntegerTypeDescriptor();
        minQueueTypeDescriptor = new MinQueueTypeDescriptor(elementType);
    }


    @Test
    void getMethodsIsEmpty() {
        Method expected = new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
        assertTrue(minQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsMinimum() {
        Method expected = new Method("minimum", elementType, new ArrayList<>());
        assertTrue(minQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsExtractMin() {
        Method expected = new Method("extractMin", elementType, new ArrayList<>());
        assertTrue(minQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsInsert() {
        List<TypeDescriptor> insertParameters = new ArrayList<>();
        insertParameters.add(elementType);
        Method expected = new Method("insert", new BooleanTypeDescriptor(), insertParameters);
        assertTrue(minQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsDecreaseKey() {
        List<TypeDescriptor> decreaseKeyParameters = new ArrayList<>();
        decreaseKeyParameters.add(elementType);
        decreaseKeyParameters.add(new RealTypeDescriptor());
        Method expected = new Method("decreaseKey", new BooleanTypeDescriptor(), decreaseKeyParameters);
        assertTrue(minQueueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getFields() {
        Set<Field> expected = new HashSet<>();
        assertEquals(expected, minQueueTypeDescriptor.getFields());
    }

    @Test
    void getTypeName() {
        assertEquals("MinQueue", minQueueTypeDescriptor.getTypeName());
    }

    @Test
    void equals1() {
        MinQueueTypeDescriptor other = new MinQueueTypeDescriptor(new IntegerTypeDescriptor());
        assertEquals(other, minQueueTypeDescriptor);
    }

    //Testing that two minQueues with different element types are not equal
    @Test
    void equals2() {
        MinQueueTypeDescriptor other = new MinQueueTypeDescriptor(new BooleanTypeDescriptor());
        assertNotEquals(other, minQueueTypeDescriptor);
    }

    //Testing that MinQueue and MaxQueue are not equal
    @Test
    void equals3() {
        MaxQueueTypeDescriptor maxQueueTypeDescriptor = new MaxQueueTypeDescriptor(new IntegerTypeDescriptor());
        assertNotEquals(maxQueueTypeDescriptor, minQueueTypeDescriptor);
    }
}