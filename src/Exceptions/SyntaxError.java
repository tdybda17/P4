package Exceptions;

import Compiler.Scanner.Symbol.EduSymbols;
import Compiler.Scanner.Token.Token;
import java_cup.runtime.Symbol;

public class SyntaxError extends RuntimeException {

    public SyntaxError(Token token) {
        super("After " + token.getEduSymbol() + " at line " + token.getLineNumber());
    }

    public SyntaxError(String message) {
        super(message);
    }

}
