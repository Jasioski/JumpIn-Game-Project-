package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A class that represents a fox on the board, which can slide across the board to move.
 */
public class Fox extends BoardItem implements Slidable {

	/**
	 * Ensures that given head and tail coordinates do not conflict with each other
	 * @param head The coordinate of the head.
	 * @param tail The coordinate of the tail.
	 */
	private static void validateArguments(Coordinate head, Coordinate tail) {
		validateArguments(head.row, head.column, tail.row, tail.column);
	}

	/**
	 * Ensures that the head and tail do not conflict with each other.
	 * @param headRow The row of the fox's head.
	 * @param headColumn The column of the fox's head.
	 * @param tailRow The row of the fox's tail.
	 * @param tailColumn The column of the fox's tail.
	 * @throws IllegalArgumentException If the hea and tail conflict with eachother.
	 */
	private static void validateArguments(int headRow, int headColumn, int tailRow, int tailColumn)
			throws IllegalArgumentException {

		if (headColumn == tailColumn && headRow == tailRow) {
			throw new IllegalArgumentException("The fox cannot have its tail and head in the same position");
		}

		if (Math.abs(headColumn - tailColumn) > 1 || Math.abs(headRow - tailRow) > 1) {
			throw new IllegalArgumentException(
					"The fox cannot have its tail more than a unit " + "a way from its head");
		}

		if (Math.abs(headColumn - tailColumn) == Math.abs(headRow - tailRow)) {
			throw new IllegalArgumentException("The fox cannot have its tail diagonal to its head");
		}
	}

	/**
	 * Ensures that the fox can slide in a specific direction.
	 * @param direction The direction that the fox should slide in.
	 * @throws IllegalArgumentException If the fox cannot slide in that direction.
	 */
	private void verifyDirection(Direction direction) throws IllegalArgumentException {
		if (direction == Direction.DOWN || direction == Direction.UP) {
			//Don't allow vertical slide if not oriented vertically
			if (this.getHead().column != this.getTail().column) {
				throw new IllegalArgumentException("Must slide in same direction "
						+ "as oriented");
			}
		}

		if (direction == Direction.LEFT || direction == Direction.RIGHT) {
			//Don't allow horizontal slide if not oriented horizontally
			if (this.getHead().row != this.getTail().row) {
				throw new IllegalArgumentException("Must slide in same direction "
						+ "as oriented");
			}
		}
	}

	/**
	 * Creates a new fox with specific row and columns for its head and tail.
	 * @param headRow The head's row.
	 * @param headColumn The head's column.
	 * @param tailRow The tail's row.
	 * @param tailColumn The tail's column.
	 */
	public Fox(int headRow, int headColumn, int tailRow, int tailColumn) {
		this(new Coordinate(headRow, headColumn),
				new Coordinate(tailRow, tailColumn));
	}

	/**
	 * Creates a new fox with coordinates for the head and tail.
	 * @param head The head's coordinates.
	 * @param tail The tail's coordinates.
	 */
	public Fox(Coordinate head, Coordinate tail) {
		super(ItemUIRepresentation.FOX);

		validateArguments(head, tail);

		this.setHeadAndTail(head, tail);
	}

	/**
	 * Returns the coordinates of the head.
	 * @return The head's coordinates.
	 */
	public Coordinate getHead() {
		return this.getCoordinates().get(0);
	}

	/**
	 * Returns the coordinates of the tail.
	 * @return The tail's coordinates.
	 */
	public Coordinate getTail() {
		return this.getCoordinates().get(1);
	}

	/**
	 * Sets the head and tail of the fox with new coordinates.
	 * @param head The coordinates of the head.
	 * @param tail The coordinates of the tail.
	 */
	public void setHeadAndTail(Coordinate head, Coordinate tail) {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();

		validateArguments(head.row, head.column, tail.row, tail.column);

		coordinates.add(head);
		coordinates.add(tail);

		this.setCoordinates(coordinates);
	}

	/**
	 * Sets the coordinates of the Fox using a list where the
	 * head is stored at index = 0 and tail is stored at index = 1
	 * @param coordinates A list of the new coordinates.
	 */
	@Override
	public void setCoordinates(List<Coordinate> coordinates) {
		if (coordinates.size() != 2) {
			throw new IllegalArgumentException("can only add a coordinate " +
					"of length 2");
		}

		this.coordinates.clear();
		this.coordinates.addAll(coordinates);
	}

