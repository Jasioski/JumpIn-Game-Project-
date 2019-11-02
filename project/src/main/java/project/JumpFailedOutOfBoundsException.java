package project;

/**
 * Thrown if a jumpable object would land out of bounds.
 */
public class JumpFailedOutOfBoundsException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    JumpFailedOutOfBoundsException(String s) {
        super("Warning: Action could not be performed. " + s + " Please try again!");
    }
}
