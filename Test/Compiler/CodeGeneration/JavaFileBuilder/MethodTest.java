package Compiler.CodeGeneration.JavaFileBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {

    private Method method;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateMethod01() {
        String expectedMethod = "void getAllMethod(String str1, String str2){int i = 1;int b = 40;}";
        method = new Method("void", "getAllMethod");
        method.appendAttribute(new Attribute("String", "str1"))
                .appendAttribute(new Attribute("String", "str2"))
                .appendBody("int i = 1;")
                .appendBody("int b = 40;");
        assertEquals(expectedMethod, method.createMethod());
    }

    @Test
    void testCreateMethodForInterface01() {
        String expectedMethod = "void getAllMethod(String str1, String str2);";
        method = new Method("void", "getAllMethod");
        method.appendAttribute(new Attribute("String", "str1"))
                .appendAttribute(new Attribute("String", "str2"));
        assertEquals(expectedMethod, method.createMethod());
    }

    @Test
    void testCreateMethodForInterface02() {
        String expectedMethod = "void getAllMethod();";
        method = new Method("void", "getAllMethod");
        assertEquals(expectedMethod, method.createMethod());
    }
}