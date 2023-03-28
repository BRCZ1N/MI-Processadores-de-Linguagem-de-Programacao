package com.analiser.utilities;

public enum Categories {

	CAT_FUNCTION("FUNCTION"),
	CAT_PROCEDURE("PROCEDURE"),
	CAT_STRUCT("STRUCT"), 
	CAT_VAR("VAR"), 
	CAT_CONST("CONST");

	private String catCode;

	private Categories(String catCode) {

		this.catCode = catCode;

	}

	public String getCatCode() {
		return catCode;
	}

	@Override
	public String toString() {

		return this.catCode;

	}

}
