package Compiler.SymbolTable.Table;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable implements ISymbolTable {

    private int scopeDepth;
    private Map<String, SymbolList> hashTable;
    private SymbolList symbols;

    public SymbolTable() {
        this.scopeDepth = 0;
        this.hashTable = new HashMap<>();
        symbols = new SymbolList();
    }

    @Override
    // Opens a new scope relatively to the current scope
    public void openScope() {

        scopeDepth += 1;
    }

    @Override
    // Closes the current scope and jumps to currents inner scope
    public void closeScope() {

        scopeDepth -= 1;
    }

    @Override
    public void enterSymbol(Symbol symbol) {
        
    }

    @Override
    public Symbol retrieveSymbol(String name) {
        return symbols.get(name);
    }

    @Override
    public boolean declaredLocally(String name) {
        return false;
    }

    public SymbolList getSymbols() {
        return symbols;
    }

}
