package Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder;

import Compiler.CodeGeneration.JavaFileBuilder.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InterfaceBuilderTest {

    private InterfaceBuilder interfaceBuilder;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateInterface01() {
        String expectedInterface = "public interface Collection {boolean isEmpty();}";
        interfaceBuilder = new InterfaceBuilder("public", "Collection")
                .appendMethod(new Method("boolean", "isEmpty"));
        assertEquals(expectedInterface, interfaceBuilder.getFileContent());
    }

    @Test
    void testCreateInterface02() {
        String expectedInterface = "public interface Collection<T> {boolean isEmpty();}";
        interfaceBuilder = new InterfaceBuilder("public", "Collection", "T")
                .appendMethod(new Method("boolean", "isEmpty"));
        assertEquals(expectedInterface, interfaceBuilder.getFileContent());
    }

    @Test
    void testCreateInterface03() {
        String expectedInterface = "public interface Collection<T, A> {boolean isEmpty();A getFile();}";
        interfaceBuilder = new InterfaceBuilder("public", "Collection", "T", "A")
                .appendMethod(new Method("boolean", "isEmpty"))
                .appendMethod(new Method("A", "getFile"));
        assertEquals(expectedInterface, interfaceBuilder.getFileContent());
    }

    @Test
    void testCreateInterface04() {
        String expectedInterface = "interface Collection<T, A> {boolean isEmpty();T getFile();}";
        interfaceBuilder = new InterfaceBuilder("", "Collection", "T", "A")
                .appendMethod(new Method("boolean", "isEmpty"))
                .appendMethod(new Method("T", "getFile"));
        assertEquals(expectedInterface, interfaceBuilder.getFileContent());
    }

    @Test
    void testCreateInterface05() {
        String expectedInterface = "interface Collection {}";
        interfaceBuilder = new InterfaceBuilder("Collection");
        assertEquals(expectedInterface, interfaceBuilder.getFileContent());
    }
}