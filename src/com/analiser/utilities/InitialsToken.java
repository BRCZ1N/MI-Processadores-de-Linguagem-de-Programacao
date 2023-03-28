package com.analiser.utilities;

public enum InitialsToken {

	TK_RESERVED_WORDS("PRE"), 
	TK_IDENTIFIER("IDE"), 
	TK_STRING("CAC"), 
	TK_NUMBER("NRO"), 
	TK_DELIMITER("DEL"),
	TK_RELATIONAL_OPERATOR("REL"), 
	TK_LOGIC_OPERATOR("LOG"), 
	TK_ARITHIMETIC_OPERATOR("ART"), 
	TK_MALFORMED_CHAIN("CMF"),
	TK_POORLY_FORMED_COMMENT("CoMF"), 
	TK_MALFORMED_NUMBER("NMF"),
	TK_MALFORMED_IDENTIFIER("IMF"),
	TK_MALFORMED_TOKEN("TMF"),
	TK_COMMENT("CMT");

	private String typeTokenCode;

	private InitialsToken(String typeTokenCode) {

		this.typeTokenCode = typeTokenCode;

	}

	public String getTypeTokenCode() {
		return typeTokenCode;
	}
	
	@Override
	public String toString() {
		
		return this.typeTokenCode;
		
	}

}
