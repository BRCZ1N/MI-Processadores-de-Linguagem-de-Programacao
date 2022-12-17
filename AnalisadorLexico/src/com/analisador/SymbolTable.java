package com.analisador;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

	private static Map<String, SymbolTableObject> symbolTable = new HashMap<String, SymbolTableObject>();

	public static Map<String, SymbolTableObject> getSymbolTable() {
		return symbolTable;
	}

	public static void setSymbolTable(Map<String, SymbolTableObject> symbolTable) {
		SymbolTable.symbolTable = symbolTable;
	}

	public void add(String symbolKey, SymbolTableObject symbol) {

		symbolTable.put(symbolKey, symbol);

	}

//	public boolean exists(String symbolName) {
//
//		return symbolTable.containsKey(symbolName);
//
//	}

	public SymbolTableObject getSymbol(String symbolKey) {

		return symbolTable.get(symbolKey);

	}

	public boolean comparableTypes(SymbolTableObject previousIdentifier, SymbolTableObject currentIdentifier) {

		if (previousIdentifier.getTypeDate().equals(currentIdentifier.getTypeDate())) {

			return true;

		}

		return false;

	}

	public boolean comparableTypes(SymbolTableObject previousIdentifier, String typeToken) {

		if (previousIdentifier.getTypeDate().equals(typeToken)) {

			return true;

		}

		return false;

	}
	
	public boolean comparableTypes(String typeA, String typeB) {

		if (typeA.equals(typeB)) {

			return true;

		}

		return false;

	}

	public static boolean isInteger(String number) {

		try {

			Integer.parseInt(number);

		} catch (NumberFormatException ex) {

			return false;

		}

		return true;

	}

}
