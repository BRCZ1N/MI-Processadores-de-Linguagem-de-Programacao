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

		return null;

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

	public void returnStatement() {

		if (tokenAtual.getLexeme().equals("return")) {

			value();

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}

		}

	}

	public void parameter() {

		type();
		value();
		auxParameter();

	}

	public void auxParameter() {

		if (tokenAtual != null) {

			if (!tokenAtual.getLexeme().equals(",")) {

				synchronToken();

			}

			parameter();

		}

	}

	public void function() {

		if (tokenAtual.getLexeme().equals("function")) {

			type();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("(")) {

				synchronToken();

			}

			functionParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			code();
			returnStatement();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		}

	}

	public void procedure() {

		if (tokenAtual.getLexeme().equals("procedure")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("(")) {

				synchronToken();

			}

			functionParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		}

	}

	public void functionParameter() {

		if (tokenAtual != null) {

			parameter();

		}

	}

	public void functionCall() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			if (!tokenAtual.getLexeme().equals("(")) {

				synchronToken();

			}

			functionParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}

		}

	}

//	public void functionParameter() {
//		
//			value();
//			auxFunction();
//		
//		
//	}

	public void auxFunction() {

		if (tokenAtual != null) {

			if (!tokenAtual.getLexeme().equals(",")) {

				synchronToken();

			}

			functionParameter();

		}

	}

	public void cmdIfExpression() {

		if (tokenAtual.getLexeme().equals("if")) {

			tokenAtual = nextToken();

			if (!tokenAtual.getLexeme().equals("(")) {

				synchronToken();

			}

			expLogic();

			if (!tokenAtual.getLexeme().equals(")")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("then")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

			ifElse();

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

			if (!tokenAtual.getLexeme().equals("(")) {

				synchronToken();

			}

			listaArg();

			if (!tokenAtual.getLexeme().equals(")")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}
		}
	}

	public void cmdRead() {

		if (tokenAtual.getLexeme().equals("read")) {

			if (!tokenAtual.getLexeme().equals("(")) {

				synchronToken();

			}

			listaArg();

			if (!tokenAtual.getLexeme().equals(")")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}

		}

	}

	public void ifElse() {

		if (tokenAtual.getLexeme().equals("else")) {

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		} else if (tokenAtual.getLexeme().equals("if")) {

			cmdIfExpression();

		}

	}

	public void cmdWhile() {

		if (tokenAtual.getLexeme().equals("while")) {

			if (!tokenAtual.getLexeme().equals("(")) {

				synchronToken();

			}

			expLogic();

			if (!tokenAtual.getLexeme().equals(")")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		}

	}

	public void struct() {

		if (tokenAtual.getLexeme().equals("struct")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			structDeclaration();

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		}

	}

	/*
	 * 
	 * 
	 * 
	 * INCONCLUSIVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public void structDeclaration() {

		if (tokenAtual != null) {

		}

	}

	public void structInvocation() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			if (!tokenAtual.getLexeme().equals(".")) {

				synchronToken();

			}

			structInvocationDecider();

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}

		}

	}

	/*
	 * 
	 * 
	 * 
	 * INCONCLUSIVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public void structInvocationDecider() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

		}

	}

	public void structExtends() {

		if (tokenAtual.getLexeme().equals("extends")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

		}

	}

	/*
	 * 
	 * 
	 * 
	 * EM ANALISEE----------------->>>>>>>><<<<<<<<<<<<<<---------------------EM
	 * ANALISEE
	 * 
	 * 
	 * 
	 * 
	 */

	public void varB() {

		if (tokenAtual.getLexeme().equals("var")) {

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			varBlock();

			if (tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		}

	}

	public void varBlock() {

		if (tokenAtual != null) {

			varDeclaration();
			varBlock();

		}

	}

	public void varDeclaration() {

		type();
		varDeclarationAux();

	}

	/*
	 * 
	 * 
	 * 
	 * EM ANALISEE----------------->>>>>>>><<<<<<<<<<<<<<---------------------EM
	 * ANALISEE
	 * 
	 * 
	 * 
	 * 
	 */

	public void varDeclarationAux() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			varAssignOrNotAssign();
			varAux();

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}

		} else {

			arrayType();
			varAux();

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}

		}

	}

	/*
	 * 
	 * 
	 * 
	 * EM ANALISEE----------------->>>>>>>><<<<<<<<<<<<<<---------------------EM
	 * ANALISEE
	 * 
	 * 
	 * 
	 * 
	 */

	public void varAux() {

		if (tokenAtual != null) {

			if (!tokenAtual.getLexeme().equals(",")) {

				synchronToken();

			}

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			} else {

				tokenAtual = nextToken();

			}

			if (tokenAtual.getLexeme().equals("[")) {

				arrayType();
				varAux();

			} else {

				varAssignOrNotAssign();
				varAux();

			}

		}

	}

	public void varAssignOrNotAssign() {

	}

	public void type() {

		if (!(tokenAtual.getTypeToken().equals("int") || tokenAtual.getLexeme().equals("string")
				|| tokenAtual.getLexeme().equals("real") || tokenAtual.getLexeme().equals("boolean"))) {

			synchronToken();

		}

	}

	public void value() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode()))) {

			synchronToken();

		}

	}

	public void dataTypeArray() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode()))) {

			synchronToken();

		}

	}

	/*
	 * 
	 * 
	 * 
	 * EM ANALISEE----------------->>>>>>>><<<<<<<<<<<<<<---------------------EM
	 * ANALISEE
	 * 
	 * 
	 * 
	 * 
	 */

	public void atribOp() {

		acess();

		if (!tokenAtual.getLexeme().equals("=")) {

			synchronToken();

		}

		expressionsAndDataTypes();

		if (!tokenAtual.getLexeme().equals(";")) {

			synchronToken();

		}

	}

	/*
	 * 
	 * 
	 * 
	 * EM ANALISEE----------------->>>>>>>><<<<<<<<<<<<<<---------------------EM
	 * ANALISEE
	 * 
	 * 
	 * 
	 * 
	 */

	public void acess() {

		structInvocation();
		arrayType();

	}

	public void program() {

		constB();

		if (!tokenAtual.getTypeToken().equals("start")) {

			synchronToken();

		}

		if (!tokenAtual.getTypeToken().equals("(")) {

			synchronToken();

		}

		if (!tokenAtual.getTypeToken().equals(")")) {

			synchronToken();

		}

		if (!tokenAtual.getTypeToken().equals("{")) {

			synchronToken();

		}

		code();

		if (!tokenAtual.getTypeToken().equals("}")) {

			synchronToken();

		}

	}

	public void constB() {

		if (tokenAtual.getLexeme().equals("const")) {

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			constBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		}

	}

	public void constBlock() {

		if (tokenAtual != null) {

			constDeclaration();
			constBlock();

		}

	}

	public void constDeclaration() {

		type();

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		if (!tokenAtual.getLexeme().equals("=")) {

			synchronToken();

		}

		expressionsAndDataTypes();
		constAux();

		if (!tokenAtual.getLexeme().equals(";")) {

			synchronToken();

		}

	}

}
