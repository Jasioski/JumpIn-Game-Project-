package project.model.exceptions;

/**
 * Thrown if the slideable object tries to slide in different orientation.
 */
public class SlideWrongOrientationException extends Exception {
    /**
     * Creates the exception with the desired message
     * @param s The message for the exception.
     */
    public SlideWrongOrientationException (String s) {
        super ("Fox must be oriented in same direction as it is sliding in! " + s + " Please try again!");
    }
}
