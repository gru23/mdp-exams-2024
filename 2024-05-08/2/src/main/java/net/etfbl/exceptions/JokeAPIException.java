package net.etfbl.exceptions;

public class JokeAPIException extends Exception {
	private static final long serialVersionUID = -1681954392732248394L;

	public JokeAPIException() {
		super();
	}
	
	public JokeAPIException(String message) {
		super(message);
	}
}
