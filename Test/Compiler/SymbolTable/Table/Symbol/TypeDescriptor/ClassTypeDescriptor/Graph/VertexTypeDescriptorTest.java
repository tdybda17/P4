package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VertexTypeDescriptorTest {
    private VertexTypeDescriptor vertexTypeDescriptor;

    //TODO: check getNeighbours ud da den infinite recursive
    @BeforeEach
    void beforeEach(){
       vertexTypeDescriptor = new VertexTypeDescriptor();
    }


    @Test
    void getMethods() {
        Set<Method> expected = new HashSet<>();

        TypeDescriptor returnType = new SetTypeDescriptor(new VertexTypeDescriptor());
        expected.add(new Method("getNeighbours", returnType, new ArrayList<>()));

        //returnType = new SetTypeDescriptor(new EdgeTypeDescriptor());
        expected.add(new Method("getOutgoingEdges", returnType, new ArrayList<>()));

        assertEquals(expected, vertexTypeDescriptor.getMethods());
    }

    @Test
    void getFields() {
        Set<Field> expected = new HashSet<>();
        expected.add(new Field("color", new ColorTypeDescriptor()));

        assertEquals(expected, vertexTypeDescriptor.getFields());
    }

    @Test
    void getTypeName() {
        assertEquals("Vertex", vertexTypeDescriptor.getTypeName());
    }

    @Test
    void equals1() {

    }

    //Testing that two stacks with different element types are not equal
    @Test
    void equals2() {
        //StackTypeDescriptor other = new StackTypeDescriptor(new BooleanTypeDescriptor());
        //assertNotEquals(other, stackTypeDescriptor);
    }

    @Test
    void addUserAttribute() {
    }
}