	/**
	 * Ensures that the slide can occur based on its new location and the rest of the slice.
	 * @param newCoordinates The new coordinates of the fox.
	 * @param slice The slice where the slide is happening.
	 * @throws SlideOutOfBoundsException If the fox would slide out of bounds.
	 * @throws SlideHitObstacleException If the fox would hit an obstacle.
	 * @throws SlideHitElevatedException If the fox would hit an elevated item.
	 */
	private void ValidateSlide(List<Coordinate> newCoordinates, List<BoardItem> slice) throws SlideOutOfBoundsException, SlideHitObstacleException,
			SlideHitElevatedException {
		// Get all coordinates in the slice without duplicates
		Set<Coordinate> sliceCoordinates = new HashSet<Coordinate>();

		for (BoardItem item: slice) {
			sliceCoordinates.addAll(item.getCoordinates());
		}

		// If all of the new coordinates are not within the slice
		// then it must have fallen out of bounds
		if (! sliceCoordinates.containsAll(newCoordinates)) {
			throw new SlideOutOfBoundsException("Sliding the fox caused it to "
					+ "go out of bounds.");
		}

		// if the new coordinates are at a coordinate that is not empty
		// or the current item then it must have hit an obstacle
		for (Coordinate newCoordinate: newCoordinates) {
			// loop over all the items in the slice
			boolean hitObstacle = false;
			boolean hitElevated = false;

			for (BoardItem sliceItem: slice) {
				if (sliceItem.getCoordinates().contains(newCoordinate))
				{
					if (sliceItem.getClass() == ElevatedBoardItem.class) {
						hitElevated = true;
					}

					// match if the item is not empty
					// and not the current item
					else if ((sliceItem.getClass() != EmptyBoardItem.class)) {
						if (!(sliceItem.equals(this))) {
							hitObstacle = true;
						}
					}
					// match if it is an elevated
				}
				// DO NOT MATCH IF THE ITEM IS EMPTY OR THE CURRENT ITEM
			}

			if (hitObstacle) {
				throw new SlideHitObstacleException("Sliding the fox from caused it to hit an obstacle.");
			}

			if (hitElevated) {
				throw new SlideHitElevatedException("An elevated item was encountered while sliding the fox " +
						"to the new position.");
			}

		}


	}

	/**
	 * Performs the slide in the desired direction and number of spaces.
	 * @param direction The direction of the slide.
	 * @param spaces The number of spaces for the slide.
	 * @param slice The slice where the slides is happening.
	 * @return A list containing the new coordinates of the fox.
	 * @throws SlideOutOfBoundsException If the slide would push the fox out of bounds.
	 * @throws SlideHitObstacleException If the slide would cause the fox to collide with an obstacle.
	 * @throws SlideHitElevatedException If the slide would cause the fox to hit an elevated item.
	 */
	public List<Coordinate> performSlide(Direction direction, int spaces, List<BoardItem> slice) throws SlideOutOfBoundsException, SlideHitObstacleException, SlideHitElevatedException {
		List<Coordinate> newCoordinates = new ArrayList<Coordinate>();
		Coordinate head = this.getHead();
		Coordinate tail = this.getTail();

		// Compute new coordinates
		Coordinate newHead;
		Coordinate newTail;

		verifyDirection(direction);

		switch (direction) {
			case DOWN:
				newHead = new Coordinate(head.row + 1, head.column);
				newTail = new Coordinate(tail.row + 1, tail.column);
				break;
			case UP:
				newHead = new Coordinate(head.row - 1, head.column);
				newTail = new Coordinate(tail.row - 1, tail.column);
				break;
			case RIGHT:
				newHead = new Coordinate(head.row, head.column + 1);
				newTail = new Coordinate(tail.row, tail.column + 1);
				break;
			case LEFT:
				newHead = new Coordinate(head.row, head.column - 1);
				newTail = new Coordinate(tail.row, tail.column - 1);
				break;
			default:
				throw new IllegalArgumentException("invalid direction.");
		}

		newCoordinates.add(newHead);
		newCoordinates.add(newTail);

		ValidateSlide(newCoordinates, slice);

		this.setCoordinates(newCoordinates);

		if (spaces == 1) {
			return newCoordinates;
		} else {
			return performSlide(direction, spaces - 1, slice);
		}
	}

	/**
	 * Attempts to slide the fox in a specific direction along a slice.
	 * Ensures the slice is correct, then uses performSlide for the actual slide logic.
	 * @param direction The direction where the item should slide.
	 * @param spaces The amount of spaces the item should slide.
	 * @param slice The slide where the slide is being performed.
	 * @return The new coordinates of the fox.
	 * @throws SlideOutOfBoundsException If the slide would push the fox out of bounds.
	 * @throws SlideHitObstacleException If the slide would cause the fox to collide with an obstacle.
	 * @throws SlideHitElevatedException If the slide would cause the fox to hit an elevated item.
	 */
	@Override
	public List<Coordinate> slide(Direction direction, int spaces, List<BoardItem> slice)
			throws SlideOutOfBoundsException, SlideHitObstacleException, SlideHitElevatedException {

		// Move zero spaces
		if (spaces == 0) {
			return this.getCoordinates();
		}

		if (slice.isEmpty()) {
			throw new IllegalArgumentException("Cannot slide through an empty"
					+ "slice.");
		}

		if (!slice.contains(this)) {
			throw new IllegalArgumentException("Cannot slide through a slice"
					+ "that does not contain this fox.");
		}

		// Store initial coordinates to rollback if an exception is thrown
		List<Coordinate> initialCoordinates = this.getCoordinates();

		try {
			return this.performSlide(direction, spaces, slice);
		} catch (SlideOutOfBoundsException | SlideHitObstacleException e) {
			// Restore the coordinates
			this.setCoordinates(initialCoordinates);

			throw e;
		}
	}
}
