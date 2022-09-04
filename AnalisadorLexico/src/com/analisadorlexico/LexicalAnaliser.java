package com.analisadorlexico;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class LexicalAnaliser {

	private LinkedList<Character> file;
	private int pos = 0;

	// public static int TokenReservedWord = 3;
	/*
	 * public static int TokenNumber = 1; public static int TokenAritmeticOperator =
	 * 2; public static int TokenIdentifier = 4; public static int TokenDigit = 5;
	 * public static int TokenLetter = 6; public static int TokenRelationalOperator
	 * = 7; public static int TokenLogicOperator = 8; public static int
	 * TokenDelimiter = 9; public static int TokenString = 10; public static int
	 * TokenSymbol = 11; public static int TokenEspace = 12;/ public boolean isDigit
	 */

	public LexicalAnaliser(String fileName) {

		String contentFile;

		try {

			contentFile = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
			toLinkedList(contentFile.toCharArray());

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public Token scanFile() {

		int state = 0;
		String lexeme = "";

		while (!isEOF(pos)) {

			if (state == 0) {

				lexeme = "";
				
				if (isLetter(file.get(pos))) {

					lexeme += file.get(pos);
					state = 1;
					pos++;

				}if(isLogicalOperator(file.get(pos))) {
					
					lexeme += file.get(pos);
					
					if((nextChar() == '&' || nextChar() == '|') && nextChar() != '!') {
						
						lexeme += nextChar();
						
					}
					
					state = 0;
					return new Token(Token.SiglaToken.TK_OPERADOR_LOGICO.name(),lexeme);
		
				}

			} else if (state == 1) {

				if (isLetter(file.get(pos)) || isDigit(file.get(pos)) || file.get(pos) == '_') {

					lexeme += file.get(pos);
					state = 1;
					pos++;

				} else {

					state = 0;
					pos++;
					return new Token(Token.SiglaToken.TK_IDENTIFICADOR.name(), lexeme);

				}

			}

		}

		return null;

	}

	public boolean isDigit(char c) {

		if (c >= '0' || c <= '9') {

			return true;

		}

		return false;

	}

	public boolean isLetter(char c) {

		if (c >= 'a' || c <= 'z' || c >= 'A' || c <= 'Z') {

			return true;

		}

		return false;

	}

	public boolean isAritmethicOperator(char c) {

		if (c == '+' || c == '-' || c == '*' || c == '/') {

			return true;

		}

		return false;

	}

	public boolean isRelationalOperator(char c) {

		if (c == '=' || c == '>' || c == '<') {

			return true;

		}

		return false;

	}

	public boolean isLogicalOperator(char c) {

		if (c == '!' || (c == '&' && nextChar() == '&') || (c == '|' && nextChar() == '|')) {

			return true;

		}

		return false;

	}

	private void toLinkedList(char[] v) {

		for (Character elementV : v) {

			file.add(elementV);

		}

	}

	public boolean isEOF(int pos) {

		if (file.size() == pos) {

			return true;

		}

		return false;

	}

	public char nextChar() {

		return file.get(pos++);

	}

}
