package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.BooleanTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.IntegerTypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private Field field;

    @BeforeEach
    void beforeEach(){
        field = new Field("testField", new IntegerTypeDescriptor());
    }

    @Test
    void getType() {
        assertEquals(field.getType(), new IntegerTypeDescriptor());
    }

    @Test
    void getFieldName() {
        assertEquals(field.getFieldName(), "testField");
    }

    @Test
    void equals1() {
        Field other = new Field("testField", new IntegerTypeDescriptor());

        assertEquals(other, field);
    }

    //Testing that two fields with different types but same name then they are still equal
    @Test
    void equals2() {
        Field other = new Field("testField", new BooleanTypeDescriptor());

        assertEquals(other, field);
    }

    //Testing wrong name
    @Test
    void equals3() {
        Field other = new Field("wrongName", new IntegerTypeDescriptor());

        assertNotEquals(other, field);
    }
}