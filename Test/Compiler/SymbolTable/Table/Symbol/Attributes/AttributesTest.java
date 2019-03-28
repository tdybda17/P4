package Compiler.SymbolTable.Table.Symbol.Attributes;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;
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
    void testingInstanceOfFuncAttr(){
        Symbol testSymbol = new Symbol("testFunctionSymbol", funcAttr, 1, new SymbolList());

        Attributes attributes = testSymbol.getAttributes();

        if(attributes instanceof FunctionAttributes) {
            //We are checking by type casting that we can get the correct info out of the symbol still.
            FunctionAttributes actualAttributes = (FunctionAttributes) attributes;
            assertEquals(funcAttr.getAttributeKind(), actualAttributes.getAttributeKind());
            assertEquals(funcAttr.getReturnType(),actualAttributes.getReturnType());
            assertEquals(funcAttr.getParameterTypes(), actualAttributes.getParameterTypes());
        } else {
            throw new RuntimeException("We should not get here");
        }
    }

    @Test
    void testingInstanceOfVariableAttr(){
        Symbol testSymbol = new Symbol("testFunctionSymbol", variableAttr, 1, new SymbolList());

        Attributes attributes = testSymbol.getAttributes();

        if(attributes instanceof VariableAttributes) {
            //We are checking by type casting that we can get the correct info out of the symbol still.
            VariableAttributes actualAttributes = (VariableAttributes) attributes;
            assertEquals(variableAttr.getAttributeKind(), actualAttributes.getAttributeKind());
            assertEquals(variableAttr.getType(), actualAttributes.getType());
        } else {
            throw new RuntimeException("We should not get here");
        }
    }

}