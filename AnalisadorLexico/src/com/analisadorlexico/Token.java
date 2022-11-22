package com.analisadorlexico;

//classe dos tokens 
public class Token {

	private String lexeme;
	private String typeToken;
	private long line;

	public Token(String typeToken, String lexeme, long line) {

		this.lexeme = lexeme;
		this.typeToken = typeToken;
		this.line = line;

	}

	public Token(String typeToken, long line) {

		this.typeToken = typeToken;
		this.line = line;

	}

	public Token() {

		this.typeToken = "";
		this.lexeme = "";

	}

	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	public String getTypeToken() {
		return typeToken;
	}

	public void setTypeToken(String typeToken) {
		this.typeToken = typeToken;
	}

	public long getLine() {
		return line;
	}

	public void setLine(long line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return this.line + " " + this.typeToken + " " + this.lexeme + " ";
	}

}
