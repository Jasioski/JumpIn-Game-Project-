package project.model.exceptions;

/**
 * Thrown if there is no object for a jumpable object to jump over.
 */
public class JumpFailedNoObstacleException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public JumpFailedNoObstacleException(String s) {
        super("Warning: Action could not be performed. " + s + " Please try again!");
    }
}
