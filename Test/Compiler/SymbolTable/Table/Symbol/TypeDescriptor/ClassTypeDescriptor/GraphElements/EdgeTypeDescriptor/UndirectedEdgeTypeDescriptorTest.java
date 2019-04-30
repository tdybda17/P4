package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections.SetTypeDescriptor;
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

class UndirectedEdgeTypeDescriptorTest {
    private EdgeTypeDescriptor undirectedEdgeTypeDescriptor;

    @BeforeEach
    void beforeEach(){
        undirectedEdgeTypeDescriptor = new UndirectedEdgeTypeDescriptor();
    }

    @Test
    void getTypeName() {
        assertEquals("UndirectedEdge", undirectedEdgeTypeDescriptor.getTypeName());
    }

    @Test
    void getMethods() {
        Set<Method> expected = new HashSet<>();
        assertEquals(expected, undirectedEdgeTypeDescriptor.getMethods());
    }

    @Test
    void getFieldColor() {
        Field expected = new Field("color", new ColorTypeDescriptor());
        assertTrue(undirectedEdgeTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void getFieldWeight() {
        Field expected = new Field("weight", new RealTypeDescriptor());
        assertTrue(undirectedEdgeTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void getFieldVertices() {
        Field expected = new Field("vertices", new SetTypeDescriptor(new VertexTypeDescriptor()));
        assertTrue(undirectedEdgeTypeDescriptor.getFields().contains(expected));
    }

    @Test
    void testAddUserAttributes1(){
        Field testField = new Field("testUndirected", new IntegerTypeDescriptor());
        undirectedEdgeTypeDescriptor.addUserAttribute(testField);

        assertTrue(undirectedEdgeTypeDescriptor.getFields().contains(testField));
    }

    @Test
    void testAddUserAttributes2(){
        Field sameAsExistingField = new Field("color", new ColorTypeDescriptor());

        assertThrows(TypeDescriptorException.class, () -> undirectedEdgeTypeDescriptor.addUserAttribute(sameAsExistingField));
    }

    @Test
    void testAddUserAttributes3(){
        Field testField = new Field("testUndirected2", new IntegerTypeDescriptor());
        undirectedEdgeTypeDescriptor.addUserAttribute(testField);
        assertTrue(new DirectedEdgeTypeDescriptor().getFields().contains(testField));
    }

    @Test
    void equals1() {
        EdgeTypeDescriptor expected = new UndirectedEdgeTypeDescriptor();

        assertEquals(expected, undirectedEdgeTypeDescriptor);
    }

    //Testing that directed edges are not equal to undirected edges.
    @Test
    void equals2() {
        EdgeTypeDescriptor directedEdgeTypeDescriptor = new DirectedEdgeTypeDescriptor();
        assertNotEquals(directedEdgeTypeDescriptor, this.undirectedEdgeTypeDescriptor);
    }

}