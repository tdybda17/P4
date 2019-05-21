package Compiler.CodeGeneration;

import Compiler.CodeGeneration.CGClassLibrary.ClassFile;
import Compiler.CodeGeneration.CGClassLibrary.Classes.*;
import Compiler.CodeGeneration.JavaFileBuilder.Attribute;
import Compiler.CodeGeneration.JavaFileBuilder.ClassBuilder.ClassBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.InterfaceBuilder.InterfaceBuilder;
import Compiler.CodeGeneration.JavaFileBuilder.Writer.FileWriter;
import Compiler.CodeGeneration.JavaFileBuilder.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

class FileWriterTest {

    private final String PATH = "/Users/toby/Programs/P4/src/AutoGenCode/";
    private FileWriter fw;

    @BeforeEach
    void setUp() {

    }

    @Disabled
    @Test
    void testWriteClass() throws IOException {
        var iFace = new InterfaceBuilder("public", "ITestFace")
                .appendMethod(new Method("void", "getInterfaceMethod")
                        .appendAttribute(new Attribute("String", "name")));

        var builder = new ClassBuilder("", "MyClass")
                .appendExtendClass("OtherClass")
                .appendImplementation(iFace)
                .appendField(new Attribute("int", "i"))
                .appendField(new Attribute("int", "b"))
                .appendMethod(new Method("int", "getI").appendBody("return i;"))
                .appendMethod(new Method("int", "getB")
                        .asFinal()
                        .appendBody("if(b > 1) return 1; else return 0;"))
                .appendMethod(new Method("int", "getAllFiles")
                        .asStatic()
                        .appendBody("return new Integer(1);"));

        fw = new FileWriter(builder);
        fw.writeFile(Paths.get(PATH + "Classes.java"));
    }

    @Disabled
    @Test
    void testWriteTheFile01() throws IOException {
        List<ClassFile> classFileList = List.of(
                new CollectionInterfaceFile(),
                new ColorEnumFile(),
                new EdgeAbstractClassFile(),
                new EdgeMaxComparatorClassFile(),
                new EdgeMinComparatorClassFile(),
                new MaxQueueClassFile(),
                new MinQueueClassFile(),
                new QueueClassFile(),
                new VertexClassFile(),
                new VertexMaxComparatorClassFile(),
                new VertexMinComparatorClassFile(),
                new DirectedEdgeClassFile(),
                new UndirectedEdgeClassFile(),
                new DiGraphClassFile(),
                new GraphClassFile(),
                new MainClassFile()
        );
        fw = new FileWriter(classFileList);
        fw.writeFile(Paths.get(PATH + "Classes.java"));
    }
}