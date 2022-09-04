
public class Token {

	private int id;
	private String lexeme;
	private String nameToken;
	private long line;
	private long columm;
	
	
	
	
	
	
	public Token(int id, String lexeme,String nameToken) {
		
		this.id = id;
		this.lexeme = lexeme;
		this.nameToken = nameToken;
		
	}
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLexeme() {
		return lexeme;
	}
	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}
	public String getNameToken() {
		return nameToken;
	}
	public void setNameToken(String nameToken) {
		this.nameToken = nameToken;
	}
	public long getLine() {
		return line;
	}
	public void setLine(long line) {
		this.line = line;
	}
	public long getColumm() {
		return columm;
	}
	public void setColumm(long columm) {
		this.columm = columm;
	}
	
	
	
	
	
	
	
	
}
