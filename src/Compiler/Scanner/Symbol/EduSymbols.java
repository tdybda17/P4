package Compiler.Scanner.Symbol;

/* Symbol specification for {{LanguageName}} */
// TODO: Rename this class when program name found

public enum EduSymbols {

    EOF(0),

    /* KEYWORDS */
    CREATE(3), IF(4), ELSE(5), FOR(6), WHILE(7), END(8), IDENTIFIER(9),

    /* OPERATORS */
    PLUS(1), MINUS(2), MULT(12), DIV(13),

    /* SEPARATORS */
    LPAREN(10), RPAREN(11)

    ;

    private int value;

    EduSymbols(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }
}
