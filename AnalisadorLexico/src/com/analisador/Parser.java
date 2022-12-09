package com.analisador;

import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

	private ArrayList<Token> listTokens = new ArrayList<Token>();
	private int countToken = 0;
	private Token tokenAtual;
	private ArrayList<String> listTypeToken = new ArrayList<String>();
	private ArrayList<String> listCmdToken = new ArrayList<String>();
	private static ArrayList<SyntacticError> listSyntacticError = new ArrayList<SyntacticError>();

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

	public static ArrayList<SyntacticError> getListSyntacticError() {
		return Parser.listSyntacticError;
	}

	public static void setListSyntacticError(ArrayList<SyntacticError> listSyntacticError) {
		Parser.listSyntacticError = listSyntacticError;
	}

	public void refreshTokenList() {

		this.listTokens = LexicalAnaliser.getListTokens();
		countToken = 0;
		listSyntacticError = new ArrayList<SyntacticError>();
		tokenAtual = listTokens.get(countToken);

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

	public Token proxToken() {

		if (!isEof()) {

			return listTokens.get(countToken++);

		}

		return null;

	}

	public void panicSynchron(String followSet) {

		ArrayList<String> followSetArrayList = new ArrayList<String>(Arrays.asList(followSet.split(" ")));

		while (!(followSetArrayList.contains(tokenAtual.getLexeme())
				|| followSetArrayList.contains(tokenAtual.getTypeToken())) && !isEof()) {

			tokenAtual = proxToken();

		}

	}

	public void startParser() {

		tokenAtual = proxToken();
		program();

	}

	// OK
	// OK
	// OK
	public void program() {

		if (!tokenAtual.getLexeme().equals("const")) {

			errorTokenParser(tokenAtual.getLine(), "const", tokenAtual.getLexeme());
			panicSynchron("PRE DEL");

		} else {

			constB();

		}

		if (!tokenAtual.getLexeme().equals("var")) {

			errorTokenParser(tokenAtual.getLine(), "var", tokenAtual.getLexeme());
			panicSynchron("PRE DEL");

		} else {

			cmdVar();

		}

		structMultiple();

		if (!tokenAtual.getLexeme().equals("start")) {

			errorTokenParser(tokenAtual.getLine(), "start", tokenAtual.getLexeme());
			panicSynchron("PRE DEL");

		} else {

			tokenAtual = proxToken();

		}

		if (!tokenAtual.getLexeme().equals("(")) {

			errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
			panicSynchron("PRE DEL");

		} else {

			tokenAtual = proxToken();

		}

		if (!tokenAtual.getLexeme().equals(")")) {

			errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
			panicSynchron("PRE DEL");

		} else {

			tokenAtual = proxToken();

		}

		if (!tokenAtual.getLexeme().equals("{")) {

			errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
			panicSynchron("PRE DEL");

		} else {

			tokenAtual = proxToken();

		}

		code();

		if (!tokenAtual.getLexeme().equals("}")) {

			errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
			panicSynchron("PRE DEL");

		} else {

			tokenAtual = proxToken();

		}

	}

	public void structMultiple() {

		if (tokenAtual.getLexeme().equals("struct")) {

			cmdStruct();
			structMultiple();

		} else {

			return;

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

			errorTokenParser(tokenAtual.getLine(), "var,struct,read,print,while,function,procedure,if,IDE",
					tokenAtual.getLexeme());
			panicSynchron("} var struct read if function IDE procedure while print");

		}

	}

	// OK
	// OK
	// OK
	public void value() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "IDE,NRO,CAC", tokenAtual.getLexeme());
			panicSynchron(";");

		} else {

			tokenAtual = proxToken();

		}

	}

	public void dataTypeArray() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "NRO,IDE", tokenAtual.getLexeme());
			panicSynchron("]");

		} else {

			tokenAtual = proxToken();

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

			errorTokenParser(tokenAtual.getLine(), "++, --, (, IDE, NRO , CAC", tokenAtual.getLexeme());
			panicSynchron("( , ;");

		} else {

			if (tokenAtual.getLexeme().equals("(")
					|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
					|| tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--")
					|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())) {

				expressionsAritmetic();

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	public void array() {

		if (tokenAtual.getLexeme().equals("[")) {

			tokenAtual = proxToken();

			dataTypeArray();

			if (!tokenAtual.getLexeme().equals("]")) {

				errorTokenParser(tokenAtual.getLine(), "]", tokenAtual.getLexeme());
				panicSynchron(", ; = )");

			} else {

				tokenAtual = proxToken();

			}

			arrayDefinition();

		}

	}

	// OK
	// OK
	// OK
	public void arrayDefinition() {

		if (tokenAtual.getLexeme().equals("[")) {

			tokenAtual = proxToken();

			dataTypeArray();

			if (!tokenAtual.getLexeme().equals("]")) {

				errorTokenParser(tokenAtual.getLine(), "]", tokenAtual.getLexeme());
				panicSynchron(", ; = )");

			} else {

				tokenAtual = proxToken();

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

			tokenAtual = proxToken();

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print start");

			} else {

				tokenAtual = proxToken();

			}

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print start");

			} else {

				tokenAtual = proxToken();

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

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron("}");

			} else {

				tokenAtual = proxToken();

			}

			varDeclarationStruct();
			varBlock();

		} else if (!listTypeToken.contains(tokenAtual.getLexeme()) && !tokenAtual.getLexeme().equals("struct")
				&& !tokenAtual.getLexeme().equals("}")) {

			errorTokenParser(tokenAtual.getLine(), "PRE(int,real,string,boolean,struct)", tokenAtual.getLexeme());
			panicSynchron("} PRE");
			varBlock();

		} else {

			return;

		}

	}

	public void varDeclarationStruct() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
			panicSynchron("int real boolean string struct }");

		} else {

			tokenAtual = proxToken();

		}

		varDeclarationStructAux();

		if (!tokenAtual.getLexeme().equals(";")) {

			errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
			panicSynchron("int real boolean string struct }");

		} else {

			tokenAtual = proxToken();

		}

	}

	public void varDeclarationStructAux() {

		if (tokenAtual.getLexeme().equals(",")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron(";");

			} else {

				tokenAtual = proxToken();

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

			errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
			panicSynchron("int real boolean string struct }");

		} else {

			tokenAtual = proxToken();

		}

		varDeclarationAux();

		if (!tokenAtual.getLexeme().equals(";")) {

			errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
			panicSynchron("int real boolean string struct }");

		} else {

			tokenAtual = proxToken();

		}

	}

	// OK
	// OK
	// OK
	public void varDeclarationAux() {

		if (tokenAtual.getLexeme().equals("=")) {

			tokenAtual = proxToken();
			expressionsAndDataTypes();
			varDeclarationAuxA();

		} else if (tokenAtual.getLexeme().equals("[")) {

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

		if (tokenAtual.getLexeme().equals(",")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron(";");

			} else {

				tokenAtual = proxToken();

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

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron("{ var struct read if function procedure while print start");

			} else {

				tokenAtual = proxToken();

			}

			structExtends();

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function procedure while print start ");

			} else {

				tokenAtual = proxToken();

			}

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function procedure IDE while print start ");

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void structExtends() {

		if (tokenAtual.getLexeme().equals("extends")) {

			tokenAtual = proxToken();
			
			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron("{");

			} else {

				tokenAtual = proxToken();

			}

		} else {

			return;

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

			tokenAtual = proxToken();

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron("var");

			} else {

				tokenAtual = proxToken();

			}

			constBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron("var");

			} else {

				tokenAtual = proxToken();

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

		} else if (!listTypeToken.contains(tokenAtual.getLexeme()) && !tokenAtual.getLexeme().equals("}")) {

			errorTokenParser(tokenAtual.getLine(), "PRE(int,real,string,boolean)", tokenAtual.getLexeme());
			panicSynchron("} PRE");
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

			errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
			panicSynchron("} int real boolean string");

		} else {

			tokenAtual = proxToken();

		}

		if (!tokenAtual.getLexeme().equals("=")) {

			errorTokenParser(tokenAtual.getLine(), "=", tokenAtual.getLexeme());
			panicSynchron("} int real boolean string");

		} else {

			tokenAtual = proxToken();

		}

		expressionsAndDataTypes();
		constAux();

		if (!tokenAtual.getLexeme().equals(";")) {

			errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
			panicSynchron("} int real boolean string");

		} else {

			tokenAtual = proxToken();

		}

	}

	// OK
	// OK
	// OK
	public void constAux() {

		if (tokenAtual.getLexeme().equals(",")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron(";");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals("=")) {

				errorTokenParser(tokenAtual.getLine(), "=", tokenAtual.getLexeme());
				panicSynchron(";");

			} else {

				tokenAtual = proxToken();

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

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			tokenAtual = proxToken();

			opAndCallersAuxA();

			if (!tokenAtual.getLexeme().equals(";")) {

				errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	public void opAndCallersAuxA() {

		if (tokenAtual.getLexeme().equals("=") || tokenAtual.getLexeme().equals("[")
				|| tokenAtual.getLexeme().equals(".")) {

			opAndCallersAuxB();
			opAndCallersAuxC();

		} else if (tokenAtual.getLexeme().equals("(")) {

			tokenAtual = proxToken();

			functionCallParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron(";");

			} else {

				tokenAtual = proxToken();

			}

		} else {

			errorTokenParser(tokenAtual.getLine(), " = , ( , [ , . ", tokenAtual.getLexeme());
			panicSynchron(";");

		}

	}

	public void opAndCallersAuxC() {

		if (tokenAtual.getLexeme().equals("=")) {

			tokenAtual = proxToken();
			expressionsAndDataTypes();

		} else {

			return;

		}

	}

	public void opAndCallersAuxB() {

		if (tokenAtual.getLexeme().equals(".")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron("; =");

			} else {

				tokenAtual = proxToken();

			}

			structInvocationDecider();
			opAndCallersAuxC();

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
			panicSynchron("}");

		} else {

			tokenAtual = proxToken();

			value();

			if (!tokenAtual.getLexeme().equals(";")) {

				errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
				panicSynchron("}");

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void cmdFunction() {

		if (tokenAtual.getLexeme().equals("function")) {

			tokenAtual = proxToken();
			type();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron("( } var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron("int real string boolean } var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			functionParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron("{ var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			code();
			returnStatement();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void cmdProcedure() {

		if (tokenAtual.getLexeme().equals("procedure")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron("( } var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron("int real boolean string } var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			functionParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

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

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron(", )");

			} else {

				tokenAtual = proxToken();

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

			tokenAtual = proxToken();
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
			auxCallParameter();

		} else {

			return;

		}

	}

	public void functionCallParameterAuxA() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "IDE,NRO,CAC", tokenAtual.getLexeme());
			panicSynchron(", )");

		} else {

			if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				tokenAtual = proxToken();
				functionCallParameterAuxB();

			} else {

				tokenAtual = proxToken();

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

			tokenAtual = proxToken();
			functionCallParameter();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void type() {

		if (!(tokenAtual.getLexeme().equals("int") || tokenAtual.getLexeme().equals("string")
				|| tokenAtual.getLexeme().equals("real") || tokenAtual.getLexeme().equals("boolean"))) {

			errorTokenParser(tokenAtual.getLine(), "int,string,real,boolean", tokenAtual.getLexeme());
			panicSynchron("IDE");

		} else {

			tokenAtual = proxToken();

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

				tokenAtual = proxToken();
				listArgAux();

			} else {

				tokenAtual = proxToken();

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

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron(")");

			} else {

				tokenAtual = proxToken();

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

			errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
			panicSynchron(")");

		} else {

			tokenAtual = proxToken();

		}

		listArgReadAux();

	}

	// OK
	// OK
	// OK
	public void listArgReadAux() {

		if (tokenAtual.getLexeme().equals(".")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron(")");

			} else {

				tokenAtual = proxToken();

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

			tokenAtual = proxToken();

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			expressionLogicRelational();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron("} { var struct read if functio procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void cmdIfExpression() {

		if (tokenAtual.getLexeme().equals("if")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			expressionLogicRelational();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron("{ then } var struct read if function procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals("then")) {

				errorTokenParser(tokenAtual.getLine(), "then", tokenAtual.getLexeme());
				panicSynchron("{ } var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			ifElse();

		}

	}

	// OK
	// OK
	// OK
	public void ifElse() {

		if (tokenAtual.getLexeme().equals("else")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getLexeme().equals("{")) {

				errorTokenParser(tokenAtual.getLine(), "{", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			code();

			if (!tokenAtual.getLexeme().equals("}")) {

				errorTokenParser(tokenAtual.getLine(), "}", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

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

			tokenAtual = proxToken();

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron(") } var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			listaArg();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron("; } var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals(";")) {

				errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void cmdRead() {

		if (tokenAtual.getLexeme().equals("read")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getLexeme().equals("(")) {

				errorTokenParser(tokenAtual.getLine(), "(", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			listaArgRead();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron("; } var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

			if (!tokenAtual.getLexeme().equals(";")) {

				errorTokenParser(tokenAtual.getLine(), ";", tokenAtual.getLexeme());
				panicSynchron("} var struct read if function IDE procedure while print");

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	public void expressionLogicRelational() {

		expressionLogicAnd();
		expressionLogicOrAux();

	}

	public void expressionLogicOrAux() {

		if (tokenAtual.getLexeme().equals("||")) {

			tokenAtual = proxToken();
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

			tokenAtual = proxToken();
			expressionLogicDenial();
			expressionLogicAndAux();

		} else {

			return;

		}

	}

	public void expressionLogicDenial() {

		expressionRelational();
		expressionLogicDenialAux();

	}

	public void expressionLogicDenialAux() {

		if (tokenAtual.getLexeme().equals("!")) {

			tokenAtual = proxToken();
			expressionRelational();
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
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getLexeme().equals("true") || tokenAtual.getLexeme().equals("false"))) {

			errorTokenParser(tokenAtual.getLine(), "IDE,NRO,PRE(TRUE,FALSE)", tokenAtual.getLexeme());
			panicSynchron(") || && ! > < >= <= == !=");

		} else {

			if (tokenAtual.getLexeme().equals("(")) {

				tokenAtual = proxToken();
				expressionLogicRelational();

				if (!tokenAtual.getLexeme().equals(")")) {

					errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
					panicSynchron(") || && ! > < >= <= == !=");

				}

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	// OK
	// OK
	// OK
	public void expressionsAritmetic() {

		term();
		expressionAritmeticAux();

	}

	// OK
	// OK
	// OK
	public void expressionAritmeticAux() {

		if (tokenAtual.getLexeme().equals("-") || tokenAtual.getLexeme().equals("+")) {

			sum();
			term();
			expressionAritmeticAux();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void term() {

		incre();
		termoAux();

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

			errorTokenParser(tokenAtual.getLine(), "++,--,(,IDE,NRO", tokenAtual.getLexeme());
			panicSynchron("* / + - , ; ) ++ --");
		}

	}

	// OK
	// OK
	// OK
	public void increAux() {

		if (tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--")) {

			operatorU();
			increAux();

		} else {

			return;

		}

	}

	// OK
	// OK
	// OK
	public void fator() {

		if (!(tokenAtual.getLexeme().equals("(")
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode()))) {

			errorTokenParser(tokenAtual.getLine(), "IDE,NRO,(", tokenAtual.getLexeme());
			panicSynchron("* / + - , ; ) ++ --");

		} else {

			if (tokenAtual.getLexeme().equals("(")) {

				tokenAtual = proxToken();

				expressionsAritmetic();

				if (!tokenAtual.getLexeme().equals(")")) {

					errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
					panicSynchron("* / + - , ; ) ++ --");

				} else {

					tokenAtual = proxToken();

				}

			}else if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				tokenAtual = proxToken();

				fatorAux();

			} else {

				tokenAtual = proxToken();

			}

		}

	}

	public void fatorAux() {

		if (tokenAtual.getLexeme().equals(".")) {

			tokenAtual = proxToken();

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				errorTokenParser(tokenAtual.getLine(), "IDE", tokenAtual.getLexeme());
				panicSynchron("* / + - , ; ) ++ --");

			} else {

				tokenAtual = proxToken();

			}

			structInvocationDecider();

		} else if (tokenAtual.getLexeme().equals("[")) {

			array();

		} else if (tokenAtual.getLexeme().equals("(")) {

			tokenAtual = proxToken();

			functionCallParameter();

			if (!tokenAtual.getLexeme().equals(")")) {

				errorTokenParser(tokenAtual.getLine(), ")", tokenAtual.getLexeme());
				panicSynchron("* / + - , ; ) ++ --");

			} else {

				tokenAtual = proxToken();

			}

		} else {

			return;

		}

	}

	public void sum() {

		if (!(tokenAtual.getLexeme().equals("+") || tokenAtual.getLexeme().equals("-"))) {

			errorTokenParser(tokenAtual.getLine(), "+,-", tokenAtual.getLexeme());
			panicSynchron("++ -- ( NRO IDE");

		} else {

			tokenAtual = proxToken();

		}

	}

	public void mult() {

		if (!(tokenAtual.getLexeme().equals("*") || tokenAtual.getLexeme().equals("/"))) {

			errorTokenParser(tokenAtual.getLine(), "*,-", tokenAtual.getLexeme());
			panicSynchron("++ -- ( NRO IDE");

		} else {

			tokenAtual = proxToken();

		}

	}

	public void operatorU() {

		if (!(tokenAtual.getLexeme().equals("++") || tokenAtual.getLexeme().equals("--"))) {

			errorTokenParser(tokenAtual.getLine(), "++,--", tokenAtual.getLexeme());
			panicSynchron("++ -- ( NRO IDE");

		} else {

			tokenAtual = proxToken();

		}

	}

	public void opRelational() {

		if (!(tokenAtual.getLexeme().equals(">") || tokenAtual.getLexeme().equals(">=")
				|| tokenAtual.getLexeme().equals("<") || tokenAtual.getLexeme().equals("<=")
				|| tokenAtual.getLexeme().equals("==") || tokenAtual.getLexeme().equals("!="))) {

			errorTokenParser(tokenAtual.getLine(), ">,>=,<=,==,!=", tokenAtual.getLexeme());
			panicSynchron("( NRO IDE");

		} else {

			tokenAtual = proxToken();

		}

	}

}
