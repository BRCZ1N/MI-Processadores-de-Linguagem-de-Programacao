package com.analisadorlexico;

import java.util.ArrayList;

public class SyntaxAnaliser {

	private ArrayList<Token> listTokens = new ArrayList<Token>();
	private int countToken = 0;
	private Token tokenAtual;

	public void refreshTokenList() {
		
		this.listTokens = LexicalAnaliser.getListTokens();
				
	}
	
	public Token nextToken() {

		if(countToken <= listTokens.size()-1) {
	
			return listTokens.get(countToken++);
			
		}
		
		return new Token();

	}

	public void execAnaliser() {
		
		tokenAtual = nextToken();
		while(tokenAtual.getLexeme() != "") {
			
			cmdIfExpression();
			
		}

	}

	public void cmdIfExpression() {

		if (tokenAtual.getLexeme().equals("if")) {

			tokenAtual = nextToken();

			if (tokenAtual.getLexeme().equals("(")) {

				tokenAtual = nextToken();
				expLogic();

				if (tokenAtual.getLexeme().equals(")")) {

					tokenAtual = nextToken();

					if (tokenAtual.getLexeme().equals("then")) {

						tokenAtual = nextToken();

						if (tokenAtual.getLexeme().equals("{")) {

							tokenAtual = nextToken();
							code();

							if (tokenAtual.getLexeme().equals("}")) {

								tokenAtual = nextToken();
								ifElse();

							}else {
								
								System.out.println("Passei AQ: "+countToken);
								// Erro sintático - }
								
							}

						} else {

							System.out.println("Passei AQ: "+countToken);
							// Erro sintático - {		
							
						}

					} else {

						System.out.println("Passei AQ: "+countToken);
						// Erro sintático - then
						
					}

				} else {
					
					System.out.println("Passei AQ: "+countToken);
					// Erro sintático - )

				}

			} else {

				System.out.println("Passei AQ: "+countToken);
				// Erro sintático - (
				
			}

		}
		

	}

	public void expLogic() {

	}

	public void code() {

	}

	public void listaArg() {

	}

//	public void cmdPrint() {
//		if (tokenAtual.getLexeme() == "print") {
//			tokenAtual = nextToken();
//			if (tokenAtual.getLexeme() == "(") {
//				tokenAtual = nextToken();
//				listaArg();
//
//				if (tokenAtual.getLexeme() == ")") {
//					tokenAtual = nextToken();
//					if (tokenAtual.getLexeme() == ";") {
//
//					} else {
//
//					}
//
//				} else {
//
//				}
//			} else {
//
//			}
//
//		}
//	}
	public void ifElse() {

		if (tokenAtual.getLexeme().equals("else")) {
			
			tokenAtual = nextToken();
			
			if(tokenAtual.getLexeme().equals("{")) {
				
				tokenAtual = nextToken();
				code();
				
				if(tokenAtual.getLexeme().equals("}")) {
					
					tokenAtual = nextToken();
					
				}else {
					
					System.out.println("Passei AQ: "+countToken);
					//Erro sintático - }
					
				}
				
			}else {
				
				System.out.println("Passei AQ: "+countToken);
				//Erros sintático - {
				
			}

		} else if(tokenAtual.getLexeme().equals("if")) {
			
			tokenAtual = nextToken();
			cmdIfExpression();
			
		}

	}

}
