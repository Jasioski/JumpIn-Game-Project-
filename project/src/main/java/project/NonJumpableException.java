package project;

public class NonJumpableException extends Exception {
    NonJumpableException(String s) {
        super("Warning: Action could not be performed. " + s + " Please try again!");
    }
}
