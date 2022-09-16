package com.analisadorlexico;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LexicalAnaliser {

	private static ArrayList<Character> file = new ArrayList<Character>();// lista que ira salvar os caracteres 
																			// presentes no arquivo de entrada
	private char lastChar = ' '; // variavel que irá salvar char necessarios
	private int pos = 0; // posição na lista de caracteres
	private int countLine = 1;
	private ReservedWords rws = new ReservedWords(); // variavel que ira servir na busca de palavras reservads
	private static ArrayList<Token> listTokens; // lista de tokens
	private ArrayList<Token> recentTokens = new ArrayList<Token>();

	public int getPos() {

		return this.pos;

	}
	
	public static void clearAllList() {
		
		listTokens.clear();
		file.clear();
		
	}
	

	public static ArrayList<Character> getFile() {

		return file;

	}

	public static ArrayList<Token> getListTokens() {

		return listTokens;

	}

// metodo que irá scannear a lista de caracteres
	public Token scanFile() {

		int state = 0;
		String lexeme = null;
		boolean eofCondition = true;
		Token token = new Token();

		if (isEOF(pos)) {

			return null;

		}

		while (eofCondition) {

			char currentChar = ' ';

			if (!isEOF(pos)) {

				currentChar = file.get(pos);

			}

			if (state == 0) {

				lexeme = "";

				// Q0 -> Q1 - Analisador de identificador
				if (isLetter(currentChar)) {

					lexeme += currentChar;
					state = 1;
					pos++;

					// Q0 -> Q2 - Analisador de operador aritmetico "-"
				} else if (currentChar == '-') {

					if (!recentTokens.isEmpty() && isDigit(nextChar())) {

						state = 0;
						lexeme += currentChar;

						token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
						recentTokens = new ArrayList<Token>();
						pos++;
						return token;

					} else {

						lexeme += currentChar;
						state = 2;
						pos++;

					}

					// Q0 -> Q18 - Analisador de operador logico "&"
				} else if (currentChar == '&') {

					lexeme += currentChar;
					state = 18;
					pos++;

					// Q0 -> Q15 - Analisador de operador relacional ou logico "!"
				} else if (currentChar == '!') {

					lexeme += currentChar;
					state = 15;
					pos++;

					// Q0 -> Q16 - Analisador de operador logico "|"
				} else if (currentChar == '|') {

					lexeme += currentChar;
					state = 16;
					pos++;

					// Q0 -> Q25 - - Analisador de operador relacional "="
				} else if (currentChar == '=') {

					lexeme += currentChar;
					state = 25;
					pos++;

					// Q0 -> Q23 - Analisador de operador relacional "<"
				} else if (currentChar == '<') {

					lexeme += currentChar;
					state = 23;
					pos++;

					// Q0 -> Q21 - Analisador de operador relacional ">"
				} else if (currentChar == '>') {

					lexeme += currentChar;
					state = 21;
					pos++;

					// Q0 -> Q7 - Analisador de operador aritmetico "+"
				} else if (currentChar == '+') {

					lexeme += currentChar;
					state = 7;
					pos++;

					// Q0 -> Q9- Analisador de operador aritmetico "*"
				} else if (currentChar == '*') {

					lexeme += currentChar;
					state = 9;
					pos++;

					// Q0 -> Q10 - Analisador de operador aritmetico OU delimitador de comentario
					// "/"
				} else if (currentChar == '/') {

					lexeme += currentChar;
					state = 10;
					pos++;

					// Q0 -> Q3 - Analisador de numero
				} else if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 3;
					pos++;

					// Q0 -> Q28 - Analisador de cadeia de caractere
				} else if (currentChar == '"') {

					lexeme += currentChar;
					state = 28;
					pos++;

					// Q0 -> Q27 - Analisdor de delimitador
				} else if (isDelimiter(currentChar)) {

					lexeme += currentChar;
					state = 27;
					pos++;

					// Q0 -> Q0
					// Desconsiderar espaço e /n
				} else if (isNewLine(currentChar) || isSpace(currentChar)) {

					recentTokens = new ArrayList<Token>();
					state = 0;
					pos++;

				} else {

					lexeme += currentChar;
					state = 0;
					pos++;
					return new ErrorToken(InitialsToken.TK_MALFORMED_TOKEN.getTypeTokenCode(), lexeme, countLine);

				}
				// estado que ira desenvolver o token de identificador ou de palavras reservadas
			} else if (state == 1) {

				// Q1 -> Q1
				if (isLetter(currentChar) || isDigit(currentChar) || currentChar == '_') {

					lexeme += currentChar;
					state = 1;
					pos++;

					// Q1 -> Q0
				} else if (isEndToken(currentChar)) {

					state = 0;

					if (rws.getListReservedWords().contains(token.getLexeme())) {

						return new Token(InitialsToken.TK_RESERVED_WORDS.getTypeTokenCode(), lexeme, countLine);

					} else if (!token.getTypeToken().equals(InitialsToken.TK_MALFORMED_IDENTIFIER.getTypeTokenCode())) {

						token = new Token(InitialsToken.TK_IDENTIFIER.getTypeTokenCode(), lexeme, countLine);

					}

					return token;

				} else {

					lexeme += currentChar;
					state = 1;
					token = new Token(InitialsToken.TK_MALFORMED_IDENTIFIER.getTypeTokenCode(), countLine);
					pos++;

				}
				// estado que ira densenvolver a analise do operador aritmetico "-" ou "--" ou
				// um numero negativo
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
				} else {

					state = 0;
					token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
					return token;

				}
				// estado que ira desenvolver a analise do numero
			} else if (state == 3) {

				// Q3 -> Q3
				if (isDigit(currentChar)) {

					state = 3;
					lexeme += currentChar;
					pos++;

					// Q3 -> Q4
				} else if (currentChar == '.') {

					state = 4;
					lastChar = currentChar;
					pos++;

					// Q3 -> Q0
				} else if (isEndToken(currentChar)) {

					if (!token.getTypeToken().equals(InitialsToken.TK_MALFORMED_NUMBER.getTypeTokenCode())) {

						token = new Token(InitialsToken.TK_NUMBER.getTypeTokenCode(), lexeme, countLine);
						recentTokens.add(token);

					}

					state = 0;
					return token;

				} else {

					state = 3;
					token = new Token(InitialsToken.TK_MALFORMED_NUMBER.getTypeTokenCode(), lexeme, countLine);

				}
				// estado que ira desenvolver a analise do numero decimal
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
					recentTokens.add(token);
					token = new Token(InitialsToken.TK_NUMBER.getTypeTokenCode(), lexeme, countLine);
					return token;

				}
				// estado que ira desenvolver mais casas apos o ponto
			} else if (state == 5) {

				// Q5 -> Q5
				if (isDigit(currentChar)) {

					lexeme += currentChar;
					state = 5;
					pos++;

					// Q5 -> Q0
				} else if (isEndToken(currentChar)) {

					state = 0;
					if (!token.getTypeToken().equals(InitialsToken.TK_MALFORMED_NUMBER.getTypeTokenCode())) {

						token = new Token(InitialsToken.TK_NUMBER.getTypeTokenCode(), lexeme, countLine);
						recentTokens.add(token);

					}
					return token;

				} else {

					state = 5;
					token = new Token(InitialsToken.TK_MALFORMED_NUMBER.getTypeTokenCode(), lexeme, countLine);

				}
				// estado que ira salvar um token de operador aritmetico
			} else if (state == 6) {

				state = 0;
				token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
				return token;

			} else if (state == 7) {

				// Q7 -> Q8
				if (currentChar == '+') {

					lexeme += currentChar;
					state = 8;
					pos++;

				} else {

					state = 0;
					token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
					return token;

				}
				// FALTA FAZER
				// Q8 -> Q0
			} else if (state == 8) {

				state = 0;
				token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
				return token;
				// estado que seta o token de operador aritmetico "*"
			} else if (state == 9) {

				// Q9 -> Q0
				lexeme += currentChar;
				state = 0;
				token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
				return token;
				// estado que desenvolve a analise de delimitadores de comentarios de linha e de
				// bloco
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

				} else {

					lexeme += currentChar;
					state = 0;
					token = new Token(InitialsToken.TK_ARITHIMETIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
					return token;

				}

				// Q11 -> Q0 - estado que servira para desconsiderar os simbolos \n e \r
			} else if (state == 11) {

				if (currentChar == '\n' || currentChar == '\r') {

					return null;

				} else {

					state = 11;
					pos++;

				}
				// estado de um comentario em bloco tendo o outro limite sendo formado
			} else if (state == 12) {

				// Q12 -> Q13
				if (currentChar == '*') {

					lexeme += currentChar;
					state = 13;
					pos++;

				} else if (isEOF(pos + 1)) {

					pos++;
					token = new ErrorToken(InitialsToken.TK_POORLY_FORMED_COMMENT.getTypeTokenCode(), lexeme,
							countLine);
					return token;

					// Q12 -> Q12
				} else {

					lexeme += currentChar;
					state = 12;
					pos++;

				}
				// estado final de um comentario em bloco
			} else if (state == 13) {

				// Q13 -> Q14
				if (currentChar == '/') {

					lexeme += currentChar;
					state = 14;
					pos++;

					// Q13 -> Q12
				} else if (isEOF(pos + 1)) {

					lexeme += currentChar;
					pos++;
					token = new ErrorToken(InitialsToken.TK_POORLY_FORMED_COMMENT.getTypeTokenCode(), lexeme,
							countLine);
					return token;

				} else {

					lexeme += currentChar;
					state = 12;
					pos++;

				}

				// Q14 -> Q0 -
			} else if (state == 14) {

				state = 0;
				return null;

			} else if (state == 15) {

				// Q15 -> Q20 - estado que ira finalizar a formação do token "=="
				if (currentChar == '=') {

					lexeme += currentChar;
					state = 20;
					pos++;

					// Q15 -> Q0
				} else {

					lexeme += currentChar;
					state = 0;
					token = new Token(InitialsToken.TK_LOGIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
					return token;

				}

			} else if (state == 16) {

				// Q15 -> Q0
				if (currentChar == '|') {

					lexeme += currentChar;
					state = 17;
					pos++;

				} else {

					lexeme += currentChar;
					state = 0;
					token = new ErrorToken(InitialsToken.TK_MALFORMED_TOKEN.getTypeTokenCode(), lexeme, countLine);
					return token;

				}

				// Q17 -> Q0
			} else if (state == 17) {

				state = 0;
				token = new Token(InitialsToken.TK_LOGIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
				return token;

			} else if (state == 18) {

				// Q18 -> Q9
				if (currentChar == '&') {

					lexeme += currentChar;
					state = 19;
					pos++;

				} else {

					lexeme += currentChar;
					state = 0;
					pos++;
					token = new ErrorToken(InitialsToken.TK_MALFORMED_TOKEN.getTypeTokenCode(), lexeme, countLine);
					return token;

				}

				// Q19 -> Q0
			} else if (state == 19) {

				state = 0;
				token = new Token(InitialsToken.TK_LOGIC_OPERATOR.getTypeTokenCode(), lexeme, countLine);
				return token;

				// Q20 -> Q0
			} else if (state == 20) {

				state = 0;
				token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.getTypeTokenCode(), lexeme, countLine);
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
					token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.getTypeTokenCode(), lexeme, countLine);
					return token;

				}

				// Q22 -> Q0
			} else if (state == 22) {

				state = 0;
				token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.getTypeTokenCode(), lexeme, countLine);
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
					token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.getTypeTokenCode(), lexeme, countLine);
					return token;

				}

				// Q24 -> Q0
			} else if (state == 24) {

				state = 0;
				token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.getTypeTokenCode(), lexeme, countLine);
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
					token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.getTypeTokenCode(), lexeme, countLine);
					return token;

				}

				// Q26 -> Q0
			} else if (state == 26) {

				state = 0;
				token = new Token(InitialsToken.TK_RELATIONAL_OPERATOR.getTypeTokenCode(), lexeme, countLine);
				return token;

				// Q27 -> Q0
			} else if (state == 27) {

				state = 0;
				token = new Token(InitialsToken.TK_DELIMITER.getTypeTokenCode(), lexeme, countLine);
				return token;
				// estado de criação de uma string
			} else if (state == 28) {

				// Q28 -> Q28
				if (isLetter(currentChar) || isDigit(currentChar) || isSymbol(currentChar)) {

					lexeme += currentChar;
					state = 28;
					pos++;

					// Q28 -> Q29
				} else if (currentChar == '"') {

					lexeme += currentChar;
					state = 29;
					pos++;

				} else if (currentChar == '\n' || currentChar == '\r') {

					state = 29;
					pos++;
					token = new ErrorToken(InitialsToken.TK_MALFORMED_CHAIN.getTypeTokenCode(), countLine);

				} else {

					state = 28;
					lexeme += currentChar;
					pos++;
					token = new ErrorToken(InitialsToken.TK_MALFORMED_CHAIN.getTypeTokenCode(), countLine);

				}
				// Q29 ->Q0 - Estado final da criação de uma string
			} else if (state == 29) {

				if (!token.getTypeToken().equals(InitialsToken.TK_MALFORMED_CHAIN.getTypeTokenCode())) {

					token = new Token(InitialsToken.TK_STRING.getTypeTokenCode(), countLine);
				}

				token.setLexeme(lexeme);
				state = 0;
				return token;

			}

		}

		return null;

	}

	private boolean isSymbol(char c) {

		if (c >= 32 && c <= 126 && c != 34) {

			return true;

		}

		return false;

	}

	private boolean isNewLine(char c) {

		if (c == '\n' || c == '\r') {

			if (c == '\n') {

				countLine++;
			}

			return true;

		}

		return false;

	}

	private boolean isDelimiter(char c) {

		if (c == ';' || c == ',' || c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}' || c == '.') {

			return true;

		}

		return false;

	}

	private boolean isAritmethicOperator(char c) {

		if (c == '+' || c == '-' || c == '*' || c == '/') {

			return true;

		}

		return false;

	}

	private boolean isRelationalOperator(char c) {

		if (c == '=' || c == '>' || c == '<') {

			return true;

		}

		return false;

	}

	private boolean isLogicalOperator(char c) {

		if (c == '!' || (c == '&' && nextChar() == '&') || (c == '|' && nextChar() == '|')) {

			return true;

		}

		return false;

	}

	private boolean isEndToken(char c) {

		if (isDelimiter(c) || isAritmethicOperator(c) || isRelationalOperator(c) || isLogicalOperator(c) || isSpace(c)
				|| isNewLine(c)) {

			return true;

		}

		return false;

	}

	private boolean isSpace(char c) {

		if (c == '\t' || c == ' ') {

			return true;

		}

		return false;

	}

	private boolean isDigit(char c) {

		if (c >= '0' && c <= '9') {

			return true;

		}

		return false;

	}

	private boolean isLetter(char c) {

		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {

			return true;

		}

		return false;

	}

	private void toArrayList(char[] v) {

		for (Character elementV : v) {

			if (elementV >= 0 && elementV <= 255) {

				file.add(elementV);

			}

		}

	}

	private boolean isEOF(int pos) {

		if (file.size() == pos) {

			return true;

		}

		return false;

	}

	private char nextChar() {

		return file.get(pos + 1);

	}

	private void backPosition() {

		pos--;

	}

	private void constructListTokensArc() {

		listTokens = new ArrayList<Token>();
		Token token = null;

		while (!isEOF(getPos())) {

			token = scanFile();

			if (token != null) {

				listTokens.add(token);

			}

		}
		
		countLine = 1;
		pos = 0;

	}

	private void archiveToList(File file) {

		String contentFile;
		try {

			contentFile = new String(Files.readAllBytes(Paths.get(file.getPath())), StandardCharsets.UTF_8);
			toArrayList(contentFile.toCharArray());

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public void execAnaliser(File file) {

		archiveToList(file);
		constructListTokensArc();

	}
}
