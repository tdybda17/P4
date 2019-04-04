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
        int depth = 1;
        scopeDisplay.open(depth);
        scopeDisplay.add(symbol1, depth);
        scopeDisplay.add(symbol2, depth);
        assertEquals(expectedList, scopeDisplay.get(1));
    }

    @Test
    void testGetSymbol01() {
        var symbol1 = new Symbol("name");
        var symbol2 = new Symbol("name2");
        var expectedList = new SymbolList(symbol1, symbol2);
        int depth = 1;
        scopeDisplay.open(depth);
        scopeDisplay.add(symbol1, depth);
        scopeDisplay.add(symbol2, depth);
        assertEquals(expectedList, scopeDisplay.get(1));
    }

}