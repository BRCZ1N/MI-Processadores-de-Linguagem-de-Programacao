package com.analisadorlexico;

public class Token {

	private String lexeme;
	private String typeToken;
	private long line;
	// private long columm;

	public Token(String typeToken, String lexeme, long line) {

		this.lexeme = lexeme;
		this.typeToken = typeToken;
		this.line = line;
		// this.columm = column;

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

//	public long getColumm() {
//		return columm;
//	}
//
//	public void setColumm(long columm) {
//		this.columm = columm;
//	}

	@Override
	public String toString() {
		return this.line+" "+this.typeToken+" "+ this.lexeme;
	}

}
