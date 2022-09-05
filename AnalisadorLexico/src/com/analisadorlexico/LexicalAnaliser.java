package com.analisadorlexico;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import com.analisadorlexico.Token.InitialsToken;

public class LexicalAnaliser {

	private LinkedList<Character> file;
	private int pos = 0;

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
		String lexeme = null;
		char currentChar = file.get(pos);

		while (!isEOF(pos)) {

			if (state == 0) {

				lexeme = "";

				// Identificador
				if (isLetter(currentChar)) {

					lexeme += currentChar;
					state = 1;
					pos++;

				} else if (currentChar == '-') {

					lexeme += currentChar;
					state = 2;
					pos++;

				} else if (currentChar == '&') {

					lexeme += currentChar;
					state = 18;
					pos++;

				} else if (currentChar == '!') {

					lexeme += currentChar;
					state = 15;
					pos++;

				} else if (currentChar == '|') {

					lexeme += currentChar;
					state = 16;
					pos++;

				} else if (currentChar == '=') {

					lexeme += currentChar;
					state = 25;
					pos++;

				} else if (currentChar == '<') {

					lexeme += currentChar;
					state = 23;
					pos++;

				} else if (currentChar == '>') {

					lexeme += currentChar;
					state = 21;
					pos++;
				} else if (currentChar == '+') {

					lexeme += currentChar;
					state = 7;
					pos++;

				} else if (currentChar == '*') {

					lexeme += currentChar;
					state = 9;
					pos++;

				} else if (currentChar == '/') {

					lexeme += currentChar;
					state = 10;
					pos++;

				} else if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 3;
					pos++;

				} else if (currentChar == '"') {

					lexeme += currentChar;
					state = 28;
					pos++;

				} else {

					// NÃO SEI O QUE É AQ

				}

			} else if (state == 1) {

				if (isLetter(currentChar) || isDigit(currentChar) || currentChar == '_') {

					lexeme += currentChar;
					state = 1;
					pos++;

				} else if (currentChar == '\t') {

					lexeme += currentChar;
					state = 0;
					pos++;
					Token token = new Token(InitialsToken.TK_IDENTIFIER.name(), lexeme);
				} else {

					// PENSANDO NOS ERROS

				}

			} else if (state == 2) {

				if (currentChar == '-') {

					lexeme += currentChar;
					state = 6;
					pos++;

				} else if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 3;
					pos++;

				} else if (currentChar == '\t') {
					lexeme += currentChar;
					state = 0;
					pos++;
					Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
				}

			}

			else if (state == 3) {

				if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 3;
					pos++;

				} else if (currentChar == '.') {

					lexeme += currentChar;
					state = 4;
					pos++;

				} else {
					lexeme += currentChar;
					state = 0;
					pos++;
					Token token = new Token(InitialsToken.TK_NUMBER.name(), lexeme);

					// ERRO LEXICO

				}

			} else if (state == 4) {

				if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 5;
					pos++;

				} else if (currentChar == '\t') {

					lexeme += currentChar;
					state = 0;
					pos++;
					Token token = new Token(InitialsToken.TK_NUMBER.name(), lexeme);

				}

			} else if (state == 5) {

				if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 5;
					pos++;

				} else {

					lexeme += currentChar;
					state = 0;
					pos++;
				}

			} else if (state == 6) {

				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);

			} else if (state == 7) {
				if (currentChar == '+') {
					lexeme += currentChar;
					state = 8;
					pos++;

				} else {
					// return + NÃO SABEMOS FAZER AINDA, FALTA SABER A RELAÇÃO COM OS NUMEROS

				}
				// FALTA FAZER

			} else if (state == 8) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
				// return ++

			} else if (state == 9) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
				// RETURN ALGO AQ

			} else if (state == 10) {

				if (currentChar == '/') {

					lexeme += currentChar;
					state = 11;
					// RETURN OPERADOR DE DIVISÃO
					pos++;

				} else if (currentChar == '*') {

					lexeme += currentChar;
					state = 12;
					pos++;

				}

			} else if (state == 11) {
				
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_DELIMITER.name(), lexeme);

			} else if (state == 12) {

				if (currentChar == '*') {

					lexeme += currentChar;
					state = 13;
					pos++;

				} else {

					lexeme += currentChar;
					state = 12;
					pos++;
				}
			} else if (state == 13) {

				if (currentChar == '/') {

					lexeme += currentChar;
					state = 14;
					pos++;

				} else {

					lexeme += currentChar;
					state = 12;
					pos++;

				}

			} else if (state == 14) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_DELIMITER.name(), lexeme);

			} else if (state == 15) {

				if (currentChar == '=') {
					lexeme += currentChar;
					state = 20;
					pos++;

				} else {

					// erro lexico

				}

			} else if (state == 16) {
				if (currentChar == '|') {
					lexeme += currentChar;
					state = 17;
					pos++;
					// return
				} else {
					// erro
				}

			} else if (state == 17) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_LOGIC_OPERATOR.name(), lexeme);
				// RETURN

			} else if (state == 18) {

				if (currentChar == '&') {
					lexeme += currentChar;
					state = 19;
					pos++;

				} else {

					// erro lexico

				}

			} else if (state == 19) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_LOGIC_OPERATOR.name(), lexeme);
				// RETURN ALGO AQ

			} else if (state == 20) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
				// RETURN ALGO

			} else if (state == 21) {

				if (currentChar == '=') {

					lexeme += currentChar;
					state = 22;
					pos++;

				} else {

					// ERRO LEXICO

				}

			} else if (state == 22) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
				// RETURN ALGO AQ

			} else if (state == 23) {

				if (currentChar == '=') {

					lexeme += currentChar;
					state = 24;
					pos++;

				} else {

					// erro

				}

			} else if (state == 24) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
				// RETURN ALGO AQ

			} else if (state == 25) {

				if (currentChar == '=') {
					lexeme += currentChar;
					state = 26;
					pos++;
					// return
				} else {
					// erro
				}

			} else if (state == 26) {
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
				// RETURN ALGO AQ

			} else if (state == 27) {

				// RETURN ALGO AQ

			} else if (state == 28) {

				if (isLetter(currentChar) || isDigit(currentChar)
						|| (currentChar >= 32 && currentChar <= 126 && currentChar != 34)) {

					lexeme += currentChar;
					state = 28;
					pos++;

				} else if (currentChar == '"') {

					lexeme += currentChar;
					state = 29;
					pos++;

				} else {

					// ERRO LEXICO
				}
			}else if (state == 29) {
				
				lexeme += currentChar;
				state = 0;
				pos++;
				Token token = new Token(InitialsToken.TK_STRING.name(), lexeme);
			}

		}

		return null;

	}

	private boolean isDigit(char c) {

		if (c >= '0' || c <= '9') {

			return true;

		}

		return false;

	}

	private boolean isLetter(char c) {

		if ((c >= 'a' || c <= 'z') || (c >= 'A' || c <= 'Z')) {

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

	public char previousChar() {

		return file.get(pos--);

	}

	public char doubleNextChar() {

		return file.get(pos + 2);

	}

}
