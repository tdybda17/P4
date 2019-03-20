package Compiler.SymbolTable.Table;

import Compiler.SymbolTable.Table.Scope.ScopeDisplay;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable implements ISymbolTable {

    private int depth;
    private ScopeDisplay scopeDisplay;
    private Map<String, SymbolList> hashTable;

    public SymbolTable() {
        this.depth = 0;
        this.hashTable = new HashMap<>();
        this.scopeDisplay = new ScopeDisplay();
    }

    @Override
    public void openScope() {
        depth += 1; // First scope will be 1
    }

    @Override
    public void closeScope() {
        depth -= 1;
    }

    @Override
    public void enterSymbol(Symbol symbol) {
    }

    @Override
    public Symbol retrieveSymbol(String name) {
        SymbolList symbolList = hashTable.get(name);
        return null;
    }

    @Override
    public boolean declaredLocally(String name) {
        return false;
    }


}
