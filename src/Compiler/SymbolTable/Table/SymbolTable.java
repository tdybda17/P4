package Compiler.SymbolTable.Table;

import Compiler.Exceptions.SymbolTableExceptions.DuplicationSymbolException;
import Compiler.SymbolTable.Table.Symbol.Symbol;

import java.util.TreeSet;

public class SymbolTable implements ISymbolTable {

    private TreeSet<Symbol> symbols;

    public SymbolTable() {
        symbols = new TreeSet<>();
    }

    @Override
    // Opens a new scope relatively to the current scope
    public void openScope() {

    }

    @Override
    // Closes the current scope and jumps to currents inner scope
    public void closeScope() {

    }

    @Override
    public void enterSymbol(Symbol symbol) {
        if(!symbols.add(symbol)) {
            throw new DuplicationSymbolException(symbol);
        }
    }

    @Override
    public Symbol retrieveSymbol(String name) {
        return null;
    }

    @Override
    public boolean declaredLocally(String name) {
        return false;
    }

    public TreeSet<Symbol> getSymbols() {
        return symbols;
    }
}
