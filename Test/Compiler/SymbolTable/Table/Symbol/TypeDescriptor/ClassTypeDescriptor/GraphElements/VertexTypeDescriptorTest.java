package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VertexTypeDescriptorTest {
    private VertexTypeDescriptor vertexTypeDescriptor;

    @BeforeEach
    void beforeEach(){
       vertexTypeDescriptor = new VertexTypeDescriptor();
    }

    @AfterEach
    void afterEach() {}

    @Test
    void getMethods() {
        Set<Method> expected = new HashSet<>();
        assertEquals(expected, vertexTypeDescriptor.getMethods());
    }

    @Test
    void getFieldsColor() {
        Field expected = new Field("color", new ColorTypeDescriptor());
        assertTrue(vertexTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void getTypeName() {
        assertEquals("Vertex", vertexTypeDescriptor.getTypeName());
    }


    @Test
    void testAddUserAttributes1(){
        Field testField = new Field("test", new IntegerTypeDescriptor());
        VertexTypeDescriptor.addUserAttribute(testField);


        Field expected = new Field("test", new IntegerTypeDescriptor());
        assertTrue(vertexTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void testAddUserAttributes2(){
        Field sameAsExistingField = new Field("color", new ColorTypeDescriptor());

        assertThrows(TypeDescriptorException.class, () -> VertexTypeDescriptor.addUserAttribute(sameAsExistingField));
    }
    @Test
    void equals1() {
        VertexTypeDescriptor expected = new VertexTypeDescriptor();

        assertEquals(expected, vertexTypeDescriptor);
    }
}