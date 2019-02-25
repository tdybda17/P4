package Compiler.Scanner.Symbol;

/* Symbol specification for {{LanguageName}} */
// TODO: Rename this class when program name found

public enum EduSymbols {

    EOF(0),

    /* KEYWORDS */
    CREATE(3), IF(4), ELSE(5), FOR(6), WHILE(7), END(8),

    /* OPERATORS */
    PLUS(1), MINUS(2)

    /* SEPARATORS */

    ;

    private int value;

    EduSymbols(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
