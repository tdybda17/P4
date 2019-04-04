package Compiler.SymbolTable.Table.Scope;

import Compiler.Exceptions.SymbolTable.ScopeError.AddingToClosedScopeDisplayError;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopeDisplay {
    private Map<Integer, SymbolList> symbolMap;

    public ScopeDisplay() {
        this.symbolMap = new HashMap<>();
    }

    public SymbolList get(final int depth) {
        return symbolMap.get(depth);
    }

    public void open(final int depth) {
        symbolMap.put(depth, new SymbolList());
    }

    public void add(Symbol symbol, int depth) {
        SymbolList symbolList = symbolMap.get(depth);
        if(symbolList == null) {
            throw new AddingToClosedScopeDisplayError("The depth: " + depth + ", was already closed.");
        } else {
            symbolMap.get(depth).add(symbol);
        }
    }

    public List<Symbol> remove(int depth) {
        List<Symbol> symbolsToRemove = symbolMap.get(depth).asList();
        symbolMap.remove(depth);
        return symbolsToRemove;
    }

}
