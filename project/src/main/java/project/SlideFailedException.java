package project;

/**
 * Thrown if the slide fails.
 */
public class SlideFailedException extends Exception {
	/**
	 * Creates the exception with the desired message
	 * @param e The message for the exception.
	 */
	public SlideFailedException (Exception e) {
		super(e);
	}
}
