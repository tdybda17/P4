package Compiler.SymbolTable.Table.Symbol.SymbolList;

import Compiler.SymbolTable.Table.Symbol.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SymbolList {

    private List<Symbol> symbols;

    public SymbolList() {
        this.symbols = new ArrayList<>();
    }

    public boolean add(Symbol symbol) {
        if(!BinarySymbolSearch.exists(this, symbol)) {
            symbols.add(symbol);
            Collections.sort(symbols);
            return true;
        } else {
            return false;
        }
    }

    public Symbol get(String name) {
        return BinarySymbolSearch.find(this, name);
    }

    public Symbol get(int index) {
        return symbols.get(index);
    }

    public List<Symbol> asList() {
        return symbols;
    }

}
