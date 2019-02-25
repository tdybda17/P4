package Compiler.Scanner.jflexConfig;

import java_cup.sym;
import java_cup.runtime.*;
import Compiler.Scanner.Symbol.EduSymbols;
import static Compiler.Scanner.Symbol.EduSymbols.*;

%%
%public
%class Scanner
%unicode
%line
%column
%cup

%{
    /* Callable methods */
    private Symbol symbol(EduSymbols sym) { return new Symbol(sym.getValue(), yyline, yycolumn); }
%}

%%


<YYINITIAL> {

      /* KEYWORDS */
      "create"                 { return symbol(CREATE); }
      "if"                     { return symbol(IF); }
      "else"                   { return symbol(ELSE); }
      "while"                  { return symbol(WHILE); }
      "for"                    { return symbol(FOR); }
      "end"                    { return symbol(END); }

      /* OPERATORS */
      "+"                      { return symbol(PLUS); }
      "-"                      { return symbol(MINUS); }

      /* SEPARATORS */


      /* IGNORE */
      " "                      { /* Ignore space and tab */ }
      "\n"                     { /* Ignore space and tab */ }
      "\t"                     { /* Ignore space and tab */ }

}

<<EOF>>                        { return symbol(EOF); }