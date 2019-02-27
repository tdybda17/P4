package Compiler.Scanner.Symbol;

/* Symbol specification for {{LanguageName}} */
// TODO: Rename this class when program name found

import Exceptions.NoSuchEduSymbolException;

public enum EduSymbols {

    EOF(0),

    /* KEYWORDS */
    CREATE(3), IF(4), ELSE(5), FOR(6), WHILE(7), END(8), IDENTIFIER(9), DO(18), FUNCTION(19),
    FOREACH(20), IN(21), THEN(22), INT(23), REAL(24), VERTEX(26), EDGE(27),

    /* OPERATORS */
    PLUS(1), MINUS(2), MULT(12), DIV(13), EQEQ(16),

    /* SEPARATORS */
    LPAREN(10), RPAREN(11), NEWLINE(17),

    /* NUMBERS */
    INTEGER_LITERAL(14), REAL_LITERAL(15),

    /* COMMENT */
    COMMENT(25)

    ;

    private int value;

    EduSymbols(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static EduSymbols getEduSymbol(int value) {
        for(EduSymbols sym : EduSymbols.values())
            if(sym.value == value)
                return sym;
        throw new NoSuchEduSymbolException("No symbol with value: " + value);
    }

    @Override
    public String toString() {
        return super.toString() + "(" + value + ")";
    }
}
