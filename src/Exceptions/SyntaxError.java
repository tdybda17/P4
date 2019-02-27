package Exceptions;

import Compiler.Scanner.Token.Token;

public class SyntaxError extends RuntimeException {

    private String message;

    public SyntaxError(Token token, Throwable e) {
        if(e instanceof NumberFormatException) {
            // Overflow error
            this.message = getStandardErrorMsg(token);
            this.message += "Reason: Overflow";
        } else if(e instanceof Error) {
            this.message = getStandardErrorMsg(token);
            this.message += e.getMessage();
        }
    }

    private String getStandardErrorMsg(Token token) {
        return "After " + token.getEduSymbol() + " at line " + token.getLineNumber() + "\n";
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public SyntaxError(String message) {
        super(message);
    }

}
