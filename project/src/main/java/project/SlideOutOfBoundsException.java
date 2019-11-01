package project;

/**
 * Thrown if the slideable object would be pushed out of bounds.
 */
public class SlideOutOfBoundsException extends Exception {
	/**
	 * Creates the exception with the desired message
	 * @param s The message for the exception.
	 */
	public SlideOutOfBoundsException (String s) {
		super ("Warning: Action could not be performed. " + s + " Please try again!");
	}
}
