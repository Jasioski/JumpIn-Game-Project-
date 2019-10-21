package project;

import java.util.List;

/**
 * Interface for objects that can jump over other boarditems.
 */
public interface Jumpable {
	/**
	 * Attempts to make the item jump in a specified direction along a slice on the board.
	 * @param direction The direction where the item is jumping.
	 * @param slice The slice where the jump is being performed.
	 * @return The new coordinates where the item has jumped to.
	 * @throws JumpFailedNoObstacleException If there is no obstacle for the item to jump over.
	 * @throws JumpFailedOutOfBoundsException If the item would jump out of bounds.
	 */
	 List<Coordinate> jump(Direction direction, List<BoardItem> slice) throws JumpFailedNoObstacleException, JumpFailedOutOfBoundsException;
}
