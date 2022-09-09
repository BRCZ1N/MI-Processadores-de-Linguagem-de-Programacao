package com.analisadorlexico;

import java.util.ArrayList;

public class Main {

	private static ArrayList<Token> listTokens = new ArrayList<Token>();

	public static void main(String[] args) {

		Token token = null;
		LexicalAnaliser lexicalAnaliserTest = new LexicalAnaliser(
				"C:\\Users\\Bruno\\eclipse-workspace\\MI-LinguagensFormais-Compiladores\\AnalisadorLexico\\src\\Files\\entrada1.txt");

		while (!lexicalAnaliserTest.isEOF(lexicalAnaliserTest.getPos())) {

			token = lexicalAnaliserTest.scanFile();
			if(token != null) {
				
				listTokens.add(token);
				
			}

		}
		
	
		validTokens();
//		System.out.println("\n");
		invalidTokens();

	}

	public static void validTokens() {

		for (Token tk : listTokens) {

			if (!(tk instanceof ErrorToken)) {

				System.out.println(tk.toString());

			}

		}

	}

	public static void invalidTokens() {

		for (Token tk : listTokens) {

			if (tk instanceof ErrorToken) {

				System.out.println(tk.toString());

			}

		}

	}

}
