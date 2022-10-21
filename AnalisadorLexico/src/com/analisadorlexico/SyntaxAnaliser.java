package com.analisadorlexico;

import java.util.ArrayList;

public class SyntaxAnaliser {

	private ArrayList<Token> listTokens;
	private int countToken = 0;
	private Token tokenAtual;

	public SyntaxAnaliser(ArrayList<Token> listTokens) {

		this.listTokens = listTokens;

	}

	public Token nextToken() {

		Token token = listTokens.get(countToken++);
		return token;

	}

	public void execAnaliser() {

	
	}

	public void cmdIfExpression() {


		if (tokenAtual.getLexeme() == "if") {

			tokenAtual = nextToken();

			if (tokenAtual.getLexeme() == "(") {

				expLogic();
				tokenAtual = nextToken();

				if (tokenAtual.getLexeme() == ")") {

					tokenAtual = nextToken();
					if (tokenAtual.getLexeme() == "then") {

						tokenAtual = nextToken();
						if(tokenAtual.getLexeme() == "{") {
							
							code();
							
							tokenAtual = nextToken();
							if(tokenAtual.getLexeme() == "}") {
								
								ifElse();		
								
							}
				
							
						}else {
							
							
							
						}

					}else {
						
						
					}

				} else {

				}

			} else {

			}

		} else {

		}

	}

	public void expLogic() {

	}

	public void code() {

	}
	public void listaArg() {
		
	}
	public void cmdPrint() {
		if (tokenAtual.getLexeme() == "print") {
			tokenAtual = nextToken();
			if(tokenAtual.getLexeme() == "(") {
				tokenAtual = nextToken();
				listaArg();
				
				if(tokenAtual.getLexeme() ==")") {
					tokenAtual = nextToken();
					if(tokenAtual.getLexeme() == ";") {
						
					}else {
						
					}
					
				}else {
					
				}
			}else {
				
			}
			
		}
	}
	
	public void ifElse() {
		tokenAtual = nextToken();
		
		if(tokenAtual.getLexeme() == "if") {
			cmdIfExpression();
		}
		
		if (tokenAtual.getLexeme() == "else") {

			tokenAtual = nextToken();

			if (tokenAtual.getLexeme() == "{") {

				code();
				tokenAtual = nextToken();
				if (tokenAtual.getLexeme() == "}") {

					tokenAtual = nextToken();
					
				}else {
					// erro }
				}
			}else {
				//erro de {
			}
		}else {
			// erro else
			
		}
	}
				
}
