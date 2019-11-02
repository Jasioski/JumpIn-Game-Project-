package project;

/**
 * Thrown if the slideable item hits an elevated item.
 */
public class SlideHitElevatedException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public SlideHitElevatedException (String s) {
        super("Warning: Action could not be performed. " + s + " Please try again!");
    }
}
