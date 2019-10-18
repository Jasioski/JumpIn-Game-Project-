package project;

import java.util.ArrayList;
import java.util.Arrays;
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

	
	List<Coordinate> moveRight(int spaces, List<BoardItem> slice) throws SlideOutOfBoundsException {
		List<Coordinate> newCoordinates = new ArrayList<Coordinate>();
		Coordinate head = this.getHead();
		Coordinate tail = this.getTail();
		
		// Compute new coordinates
		Coordinate newHead = new Coordinate(head.row, head.column + spaces);
		Coordinate newTail = new Coordinate(tail.row, tail.column + spaces);
		
		newCoordinates.add(newHead);
		newCoordinates.add(newTail);

		// for each coordinate in newCoordinates
		// try to find a matching coordinate in the slice
		
		// Get all coordinates in the slice without duplicates
		Set<Coordinate> sliceCoordinates = new HashSet<Coordinate>();
		
		for (BoardItem item: slice) {
			sliceCoordinates.addAll(item.getCoordinates());
		}
	
		// If all of the new coordinates are not within the slice
		// then it must have fallen out of bounds
		if (! sliceCoordinates.containsAll(newCoordinates)) {
			throw new SlideOutOfBoundsException("moving fox from " + head + tail
					+" right by " + spaces + "causes it to go out of bounds");
		}
		
		return newCoordinates;
	}
	
	@Override
	public List<Coordinate> slide(Direction direction, int spaces, List<BoardItem> slice)
			throws SlideOutOfBoundsException {
		
		if (slice.isEmpty()) {
			throw new IllegalArgumentException("cannot slide through an empty"
					+ "slice ");
		}
		
		if (!slice.contains(this)) {
			throw new IllegalArgumentException("cannot slide through a slice"
					+ "that does not contain this fox");
		}
		
		List<Coordinate> newCoordinates = new ArrayList<>();
		
		// Compute where the fox would move to
		switch (direction) {
			case DOWN:
				break;
			case LEFT:
				break;
			case RIGHT:
				newCoordinates = this.moveRight(spaces, slice);
				break;
			case UP:
				break;
			default:
				break;
		}
		
		this.setCoordinates(newCoordinates);
		
		return newCoordinates;
	}

}
