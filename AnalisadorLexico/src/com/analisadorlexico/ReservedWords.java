package com.analisadorlexico;

import java.util.ArrayList;

public class ReservedWords {

	private ArrayList<String> listReservedWords = new ArrayList<String>();

	public ArrayList<String> getListReservedWords() {

		return this.listReservedWords;

	}

	public ReservedWords() {

		reservedWordsGenerate();

	}

	public void reservedWordsGenerate() {

		listReservedWords.add("var");
		listReservedWords.add("const");
		listReservedWords.add("struct");
		listReservedWords.add("extends");
		listReservedWords.add("procedure");
		listReservedWords.add("function");
		listReservedWords.add("start");
		listReservedWords.add("return");
		listReservedWords.add("if");
		listReservedWords.add("else");
		listReservedWords.add("then");
		listReservedWords.add("while");
		listReservedWords.add("read");
		listReservedWords.add("print");
		listReservedWords.add("int");
		listReservedWords.add("real");
		listReservedWords.add("boolean");
		listReservedWords.add("string");
		listReservedWords.add("true");
		listReservedWords.add("false");

	}
	
	public boolean isReservedWord(String lexeme) {

		for (String reservedWord : getListReservedWords()) {

			if (lexeme.equals(reservedWord)) {

				return true;

			}

		}

		return false;

	}

}
