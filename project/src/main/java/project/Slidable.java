package project;

import java.util.List;

/**
 * An interface representing objects that can slide on the board.
 */
public interface Slidable {
	/**
	 * Attempts to slide the object in a direction a specific number of spaces.
	 * @param direction The direction where the item should slide.
	 * @param spaces The amount of spaces the item should slide.
	 * @param slice The slide where the slide is being performed.
	 * @return The new list of coordinates containing the item.
	 * @throws SlideOutOfBoundsException If the item would slide out of bounds.
	 * @throws SlideHitObstacleException If the item would collide with an obstacle.
	 * @throws SlideHitElevatedException If the item would hit an elevated item.
	 */
	public List<Coordinate> slide (Direction direction, int spaces, List<BoardItem> slice) throws SlideOutOfBoundsException, SlideHitObstacleException, SlideHitElevatedException;
}
