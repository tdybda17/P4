package Compiler.CodeGeneration.JavaFileBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttributeTest {

    private Attribute attribute;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetAttribute() {
        String expectedAttr = "String str";
        Attribute actualAttr = new Attribute("String", "str");
        assertEquals(expectedAttr, actualAttr.getAttribute());
    }

    @Test
    void testGetAttributeWithAccessModifier01() {
        String expectedAttr = "private String str";
        Attribute actualAttr = new Attribute("private", "String", "str");
        assertEquals(expectedAttr, actualAttr.getAttribute());
    }

    @Test
    void testGetAttributeWithAccessModifierAndValue01() {
        String expectedAttr = "private int i = 21";
        Attribute actualAttr = new Attribute("private", "int", "i").withValue("21");
        assertEquals(expectedAttr, actualAttr.getAttribute());
    }

    @Test
    void testGetAttributeWithValue01() {
        String expectedAttr = "int i";
        Attribute actualAttr = new Attribute("int", "i");
        assertEquals(expectedAttr, actualAttr.getAttribute());
    }
}