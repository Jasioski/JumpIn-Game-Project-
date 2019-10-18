package project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fox extends BoardItem implements Slidable {

	private static final Character FOX_DISPLAY_CHARACTER = 'F';
	
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

	public Fox(int headRow, int headColumn, int tailRow, int tailColumn) {
		this(new Coordinate(headRow, headColumn),
				new Coordinate(tailRow, tailColumn));	
	}

	public Fox(Coordinate head, Coordinate tail) {
		super(FOX_DISPLAY_CHARACTER);
		
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
	
	public Coordinate setTail(Coordinate tail) {
		return this.getCoordinates().set(1, tail);
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
		//FIXME: List does not guarantee ordering??
		this.coordinates.addAll(coordinates);
	}

	private void ValidateMove(List<Coordinate> newCoordinates, List<BoardItem> slice) throws SlideOutOfBoundsException, SlideHitObstacleException {
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
			boolean hitObstacle = slice.stream().anyMatch(sliceItem -> {
				// check if is at the same coordinate as one of the new coordinates
				if (sliceItem.getCoordinates().contains(newCoordinate)) 
				{
					// match if the item is not empty 
					// and not the current item
					if ((sliceItem.getClass() != EmptyBoardItem.class)) {
						if (!(sliceItem.equals(this))) {
							return true;
						}
					}
				}
				// do not match if the item is empty or the current item
				return false;
			});
			
			if (hitObstacle) {
				throw new SlideHitObstacleException("Sliding the fox from caused it to hit an obstacle");
			}
				
		}
		

	}

	private List<Coordinate> slideLeft(int spaces, List<BoardItem> slice) throws SlideOutOfBoundsException, SlideHitObstacleException {
		List<Coordinate> newCoordinates = new ArrayList<Coordinate>();
		Coordinate head = this.getHead();
		Coordinate tail = this.getTail();
		
		// Compute new coordinates
		Coordinate newHead = new Coordinate(head.row, head.column - 1);
		Coordinate newTail = new Coordinate(tail.row, tail.column - 1);
		
		newCoordinates.add(newHead);
		newCoordinates.add(newTail);

		ValidateMove(newCoordinates, slice);
		
		// Move the fox
		this.setCoordinates(newCoordinates);
		
		if (spaces == 1) {
			return newCoordinates;
		} else {
			return slideLeft(spaces - 1, slice);
		}
	}
	
	// TODO: rename to slideRight
	private List<Coordinate> moveRight(int spaces, List<BoardItem> slice) throws SlideOutOfBoundsException, SlideHitObstacleException {
		List<Coordinate> newCoordinates = new ArrayList<Coordinate>();
		Coordinate head = this.getHead();
		Coordinate tail = this.getTail();
		
		// Compute new coordinates
		Coordinate newHead = new Coordinate(head.row, head.column + 1);
		Coordinate newTail = new Coordinate(tail.row, tail.column + 1);
		
		newCoordinates.add(newHead);
		newCoordinates.add(newTail);

		ValidateMove(newCoordinates, slice);
		
		// Move the fox
		this.setCoordinates(newCoordinates);
		
		if (spaces == 1) {
			return newCoordinates;
		} else {
			return moveRight(spaces - 1, slice);
		}
	}
	
	@Override
	public List<Coordinate> slide(Direction direction, int spaces, List<BoardItem> slice)
			throws SlideFailedException {
		
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
		
		List<Coordinate> newCoordinates = new ArrayList<>();
		try {
			// Compute where the fox would move to
			switch (direction) {
			case DOWN:
				break;
			case LEFT:
				newCoordinates = this.slideLeft(spaces, slice);
				break;
			case RIGHT:
				newCoordinates = this.moveRight(spaces, slice);
				break;
			case UP:
				break;
			default:
				break;
			}
		} catch (SlideOutOfBoundsException | SlideHitObstacleException e) {
			// Restore the coordinates
			this.setCoordinates(initialCoordinates);
			
			throw new SlideFailedException(e);
		}
		
		return newCoordinates;
	}


}
