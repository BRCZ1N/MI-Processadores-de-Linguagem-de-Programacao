package com.analisadorlexico;

public class Token {

	private String lexeme;
	private String typeToken;
//	private long   line;
//	private long   columm;

	public enum SiglaToken {

		TK_PALAVRA_RESERVADA("PRE"), TK_IDENTIFICADOR("IDE"), TK_NUMERO("NRO"), TK_DELIMITADOR("DEL"), TK_OPERADOR_RELACIONAL("REL"),
		TK_OPERADOR_LOGICO("LOG"), TK_OPERADOR_ARITMETICO("ART"), TK_CADEIA_MAL_FORMADA("CMF"), TK_COMENTARIO_MAL_FORMADO("CoMF"),
		TK_CARACTER_INVALIDO("CIN");

		private SiglaToken(String string) {

		}

	}

	public Token(String lexeme, String nameToken) {

		this.lexeme = lexeme;
		this.typeToken = nameToken;

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
