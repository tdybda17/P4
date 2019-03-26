package Compiler.SymbolTable.Table;

import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.SymbolTable.ScopeError.IllegalSymbolNameError;
import Compiler.SymbolTable.Table.Scope.ScopeDisplay;
import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable implements ISymbolTable {

    private int depth;
    private ScopeDisplay scopeDisplay;
    private Map<String, SymbolList> hashMap;

    public SymbolTable() {
        this.depth = 0;
        this.hashMap = new HashMap<>();
        this.scopeDisplay = new ScopeDisplay();
    }

    @Override
    public void openScope() {
        depth += 1; // First scope will be 1
        scopeDisplay.open(depth);
    }

    @Override
    public void closeScope() {
        // Delete symbols currently in scope
        deleteSymbolsInCurrentDepth();
        depth -= 1;
    }

    private void deleteSymbolsInCurrentDepth() {
        List<Symbol> symbolsToRemove = scopeDisplay.remove(depth);
        for (Symbol symbol : symbolsToRemove)
            hashMap.remove(symbol.getName());
    }

    @Override
    public void enterSymbol(String name, Attributes attributes) {
        validateInputName(name);
        Symbol oldSymbol = retrieveSymbol(name);
        if(oldSymbol == null) {
            Symbol newSymbol = new Symbol(name, attributes, depth, scopeDisplay.get(depth));
            scopeDisplay.add(newSymbol);
            addToHashTable(newSymbol);
        } else {
            throw new DuplicateSymbolError(oldSymbol);
        }
    }

    private void validateInputName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalSymbolNameError("Name were null or blank");
        }
    }

    private void addToHashTable(Symbol symbol) {
        String name = symbol.getName();
        if(!hashMap.containsKey(name))
            hashMap.put(name, new SymbolList());
        hashMap.get(name).add(symbol);
    }

    @Override
    public Symbol retrieveSymbol(String name) {
        SymbolList symbolList = hashMap.get(name);
        if(symbolList != null)
            return symbolList.get(name);
        else return null;
    }

    @Override
    public boolean declaredLocally(String name) {
        return false;
    }

    /* Method for testing */
    Map<String, SymbolList> getHashMap() {
        return hashMap;
    }

    /* Method for testing */
    SymbolList getCurrentScopeDisplay() {
        return scopeDisplay.get(depth);
    }
}
