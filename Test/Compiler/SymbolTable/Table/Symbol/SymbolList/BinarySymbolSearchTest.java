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
                new Symbol("name1"),
                new Symbol("name2"),
                new Symbol("name3")
        );
        Symbol expectedSymbol = symbolList.get(1);
        assertEquals(expectedSymbol, BinarySymbolSearch.find(symbolList, expectedSymbol.getName()));
    }

    @Test
    void testCorrectSymbolReturned02() {
        symbolList.add(
                new Symbol("name"),
                new Symbol("name"), // Not added!
                new Symbol("name"), // Not added!
                new Symbol("id")
        );
        Symbol expectedSymbol = symbolList.get(0);
        assertEquals(expectedSymbol, BinarySymbolSearch.find(symbolList, expectedSymbol.getName()));
    }

    @Test
    void testCorrectSymbolReturned03() {
        symbolList.add(new Symbol("name"));
        assertNull(BinarySymbolSearch.find(symbolList, "name2"));
    }

    @Test
    void testSymbolExists01() {
        symbolList.add(new Symbol("name"));
        assertFalse(BinarySymbolSearch.exists(symbolList, new Symbol("name2")));
    }

    @Test
    void testSymbolExists02() {
        symbolList.add(new Symbol("symbolname"));
        assertTrue(BinarySymbolSearch.exists(symbolList, new Symbol("symbolname")));
    }
}