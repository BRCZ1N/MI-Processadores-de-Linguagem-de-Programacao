package com.analisadorlexico;

public class Main {

	public static void main(String[] args) {

		Token token = null;
		LexicalAnaliser lexicalAnaliserTest = new LexicalAnaliser(
				"C:\\Users\\Bruno\\eclipse-workspace\\MI-LinguagensFormais-Compiladores\\AnalisadorLexico\\src\\Files\\entrada1.txt");

		while(!lexicalAnaliserTest.isEOF(lexicalAnaliserTest.getPos())) {
			
			token = lexicalAnaliserTest.scanFile();
			System.out.println(token.toString());
			
		}

	}

}
