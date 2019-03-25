package Compiler.SymbolTable.Table.Symbol.SymbolList;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolListTest {

    private SymbolList symbolList;

    @BeforeEach
    void setUp() {
        symbolList = new SymbolList();
    }

    @Test
    void testAddAndGet01() {
        String name = "testname";
        symbolList.add(new Symbol(name));
        assertEquals(name, symbolList.get(name).getName());
    }

    @Test
    void testAddAndGet02() {
        String name = "testName";
        symbolList.add(new Symbol(name));
        assertNull(symbolList.get("notANameInList"));
    }
}