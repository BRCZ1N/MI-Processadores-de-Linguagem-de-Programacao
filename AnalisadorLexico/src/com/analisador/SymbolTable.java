package com.analisador;

import java.util.HashMap;

public class SymbolTable {

	private HashMap<String, SymbolTableObject> symbolTable = new HashMap<String, SymbolTableObject>();

	public HashMap<String, SymbolTableObject> getSymbolTable() {
		return symbolTable;
	}

	public void setSymbolTable(HashMap<String, SymbolTableObject> symbolTable) {

		this.symbolTable = symbolTable;

	}

	public void add(String symbolKey, SymbolTableObject symbol) {

		symbolTable.put(symbolKey, symbol);

	}

	public boolean exists(String symbolName) {

		return symbolTable.containsKey(symbolName);

	}

	public SymbolTableObject getSymbol(String symbolKey) {

		return symbolTable.get(symbolKey);

	}

	public boolean comparableTypes(SymbolTableObject previousIdentifier, SymbolTableObject currentIdentifier) {

		if (previousIdentifier.getTypeDateToken().equals(currentIdentifier.getTypeDateToken())) {

			return true;

		}

		return false;

	}
	
	public boolean comparableTypes(SymbolTableObject previousIdentifier, String typeToken) {

		if (previousIdentifier.getTypeDateToken().equals(typeToken)) {

			return true;

		}

		return false;

	}
	
	
	

	public boolean isInteger(String number) {

		try {
			
			Integer.parseInt(number);
			
		} catch (NumberFormatException ex) {
			
			return false;
			
		}
		
		return true;

	}

}
