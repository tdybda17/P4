package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.DirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.EdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor.UndirectedEdgeTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.RealTypeDescriptor;
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
    void getFields() {
        Set<Field> expected = new HashSet<>();
        expected.add(new Field("color", new ColorTypeDescriptor()));
        expected.add(new Field("weight", new RealTypeDescriptor()));
        expected.add(new Field("startVertex", new VertexTypeDescriptor()));
        expected.add(new Field("endVertex", new VertexTypeDescriptor()));
        assertEquals(expected, directedEdgeTypeDescriptor.getFields());
    }

    @Test
    void testAddUserAttributes1(){
        Field testField = new Field("test", new IntegerTypeDescriptor());
        directedEdgeTypeDescriptor.addUserAttribute(testField);


        Set<Field> expected = new HashSet<>();
        expected.add(new Field("color", new ColorTypeDescriptor()));
        expected.add(new Field("weight", new RealTypeDescriptor()));
        expected.add(new Field("startVertex", new VertexTypeDescriptor()));
        expected.add(new Field("endVertex", new VertexTypeDescriptor()));
        expected.add(testField);
        assertEquals(expected, directedEdgeTypeDescriptor.getFields());
    }

    @Test
    void testAddUserAttributes2(){
        Field sameAsExistingField = new Field("color", new ColorTypeDescriptor());

        assertThrows(TypeDescriptorException.class, () -> directedEdgeTypeDescriptor.addUserAttribute(sameAsExistingField));
    }
    @Test
    void equals1() {
        EdgeTypeDescriptor expected = new DirectedEdgeTypeDescriptor();

        assertEquals(expected, directedEdgeTypeDescriptor);
    }

    //Right now two type descriptors are still equal if they have different fields, however in our langauge you cannot make two
    //different versions of edges.
    @Test
    void equals2() {
        Field testField = new Field("test", new IntegerTypeDescriptor());
        directedEdgeTypeDescriptor.addUserAttribute(testField);

        EdgeTypeDescriptor expected = new DirectedEdgeTypeDescriptor();
        assertEquals(expected, directedEdgeTypeDescriptor);
    }

    //Testing that directed edges are not equal to undirected edges.
    @Test
    void equals3() {
        EdgeTypeDescriptor undirectedEdgeTypeDescriptor = new UndirectedEdgeTypeDescriptor();
        assertNotEquals(undirectedEdgeTypeDescriptor, directedEdgeTypeDescriptor);
    }

}