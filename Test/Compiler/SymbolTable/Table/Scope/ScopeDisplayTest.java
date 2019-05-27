package Compiler.SymbolTable.Table.Scope;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScopeDisplayTest {

    private ScopeDisplay scopeDisplay;

    @BeforeEach
    void setUp() {
        scopeDisplay = new ScopeDisplay();
    }

    @Test
    void testThatNoDuplicatesCanBeAddedToSameScope() {
        var symbol1 = new Symbol("name");
        var symbol2 = new Symbol("name");
        var expectedList = new SymbolList(symbol1);
        scopeDisplay.open();
        scopeDisplay.add(symbol1);
        scopeDisplay.add(symbol2);
        assertEquals(expectedList, scopeDisplay.getCurrentSymbolList());
    }

    @Test
    void testGetSymbol01() {
        var symbol1 = new Symbol("name");
        var symbol2 = new Symbol("name2");
        var expectedList = new SymbolList(symbol1, symbol2);
        scopeDisplay.open();
        scopeDisplay.add(symbol1);
        scopeDisplay.add(symbol2);
        assertEquals(expectedList, scopeDisplay.getCurrentSymbolList());
    }

}