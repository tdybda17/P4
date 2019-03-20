package Compiler.SymbolTable.Table.Scope;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.HashMap;
import java.util.Map;

public class ScopeDisplay {

    private Map<Integer, SymbolList> symbolMap;

    public ScopeDisplay() {
        this.symbolMap = new HashMap<>();
    }

    public SymbolList get(final int depth) {
        return symbolMap.get(depth);
    }

    public void add(final int depth, Symbol symbol) {
        if(!symbolMap.containsKey(depth))
            symbolMap.put(depth, new SymbolList());
        symbolMap.get(depth).add(symbol);
    }

}
