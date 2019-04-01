package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.EdgeTypeDescriptor;

import Compiler.Exceptions.SymbolTable.TypeDescriptorException;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.GraphElements.VertexPairTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Field;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Method;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.ColorTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.SimpleDataTypeDescriptor.RealTypeDescriptor;
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
    void getFields() {
        Set<Field> expected = new HashSet<>();
        expected.add(new Field("color", new ColorTypeDescriptor()));
        expected.add(new Field("weight", new RealTypeDescriptor()));
        expected.add(new Field("vertices", new VertexPairTypeDescriptor()));
        assertEquals(expected, undirectedEdgeTypeDescriptor.getFields());
    }

    @Test
    void testAddUserAttributes1(){
        Field testField = new Field("test", new IntegerTypeDescriptor());
        undirectedEdgeTypeDescriptor.addUserAttribute(testField);


        Set<Field> expected = new HashSet<>();
        expected.add(new Field("color", new ColorTypeDescriptor()));
        expected.add(new Field("weight", new RealTypeDescriptor()));
        expected.add(new Field("vertices", new VertexPairTypeDescriptor()));
        expected.add(testField);
        assertEquals(expected, undirectedEdgeTypeDescriptor.getFields());
    }

    @Test
    void testAddUserAttributes2(){
        Field sameAsExistingField = new Field("color", new ColorTypeDescriptor());

        assertThrows(TypeDescriptorException.class, () -> undirectedEdgeTypeDescriptor.addUserAttribute(sameAsExistingField));
    }
    @Test
    void equals1() {
        EdgeTypeDescriptor expected = new UndirectedEdgeTypeDescriptor();

        assertEquals(expected, undirectedEdgeTypeDescriptor);
    }

    //Right now two type descriptors are still equal if they have different fields, however in our langauge you cannot make two
    //different versions of edges.
    @Test
    void equals2() {
        Field testField = new Field("test", new IntegerTypeDescriptor());
        undirectedEdgeTypeDescriptor.addUserAttribute(testField);

        EdgeTypeDescriptor expected = new UndirectedEdgeTypeDescriptor();
        assertEquals(expected, undirectedEdgeTypeDescriptor);
    }

    //Testing that directed edges are not equal to undirected edges.
    @Test
    void equals3() {
        EdgeTypeDescriptor directedEdgeTypeDescriptor = new DirectedEdgeTypeDescriptor();
        assertNotEquals(directedEdgeTypeDescriptor, this.undirectedEdgeTypeDescriptor);
    }

}