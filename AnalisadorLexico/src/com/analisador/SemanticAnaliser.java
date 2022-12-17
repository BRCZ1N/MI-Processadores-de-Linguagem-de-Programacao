package com.analisador;

public class SemanticAnaliser {

	private SymbolTable symbolTable = new SymbolTable();

	public boolean checkType(Token previousToken, Token currentToken) {

		String symbolPreviousType;
		String symbolCurrentType;

		if (previousToken.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			symbolPreviousType = symbolTable.getSymbol(previousToken.getLexeme()).getTypeDate();

		} else if (previousToken.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())) {

			if (SymbolTable.isInteger(previousToken.getLexeme())) {

				symbolPreviousType = "int";

			} else {

				symbolPreviousType = "float";

			}

		} else if (previousToken.getLexeme().equals("true") || previousToken.getLexeme().equals("false")) {

			symbolPreviousType = "boolean";

		} else {

			symbolPreviousType = "string";

		}

		if (currentToken.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			symbolCurrentType = symbolTable.getSymbol(currentToken.getLexeme()).getTypeDate();

		} else if (currentToken.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())) {

			if (SymbolTable.isInteger(previousToken.getLexeme())) {

				symbolCurrentType = "int";

			} else {

				symbolCurrentType = "float";

			}

		} else if (currentToken.getLexeme().equals("true") || currentToken.getLexeme().equals("false")) {

			symbolCurrentType = "boolean";

		} else {

			symbolCurrentType = "string";

		}

		if (!symbolTable.comparableTypes(symbolPreviousType, symbolCurrentType)) {

			return false;

		}

		return true;

	}
	
}
