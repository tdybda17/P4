package Compiler.SymbolTable.Table;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    private SymbolTable symbolTable;
    private String name;

    @BeforeEach
    void setUp() {
        symbolTable = new SymbolTable();
        name = null;
    }

    @Test
    void enterSymbol() {
        symbolTable.openScope();
        name = "symbolName";
        symbolTable.enterSymbol(name, new Object());
        assertEquals(name, symbolTable.retrieveSymbol(name).getName());
    }

    @Test
    void retrieveSymbol() {
    }
}