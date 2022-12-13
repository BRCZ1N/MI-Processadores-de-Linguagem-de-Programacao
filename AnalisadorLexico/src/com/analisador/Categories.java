package com.analisador;

public enum Categories {

	CAT_VAR("VAR"), 
	CAT_CONST("CONST"), 
	CAT_FUNCTION("FUNC"), 
	CAT_PROCEDURE("PROC"), 
	CAT_ARRAY("ARRAY");

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
