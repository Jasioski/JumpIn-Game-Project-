package project.model.exceptions;

public class NonMovableItemException extends Exception {
    public NonMovableItemException(String s) {
        super(s);
    }
    public NonMovableItemException() {
        this("Cannot move a not movable item");
    }
}
