package Compiler.SymbolTable.Table.Symbol.TypeDescriptor;

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
        assertEquals(new BooleanTypeDescriptor(), factory.create("boolean"));
    }

    @Test
    void createColor() {
        assertEquals(new ColorTypeDescriptor(), factory.create("Color"));
    }

    @Test
    void createInteger() {
        assertEquals(new IntegerTypeDescriptor(), factory.create("int"));
    }

    @Test
    void createReal() {

    }
}