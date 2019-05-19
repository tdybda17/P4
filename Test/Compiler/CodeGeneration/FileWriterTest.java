package Compiler.CodeGeneration;

import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Writer.FileWriter;
import Compiler.CodeGeneration.JavaFileBuilder.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

class FileWriterTest {

    private final String PATH = "/Users/toby/Programs/P4/src/AutoGenCode/";
    private FileWriter fw;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testWriteClass() throws IOException {
        var builder = new ClassBuilder("", "MyClass")
                .appendField(new Attribute("int", "i"))
                .appendField(new Attribute("int", "b"))
                .appendMethod(new Method("int", "getI").appendBody("return i;"))
                .appendMethod(new Method("void", "getB")
                        .asFinal()
                        .appendBody("if(b > 1) return 1 else return 0;"))
                .appendMethod(new Method("int", "getAllFiles")
                        .asStatic()
                        .appendBody("return new Integer(1);"));




        fw = new FileWriter(builder);
        fw.writeFile(Paths.get(PATH + "Classes.java"));
    }
}