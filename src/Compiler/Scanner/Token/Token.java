package Compiler.Scanner.Token;

import Compiler.Scanner.Symbol.EduSymbols;
import java_cup.runtime.Symbol;

public class Token {

    private Symbol javaCupSym;
    private EduSymbols eduSymbol;

    public Token(Symbol javaCupSym, EduSymbols eduSymbol) {
        this.javaCupSym = javaCupSym;
        this.eduSymbol = eduSymbol;
    }

    public EduSymbols getEduSymbol() {
        return eduSymbol;
    }

    public int getLineNumber() {
        return javaCupSym.left + 1;
    }

    public int getColumnNumber() {
        return javaCupSym.right + 1;
    }

    public Object getValue() {
        return javaCupSym.value;
    }

    public Symbol getJavaCupSym() {
        return javaCupSym;
    }

    @Override
    public String toString() {
        return "Token{'" + getEduSymbol().toString() + "', value: '" + getValue() + "'}";
    }
}
