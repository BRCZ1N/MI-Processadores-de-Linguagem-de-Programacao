package com.analisadorlexico;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

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

				if (isLetter(currentChar)) {

					lexeme += currentChar;
					state = 1;
					pos++;

				}else if(currentChar == '-') {

					lexeme += currentChar;
					state = 2;
					pos++;
		
					
				}else if(currentChar == '&') {
					
					lexeme += currentChar;
					state = 18;
					pos++;
		
				}else if(currentChar == '!') {
					if (nextChar() == '=') {
						lexeme += currentChar;
						state = 20;
						pos++;
					}else{
						
						// retornar o operador de negação
					}
					
					
				}else if(currentChar == '|') {
				
					lexeme += currentChar;
					state = 16;
					pos++;
					
				}else if(currentChar == '=') {
					if(nextChar() == '=') {
						lexeme += currentChar;
						state = 25;
						pos++;
						
					}else {
						// retornar o operador relacional = 
					}
					
				}else if(currentChar == '<') {
					if(nextChar() == '=') {
						lexeme += currentChar;
						state = 23;
						pos++;
					}else {  	
						// retornar o operador <
					}
					
					
				}else if(currentChar == '>') {
					if(nextChar() == '=') {
						lexeme += currentChar;
						state = 21;
						pos++;
					}else {
						// return o operador >
					}
					
					
				}else if(currentChar == '+') {
					
					
					lexeme += currentChar;
					state = 7;
					pos++;
					
				}else if(currentChar == '*') {
					
					lexeme += currentChar;
					state = 9;
					pos++;

				}else if(currentChar == '/') {
					if(nextChar() == '/') {
						lexeme += currentChar;
						state = 11;
						pos++;
					}else if (nextChar()=='*') {
						lexeme += currentChar;
						state = 12;
					}else {
						// return operador /
					}
					
					
				}else if(isDigit(currentChar)) {
					
					lexeme += currentChar;
					state = 3;
					pos++;
					
				}else if(currentChar == '"') {
					
					lexeme += currentChar;
					state = 28;
					pos++;
					
				}else {
					
				//NÃO SEI O QUE É AQ	
					
				}
				
			}else if (state == 18) {
				if(currentChar == '&') {
					lexeme +=  currentChar;
					state = 0;
					pos++;
					// return aq
				}else {
					// erro lexico
				}

			}else if (state == 16) {
				if(currentChar == '|') {
					lexeme += currentChar;
					state = 0;
					pos++;
					// return
				}else {
					//erro
				}
			
			}else if(state == 9) {
				//return operador *
			}else if(state == 20) {
				if(currentChar == '=') {
					lexeme += currentChar;
					state = 0;
					pos++;
				}else {
					//erro
				}
			}else if(state == 25) {
				if(currentChar == '=') {
					lexeme += currentChar;
					state = 0;
					pos++;
					
				}else {
					//erro
				}
			}else if(state == 23) {
				if(currentChar == '=') {
					lexeme += currentChar;
					state = 0;
					pos++;
				}else {
					//erro
				}
			}else if(state == 21) {
				if (currentChar == '=') {
					lexeme += currentChar;
					state = 0;
					pos++;
				}else {
					//erro
				}
			}else if(state == 11) {
				if (currentChar == '/') {
					lexeme += currentChar;
					state = 0;
					pos++;
				}
			}else if(state == 12) {
				if (currentChar == '*') {
					lexeme += currentChar;
				}
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
