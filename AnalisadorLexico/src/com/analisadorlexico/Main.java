package com.analisadorlexico;

public class Main {

	public static void main(String[] args) {

		Token token = null;
		LexicalAnaliser lexicalAnaliserTest = new LexicalAnaliser("entrada1.txt");

		do {

			token = lexicalAnaliserTest.scanFile();
			System.out.println(token.getTypeToken()+" "+token.getLexeme());

		} while (token != null);

	}

}
