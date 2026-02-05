package exceptions;

public class InvalidFormatException extends Exception {
	private static final long serialVersionUID = -4848441532356701980L;

	public InvalidFormatException() {
		super();
	}
	
	public InvalidFormatException(String message) {
		super(message);
	}
}
