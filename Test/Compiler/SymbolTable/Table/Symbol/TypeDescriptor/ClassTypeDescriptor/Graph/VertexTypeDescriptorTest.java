package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Graph;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VertexTypeDescriptorTest {
    private VertexTypeDescriptor vertexTypeDescriptor;

    @BeforeEach
    void beforeEach(){
       vertexTypeDescriptor = new VertexTypeDescriptor();
    }


    @Test
    void getMethods() {
        Set<Method> expected = new HashSet<>();
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
    void testAddUserAttributes1(){
        Field testField = new Field("test", new IntegerTypeDescriptor());
        vertexTypeDescriptor.addUserAttribute(testField);


        Set<Field> expected = new HashSet<>();
        expected.add(new Field("color", new ColorTypeDescriptor()));
        expected.add(testField);
        assertEquals(expected, vertexTypeDescriptor.getFields());
    }

    @Test
    void testAddUserAttributes2(){
        Field sameAsExistingField = new Field("color", new ColorTypeDescriptor());

        assertThrows(TypeDescriptorException.class, () -> vertexTypeDescriptor.addUserAttribute(sameAsExistingField));
    }
    @Test
    void equals1() {
        VertexTypeDescriptor expected = new VertexTypeDescriptor();

        assertEquals(expected, vertexTypeDescriptor);
    }

    //Right now two type descriptors are still equal if they have different fields, however in our langauge you cannot make two
    //different versions of vertices.
    @Test
    void equals2() {
        Field testField = new Field("test", new IntegerTypeDescriptor());
        vertexTypeDescriptor.addUserAttribute(testField);

        VertexTypeDescriptor expected = new VertexTypeDescriptor();
        assertEquals(expected, vertexTypeDescriptor);
    }
}