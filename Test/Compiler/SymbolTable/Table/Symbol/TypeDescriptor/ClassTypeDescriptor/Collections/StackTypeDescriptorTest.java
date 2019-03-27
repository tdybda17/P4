package Compiler.SymbolTable.Table.Symbol.TypeDescriptor.ClassTypeDescriptor.Collections;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.IntegerTypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTypeDescriptorTest {
    private StackTypeDescriptor stackTypeDescriptor;

    @BeforeEach
    void beforeEach(){
        //We create an stack of integers
        stackTypeDescriptor = new StackTypeDescriptor(new IntegerTypeDescriptor());
    }


    @Test
    void getMethods() {
    }

    @Test
    void getFields() {
    }

    @Test
    void getTypeName() {
        assertEquals("Stack", stackTypeDescriptor.getTypeName());
    }
}