package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.IntegerTypeDescriptor;
import Compiler.SymbolTable.Table.Symbol.TypeDescriptor.TypeDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttributesTest {
    private FunctionAttributes funcAttr;
    private VariableAttributes variableAttr;

    @BeforeEach
    void setUp() {
        List<TypeDescriptor> parameterTypes = new ArrayList<>();
        funcAttr = new FunctionAttributes(new IntegerTypeDescriptor(), parameterTypes);
        variableAttr = new VariableAttributes(new IntegerTypeDescriptor());
    }

    @Test
    void testingInstanceOf(){
        Attributes attributes = funcAttr;

        if(attributes instanceof FunctionAttributes) {
            assertEquals (new IntegerTypeDescriptor(),((FunctionAttributes) attributes).getReturnType());
        } else if (attributes instanceof VariableAttributes) {
            throw new RuntimeException("We should not get here");
        }
    }

}