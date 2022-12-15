package com.analisador;

import java.util.LinkedList;

public class SymbolTableObject {

	private String id;
	private Long line;
	private String typeDate;
	private String typeToken;
	private LinkedList<FunctionsSymbolTableObject> paramFunc;
	private String typeReturn;
	//private String categoryObject;

	public SymbolTableObject() {

	}

	public SymbolTableObject(Token token, String typeDate) {

		this.id = token.getLexeme();
		this.line = token.getLine();
		this.typeDate = typeDate;
		this.typeToken = token.getTypeToken();

	}

	public SymbolTableObject(Token token, LinkedList<FunctionsSymbolTableObject> paramFunc, String typeReturn) {

		this.id = token.getLexeme();
		this.line = token.getLine();
		this.paramFunc = paramFunc;
		this.typeReturn = typeReturn;

	}
	
	public SymbolTableObject(Token token, LinkedList<FunctionsSymbolTableObject> paramFunc) {

		this.id = token.getLexeme();
		this.line = token.getLine();
		this.paramFunc = paramFunc;
		this.typeReturn = null;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getLine() {
		return line;
	}

	public void setLine(Long line) {
		this.line = line;
	}

	public String getTypeDate() {
		return typeDate;
	}

	public void setTypeDate(String typeDate) {
		this.typeDate = typeDate;
	}

	public String getTypeToken() {
		return typeToken;
	}

	public void setTypeToken(String typeToken) {
		this.typeToken = typeToken;
	}

	public LinkedList<FunctionsSymbolTableObject> getParamFunc() {
		return paramFunc;
	}

	public void setParamFunc(LinkedList<FunctionsSymbolTableObject> paramFunc) {
		this.paramFunc = paramFunc;
	}

	public String getTypeReturn() {
		return typeReturn;
	}

	public void setTypeReturn(String typeReturn) {
		this.typeReturn = typeReturn;
	}

}
