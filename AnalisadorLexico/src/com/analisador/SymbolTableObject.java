package com.analisador;

public class SymbolTableObject {

	private String lexeme;
	private Long line;
	private String typeDateToken;
	private String typeToken;
//  private String categoryObject;
//	private String returnType;

	public SymbolTableObject(Token token, String typeDateToken) {

		this.lexeme = token.getLexeme();
		this.line = token.getLine();
		this.typeDateToken = typeDateToken;
		this.typeToken = token.getTypeToken();

	}

	public String getLexeme() {
		return lexeme;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}

	public Long getLine() {
		return line;
	}

	public void setLine(Long line) {
		this.line = line;
	}

	public String getTypeDateToken() {
		return typeDateToken;
	}

	public void setTypeDateToken(String typeDateToken) {
		this.typeDateToken = typeDateToken;
	}

	public String getTypeToken() {
		return typeToken;
	}

	public void setTypeToken(String typeToken) {
		this.typeToken = typeToken;
	}

//	public String getReturnType() {
//		return returnType;
//	}
//
//	public void setReturnType(String returnType) {
//		this.returnType = returnType;
//	}

}
