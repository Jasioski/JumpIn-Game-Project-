package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class representing the rabbit object on the board. Can jump over other objects and land inside containers.
 */
public class Rabbit extends BoardItem implements Jumpable, Containable {
	/**
	 * Denotes whether the object is currently attempting to jump, used by the jumping functions.
	 */
	private boolean isCurrentlyJumping;

	/**
	 * Creates a rabbit at a specific row and column.
	 * @param row The rabbit's row.
	 * @param column The rabbit's column.
	 */
	public Rabbit(int row, int column) {
		this(new Coordinate(row, column));
	}

	/**
	 * Creates a rabbit at a specific coordinate.
	 * @param coordinate The rabbit's coordinate.
	 */
	public Rabbit(Coordinate coordinate) {
		super(ItemUIRepresentation.RABBIT);
		this.setCoordinate(coordinate);
		isCurrentlyJumping = false;
	}

	/**
	 * Sets the rabbit's coordinate.
	 * @param coordinate The coordinate the rabbit is being set at.
	 */
	public void setCoordinate(Coordinate coordinate) {
		this.coordinates.clear();
		this.coordinates.add(coordinate);
	}

	/**
	 * Sets the rabbit's coordinates using a list of coordinates.
	 * @param coordinates The item's coordinates.
	 */
	@Override
	public void setCoordinates(List<Coordinate> coordinates) {
		if (coordinates.size() != 1) {
			if (coordinates.size() != 1) {
				throw new IllegalArgumentException("can only add a coordinate "
						+ "of length 1");
			}
		}

		this.setCoordinate(coordinates.get(0));
	}

	/**
	 * Gets the rabbit's coordinates.
	 */
	public Coordinate getCoordinate() {
		return this.getCoordinates().get(0);
	}

	/**
	 * Attempts to jump in a specific direction and slice on the board.
	 * @param direction The direction where the item is jumping.
	 * @param slice The slice where the jump is being performed.
	 * @return The new coordinates of the rabbit.
	 * @throws JumpFailedNoObstacleException If there is no obstacle for the rabbit to jump over.
	 * @throws JumpFailedOutOfBoundsException If the rabbit would be jumping out of bounds.
	 */
	@Override
	public List<Coordinate> jump(Direction direction, List<BoardItem> slice) throws JumpFailedNoObstacleException, JumpFailedOutOfBoundsException {
		List<Coordinate> oldCoordinates = this.getCoordinates();

		try {
			return performJump(direction, slice);
		} catch (JumpFailedNoObstacleException | JumpFailedOutOfBoundsException e) {
			this.setCoordinates(oldCoordinates);
			throw e;
		}
	}

	/**
	 * Used by the jump function to actually perform the jump, and contains the logic to determine whether the rabbit can jump.
	 * @param direction The direction the rabbit is jumping in.
	 * @param slice The slice where the jump is being performed.
	 * @return The new coordinates where the rabbit lands.
	 * @throws JumpFailedNoObstacleException If the rabbit has no obstacle to jump over.
	 * @throws JumpFailedOutOfBoundsException If the rabbit would be jumping out of bounds.
	 */
	private List<Coordinate> performJump(Direction direction, List<BoardItem> slice) throws JumpFailedNoObstacleException, JumpFailedOutOfBoundsException {
		Coordinate currentCoordinate = this.getCoordinate();
		Coordinate newCoordinate;
		switch (direction) {
			case RIGHT:
				newCoordinate = new Coordinate(currentCoordinate.row, currentCoordinate.column + 1);
				break;
			case LEFT:
				newCoordinate = new Coordinate(currentCoordinate.row, currentCoordinate.column - 1);
				break;
			case DOWN:
				newCoordinate = new Coordinate(currentCoordinate.row + 1, currentCoordinate.column);
				break;
			case UP:
				newCoordinate = new Coordinate(currentCoordinate.row - 1, currentCoordinate.column);
				break;
			default:
				throw new IllegalArgumentException("Invalid Direction");
		}

		// Get all coordinates in the slice without duplicates
		Set<Coordinate> sliceCoordinates = new HashSet<Coordinate>();

		for (BoardItem item : slice) {
			sliceCoordinates.addAll(item.getCoordinates());
		}

		// Check if we are in the board
		if (!sliceCoordinates.contains(newCoordinate)) {
			throw new JumpFailedOutOfBoundsException("Jumping the rabbit " +
					"caused the rabbit to go out of the slice");
		}

		// Check if we are hitting an obstacle
		// loop over all the items in the slice
		boolean hitObstacle = slice.stream().anyMatch(sliceItem -> {

			// do not match if not an empty elevated
			if (sliceItem.getClass() == ElevatedBoardItem.class) {
				ElevatedBoardItem elevatedBoardItem = (ElevatedBoardItem) sliceItem;
				if (elevatedBoardItem.getContainingItem().isEmpty()) {
					return false;
				}
			}

			// check if is at the same coordinate as one of the new coordinates
			if (sliceItem.getCoordinates().contains(newCoordinate)) {
				// not empty
				if ((sliceItem.getClass() != EmptyBoardItem.class)) {
					// not current item
					if (!(sliceItem.equals(this))) {
						return true;
					}
				}
			}

			// do not match if the item is empty or the current item
			return false;
		});

		// Hitting Obstacle
		if (hitObstacle) {
			this.setCoordinate(newCoordinate);
			isCurrentlyJumping = true;

			// Keep going
			return performJump(direction, slice);
		}
		// Found empty spot
		else {
			List<Coordinate> newCoordinates = new ArrayList<>();

			// If we have jumped over something
			if (isCurrentlyJumping) {
				// Stay in the new spot
				newCoordinates.add(newCoordinate);
				this.setCoordinate(newCoordinate);
				isCurrentlyJumping = false;
				return newCoordinates;
			}

			// If we haven't jumped over anything then throw
			// Rabbits cannot jump to adjacent blocks,
			// they must jump over something
			else {
				throw new JumpFailedNoObstacleException("the rabbit did not " +
						"jump over anything");
			}
		}
	}
}
