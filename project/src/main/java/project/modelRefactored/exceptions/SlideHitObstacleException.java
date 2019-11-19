package project.modelRefactored.exceptions;

/**
 * Returned if the slideable item would collide with an obstacle.
 */
public class SlideHitObstacleException extends Exception {
	/**
	 * Creates the exception with the desired message
	 * @param s The message for the exception.
	 */
	public SlideHitObstacleException (String s) {
		super ("Warning: Action could not be performed. " + s + " Please try again!");
	}
}
