package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fox extends BoardItem implements Slidable {

	private static void validateArguments(Coordinate head, Coordinate tail) {
		validateArguments(head.row, head.column, tail.row, tail.column);
	}
	
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

	public Fox(int headRow, int headColumn, int tailRow, int tailColumn) {
		this(new Coordinate(headRow, headColumn),
				new Coordinate(tailRow, tailColumn));	
	}

	public Fox(Coordinate head, Coordinate tail) {
		super(ItemUIRepresentation.FOX);
		
		validateArguments(head, tail);
		
		this.setHeadAndTail(head, tail);
	}

	public Coordinate getHead() {
		return this.getCoordinates().get(0);
	}
	
	public Coordinate getTail() {
		return this.getCoordinates().get(1);
	}
	
	public void setHeadAndTail(Coordinate head, Coordinate tail) {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		
		validateArguments(head.row, head.column, tail.row, tail.column);
		
		coordinates.add(head);
		coordinates.add(tail);
		
		this.setCoordinates(coordinates);
	}
	
	/**
	 * Sets the coordinates of the Fox where
	 * head is stored at index = 0 and tail
	 * is stored at index = 1
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
			throw new SlideOutOfBoundsException("Sliding the fox caused it to"
					+ "go out of bounds");
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
				throw new SlideHitObstacleException("Sliding the fox from caused it to hit an obstacle");
			}

			if (hitElevated) {
				throw new SlideHitElevatedException("fox hit an elevated tile position" +
					"when it was trying to slide");
			}

		}
		

	}

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
				throw new IllegalArgumentException("invalid direction");
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

	@Override
	public List<Coordinate> slide(Direction direction, int spaces, List<BoardItem> slice)
			throws SlideOutOfBoundsException, SlideHitObstacleException, SlideHitElevatedException {

		// Move zero spaces
		if (spaces == 0) {
			return this.getCoordinates();
		}

		if (slice.isEmpty()) {
			throw new IllegalArgumentException("cannot slide through an empty"
					+ "slice ");
		}
		
		if (!slice.contains(this)) {
			throw new IllegalArgumentException("cannot slide through a slice"
					+ "that does not contain this fox");
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
