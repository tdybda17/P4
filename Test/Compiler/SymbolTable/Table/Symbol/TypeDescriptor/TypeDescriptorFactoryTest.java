package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

import Compiler.Parser.GeneratedFiles.ASTSIMPLE_TYPES;
import Compiler.Parser.GeneratedFiles.TestParserTreeConstants;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.NumberTypeDesciptor.IntegerTypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeDescriptorFactoryTest {
    private TypeDescriptorFactory factory;

    @BeforeEach
    void beforeEach(){
        factory = new TypeDescriptorFactory();
    }

    @Test
    void createBoolean() {
        ASTSIMPLE_TYPES typeNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        typeNode.jjtSetValue("boolean");
        assertEquals(new BooleanTypeDescriptor(), factory.create(typeNode));
    }

    @Test
    void createColor() {
        ASTSIMPLE_TYPES typeNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        typeNode.jjtSetValue("Color");
        assertEquals(new ColorTypeDescriptor(), factory.create(typeNode));
    }

    @Test
    void createInteger() {
        ASTSIMPLE_TYPES typeNode = new ASTSIMPLE_TYPES(TestParserTreeConstants.JJTSIMPLE_TYPES);
        typeNode.jjtSetValue("int");
        assertEquals(new IntegerTypeDescriptor(), factory.create(typeNode));
    }

    @Test
    void createReal() {

    }
}