package Compiler.SymbolTable.Table;

import Compiler.Exceptions.SymbolTable.ScopeError.DuplicateSymbolError;
import Compiler.Exceptions.SymbolTable.ScopeError.IllegalSymbolNameError;
import Compiler.Exceptions.SymbolTable.ScopeError.NoSuchSymbolError;
import Compiler.Exceptions.SymbolTable.SymbolTableException;
import Compiler.SymbolTable.Table.Scope.ScopeDisplay;
import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.*;

public class SymbolTable implements ISymbolTable {
    private ScopeDisplay scopeDisplay;
    private Map<String, Symbol> hashMap;

    public SymbolTable() {
        this.hashMap = new HashMap<>();
        this.scopeDisplay = new ScopeDisplay();
    }

    @Override
    public void openScope() {
        scopeDisplay.open();
    }

    @Override
    public void closeScope() {
        // Delete symbols currently in scope
        deleteSymbolsInCurrentDepth();
    }

    private void deleteSymbolsInCurrentDepth() {
        List<Symbol> symbolsToRemove = scopeDisplay.close();
        for (Symbol symbol : symbolsToRemove)
            hashMap.remove(symbol.getName());
    }

    @Override
    public void enterSymbol(String name, Attributes attributes) {
        validateInputName(name);

        Symbol oldSymbol = hashMap.get(name);
        if(oldSymbol == null) {
            Symbol newSymbol = new Symbol(name, attributes);
            scopeDisplay.add(newSymbol);
            addToHashTable(newSymbol);
        } else {
            throw new DuplicateSymbolError(oldSymbol);
        }
    }

    public void enterSymbol(Symbol symbol) {
        enterSymbol(symbol.getName(), symbol.getAttributes());
    }

    private void validateInputName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalSymbolNameError("Name were null or blank");
        }
    }

    private void addToHashTable(Symbol symbol) {
        String name = symbol.getName();
        if(!hashMap.containsKey(name)) {
            hashMap.put(name, symbol);
        }
        else {
            throw new DuplicateSymbolError("An symbol with the name: " + name + ", already exists in the table");
        }
    }

    @Override
    public Symbol retrieveSymbol(String name) {
        Symbol symbol = hashMap.get(name);
        if(symbol!= null)
            return symbol;
        else throw new NoSuchSymbolError("The given identifier name: " + name + ", could not be retrieved because no mapping exists for it");
    }

    public boolean containsSymbol(String name) {
        Symbol symbol = hashMap.get(name);
        return symbol != null;
    }

    @Override
    public String toString() {
        return  scopeDisplay.toString() + "\n" +
                "hashMap = " + hashMap + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolTable that = (SymbolTable) o;
        return  Objects.equals(scopeDisplay, that.scopeDisplay) &&
                Objects.equals(hashMap, that.hashMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scopeDisplay, hashMap);
    }
}
