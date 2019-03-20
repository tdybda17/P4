package Compiler.SymbolTable.Table.Symbol.SymbolList;

import Compiler.SymbolTable.Table.Symbol.Symbol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SymbolList {

    private List<Symbol> symbols;

    public SymbolList() {
        this.symbols = new ArrayList<>();
    }

    public SymbolList(Symbol ... symbols) {
        this.symbols = new ArrayList<>();
        add(symbols);
    }

    public void add(Symbol ... symbols) {
        for (Symbol sym : symbols)
            add(sym);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolList that = (SymbolList) o;
        return Objects.equals(symbols, that.symbols);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbols);
    }
}
