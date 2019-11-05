package project.model.exceptions;

/**
 * Thrown if an unmovable item is attempted to be moved.
 */
public class NonMovableItemException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public NonMovableItemException(String s) {
        super(s);
    }

    /**
     * Creates the exception with a default message
     */
    public NonMovableItemException() {
        this("Cannot move a not movable item");
    }
}
