package exceptions;

public class IllegalArgumentException extends Exception {
	private static final long serialVersionUID = -5757847135791297342L;

	public IllegalArgumentException() {
		super();
	}
	
	public IllegalArgumentException(String message) {
		super(message);
	}
}
