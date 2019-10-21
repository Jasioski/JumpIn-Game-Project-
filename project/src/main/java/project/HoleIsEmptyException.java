package project;

/**
 * Thrown if the ContainerItem is already empty and attempted to be removed from.
 */
public class HoleIsEmptyException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public HoleIsEmptyException( String s) 
    { 
        super(s); 
    } 
} 