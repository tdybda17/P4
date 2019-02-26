package Compiler.Scanner.jflexConfig;

import java_cup.sym;
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
    private Symbol symbol(EduSymbols javaCupSym) { return new Symbol(javaCupSym.getValue(), yyline, yycolumn); }
    private Symbol symbol(EduSymbols javaCupSym, Object value) { return new Symbol(javaCupSym.getValue(), yyline, yycolumn, value); }

%}

/* identifiers */
Identifier      = [a-zA-Z]([0-9a-zA-Z])*

/* numbers */
AfterNumber     = [\n] | [\t] | [" "]
Integer         = 0 | [1-9][0-9]*
Real            = {Integer}[.][0-9]*

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

      /* OPERATORS */
      "+"                      { return symbol(PLUS); }
      "-"                      { return symbol(MINUS); }
      "*"                      { return symbol(MULT); }
      "/"                      { return symbol(DIV); }
      "=="                     { return symbol(EQEQ); }

      /* SEPARATORS */
      "("                      { return symbol(LPAREN); }
      ")"                      { return symbol(RPAREN); }
      "\n"                     { return symbol(NEWLINE); }

      /* IGNORE */
      " "                      { /* Ignore space */ }
      "\t"                     { /* Ignore tab */ }

      /* NUMBERS */
      {Integer}                { return symbol(INTEGER); }
      {Real}                   { return symbol(REAL); }

      /* IDENTIFIER */
      {Identifier}             { return symbol(IDENTIFIER, yytext()); }


}

<<EOF>>                        { return symbol(EOF); }