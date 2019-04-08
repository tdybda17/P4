package Compiler.SymbolTable.Table.Symbol;

import Compiler.SymbolTable.Table.Symbol.Attributes.Attributes;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.Objects;

public class Symbol implements Comparable<Symbol> {
    private String name;
    private Attributes attributes;

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, Attributes attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Symbol sym) {
        return this.name.compareTo(sym.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        return Objects.equals(getName(), symbol.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
