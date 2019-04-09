package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QueueTypeDescriptorTest {
    private QueueTypeDescriptor queueTypeDescriptor;
    private TypeDescriptor elementType;

    @BeforeEach
    void beforeEach(){
        //We create a queue of integers
        elementType = new IntegerTypeDescriptor();
        queueTypeDescriptor = new QueueTypeDescriptor(elementType);
    }


    @Test
    void getMethodsIsEmpty() {
        Method expected = new Method("isEmpty", new BooleanTypeDescriptor(), new ArrayList<>());
        assertTrue(queueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsDequeue() {
        Method expected = new Method("dequeue", elementType, new ArrayList<>());
        assertTrue(queueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getMethodsEnqueue() {
        List<TypeDescriptor> enqueueParameters = new ArrayList<>();
        enqueueParameters.add(elementType);
        Method expected = new Method("enqueue", new BooleanTypeDescriptor(), enqueueParameters);
        assertTrue(queueTypeDescriptor.getMethods().contains(expected));
    }

    @Test
    void getFields() {
        Set<Field> expected = new HashSet<>();
        assertEquals(expected, queueTypeDescriptor.getFields());
    }

    @Test
    void getTypeName() {
        assertEquals("Queue", queueTypeDescriptor.getTypeName());
    }

    @Test
    void equals1() {
        QueueTypeDescriptor other = new QueueTypeDescriptor(new IntegerTypeDescriptor());
        assertEquals(other, queueTypeDescriptor);
    }

    //Testing that two queues with different element types are not equal
    @Test
    void equals2() {
        QueueTypeDescriptor other = new QueueTypeDescriptor(new BooleanTypeDescriptor());
        assertNotEquals(other, queueTypeDescriptor);
    }
}