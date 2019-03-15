package Compiler.SymbolTable.Table.Scope;

import Compiler.SymbolTable.Table.Symbol.Symbol;
import org.w3c.dom.Node;

import java.util.List;

public class Scope implements IScope {

    private List<Symbol> symbols;
    private Scope innerScope;
    private Scope outerScope;

    @Override
    public IScope getInnerScope() {
        return innerScope;
    }

    @Override
    public IScope getOuterScope() {
        return outerScope;
    }

    @Override
    public List<Symbol> getSymbols() {
        return symbols;
    }
}
