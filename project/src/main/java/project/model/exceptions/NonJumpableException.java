package project.model.exceptions;

/**
 * Thrown if an attempt to jump is made on an object that isn't jumpable.
 */
public class NonJumpableException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public NonJumpableException(String s) {
        super("Warning: Action could not be performed. " + s + " Please try again!");
    }
}
