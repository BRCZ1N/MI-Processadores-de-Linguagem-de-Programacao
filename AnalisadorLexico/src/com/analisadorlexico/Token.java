package com.analisadorlexico;

public class Token {

	private String lexeme;
	private String typeToken;
	//private long line;
	//private long columm;

	public enum InitialsToken {

		TK_RESERVED_WORDS("PRE"), TK_IDENTIFIER("IDE"), TK_NUMBER("NRO"), TK_DELIMITER("DEL"), TK_RELATIONAL_OPERATOR("REL"),
		TK_LOGIC_OPERATOR("LOG"), TK_ARITHIMETIC_OPERATOR("ART"), TK_MALFORMED_CHAIN("CMF"), TK_POORLY_FORMED_COMMENT("CoMF"),
		TK_INVALID_CHARACTER("CIN"), TK_STRING("STG");

		private InitialsToken(String string) {

		}

	}

	public Token(String lexeme, String nameToken) {

		this.lexeme = lexeme;
		this.typeToken = nameToken;
		//this.line = line;
		//this.columm = column;

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

	public void setTypeToken(String nameToken) {
		this.typeToken = nameToken;
	}

//	public long getLine() {
//		return line;
//	}
//
//	public void setLine(long line) {
//		this.line = line;
//	}
//
//	public long getColumm() {
//		return columm;
//	}
//
//	public void setColumm(long columm) {
//		this.columm = columm;
//	}
	
	@Override
	public String toString() {
		return this.typeToken+""+""+this.lexeme ;
	}

}
