package Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder;

import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassBuilderTest {

    private ClassBuilder builder;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testSimpleClassBuilding01() {
        String simpleClass = "public class TestClass {int i;int b;}";
        builder = new ClassBuilder("public", "TestClass")
                .appendField(new Attribute("int", "i"))
                .appendField(new Attribute("int", "b"));
        assertEquals(simpleClass, builder.getFileContent());
    }

    @Test
    void testSimpleClassBuilding02() {
        String simpleClass = "class TestClass<T> {int i = 1;T hej = \"hej\";}";
        builder = new ClassBuilder("", "TestClass", "T")
                .appendField(new Attribute("int", "i").withValue("1"))
                .appendField(new Attribute("T", "hej").withValue("\"hej\""));
        assertEquals(simpleClass, builder.getFileContent());
    }
}