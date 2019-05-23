package Compiler.CodeGeneration.JavaFileBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConstructorTest {

    private Constructor constructor;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testCreateConstructor01() {
        String expectedConstructor = "public Method(String h, int a) {this.h = h;}";
        constructor = new Constructor(
                List.of(new Attribute("String", "h"),
                        new Attribute("int", "a")), "this.h = h;");
        assertEquals(expectedConstructor, constructor.getConstructor("Method"));
    }
}