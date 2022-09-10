package com.analisadorlexico;

public class Main {
	
	static LexicalAnaliser lexicalAnaliser = new LexicalAnaliser();

	public static void main(String[] args) {

		lexicalAnaliser.execAnaliser("entrada1.txt");
		writeAndPrintValidTokens();
		System.out.println("\n");
		writeAndPrintInvalidTokens();
		

	}

	public static void writeAndPrintValidTokens() {
		
		for (Token tk : lexicalAnaliser.getListTokens()) {

			if (!(tk instanceof ErrorToken)) {

				System.out.println(tk.toString());

			}

		}

	}

	public static void writeAndPrintInvalidTokens() {

		for (Token tk : lexicalAnaliser.getListTokens()) {

			if (tk instanceof ErrorToken) {

				System.out.println(tk.toString());

			}

		}

	}
	
}
