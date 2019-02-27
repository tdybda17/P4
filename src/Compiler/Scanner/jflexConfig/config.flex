package Compiler.Scanner.jflexConfig;

import java_cup.runtime.*;
import Compiler.Scanner.Symbol.EduSymbols;
import static Compiler.Scanner.Symbol.EduSymbols.*;

%%
%public
%class JFlexScanner
%unicode
%line
%column
%cup

%{
    /* Callable methods */
    private Symbol symbol(EduSymbols javaCupSym) {
        return new Symbol(javaCupSym.getValue(), yyline, yycolumn);
    }

    private Symbol symbol(EduSymbols javaCupSym, Object value) {
        return new Symbol(javaCupSym.getValue(), yyline, yycolumn, value);
    }

    private int parseToInt(String value) {
        return Integer.parseInt(value);
    }

    private double parseToReal(String value) {
        return Double.parseDouble(value);
    }

%}

/* identifiers */
Identifier      = [a-zA-Z]([0-9a-zA-Z])*

/* numbers */
AfterNumber     = [\n] | [\t] | [" "]
Integer         = 0 | [1-9][0-9]*
Real            = {Integer}[.][0-9]*
Comment         = "//"[^\n]*
%%

<YYINITIAL> {

      /* KEYWORDS */
      "create"                 { return symbol(CREATE); }
      "if"                     { return symbol(IF); }
      "else"                   { return symbol(ELSE); }
      "while"                  { return symbol(WHILE); }
      "for"                    { return symbol(FOR); }
      "end"                    { return symbol(END); }
      "do"                     { return symbol(DO); }
      "function"               { return symbol(FUNCTION); }
      "foreach"                { return symbol(FOREACH); }
      "in"                     { return symbol(IN); }
      "then"                   { return symbol(THEN); }
      "int"                    { return symbol(INT); }
      "real"                   { return symbol(REAL); }
      "Vertex"                 { return symbol(VERTEX); }
      "Edge"                   { return symbol(EDGE); }

      /* OPERATORS */
      "+"                      { return symbol(PLUS); }
      "-"                      { return symbol(MINUS); }
      "*"                      { return symbol(MULT); }
      "/"                      { return symbol(DIV); }
      "=="                     { return symbol(EQEQ); }
      "="                      { return symbol(EQUALS); }

      /* SEPARATORS */
      "("                      { return symbol(LPAREN); }
      ")"                      { return symbol(RPAREN); }
      "\n"                     { return symbol(NEWLINE); }

      /* IGNORE */
      " "                      { /* Ignore space */ }
      "\t"                     { /* Ignore tab */ }

      /* NUMBERS */
      {Integer}                { return symbol(INTEGER_LITERAL, parseToInt(yytext())); }
      {Real}                   { return symbol(REAL_LITERAL, parseToReal(yytext())); }

      /* IDENTIFIER */
      {Identifier}             { return symbol(IDENTIFIER, yytext()); }

      /* COMMENT */
      {Comment}                { return symbol(COMMENT); }


}

<<EOF>>                        { return symbol(EOF); }