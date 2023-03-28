package com.analiser.semantic;

import java.util.HashMap;
import java.util.Map;

import com.analiser.utilities.Categories;

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

	public SymbolTableObject getSymbol(String symbolKey) {

		return symbolTable.get(symbolKey);

	}

	public String getSymbolTypeInAttributes(String keyParameter) {

		for (Map.Entry<String, SymbolTableObject> symbolTableObject : symbolTable.entrySet()) {

			if (!(symbolTableObject.getValue().getCategoryObject().equals(Categories.CAT_CONST.getCatCode())
					|| symbolTableObject.getValue().getCategoryObject().equals(Categories.CAT_VAR.getCatCode()))) {

				for (AttributesSymbolTableObject attributes : symbolTableObject.getValue().getParamFunc()) {

					if (attributes.getNameAttribute().equals(keyParameter)) {

						return attributes.getTypeAttribute();

					}

				}
				
			}

			

		}

		return null;

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
