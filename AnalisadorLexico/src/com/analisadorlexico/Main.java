package com.analisadorlexico;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

	public static void main(String[] args) {

		LexicalAnaliser lexicalAnaliser = new LexicalAnaliser();

		for (File file : searchArchives()) {

			lexicalAnaliser.execAnaliser(file.getAbsoluteFile());
			writeTokensInArchive(file.getName());

		}

	}

	private static File[] searchArchives() {

		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().startsWith("entrada");
			}
		};

		File file = new File("src/files");
		File[] files = file.listFiles(filter);

		return files;

	}

	public static void writeTokensInArchive(String nameArc) {

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

			if (!(tk instanceof ErrorToken)) {

				recordArc.println(tk.toString());

			}

		}

		recordArc.print("\n");

		for (Token tk : LexicalAnaliser.getListTokens()) {

			if (tk instanceof ErrorToken) {

				recordArc.println(tk.toString());

			}

		}

		if (!LexicalAnaliser.containsErroToken()) {

			recordArc.println("O ARQUIVO NAO APRESENTA ERROS");

		}

		recordArc.close();
	}

}
