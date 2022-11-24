package com.analisador;

import java.util.ArrayList;

public class Parser {

	private ArrayList<Token> listTokens = new ArrayList<Token>();
	private int countToken = 0;
	private Token tokenAtual;
	private ArrayList<String> listTypeToken = new ArrayList<String>();
	private ArrayList<String> listCmdToken = new ArrayList<String>();
	private ArrayList<SyntacticError> listSyntacticError = new ArrayList<SyntacticError>();

	public Parser() {

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

	public void errorTokenParser(long lineError, String tokenError, String messageError) {

		SyntacticError error = new SyntacticError(lineError, tokenError, messageError);
		listSyntacticError.add(error);

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

	public Token proxToken() {

		if (!isEof()) {

			return listTokens.get(countToken + 1);

		}

		return null;

	}

	public void panicSynchron() {

		if (tokenAtual != null) {

			while (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_DELIMITER.getTypeTokenCode())
					|| tokenAtual.getTypeToken().equals(InitialsToken.TK_RESERVED_WORDS.getTypeTokenCode()))
					&& !isEof()) {

				tokenAtual = nextToken();

			}

		} else {

		}

	}

	public void startParser() {

		program();

	}

	// OK
	// OK
	// OK
	public void program() {

		constB();

		if (!tokenAtual.getTypeToken().equals("start")) {

			errorTokenParser(tokenAtual.getLine(), "start", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		if (!tokenAtual.getTypeToken().equals("(")) {

			errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		if (!tokenAtual.getTypeToken().equals(")")) {

			errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		if (!tokenAtual.getTypeToken().equals("{")) {

			errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		code();

		if (!tokenAtual.getTypeToken().equals("}")) {

			errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	// OK
	// OK
	// OK
	public void code() {

		if (listCmdToken.contains(tokenAtual.getLexeme())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

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

			opAndCallers();

		} else {

			errorTokenParser(tokenAtual.getLine(), "var,struct,read,print,while,function,procedure,if,IDENTIFIER",
					tokenAtual.getLexeme());
			panicSynchron();

		}

	}

	// OK
	// OK
	// OK
	public void value() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER,NUMBER,STRING", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	public void dataTypeArray() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(),
					InitialsToken.TK_NUMBER.getTypeTokenCode() + "," + InitialsToken.TK_IDENTIFIER.getTypeTokenCode(),
					tokenAtual.getLexeme());
			panicSynchron();

		}

	}

	// FALTA
	// FALTA
	// FALTA
	// FALTA
	// FALTA
	// FALTA
	public void expressionsAndDataTypes() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--")
				|| tokenAtual.getLexeme().equals("("))) {

			errorTokenParser(tokenAtual.getLine(), "++, --, (, IDENTIFIER, NUMBER , STRING", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			if (tokenAtual.getLexeme().equals("(")
					|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				expressionsAritmetic();

			}

		}

	}

	public void array() {

		if (!tokenAtual.getLexeme().equals("[")) {

			errorTokenParser(tokenAtual.getLine(), "[", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		dataTypeArray();

		if (!tokenAtual.getLexeme().equals("]")) {

			errorTokenParser(tokenAtual.getLine(), "]", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		arrayDefinition();

	}

	// OK
	// OK
	// OK
	public void arrayDefinition() {

		if (tokenAtual.getLexeme().equals("[")) {

			tokenAtual = nextToken();

			dataTypeArray();

			if (!tokenAtual.getLexeme().equals("]")) {

				errorTokenParser(tokenAtual.getLine(), "]", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void cmdVar() {

		if (tokenAtual.getLexeme().equals("var")) {

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

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

		} else if (tokenAtual.getLexeme().equals("struct")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			varDeclarationStruct();
			varBlock();

		} else {

			return;

		}

	}

	public void varDeclarationStruct() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		varDeclarationStructAux();

		if (!tokenAtual.getLexeme().equals(";")) {

			errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	public void varDeclarationStructAux() {

		if (tokenAtual.getLexeme().equals(",")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void varDeclaration() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		varDeclarationAux();

		if (!tokenAtual.getTypeToken().equals(";")) {

			errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

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

			array();
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

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

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

		if (tokenAtual.getLexeme().equals("struct")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			structAux();

		}

	}

	// OK
	// OK
	// OK
	public void structAux() {

		if (tokenAtual.getLexeme().equals("{")) {

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		} else if (tokenAtual.getTypeToken().equals("extends")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		} else {

			errorTokenParser(tokenAtual.getLine(), "{ , extends", tokenAtual.getLexeme());
			panicSynchron();

		}

	}

	public void structInvocationDecider() {

		if (tokenAtual.getLexeme().equals("[")) {

			array();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void constB() {

		if (tokenAtual.getLexeme().equals("const")) {

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			constBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

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

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		if (!tokenAtual.getLexeme().equals("=")) {

			errorTokenParser(tokenAtual.getLine(), "=", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		expressionsAndDataTypes();
		constAux();

		if (!tokenAtual.getLexeme().equals(";")) {

			errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	// OK
	// OK
	// OK
	public void constAux() {

		if (tokenAtual.getLexeme().equals(",")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("=")) {

				errorTokenParser(tokenAtual.getLine(), "=", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

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
	public void opAndCallers() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		opAndCallersAuxA();

		if (!tokenAtual.getLexeme().equals(";")) {

			errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	public void opAndCallersAuxA() {

		if (tokenAtual.getLexeme().equals("=") || tokenAtual.getLexeme().equals("[")
				|| tokenAtual.getLexeme().equals(".")) {

			opAndCallersAuxB();
			opAndCallersAuxC();

		} else if (tokenAtual.getLexeme().equals("(")) {

			functionCallParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		} else {

			errorTokenParser(tokenAtual.getLine(), " = , ( , [ , . ", tokenAtual.getLexeme());
			panicSynchron();

		}

	}

	public void opAndCallersAuxC() {

		if (tokenAtual.getLexeme().equals("=")) {

			expressionsAndDataTypes();

		} else {

			return;

		}

	}

	public void opAndCallersAuxB() {

		if (tokenAtual.getLexeme().equals(".")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			structInvocationDecider();

		} else if (tokenAtual.getLexeme().equals("[")) {

			array();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void returnStatement() {

		if (!tokenAtual.getLexeme().equals("return")) {

			errorTokenParser(tokenAtual.getLine(), "return", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		value();

		if (!tokenAtual.getLexeme().equals(";")) {

			errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	// OK
	// OK
	// OK
	public void cmdFunction() {

		if (tokenAtual.getLexeme().equals("function")) {

			type();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			functionParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			code();
			returnStatement();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void cmdProcedure() {

		if (tokenAtual.getLexeme().equals("procedure")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			functionParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void functionParameter() {

		if (listTypeToken.contains(tokenAtual.getLexeme())) {

			type();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

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

			array();

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
	public void functionCallParameter() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode())) {

			functionCallParameterAuxA();

		} else {

			return;

		}

	}

	public void functionCallParameterAuxA() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER,NUMBER,STRING", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				functionCallParameterAuxB();

			}

		}

	}

	public void functionCallParameterAuxB() {

		if (tokenAtual.getLexeme().equals("[")) {

			array();

		} else {

			return;

		}

	}

	public void auxCallParameter() {

		if (tokenAtual.getLexeme().equals(",")) {

			functionCallParameter();

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

			errorTokenParser(tokenAtual.getLine(), "int,string,real,boolean", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

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

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			structInvocationDecider();

		} else if (tokenAtual.getLexeme().equals("[")) {

			array();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void listaArgRead() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

		listArgReadAux();

	}

	// OK
	// OK
	// OK
	public void listArgReadAux() {

		if (tokenAtual.getLexeme().equals(".")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDENTIFIER", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			structInvocationDecider();

		} else if (tokenAtual.getLexeme().equals("[")) {

			array();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void cmdWhile() {

		if (tokenAtual.getLexeme().equals("while")) {

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			expressionLogicRelational();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void cmdIfExpression() {

		if (tokenAtual.getLexeme().equals("if")) {

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			expressionLogicRelational();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("then")) {

				errorTokenParser(tokenAtual.getLine(), "then", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			ifElse();

		}

	}

	// OK
	// OK
	// OK
	public void ifElse() {

		if (tokenAtual.getLexeme().equals("else")) {

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

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

		if (tokenAtual.getLexeme().equals("print")) {

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			listaArg();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals(";")) {

				errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void cmdRead() {

		if (tokenAtual.getLexeme().equals("read")) {

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			listaArgRead();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

			if (!tokenAtual.getLexeme().equals(";")) {

				errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
				panicSynchron();

			} else {

				tokenAtual = nextToken();

			}

		}

	}

	public void expressionLogicRelational() {

		expressionLogicAnd();
		expressionLogicOrAux();

	}

	public void expressionLogicOrAux() {

		if (tokenAtual.getLexeme().equals("||")) {

			expressionLogicAnd();
			expressionLogicOrAux();

		} else {

			return;

		}

	}

	public void expressionLogicAnd() {

		expressionLogicDenial();
		expressionLogicAndAux();

	}

	public void expressionLogicAndAux() {

		if (tokenAtual.getLexeme().equals("&&")) {

			expressionLogicDenial();
			expressionLogicAndAux();

		} else {

			return;

		}

	}

	public void expressionLogicDenial() {

		expressionLogicRelational();
		expressionLogicDenialAux();

	}

	public void expressionLogicDenialAux() {

		if (tokenAtual.getLexeme().equals("!")) {

			expressionLogicRelational();
			expressionLogicDenialAux();

		} else {

			return;

		}

	}

	public void expressionRelational() {

		fatorRelational();
		expressionRelationalAux();

	}

	public void expressionRelationalAux() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_RELATIONAL_OPERATOR.getTypeTokenCode())) {

			opRelational();
			fatorRelational();
			expressionRelationalAux();

		} else {

			return;

		}

	}

	public void fatorRelational() {

		if (!(tokenAtual.getLexeme().equals("(")
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER,NUMBER,(", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			if (tokenAtual.getLexeme().equals("(")) {

				expressionLogicRelational();

			}

		}

	}

	// OK
	// OK
	// OK
	public void expressionsAritmetic() {

		if (!(tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--")
				|| tokenAtual.getLexeme().equals("(")
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "++,--,(,IDENTIFIER,NUMBER", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			term();
			expressionsAritmeticAux();

		}

	}

	// OK
	// OK
	// OK
	public void expressionsAritmeticAux() {

		if (tokenAtual.getLexeme().equals("-") || tokenAtual.getLexeme().equals("+")) {

			sum();
			term();
			expressionsAritmeticAux();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void term() {

		if (!(tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--")
				|| tokenAtual.getLexeme().equals("(")
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "++,--,(,IDENTIFIER,NUMBER", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			incre();
			termoAux();

		}

	}

	// OK
	// OK
	// OK
	public void termoAux() {

		if (tokenAtual.getLexeme().equals("*") || tokenAtual.getLexeme().equals("/")) {

			mult();
			incre();
			termoAux();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK

	public void incre() {

		if (tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--")) {

			operatorU();
			incre();
			increAux();

		} else if (tokenAtual.getLexeme().equals("(")
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())) {

			fator();
			increAux();

		} else {

			errorTokenParser(tokenAtual.getLine(), "++,--,(,IDENTIFIER,NUMBER", tokenAtual.getLexeme());
			panicSynchron();

		}

	}

	// OK
	// OK
	// OK
	public void increAux() {

		if (tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--")) {

			increAux();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void fator() {

		if (tokenAtual.getLexeme().equals("(")
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())) {

			if (tokenAtual.getLexeme().equals("(")) {

				expressionsAritmetic();

				if (!tokenAtual.getLexeme().equals(")")) {

					errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
					panicSynchron();

				} else {

					tokenAtual = nextToken();

				}

			}

		} else {

			errorTokenParser(tokenAtual.getLine(), "IDENTIFIER,NUMBER,(", tokenAtual.getLexeme());
			panicSynchron();

		}

	}

	public void sum() {

		if (!(tokenAtual.getLexeme().equals("+") || tokenAtual.getLexeme().equals("-"))) {

			errorTokenParser(tokenAtual.getLine(), "+,-", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	public void mult() {

		if (!(tokenAtual.getLexeme().equals("*") || tokenAtual.getLexeme().equals("/"))) {

			errorTokenParser(tokenAtual.getLine(), "*,-", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	public void operatorU() {

		if (!(tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--"))) {

			errorTokenParser(tokenAtual.getLine(), "++,--", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

	public void opRelational() {

		if (!(tokenAtual.getLexeme().equals(">") || tokenAtual.getLexeme().equals(">=")
				|| tokenAtual.getLexeme().equals("<") || tokenAtual.getLexeme().equals("<=")
				|| tokenAtual.getLexeme().equals("==") || tokenAtual.getLexeme().equals("!="))) {

			errorTokenParser(tokenAtual.getLine(), ">,>=,<=,==,!=", tokenAtual.getLexeme());
			panicSynchron();

		} else {

			tokenAtual = nextToken();

		}

	}

}
