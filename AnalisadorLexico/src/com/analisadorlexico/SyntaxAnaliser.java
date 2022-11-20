package com.analisadorlexico;

import java.util.ArrayList;

public class SyntaxAnaliser {

	private ArrayList<Token> listTokens = new ArrayList<Token>();
	private int countToken = 0;
	private Token tokenAtual;
	private ArrayList<String> listTypeToken = new ArrayList<String>();

	public SyntaxAnaliser() {

		listTypeToken.add("string");
		listTypeToken.add("real");
		listTypeToken.add("boolean");
		listTypeToken.add("int");

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

	/*
	 * 
	 * <Var> ::= var'{'<Var_Block>'}' <Var_Block> ::= <> | <Type> <Var_Declaration>
	 * <Var_Block> <Var_Declaration> ::= Identifier <Var_Declaration_Aux>';'
	 * <Var_Declaration_Aux> ::= '=' <Expressions_And_DataTypes>
	 * <Var_Declaration_AuxA> | '['<Data_Type_Array>']'<Array_Definition>
	 * <Var_Declaration_AuxA> | <> <Var_Declaration_AuxA> ::= <> | ',' Identifier
	 * <Var_Declaration_Aux>
	 * 
	 */

	// OK
	// OK
	// OK
	public void var() {

		if (tokenAtual.getLexeme().equals("var")) {

			if (!tokenAtual.getLexeme().equals("{")) {

				synchronToken();

			}

			varBlock();

			if (!tokenAtual.getLexeme().equals("}")) {

				synchronToken();

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

	/*
	 * 
	 * <Struct> ::= struct Identifier <Struct_Declaration> <Struct_Declaration> ::=
	 * <Struct_Declaration_AuxA>'{'<Var_Block>'}' <Struct_Declaration_AuxA> ::= <> |
	 * <Struct_Extends> <Struct_Invocation> ::=
	 * Identifier'.'<Struct_Invocation_Decider>';' <Struct_Invocation_Decider> ::=
	 * Identifier <Struct_Invocation_Decider_Aux> <Struct_Invocation_Decider_Aux>
	 * ::= <> | '['<Data_Type_Array>']'<Array_Definition> <Struct_Extends> ::=
	 * extends Identifier
	 * 
	 */
	public void struct() {

		if (tokenAtual.getLexeme().equals("struct")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			structDeclaration();

		}

	}

	/*
	 * First(StructDeclaration) = { { , extends }
	 * 
	 */

	public void structDeclaration() {

		structDeclarationAuxA();

		if (!tokenAtual.getLexeme().equals("{")) {

			synchronToken();

		}

		varBlock();

		if (!tokenAtual.getLexeme().equals("}")) {

			synchronToken();

		}

	}

	public void structDeclarationAuxA() {

		if (tokenAtual.getLexeme().equals("extends")) {

			if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

				synchronToken();

			}

			if (!tokenAtual.getTypeToken().equals((InitialsToken.TK_IDENTIFIER.getTypeTokenCode()))) {

				synchronToken();

			}

		} else {

			return;

		}

	}

	public void structInvocation() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		if (!tokenAtual.getLexeme().equals(".")) {

			synchronToken();

		}

		structInvocationDecider();

		if (!tokenAtual.getLexeme().equals(";")) {

			synchronToken();

		}

	}

	public void structInvocationDecider() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		structInvocationDeciderAux();

	}

	public void structInvocationDeciderAux() {

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

		if (listTypeToken.contains(tokenAtual.getLexeme())) {

			constDeclaration();
			constBlock();

		} else {

			return;

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

	public void acess() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			synchronToken();

		}

		acessAux();

	}

	public void acessAux() {

		if (tokenAtual.getTypeToken().equals(".")) {

			structInvocationDecider();

		} else if (tokenAtual.getLexeme().equals("[")) {

			dataTypeArray();

			if (!tokenAtual.getLexeme().equals("]")) {

				synchronToken();

			}

			arrayDefinition();

		}else {
			
			return;
			
		}

	}
	
	public void returnStatement() {

		if (!tokenAtual.getLexeme().equals("return")) {

			synchronToken();

		}

		value();

		if (!tokenAtual.getLexeme().equals(";")) {

			synchronToken();

		}

	}

	public void auxParameter() {

		if (tokenAtual.getLexeme().equals(",")) {

			type();
			value();
			auxParameter();

		} else {

			return;

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

		if (listTypeToken.contains(tokenAtual.getLexeme())) {

			type();
			value();
			auxParameter();

		} else {

			return;

		}

	}

	public void functionCall() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			if (!tokenAtual.getLexeme().equals("(")) {

				synchronToken();

			}

			functionParameterCall();

			if (!tokenAtual.getLexeme().equals(")")) {

				synchronToken();

			}

			if (!tokenAtual.getLexeme().equals(";")) {

				synchronToken();

			}

		}

	}

	public void functionParameterCall() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode())) {

			value();
			auxFunction();

		} else {

			return;

		}

	}

	public void auxFunction() {

		if (tokenAtual.getLexeme().equals(",")) {

			functionParameter();

		} else {

			return;

		}

	}
	
	
	
	
	
	
	

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

	public void execAnaliser() {

		tokenAtual = nextToken();
		while (tokenAtual.getLexeme() != "") {

			cmdIfExpression();

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

	// OK
	// OK
	// OK
	public void listaArg() {

		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| !tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| !tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode())) {

			synchronToken();

		} else if (tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {

			listArgAux();

		}

	}

	// OK
	// OK
	// OK
	public void listArgAux() {

		if (tokenAtual.getLexeme().equals(".")) {

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

	}

	// OK
	// OK
	// OK
	public void listArgReadAux() {

		if (tokenAtual.getLexeme().equals(".")) {

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

		} else {

			return;

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

//	public boolean type() {
//
//		if (!tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())) {
//
//			synchronToken();
//
//		} else {
//
//			tokenAtual = nextToken();
//
//		}
//
//		if (tokenAtual.getLexeme().equals("[")) {
//
//			arrayType();
//			varAux();
//
//		} else {
//
//			varAssignOrNotAssign();
//			varAux();
//
//		}
//
//	}else
//
//	{
//
//		return;
//
//	}
//
//	}

//	public void varAssignOrNotAssign() {
//
//		if (tokenAtual.getLexeme().equals("=")) {
//
//			ExpressionsAndDataTypes();
//
//		} else {
//
//			return;
//
//		}
//
//	}

//	public void type() {
//
//		if (!(tokenAtual.getTypeToken().equals("int") || tokenAtual.getLexeme().equals("string")
//				|| tokenAtual.getLexeme().equals("real") || tokenAtual.getLexeme().equals("boolean"))) {
//
//			synchronToken();
//
//			return false;
//
//		}
//
//		return true;
//
//	}

	public void value() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode()))) {

			synchronToken();

		}

	}

	public void expressionsAndDataTypes() {

		if (tokenAtual.getTypeToken().equals(InitialsToken.TK_STRING.getTypeTokenCode())) {
			
			
		} else if(){


		}else {
			

		} else {

			expressionAritmetic();

		}

	}

	public void dataTypeArray() {

		if (!(tokenAtual.getTypeToken().equals(InitialsToken.TK_NUMBER.getTypeTokenCode())
				|| tokenAtual.getTypeToken().equals(InitialsToken.TK_IDENTIFIER.getTypeTokenCode()))) {

			synchronToken();

		}

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

	public void expLogic() {

	}

	public void code() {

	}

}
