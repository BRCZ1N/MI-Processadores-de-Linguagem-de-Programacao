package com.analisador;

public class FunctionsSymbolTableObject {

	private String nameParam;
	private String typeParam;

	public FunctionsSymbolTableObject(String nameParam, String typeParam) {

		this.nameParam = nameParam;
		this.typeParam = typeParam;

	}

	public String getNameParam() {

		return nameParam;

	}

	public void setNameParam(String nameParam) {

		this.nameParam = nameParam;

	}

	public String getTypeParam() {

		return typeParam;

	}

	public void setTypeParam(String typeParam) {

		this.typeParam = typeParam;

	}

}
