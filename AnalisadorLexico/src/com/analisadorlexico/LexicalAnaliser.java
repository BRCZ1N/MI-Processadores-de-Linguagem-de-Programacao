package com.analisadorlexico;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

public class LexicalAnaliser {
	
	private LinkedList<Character> contentEntry;
	
	//public static int TokenReservedWord = 3;
	/*public static  int TokenNumber = 1;
    public static int TokenAritmeticOperator = 2;
    public static int TokenIdentifier = 4;
    public static int TokenDigit = 5;
    public static int TokenLetter = 6;
    public static int TokenRelationalOperator = 7;
    public static int TokenLogicOperator = 8;
    public static int TokenDelimiter = 9;
    public static int TokenString = 10;
    public static int TokenSymbol = 11;
    public static int TokenEspace = 12;/
	public boolean isDigit*/

	
	public LexicalAnaliser(String fileName){
		
		String contentFile;
		
		try {
			
			contentFile = new String(Files.readAllBytes(Paths.get(fileName)),StandardCharsets.UTF_8);
			toLinkedList(contentFile.toCharArray());
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public boolean isLetter(char c){
		
		if(c >= 'a' || c <= 'z' || c >= 'A' || c <= 'Z' ) {
			
			return true;
			
		}
	
		return false;
		
	}
	
	public boolean isAritmethicOperator(char c){

		if(c == '+' || c == '-' || c == '*' || c == '/') {
			
			return true;
			
		}
		
		return false;
	
	}
	
	public boolean isRelationalOperator(char c) {
		
		if(c == '=' || c == '>' || c == '<') {  
			
			return true;
			
		}
		
		return false;
		
	}
	
	public boolean isLogicalOperator(char c, char nextC) {

		if(c == '!'|| (c == '&' && nextC == '&') || (c == '|' && nextC == '|')) {
			
			
			return true;
			
		}
		
		return false;
	
	}
	
	private void toLinkedList(char[] v) {
		
		for(Character elementV: v) {
			
			contentEntry.add(elementV);
	
		}
		
	}
	
	public void isEOF() {
		
		
		
	}

}
