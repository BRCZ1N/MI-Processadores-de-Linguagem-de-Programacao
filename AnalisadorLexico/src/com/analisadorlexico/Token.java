package com.analisadorlexico;

public class Token {

	private String lexeme;
	private String typeToken;
	// private long line;
	// private long columm;

	public enum InitialsToken {

		TK_RESERVED_WORDS("PRE"), TK_IDENTIFIER("IDE"), TK_NUMBER("NRO"), TK_DELIMITER("DEL"),
		TK_RELATIONAL_OPERATOR("REL"), TK_LOGIC_OPERATOR("LOG"), TK_ARITHIMETIC_OPERATOR("ART"),
		TK_MALFORMED_CHAIN("CMF"), TK_POORLY_FORMED_COMMENT("CoMF"), TK_INVALID_CHARACTER("CIN"), TK_STRING("STG"),
		TK_COMMENT("CMT");

		private InitialsToken(String string) {

		}

	}

	public enum ReservedWords {
		TK_RESERVED_VAR("var"), TK_RESERVED_CONST("const"), TK_RESERVED_STRUCT("struct"),
		TK_RESERVED_EXTENDS("extends"), TK_RESERVED_PROCEDURE("procedure"), TK_RESERVED_FUCTION("function"),
		TK_RESERVED_START("start"), TK_RESERVED_RETURN("return"), TK_RESERVED_IF("if"), TK_RESERVED_ELSE("else"),
		TK_RESERVED_THEN("then"), TK_RESERVED_WHILE("while"), TK_RESERVED_READ("read"), TK_RESERVED_PRINT("print"),
		TK_RESERVED_INT("int"), TK_RESERVED_REAL("real"), TK_RESERVED_BOOLEAN("boolean"), TK_RESERVED_STRING("string"),
		TK_RESERVED_TRUE("true"), TK_RESERVED_FALSE("false");

		private ReservedWords(String string) {

		}
	}

	public Token(String lexeme, String nameToken) {

		this.lexeme = lexeme;
		this.typeToken = nameToken;
		// this.line = line;
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
		return this.typeToken + "" + "" + this.lexeme;
	}

}
