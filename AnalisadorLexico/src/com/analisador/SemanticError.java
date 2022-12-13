package com.analisador;

public class SemanticError {

	private long lineError;
	private String errorFound;

	public SemanticError(long lineError, String errorFound) {

		this.errorFound = errorFound;
		this.lineError = lineError;

	}

	public String getErrorFound() {
		return errorFound;
	}

	public void setErrorFound(String errorFound) {
		this.errorFound = errorFound;
	}

	public long getLineError() {
		return lineError;
	}

	public void setLineError(long lineError) {
		this.lineError = lineError;
	}

	@Override
	public String toString() {
		return "Error semantic - Line: " + this.lineError + " - Error found: " + errorFound;
	}

}
