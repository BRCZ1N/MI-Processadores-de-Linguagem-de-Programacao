package com.analiser.semantic;

import java.util.LinkedList;

import com.analiser.lexical.Token;

public class SymbolTableObject {

	private String id;
	private Long line;
	private String typeDate;
	private String typeToken;
	private LinkedList<AttributesSymbolTableObject> paramFunc;
	private String typeReturn;
	private String categoryObject;

	public SymbolTableObject() {

	}
	
	public SymbolTableObject(String categoryObject) {
		
		this.categoryObject = categoryObject;

	}

	public SymbolTableObject(Token token, String typeDate) {

		this.id = token.getLexeme();
		this.line = token.getLine();
		this.typeDate = typeDate;
		this.typeToken = token.getTypeToken();

	}

	public SymbolTableObject(Token token, LinkedList<AttributesSymbolTableObject> paramFunc, String typeReturn) {

		this.id = token.getLexeme();
		this.line = token.getLine();
		this.paramFunc = paramFunc;
		this.typeReturn = typeReturn;

	}
	
	public SymbolTableObject(Token token, LinkedList<AttributesSymbolTableObject> paramFunc) {

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

	public LinkedList<AttributesSymbolTableObject> getParamFunc() {
		return paramFunc;
	}

	public void setParamFunc(LinkedList<AttributesSymbolTableObject> paramFunc) {
		this.paramFunc = paramFunc;
	}

	public String getTypeReturn() {
		return typeReturn;
	}

	public void setTypeReturn(String typeReturn) {
		this.typeReturn = typeReturn;
	}

	public String getCategoryObject() {
		return categoryObject;
	}

	public void setCategoryObject(String categoryObject) {
		this.categoryObject = categoryObject;
	}

}
