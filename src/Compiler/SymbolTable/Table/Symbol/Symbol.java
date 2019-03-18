package Compiler.SymbolTable.Table.Symbol;

import Compiler.Exceptions.SymbolTableExceptions.NoValueSetForSymbol;

import java.util.Objects;

public class Symbol implements Comparable<Symbol> {

    private int num; // TODO: firgure out how to handle this
    private String name;
    private Object value;

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        if(value != null) return value;
        else throw new NoValueSetForSymbol("No value for symbol: " + name);
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

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
