package project;

/**
 * An exception thrown if an item is being placed on a board space that is not empty.
 */
public class BoardItemNotEmptyException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public BoardItemNotEmptyException( String s) 
    { 
        super(s); 
    } 
} 