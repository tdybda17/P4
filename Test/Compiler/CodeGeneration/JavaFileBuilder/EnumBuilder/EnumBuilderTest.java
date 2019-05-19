package Compiler.CodeGeneration.JavaFileBuilder.EnumBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EnumBuilderTest {

    private EnumBuilder enumBuilder;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCreateEnumClass01() {
        String expected = "public enum Color {}";
        enumBuilder = new EnumBuilder("public", "Color");
        assertEquals(expected, enumBuilder.getFileContent());
    }

    @Test
    void testCreateEnumClass02() {
        String expected = "enum Color {}";
        enumBuilder = new EnumBuilder("Color");
        assertEquals(expected, enumBuilder.getFileContent());
    }

    @Test
    void testCreateEnumClasses03() {
        String expected = "enum Color {GRAY, BLACK, WHITE}";
        enumBuilder = new EnumBuilder("Color");
        enumBuilder.appendEnum("GRAY")
                .appendEnum("BLACK")
                .appendEnum("WHITE");
        assertEquals(expected, enumBuilder.getFileContent());
    }
}