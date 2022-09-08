package com.analisadorlexico;

public enum InitialsToken {

	TK_RESERVED_WORDS("PRE"), TK_IDENTIFIER("IDE"), TK_NUMBER("NRO"), TK_DELIMITER("DEL"),
	TK_RELATIONAL_OPERATOR("REL"), TK_LOGIC_OPERATOR("LOG"), TK_ARITHIMETIC_OPERATOR("ART"), TK_MALFORMED_CHAIN("CMF"),
	TK_POORLY_FORMED_COMMENT("CoMF"), TK_INVALID_CHARACTER("CIN"), TK_STRING("STG"), TK_COMMENT("CMT");

	private String typeTokenCode;
		
	private InitialsToken(String typeTokenCode) {
		
		this.typeTokenCode = typeTokenCode;

	}

	public String getTypeTokenCode() {
		return typeTokenCode;
	}


}