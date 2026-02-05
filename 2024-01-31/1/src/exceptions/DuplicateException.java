package exceptions;

public class DuplicateException extends Exception {
	private static final long serialVersionUID = -4763948178307519045L;

	public DuplicateException() {
		super();
	}
	
	public DuplicateException(String message) {
		super(message);
	}
}
