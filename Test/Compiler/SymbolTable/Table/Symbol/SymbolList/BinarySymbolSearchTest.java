package Compiler.SymbolTable.Table.Symbol.SymbolList;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinarySymbolSearchTest {

    private SymbolList symbolList;

    @BeforeEach
    void setUp() {
        symbolList = new SymbolList();
    }

    @Test
    void testCorrectSymbolReturned01() {
        symbolList.add(
                new Symbol("name1", 1),
                new Symbol("name2", 1),
                new Symbol("name3", 1)
        );
        Symbol expectedSymbol = symbolList.get(1);
        assertEquals(expectedSymbol, BinarySymbolSearch.find(symbolList, expectedSymbol.getName()));
    }

    @Test
    void testCorrectSymbolReturned02() {
        symbolList.add(
                new Symbol("name", 1),
                new Symbol("name", 2), // Not added!
                new Symbol("name", 3), // Not added!
                new Symbol("id", 3)
        );
        Symbol expectedSymbol = symbolList.get(0);
        assertEquals(expectedSymbol, BinarySymbolSearch.find(symbolList, expectedSymbol.getName()));
    }

    @Test
    void testCorrectSymbolReturned03() {
        symbolList.add(new Symbol("name", 1));
        assertNull(BinarySymbolSearch.find(symbolList, "name2"));
    }

    @Test
    void testSymbolExists01() {
        symbolList.add(new Symbol("name", 1));
        assertFalse(BinarySymbolSearch.exists(symbolList, new Symbol("name2", 2)));
    }

    @Test
    void testSymbolExists02() {
        symbolList.add(new Symbol("symbolname", 1));
        assertTrue(BinarySymbolSearch.exists(symbolList, new Symbol("symbolname", 1)));
    }
}