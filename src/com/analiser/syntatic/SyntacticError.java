package com.analiser.syntatic;

public class SyntacticError {

	private long lineError;
	private String expectedToken;
	private String foundToken;

	public SyntacticError(long lineError, String expectedToken, String foundToken) {

		this.expectedToken = expectedToken;
		this.lineError = lineError;
		this.foundToken = foundToken;

	}

	public long getLineError() {
		return lineError;
	}

	public void setLineError(long lineError) {
		this.lineError = lineError;
	}

	public String getExpectedToken() {
		return expectedToken;
	}

	public void setExpectedToken(String expectedToken) {
		this.expectedToken = expectedToken;
	}

	public String getFoundToken() {
		return foundToken;
	}

	public void setFoundToken(String foundToken) {
		this.foundToken = foundToken;
	}

	@Override
	public String toString() {
		return "Error syntactical - Line: " + this.lineError + " - Expected token(s): " + this.expectedToken + " - Token found: "+ foundToken;
	}

}
