package Compiler.SymbolTable.Table;

import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.SymbolTable.ScopeError.GettingFromClosedScopeDisplayError;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchSymbolError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SymbolTableTest {

    private SymbolTable symbolTable;

    @BeforeEach
    void setUp() {
        symbolTable = new SymbolTable();
    }

    //Testing that you cannot enter the same symbol twice in the same scope
    @Test
    void testEnterSymbol01() {
        String name = "firstSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name, null);
        assertThrows(DuplicateSymbolError.class, () -> symbolTable.enterSymbol(name, null));
    }

    //Testing that you cannot enter the same symbol twice in different scopes
    @Test
    void testEnterSymbol02() {
        String name = "firstSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name, null);
        symbolTable.openScope();
        assertThrows(DuplicateSymbolError.class, () -> symbolTable.enterSymbol(name, null));
    }

    //Testing that you can reenter a symbol in the symbol table if the scope with the earlier declaration is closed
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


    //Testing that you can reenter two symbols in the symbol table if the scope with their earlier declarations is closed
    @Test
    void testEnterSymbol04() {
        String name1 = "firstSymbol";
        String name2 = "secondSymbol";
        symbolTable.openScope();
        symbolTable.openScope();
        symbolTable.enterSymbol(name1, null);
        symbolTable.enterSymbol(name2, null);
        symbolTable.closeScope();
        symbolTable.enterSymbol(name1, null);
        symbolTable.enterSymbol(name2, null);
    }

    @Test
    void testRetrieveSymbol01() {
        String name1 = "firstSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name1, null);
        assertEquals(name1, symbolTable.retrieveSymbol(name1).getName());
    }

    @Test
    void testRetrieveSymbol02() {
        String name1 = "firstSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name1, null);
        symbolTable.openScope();
        symbolTable.openScope();
        assertEquals(name1, symbolTable.retrieveSymbol(name1).getName());
    }

    @Test
    void testSymbolsAreRemovedFromHashMap01() {
        String name = "firstSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name, null);
        symbolTable.closeScope();
        assertThrows(NoSuchSymbolError.class, ()-> symbolTable.retrieveSymbol(name));
    }

    @Test
    void testSymbolsAreRemovedFromHashMap02() {
        String name1 = "firstSymbol";
        String name2 = "secondSymbol";
        String name3 = "thirdSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name1, null);
        symbolTable.openScope();
        symbolTable.enterSymbol(name2, null);
        symbolTable.openScope();
        symbolTable.enterSymbol(name3, null);
        symbolTable.closeScope();
        symbolTable.enterSymbol(name3, null);
        symbolTable.closeScope();
        symbolTable.enterSymbol(name2, null);
        symbolTable.closeScope();
        assertThrows(NoSuchSymbolError.class, ()-> symbolTable.retrieveSymbol(name1));
        assertThrows(NoSuchSymbolError.class, ()-> symbolTable.retrieveSymbol(name2));
        assertThrows(NoSuchSymbolError.class, ()-> symbolTable.retrieveSymbol(name3));
    }

    @Test
    void testSymbolsAreRemovedFromScopeDisplay() {
        String name1 = "firstSymbol";
        String name2 = "secondSymbol";
        symbolTable.openScope();
        symbolTable.enterSymbol(name1, null);
        symbolTable.openScope();
        symbolTable.enterSymbol(name2, null);
        symbolTable.closeScope();
        assertDoesNotThrow(() -> symbolTable.retrieveSymbol(name1));
        assertThrows(NoSuchSymbolError.class, ()-> symbolTable.retrieveSymbol(name2));
    }
}