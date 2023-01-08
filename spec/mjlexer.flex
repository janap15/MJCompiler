
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ispis greske i oporavak
	private Integer errorCol = 0;
	private StringBuilder errorStr = new StringBuilder();
	
	private void addError(){
		if (errorStr.length() == 0) errorCol = yycolumn + 1;
		errorStr.append(yytext());
	}
	
	private void printError(){
		if (errorStr.length() > 0) {
			System.err.println("Leksicka greska " + errorStr.toString() + " na liniji " + (yyline + 1) + " u koloni " + errorCol);
			errorStr = new StringBuilder();
		}
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		printError();
		return new Symbol(type, yyline + 1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		printError();
		return new Symbol(type, yyline + 1, yycolumn, value);
	}
	
%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ printError(); }
"\b" 	{ printError(); }
"\t" 	{ printError(); }
"\r\n" 	{ printError(); }
"\f" 	{ printError(); }

"program"		{ return new_symbol(sym.PROG, yytext()); }
"break"			{ return new_symbol(sym.BREAK, yytext()); }
"class"			{ return new_symbol(sym.CLASS, yytext()); }
"enum"			{ return new_symbol(sym.ENUM, yytext()); }
"else"			{ return new_symbol(sym.ELSE, yytext()); }
"const"			{ return new_symbol(sym.CONST, yytext()); }
"if"			{ return new_symbol(sym.IF, yytext()); }
"do"			{ return new_symbol(sym.DO, yytext()); }
"while"			{ return new_symbol(sym.WHILE, yytext()); }
"new"			{ return new_symbol(sym.NEW, yytext()); }
"print"			{ return new_symbol(sym.PRINT, yytext()); }
"read"			{ return new_symbol(sym.READ, yytext()); }
"return"		{ return new_symbol(sym.RETURN, yytext()); }
"void"			{ return new_symbol(sym.VOID, yytext()); }
"extends"		{ return new_symbol(sym.EXTENDS, yytext()); }
"continue"		{ return new_symbol(sym.CONTINUE, yytext()); }
"foreach"		{ return new_symbol(sym.FOREACH, yytext()); }

"++"			{ return new_symbol(sym.INCREMENT, yytext()); }
"--"			{ return new_symbol(sym.DECREMENT, yytext()); }
"+"				{ return new_symbol(sym.PLUS, yytext()); }
"-"				{ return new_symbol(sym.MINUS, yytext()); }
"*"				{ return new_symbol(sym.TIMES, yytext()); }
"%"				{ return new_symbol(sym.MODULO, yytext()); }
"=="			{ return new_symbol(sym.EQUAL, yytext()); }
"!="			{ return new_symbol(sym.NOTEQUAL, yytext()); }
"<="			{ return new_symbol(sym.LESSEQUAL, yytext()); }
">="			{ return new_symbol(sym.GREATEREQUAL, yytext()); }
">"				{ return new_symbol(sym.GREATER, yytext()); }
"<"				{ return new_symbol(sym.LESS, yytext()); }
"&&"			{ return new_symbol(sym.AND, yytext()); }
"||"			{ return new_symbol(sym.OR, yytext()); }
"="				{ return new_symbol(sym.ASSIGN, yytext()); }
";"				{ return new_symbol(sym.SEMI, yytext()); }
":"				{ return new_symbol(sym.COLON, yytext()); }
","				{ return new_symbol(sym.COMMA, yytext()); }
"."				{ return new_symbol(sym.DOT, yytext()); }
"("				{ return new_symbol(sym.LPAREN, yytext()); }
")"				{ return new_symbol(sym.RPAREN, yytext()); }
"["				{ return new_symbol(sym.LBRACKET, yytext()); }
"]"				{ return new_symbol(sym.RBRACKET, yytext()); }
"{"				{ return new_symbol(sym.LBRACE, yytext()); }
"}"				{ return new_symbol(sym.RBRACE, yytext()); }
"=>"			{ return new_symbol(sym.RIGHTARROW, yytext()); }

"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }
"/"		{ return new_symbol(sym.DIVIDES, yytext()); }

"true"|"false"  { return new_symbol(sym.BOOL, new Boolean (yytext())); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }
"'"[\x00-\x7e]"'"  { return new_symbol(sym.CHAR, new Character (yytext().charAt(1))); }

. { addError(); }

