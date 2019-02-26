package Compiler.Scanner;

import Compiler.Scanner.Symbol.EduSymbols;
import Compiler.Scanner.Token.Token;
import Compiler.Scanner.jflexConfig.JFlexScanner;
import Exceptions.SyntaxError;
import java_cup.runtime.Symbol;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/* Scanner wrapper for the jFlex generated scanner */

public class Scanner extends JFlexScanner {

    private List<Token> tokenList;

    public Scanner(Reader in) {
        super(in);
        tokenList = new ArrayList<>();
    }

    public Scanner(String in) {
        super(new StringReader(in));
        tokenList = new ArrayList<>();
    }

    public List<Token> scan() {
        Symbol javaCupSym;
        Token token = null;
        try {
            while((javaCupSym = super.next_token()).sym != 0) { // Where 0 is EOF
                token = createToken(javaCupSym);
                tokenList.add(token);
            }
        }
        catch (Error e) {
            if(token != null) throw new SyntaxError(token);
            else throw new SyntaxError("No tokens were created");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return tokenList;
    }

    private Token createToken(Symbol javaCupSym) {
        return new Token(
                javaCupSym,
                EduSymbols.getEduSymbol(javaCupSym.sym)
        );
    }

    public List<Token> getTokenList() {
        return tokenList;
    }
}
