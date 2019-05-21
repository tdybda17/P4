package Compiler.CodeGeneration.CGClassLibrary.Classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectionInterfaceFileTest {

    private CollectionInterfaceFile file;

    @BeforeEach
    void setUp() {
        file = new CollectionInterfaceFile();
    }

    @Test
    void testGetClassName01() {
        assertEquals("Collection", file.getClassName());
    }
}