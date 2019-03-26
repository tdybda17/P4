package Compiler.SymbolTable.Table;

import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    private SymbolTable symbolTable;

    @BeforeEach
    void setUp() {
        symbolTable = new SymbolTable();
    }

    @Test
    void testEnterSymbol01() {
        String name = "firstSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name, null);
        assertThrows(DuplicateSymbolError.class, () -> symbolTable.enterSymbol(name, null));
    }

    @Test
    void testEnterSymbol02() {
        String name = "firstSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name, null);
        symbolTable.openScope();
        assertThrows(DuplicateSymbolError.class, () -> symbolTable.enterSymbol(name, null));
    }

    @Test
    void testEnterSymbol03() {
        String name1 = "firstSymbol";
        String name2 = "secondSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name1, null);
        symbolTable.openScope();
        symbolTable.enterSymbol(name2, null);
        symbolTable.closeScope();
        symbolTable.enterSymbol(name2, null);
    }

}