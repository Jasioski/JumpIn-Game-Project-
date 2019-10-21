package project;

/**
 * Thrown if the item is not slideable.
 */
public class NonSlideableException extends Exception
{
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public NonSlideableException( String s)
    {
        super(s);
    }
}