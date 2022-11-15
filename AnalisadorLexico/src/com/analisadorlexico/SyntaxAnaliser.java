package com.analisadorlexico;

import java.util.ArrayList;

public class SyntaxAnaliser {

	private ArrayList<Token> listTokens = new ArrayList<Token>();
	private int countToken = 0;
	private Token tokenAtual;

	public void refreshTokenList() {

		this.listTokens = LexicalAnaliser.getListTokens();

	}
	
	public boolean isEof() {

		if (countToken > listTokens.size() - 1) {

			return true;

		}

		return false;

	}

	public Token nextToken() {

		if (!isEof()) {

			return listTokens.get(countToken++);

		}

		return new Token();

	}

	public Token synchronToken() {

		while (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_DELIMITER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_RESERVED_WORDS.getTypeTokenCode())) && !isEof()) {

			tokenAtual = nextToken();

		}

		return tokenAtual;

	}

	public void execAnaliser() {

		tokenAtual = nextToken();
		while (tokenAtual.getLexeme() != "") {

			cmdIfExpression();

		}

	}

	public void cmdIfExpression() {

		if (tokenAtual.getLexeme().equals("if")) {

			tokenAtual = nextToken();

			if (tokenAtual.getLexeme().equals("(")) {

				tokenAtual = nextToken();
				expLogic();

			} else {

				synchronToken();

			}

			if (tokenAtual.getLexeme().equals(")")) {

				tokenAtual = nextToken();

			} else {

				synchronToken();

			}

			if (tokenAtual.getLexeme().equals("then")) {

				tokenAtual = nextToken();

			} else {

				synchronToken();

			}

			if (tokenAtual.getLexeme().equals("{")) {

				tokenAtual = nextToken();
				code();

			} else {

				synchronToken();

			}

			if (tokenAtual.getLexeme().equals("}")) {

				tokenAtual = nextToken();
				ifElse();

			} else {

				synchronToken();

			}

		}

	}

	public void expLogic() {

	}

	public void code() {

	}

	public void listaArg() {

	}
	

	public void cmdPrint() {
		if (tokenAtual.getLexeme().equals("print")) {
			tokenAtual = nextToken();
			if (tokenAtual.getLexeme().equals("(")) {
				tokenAtual = nextToken();
				listaArg();
			} else {
				synchronToken();
			}

			if (tokenAtual.getLexeme().equals(")")) {
				tokenAtual = nextToken();

			} else {
				synchronToken();
			}

			if (tokenAtual.getLexeme().equals(";")) {

			} else {
				synchronToken();

			}
		}
	}
	public void cmdRead() {
		if(tokenAtual.getLexeme().equals("read")){
			tokenAtual = nextToken();
			if (tokenAtual.getLexeme().equals("(")) {
				tokenAtual = nextToken();
				listaArg();
			} else {
				synchronToken();
			}

			if (tokenAtual.getLexeme().equals(")")) {
				tokenAtual = nextToken();

			} else {
				synchronToken();
			}

			if (tokenAtual.getLexeme().equals(";")) {

			} else {
				synchronToken();

			}
		}
		
	}


	public void ifElse() {

		if (tokenAtual.getLexeme().equals("else")) {

			tokenAtual = nextToken();

			if (tokenAtual.getLexeme().equals("{")) {

				tokenAtual = nextToken();
				code();

			} else {

				synchronToken();

			}
			if (tokenAtual.getLexeme().equals("}")) {

				tokenAtual = nextToken();

			} else {

				synchronToken();

			}

		} else if (tokenAtual.getLexeme().equals("if")) {

			tokenAtual = nextToken();
			cmdIfExpression();

		}

	}
	public void var() {
		if(tokenAtual.getLexeme().equals("var")) {
			tokenAtual = nextToken();
			if(tokenAtual.getLexeme().equals("{")){
				tokenAtual = nextToken();
				code();

			}else {

				synchronToken();

			}
			if (tokenAtual.getLexeme().equals("}")) {

				tokenAtual = nextToken();

			} else {

				synchronToken();

			}

				
		}
			
	}
	public void varBlock(){
		if (tokenAtual.getLexeme().equals("")) {
			tokenAtual = nextToken();		
		}else if(type(tokenAtual)) {
			
			
			
		}
		
		
	}

	private boolean type(Token typeL) {
		if(typeL.getLexeme()== "if" ||typeL.getLexeme()== "string" ||typeL.getLexeme()== "real" ||typeL.getLexeme()== "boolean") {
			return true;
		}
		return false;
	}
}

