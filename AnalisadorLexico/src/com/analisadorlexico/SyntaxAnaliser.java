package com.analisadorlexico;

import java.util.ArrayList;

public class SyntaxAnaliser {

	private ArrayList<Token> listTokens = new ArrayList<Token>();
	private int countToken = 0;
	private Token tokenAtual;
	private ArrayList<String> listTypeToken = new ArrayList<String>();
	private ArrayList<String> listCmdToken = new ArrayList<String>();

	public SyntaxAnaliser() {

		listTypeToken.add("string");
		listTypeToken.add("real");
		listTypeToken.add("boolean");
		listTypeToken.add("int");

		listCmdToken.add("var");
		listCmdToken.add("struct");
		listCmdToken.add("read");
		listCmdToken.add("print");
		listCmdToken.add("while");
		listCmdToken.add("function");
		listCmdToken.add("procedure");
		listCmdToken.add("if");

	}

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

	public void dataTypeArray() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode()))) {

			synchronToken();

		}

	}

	public void startSymbol() {

		program();

	}

	// OK
	// OK
	// OK
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

	// OK
	// OK
	// OK
	public void code() {

		if (listCmdToken.contains(tokenAtual.getLexeme())) {

			codeAux();
			code();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void codeAux() {

		if (tokenAtual.getLexeme().equals("var")) {

			cmdVar();

		} else if (tokenAtual.getLexeme().equals("struct")) {

			cmdStruct();

		} else if (tokenAtual.getLexeme().equals("read")) {

			cmdRead();

		} else if (tokenAtual.getLexeme().equals("print")) {

			cmdPrint();

		} else if (tokenAtual.getLexeme().equals("while")) {

			cmdWhile();

		} else if (tokenAtual.getLexeme().equals("function")) {

			cmdFunction();

		} else if (tokenAtual.getLexeme().equals("procedure")) {

			cmdProcedure();

		} else if (tokenAtual.getLexeme().equals("if")) {

			cmdIfExpression();

		} else if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			if (tokenAtual.getLexeme().equals("(")) {

				functionCall();

			} else if (tokenAtual.getLexeme().equals("=")) {

				atribOp();

			} else {

				// ERRO

			}

		}

	}

	// OK
	// OK
	// OK
	public void arrayDefinition() {

		if (tokenAtual.getLexeme().equals("[")) {

			dataTypeArray();

			if (!tokenAtual.getLexeme().equals("]")) {

				synchronToken();

			}

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void value() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode()))) {

			synchronToken();

		}

	}

	// FALTA
	// FALTA
	// FALTA
	// FALTA
	// FALTA
	// FALTA
	public void expressionsAndDataTypes() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			if (tokenAtual.getLexeme().equals("(")) {

				functionCallParameter();

			}

		} else {

			// ERRO ALGO ASSIM AQ

		}

	}

	// OK
	// OK
	// OK
	public void cmdVar() {

		if (!tokenAtual.getLexeme().equals("{")) {

			synchronToken();

		}

		varBlock();

		if (!tokenAtual.getLexeme().equals("}")) {

			synchronToken();

		}

	}

	// OK
	// OK
	// OK
	public void varBlock() {

		if (listTypeToken.contains(tokenAtual.getLexeme())) {

			type();
			varDeclaration();
			varBlock();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void varDeclaration() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		varDeclarationAux();

		if (!tokenAtual.getTypeToken().equals(";")) {

			synchronToken();

		}

	}

	// OK
	// OK
	// OK
	public void varDeclarationAux() {

		if (tokenAtual.getTypeToken().equals("=")) {

			expressionsAndDataTypes();
			varDeclarationAuxA();

		} else if (tokenAtual.getTypeToken().equals("[")) {

			dataTypeArray();

			if (!tokenAtual.getTypeToken().equals("]")) {

				synchronToken();

			}

			arrayDefinition();
			varDeclarationAuxA();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void varDeclarationAuxA() {

		if (tokenAtual.getTypeToken().equals(",")) {

			if (!tokenAtual.getLexeme().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			varDeclarationAux();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void cmdStruct() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		structAux();

	}

	// OK
	// OK
	// OK
	public void structAux() {

		if (tokenAtual.getLexeme().equals("{")) {

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		} else if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}

		} else if (tokenAtual.getTypeToken().equals("extends")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

			}

		} else {

			// ERRO

		}

	}

	// OK
	// OK
	// OK
	// DECIDIR SE VAI APAGAR OU NÃO
	//
	//
	//
	//
	public void structInvocation() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		if (!tokenAtual.getLexeme().equals(".")) {

			synchronToken();

		}

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		structInvocationDecider();

	}

	// OK
	// OK
	// OK
	// DECIDIR SE VAI APAGAR OU NÃO
	//
	//
	//
	//
	public void structInvocationDecider() {

		if (tokenAtual.getLexeme().equals("[")) {

			dataTypeArray();

			if (tokenAtual.getLexeme().equals("]")) {

				synchronToken();

			}

			arrayDefinition();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void constB() {

		if (!tokenAtual.getLexeme().equals("{")) {

			synchronToken();

		}

		constBlock();

		if (!tokenAtual.getLexeme().equals("}")) {

			synchronToken();

		}

	}

	// OK
	// OK
	// OK
	public void constBlock() {

		if (listTypeToken.contains(tokenAtual.getLexeme())) {

			constDeclaration();
			constBlock();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
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

	// OK
	// OK
	// OK
	public void constAux() {

		if (tokenAtual.getLexeme().equals(",")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals("=")) {

				synchronToken();

			}

			expressionsAndDataTypes();
			constAux();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
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

	// OK
	// OK
	// OK
	public void acess() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		acessAux();

	}

	// OK
	// OK
	// OK
	public void acessAux() {

		if (tokenAtual.getTypeToken().equals(".")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			structInvocationDecider();

		} else if (tokenAtual.getLexeme().equals("[")) {

			dataTypeArray();

			if (!tokenAtual.getLexeme().equals("]")) {

				synchronToken();

			}

			arrayDefinition();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void returnStatement() {

		if (!tokenAtual.getLexeme().equals("return")) {

			synchronToken();

		}

		value();

		if (!tokenAtual.getLexeme().equals(";")) {

			synchronToken();

		}

	}

	// OK
	// OK
	// OK
	public void cmdFunction() {

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

	// OK
	// OK
	// OK
	public void cmdProcedure() {

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

	// OK
	// OK
	// OK
	public void functionParameter() {

		if (listTypeToken.contains(tokenAtual.getLexeme())) {

			type();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			functionParameterAux();
			auxParameter();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void functionParameterAux() {

		if (tokenAtual.getLexeme().equals("[")) {

			dataTypeArray();

			if (tokenAtual.getLexeme().equals("]")) {

				synchronToken();

			}

			arrayDefinition();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void auxParameter() {

		if (tokenAtual.getLexeme().equals(",")) {

			functionParameter();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void functionCall() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		if (!tokenAtual.getLexeme().equals("(")) {

			synchronToken();

		}

		functionCallParameter();

		if (!tokenAtual.getLexeme().equals(")")) {

			synchronToken();

		}

		if (!tokenAtual.getLexeme().equals(";")) {

			synchronToken();

		}

	}

	// OK
	// OK
	// OK
	public void functionCallParameter() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode())) {

			if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				acess();

			}

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void type() {

		if (!(tokenAtual.getTypeToken().equals("int") || tokenAtual.getLexeme().equals("string")
				|| tokenAtual.getLexeme().equals("real") || tokenAtual.getLexeme().equals("boolean"))) {

			synchronToken();

		}

	}

	// OK
	// OK
	// OK
	public void listaArg() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode())) {

			if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				listArgAux();

			}

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void listArgAux() {

		if (tokenAtual.getLexeme().equals(".")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			structInvocationDecider();

		} else if (tokenAtual.getLexeme().equals("[")) {

			dataTypeArray();

			if (!tokenAtual.getLexeme().equals("]")) {

				synchronToken();

			}

			arrayDefinition();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void listaArgRead() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		listArgReadAux();

	}

	// OK
	// OK
	// OK
	public void listArgReadAux() {

		if (tokenAtual.getLexeme().equals(".")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			structInvocationDecider();

		} else if (tokenAtual.getLexeme().equals("[")) {

			dataTypeArray();

			if (!tokenAtual.getLexeme().equals("]")) {

				synchronToken();

			}

			arrayDefinition();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void cmdWhile() {

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

	// OK
	// OK
	// OK
	public void cmdIfExpression() {

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

	// OK
	// OK
	// OK
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

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void cmdPrint() {

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

	// OK
	// OK
	// OK
	public void cmdRead() {

		if (!tokenAtual.getLexeme().equals("(")) {

			synchronToken();

		}

		listaArgRead();

		if (!tokenAtual.getLexeme().equals(")")) {

			synchronToken();

		}

		if (!tokenAtual.getLexeme().equals(";")) {

			synchronToken();

		}

	}

	public void expressionLogic() {
		expressionRelational();

	}

	public void expressionRelational() {
		fatorRelational();
		expressionRelationalAux();

	}

	public void expressionRelationalAux() {

	}

	public void expressionLogicAux() {

	}

	public void fatorRelational() {

		if (!tokenAtual.getLexeme().equals("(")) {
			synchronToken();

		}
		expressionRelational();
		if (!tokenAtual.getLexeme().equals(")")) {
			synchronToken();
		}
		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| !tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())) {
			synchronToken();
		}
		return;
	}

	public void expLogic() {

	}

	public void opLogic() {

	}

	public void opRelational() {

	}

}
