package com.analisadorlexico;

public class ErrorToken extends Token {

	public ErrorToken(String typeToken, String lexeme, long line) {

		super(typeToken, lexeme, line);

	}
	
	@Override
	public String toString() {
		return getLine()+" "+getTypeToken()+" "+getLexeme();
	}
	
	
}