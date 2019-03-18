package Compiler.SymbolTable.Table;

import Compiler.Exceptions.SymbolTableExceptions.DuplicationSymbolException;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTreeImplementationTest {

    private SymbolTableTreeImplementation symbolTable;

    @BeforeEach
    void setUp() {
        symbolTable = new SymbolTableTreeImplementation();
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
                List.of(ASymbol, BSymbol, CSymbol).toString(),
                symbolTable.getSymbols().toString()
        );
    }

    @Test
    void testDuplicationSymbolsNotAllowed() {
        Symbol symbol1 = new Symbol("id");
        Symbol symbol2 = new Symbol("id");
        symbolTable.enterSymbol(symbol1);
        assertThrows(DuplicationSymbolException.class, () -> symbolTable.enterSymbol(symbol2));
    }



}