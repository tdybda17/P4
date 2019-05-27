package Compiler.SymbolTable.Table.Scope;

import Compiler.Exceptions.SymbolTable.ScopeError.AddingToClosedScopeDisplayError;
import Compiler.Exceptions.SymbolTable.ScopeError.GettingFromClosedScopeDisplayError;
import Compiler.SymbolTable.Table.Symbol.Symbol;
import Compiler.SymbolTable.Table.Symbol.SymbolList.SymbolList;

import java.util.*;

public class ScopeDisplay {
    private Stack<SymbolList> symbolStack;

    public ScopeDisplay() {
        this.symbolStack = new Stack<>();
    }

    public SymbolList getCurrentSymbolList() {
        if(symbolStack.isEmpty()) {
            throw new GettingFromClosedScopeDisplayError("You tried to get the symbol list of an empty scope display");
        }
        return symbolStack.peek();
    }

    public void open() {
        symbolStack.push(new SymbolList());
    }

    public void add(Symbol symbol) {
        if(symbolStack.isEmpty()) {
            throw new AddingToClosedScopeDisplayError("You tried to add a symbol to a closed scope display");
        }

        SymbolList temp = symbolStack.pop();
        temp.add(symbol);
        symbolStack.push(temp);
    }

    public List<Symbol> close() {
        return symbolStack.pop().asList();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScopeDisplay {\n");
        for(SymbolList list : symbolStack) {
            stringBuilder.append('\t').append(list).append('\n');
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScopeDisplay that = (ScopeDisplay) o;
        return Objects.equals(symbolStack, that.symbolStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbolStack);
    }
}
