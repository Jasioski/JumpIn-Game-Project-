package project.model.exceptions;

public class NonJumpableException extends Exception {
    public NonJumpableException(String s) {
        super("Warning: Action could not be performed. " + s + " Please try again!");
    }
}
