package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.EdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.RealTypeDescriptor;
import org.junit.jupiter.api.AfterAll;
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
    void getFieldStartVertex() {
        Field expected = new Field("startVertex", new VertexTypeDescriptor());
        assertTrue(directedEdgeTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void getFieldEndVertex() {
        Field expected = new Field("endVertex", new VertexTypeDescriptor());
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