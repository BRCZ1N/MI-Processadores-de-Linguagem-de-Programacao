package com.analisadorlexico;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
	
	public static void main(String[] args) throws IOException {
		
		LexicalAnaliser lexicalAnaliser = new LexicalAnaliser();
		SyntaxAnaliser syntaxAnaliser = new SyntaxAnaliser();

		for(File file:searchArchives()) {
			
			lexicalAnaliser.execAnaliser(file);
			syntaxAnaliser.refreshTokenList();
			syntaxAnaliser.execAnaliser();
			
			writeTokensInArchive(file.getName());
			LexicalAnaliser.clearAllList();
			
		}
		
		
		
	}
	
	
	
	
	
	private static File[] searchArchives() {

		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return !file.getName().endsWith("-saida.txt");
			}
		};

		File file = new File("src/files");
		File[] files = file.listFiles(filter);

		return files;

	}

	public static void writeTokensInArchive(String nameArc) throws IOException {
		
		File file = new File("src/files/" + "[" + nameArc + "]-saida.txt");
		FileWriter arc = null;

		try {

			file.createNewFile();
			arc = new FileWriter(file);

		} catch (IOException e) {

			e.printStackTrace();

		}

		PrintWriter recordArc = new PrintWriter(arc);

		for (Token tk : LexicalAnaliser.getListTokens()) {

			if (!(tk instanceof ErrorToken) && tk.getTypeToken() != InitialsToken.TK_COMMENT.getTypeTokenCode()) {

				recordArc.println(tk.toString());

			}

		}

		recordArc.println();

		for (Token tk : LexicalAnaliser.getListTokens()) {

			if (tk instanceof ErrorToken) {

				recordArc.println(tk.toString());

			}

		}
		
		if(!LexicalAnaliser.containsLexicalError()) {
			
			recordArc.println("Esse arquivo não contém erro");
			
		}
		
		recordArc.close();
		arc.close();
	
	}
	
}
