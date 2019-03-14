package Compiler.SymbolTable.Table;

import Compiler.SymbolTable.Table.Symbol.Symbol;

public class SymbolTable implements ISymbolTable {

    @Override
    public void enterSymbol(Symbol symbol) {

    }

    @Override
    public Symbol retrieveSymbol(String name) {
        return null;
    }

    @Override
    public boolean declaredLocally(String name) {
        return false;
    }
}
