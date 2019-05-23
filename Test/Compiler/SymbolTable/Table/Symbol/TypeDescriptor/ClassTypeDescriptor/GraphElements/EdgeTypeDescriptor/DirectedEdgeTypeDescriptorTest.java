package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.RealTypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DirectedEdgeTypeDescriptorTest {
    private EdgeTypeDescriptor directedEdgeTypeDescriptor;

    @BeforeEach
    void beforeEach(){
        directedEdgeTypeDescriptor = new DirectedEdgeTypeDescriptor();
    }

    @Test
    void getTypeName() {
        assertEquals("DirectedEdge", directedEdgeTypeDescriptor.getTypeName());
    }

    @Test
    void getMethods() {
        Set<Method> expected = new HashSet<>();
        assertEquals(expected, directedEdgeTypeDescriptor.getMethods());
    }

    @Test
    void getFieldColor() {
        Field expected = new Field("color", new ColorTypeDescriptor());
        assertTrue(directedEdgeTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void getFieldWeight() {
        Field expected = new Field("weight", new RealTypeDescriptor());
        assertTrue(directedEdgeTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void getFieldSource() {
        Field expected = new Field("source", new VertexTypeDescriptor());
        assertTrue(directedEdgeTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void getFieldTarget() {
        Field expected = new Field("target", new VertexTypeDescriptor());
        assertTrue(directedEdgeTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void testAddUserAttributes1(){
        Field testField = new Field("testDirected", new IntegerTypeDescriptor());
        directedEdgeTypeDescriptor.addUserAttribute(testField);

        assertTrue(directedEdgeTypeDescriptor.getFields().contains(testField));
    }

    @Test
    void testAddUserAttributes2(){
        Field sameAsExistingField = new Field("color", new ColorTypeDescriptor());

        assertThrows(TypeDescriptorException.class, () -> directedEdgeTypeDescriptor.addUserAttribute(sameAsExistingField));
    }

    //Testing that all our edge type descriptors get the added user attributes
    @Test
    void testAddUserAttributes3(){
        Field testField = new Field("testDirected2", new IntegerTypeDescriptor());

        directedEdgeTypeDescriptor.addUserAttribute(testField);

        assertTrue(new DirectedEdgeTypeDescriptor().getFields().contains(testField));
    }

    @Test
    void equals1() {
        EdgeTypeDescriptor expected = new DirectedEdgeTypeDescriptor();

        assertEquals(expected, directedEdgeTypeDescriptor);
    }

    //Testing that directed edges are not equal to undirected edges.
    @Test
    void equals2() {
        EdgeTypeDescriptor undirectedEdgeTypeDescriptor = new UndirectedEdgeTypeDescriptor();
        assertNotEquals(undirectedEdgeTypeDescriptor, directedEdgeTypeDescriptor);
    }
}