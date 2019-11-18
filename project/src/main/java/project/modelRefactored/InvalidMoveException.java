package project.modelRefactored;

/**
 * An exception thrown if an attempted move is invalid.
 */
public class InvalidMoveException extends Exception {

    /**
     * Creates the exception with the desired string.
     * @param s The message of the error.
     */
    public InvalidMoveException(String s) {
        super(s);
    }

}
