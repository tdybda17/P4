package Compiler.SymbolTable.Table;

import Compiler.Exceptions.ScopeError.DuplicateSymbolError;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    private SymbolTable symbolTable;

    @BeforeEach
    void setUp() {
        symbolTable = new SymbolTable();
    }

    @Test
    void testThatSymbolsAppearsInLexicographicalOrder01() {
        Symbol ASymbol = new Symbol("a");
        Symbol BSymbol = new Symbol("b");
        Symbol CSymbol = new Symbol("c");
        symbolTable.enterSymbol(CSymbol);
        symbolTable.enterSymbol(BSymbol);
        symbolTable.enterSymbol(ASymbol);
        assertEquals(
                List.of(ASymbol, BSymbol, CSymbol),
                symbolTable.getSymbols().asList()
        );
    }

    @Test
    void testDuplicationSymbolsNotAllowed() {
        Symbol symbol1 = new Symbol("id");
        Symbol symbol2 = new Symbol("id");
        symbolTable.enterSymbol(symbol1);
        assertThrows(DuplicateSymbolError.class, () -> symbolTable.enterSymbol(symbol2));
    }

    @Test
    void testRetrieveSymbol() {
        for (int i = 0; i < 1000; i++) {
            symbolTable.enterSymbol(new Symbol("id" + i));
        }
        Symbol symbol = new Symbol("id500");
        assertEquals(symbol, symbolTable.retrieveSymbol(symbol.getName()));
    }

}