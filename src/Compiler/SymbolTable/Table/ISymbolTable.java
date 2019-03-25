package Compiler.SymbolTable.Table;

import Compiler.SymbolTable.Table.Symbol.Symbol;

public interface ISymbolTable {

    void openScope();
    void closeScope();
    void enterSymbol(String name, Object type);
    Symbol retrieveSymbol(String name);
    boolean declaredLocally(String name);

}
