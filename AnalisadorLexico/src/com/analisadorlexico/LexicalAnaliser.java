package com.analisadorlexico;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;

import com.analisadorlexico.Token.InitialsToken;

public class LexicalAnaliser {

	private LinkedList<Character> file;
	private char lastChar = ' ';
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
		int line = 0;
		char currentChar = file.get(pos);

		while (!isEOF(pos)) {
			
			if (state == 0) {
				
				lexeme = "";

				// Q0 -> Q1
				if (isLetter(currentChar)) {

					lexeme += currentChar;
					state = 1;
					pos++;

					// Q0 -> Q2
				} else if (currentChar == '-') {

					lexeme += currentChar;
					state = 2;
					pos++;

					// Q0 -> Q18
				} else if (currentChar == '&') {

					lexeme += currentChar;
					state = 18;
					pos++;

					// Q0 -> Q15
				} else if (currentChar == '!') {

					lexeme += currentChar;
					state = 15;
					pos++;

					// Q0 -> Q16
				} else if (currentChar == '|') {

					lexeme += currentChar;
					state = 16;
					pos++;

					// Q0 -> Q25
				} else if (currentChar == '=') {

					lexeme += currentChar;
					state = 25;
					pos++;

					// Q0 -> Q23
				} else if (currentChar == '<') {

					lexeme += currentChar;
					state = 23;
					pos++;

					// Q0 -> Q21
				} else if (currentChar == '>') {

					lexeme += currentChar;
					state = 21;
					pos++;

					// Q0 -> Q7
				} else if (currentChar == '+') {

					lexeme += currentChar;
					state = 7;
					pos++;

					// Q0 -> Q11
				} else if (currentChar == '*') {

					lexeme += currentChar;
					state = 9;
					pos++;

					// Q0 -> Q10
				} else if (currentChar == '/') {

					lexeme += currentChar;
					state = 10;
					pos++;

					// Q0 -> Q3
				} else if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 3;
					pos++;

					// Q0 -> Q28
				} else if (currentChar == '"') {

					lexeme += currentChar;
					state = 28;
					pos++;

					// Q0 -> Q27
				} else if (currentChar == ';' || currentChar == ',' || currentChar == '(' || currentChar == ')'
						|| currentChar == '[' || currentChar == ']' || currentChar == '{' || currentChar == '}') {

					lexeme += currentChar;
					state = 27;
					pos++;

					// Q0 -> Q0
				} else {

					state = 0;
					pos++;

				}

			} else if (state == 1) {

				// Q1 -> Q1
				if (isLetter(currentChar) || isDigit(currentChar) || currentChar == '_') {

					lexeme += currentChar;
					state = 1;
					pos++;
				
					// Q1 -> Q0
				}else {
					state = 0;
					Token token = new Token(InitialsToken.TK_IDENTIFIER.name(), lexeme);
					return token;

				}

			} else if (state == 2) {

				// Q2 -> Q6
				if (currentChar == '-') {

					lexeme += currentChar;
					state = 6;
					pos++;

					// Q2 -> Q3
				} else if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 3;
					pos++;

					// Q2 -> Q3
				} else if (currentChar == '\t') {

					state = 0;
					Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
					return token;

				}

			} else if (state == 3) {

				// Q3 -> Q3
				if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 3;
					pos++;

					// Q3 -> Q4
				} else if (currentChar == '.') {

					state = 4;
					lastChar = currentChar;
					pos++;

					// Q3 -> Q0
				} else {

					state = 0;
					Token token = new Token(InitialsToken.TK_NUMBER.name(), lexeme);
					return token;

				}

			} else if (state == 4) {

				// Q4 -> Q5
				if (isDigit(currentChar)) {

					lexeme += lastChar;
					lexeme += currentChar;
					state = 5;
					pos++;

					// Q4 -> Q5
				} else {

					backPosition();
					state = 0;
					Token token = new Token(InitialsToken.TK_NUMBER.name(), lexeme);
					return token;

				}

			} else if (state == 5) {

				// Q5 -> Q5
				if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 5;
					pos++;

					// Q5 -> Q0
				} else {

					state = 0;
					Token token = new Token(InitialsToken.TK_NUMBER.name(), lexeme);
					return token;

				}

			} else if (state == 6) {

				state = 0;
				Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
				return token;

			} else if (state == 7) {

				// Q7 -> Q8
				if (currentChar == '+') {

					lexeme += currentChar;
					state = 8;
					pos++;

				} else {

					state = 0;
					Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
					return token;

				}
				// FALTA FAZER

			} else if (state == 8) {

				// Q8 -> Q0
				if (currentChar == '+') {

					state = 0;
					Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
					return token;

				}

			} else if (state == 9) {

				// Q9 -> Q0
				lexeme += currentChar;
				state = 0;
				Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
				return token;

			} else if (state == 10) {

				// Q10 -> Q11
				if (currentChar == '/') {

					lexeme += currentChar;
					state = 11;
					pos++;

					// Q10 -> Q12
				} else if (currentChar == '*') {

					lexeme += currentChar;
					state = 12;
					pos++;

				}else {
					
					lexeme += currentChar;
					state = 0;
					Token token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.name(), lexeme);
					return token;
					
				}

				// Q11 -> Q0
			} else if (state == 11) {

				lexeme += currentChar;
				state = 0;
				Token token = new Token(InitialsToken.TK_COMMENT.name(), lexeme);
				return token;

			} else if (state == 12) {

				// Q12 -> Q13
				if (currentChar == '*') {

					lexeme += currentChar;
					state = 13;
					pos++;

					// Q12 -> Q12
				} else {

					lexeme += currentChar;
					state = 12;
					pos++;

				}

			} else if (state == 13) {

				// Q13 -> Q14
				if (currentChar == '/') {

					lexeme += currentChar;
					state = 14;
					pos++;

					// Q13 -> Q12
				} else {

					lexeme += currentChar;
					state = 12;
					pos++;

				}

				// Q14 -> Q0
			} else if (state == 14) {

				lexeme += currentChar;
				state = 0;
				Token token = new Token(InitialsToken.TK_COMMENT.name(), lexeme);
				return token;

			} else if (state == 15) {

				// Q15 -> Q20
				if (currentChar == '=') {

					lexeme += currentChar;
					state = 20;
					pos++;

					// Q15 -> Q0
				} else {

					lexeme += currentChar;
					state = 0;
					Token token = new Token(InitialsToken.TK_LOGIC_OPERATOR.name(), lexeme);
					return token;

				}

			} else if (state == 16) {

				// Q15 -> Q0
				if (currentChar == '|') {

					lexeme += currentChar;
					state = 17;
					pos++;
					// return
				} else {

					// erro

				}

				// Q17 -> Q0
			} else if (state == 17) {

				state = 0;
				Token token = new Token(InitialsToken.TK_LOGIC_OPERATOR.name(), lexeme);
				return token;

			} else if (state == 18) {

				// Q18 -> Q9
				if (currentChar == '&') {

					lexeme += currentChar;
					state = 19;
					pos++;

				} else {

					// erro lexico

				}

				// Q19 -> Q0
			} else if (state == 19) {

				state = 0;
				Token token = new Token(InitialsToken.TK_LOGIC_OPERATOR.name(), lexeme);
				return token;

				// Q20 -> Q0
			} else if (state == 20) {

				state = 0;
				Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
				return token;

			} else if (state == 21) {

				// Q21 -> Q22
				if (currentChar == '=') {

					lexeme += currentChar;
					state = 22;
					pos++;

					// Q22 -> Q0
				} else {

					state = 0;
					Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
					return token;

				}

				// Q22 -> Q0
			} else if (state == 22) {

				state = 0;
				Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
				return token;

			} else if (state == 23) {

				// Q23 -> Q24
				if (currentChar == '=') {

					lexeme += currentChar;
					state = 24;
					pos++;

					// Q23 -> Q0
				} else {

					state = 0;
					Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
					return token;

				}

				// Q24 -> Q0
			} else if (state == 24) {

				state = 0;
				Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
				return token;

			} else if (state == 25) {

				// Q25 -> Q26
				if (currentChar == '=') {

					lexeme += currentChar;
					state = 26;
					pos++;

					// Q25 -> Q0
				} else {

					state = 0;
					Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
					return token;

				}

				// Q26 -> Q0
			} else if (state == 26) {

				state = 0;
				Token token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.name(), lexeme);
				return token;

				// Q27 -> Q0
			} else if (state == 27) {

				state = 0;
				Token token = new Token(InitialsToken.TK_DELIMITER.name(), lexeme);
				return token;

			} else if (state == 28) {

				// Q28 -> Q28
				if (isLetter(currentChar) || isDigit(currentChar)
						|| isSymbol(currentChar)) {

					lexeme += currentChar;
					state = 28;
					pos++;

					// Q28 -> Q29
				} else if (currentChar == '"') {

					lexeme += currentChar;
					state = 29;
					pos++;

				} else if(currentChar == '\n'){
					// erro de mal formação de token por quebra de linha 

				}else {
					//erro de simbolo não permitido 
					
				}
				// Q29 ->Q0
			} else if (state == 29) {

				state = 0;
				Token token = new Token(InitialsToken.TK_STRING.name(), lexeme);
				return token;
			}

		}

		return null;

	}
	private boolean isJumpLine(char c) {
		if (c == '\n'|| c == '\r') {
			return true;
		}
		return false;
	}
	private boolean isSymbol(char c) {
		if (c >= 32 && c <= 126 && c!= 34) {
			return true;
		}
		return false;
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

	private void backPosition() {

		pos--;

	}

}
