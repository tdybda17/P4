package Exceptions;

import Compiler.Scanner.Token.Token;

public class SyntaxError extends RuntimeException {

    public SyntaxError(Token token) {
        super("After " + token.getEduSymbol() + " at line " + token.getLineNumber());
    }

    public SyntaxError(String message) {
        super(message);
    }

}
