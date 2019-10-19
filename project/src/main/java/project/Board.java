package project;

import java.util.ArrayList;	
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Board {
	
	private BoardItem[][] items;

	private int rows;
	private int columns;
	
	private static void validateArguments(int rows, int columns) throws IllegalArgumentException { 
		if (rows <= 0 || columns <= 0) { 
			throw new IllegalArgumentException("Rows and columns must be positive"
					+ "integers");
		}
	}
	
	public Board(int rows, int columns) {
		
		validateArguments(rows, columns);
		
		this.rows = rows;
		this.columns = columns;
		
		items = new BoardItem[rows][columns];
		
		// Initialize Board Items
		for (int row = 0; row < rows; row ++) {
			for (int column = 0; column < columns; column++) {
				items[row][column] = new EmptyBoardItem(row, column);
			}
		}
		
	}

	public Board(int dimension) {
		this(dimension, dimension);
	}

	public int getRows() {
		return columns;
	}

	public int getColumns() {
		return rows;
	}

	public BoardItem getItem(int row, int column) throws IllegalArgumentException {
		
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException("row and column must be positive integers");
		}
		
		if (row >= this.rows || column >= this.columns) {
			throw new IllegalArgumentException("row and column must be within the range of the board");
		}
		
		return items[row][column];
	}
	

	public BoardItem getItem(Coordinate coordinate) {
		return getItem(coordinate.row, coordinate.column);
	}
	
	@Override
	public String toString() {
		String str = "";
		
		for (int row = 0; row < rows; row ++) {
			for (int column = 0; column < columns; column++) {
				BoardItem item = items[row][column];
				str += item.toString();
				str += " ";
			}
			
			str += "\n";
		}
		
		return str;
	}

	public void setItem(int row, int column, BoardItem item) 
			throws BoardItemNotEmptyException {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		coordinates.add(new Coordinate(row, column));
		
		setItem(coordinates, item);
	}
	
	/**
	 * Used to set an item on the board
	 * 
	 * It is expected that this method is used for setting up a board.
	 * It should not be used for player moves as it does not support
	 * cleaning up the items old position from the board or for placing
	 * items inside holes 
	 * 
	 * This method delegates to item.setCoordinate() to set the coordinates
	 * of the item as well.
	 * 
	 * @param coordinates where to set
	 * @param item to set
	 * @throws BoardItemNotEmptyException if the coordinate is not empty
	 */
	public void setItem(List<Coordinate> coordinates, BoardItem item) 
			throws BoardItemNotEmptyException {
		
		// Check that the coordinates are not empty
		if (coordinates.isEmpty()) {
			throw new IllegalArgumentException("coordinates cannot be empty");
		}
		
		// Check that the coordinates are empty
		for (Coordinate coordinate: coordinates) {
			if (items[coordinate.row][coordinate.column].getClass()
					!= EmptyBoardItem.class) {
				
				throw new BoardItemNotEmptyException(
						"Cannot set an item on the "
						+ "board if there is already a non empty item in the"
						+ "slot");
			}
		}
		
		// Set the coordinates
		for (Coordinate coordinate: coordinates) {
			items[coordinate.row][coordinate.column] = item;
		}
		
		item.setCoordinates(coordinates);
	}
	
	public void setEmptyItem(Coordinate coordinate) {
		items[coordinate.row][coordinate.column] = new EmptyBoardItem(coordinate);
	}

	/**
	 * Returns a slice of the board along the given row
	 * @return List of items on that row
	 */
	private List<BoardItem> getRow(int row) {
		List <BoardItem> slice = new ArrayList<BoardItem>();

		for (int column = 0; column < this.columns; column++) {
			slice.add(items[row][column]);
		}

		return slice;
	}

	/**
	 * Returns a slice of the board down the given column
	 * @return List of items down the column
	 */
	private List<BoardItem> getColumn(int column) {
		List <BoardItem> slice = new ArrayList<BoardItem>();

		for (int row = 0; row < this.rows; row++) {
			slice.add(items[row][column]);
		}

		return slice;
	}


	private List<BoardItem> getSlice(Direction direction, BoardItem item) {
		Coordinate itemCoordinate = item.getCoordinates().get(0);
		List<BoardItem> slice = new ArrayList<BoardItem>();

		switch (direction) {
			case UP:
			case DOWN:
				slice = this.getColumn(itemCoordinate.column);
				break;
			case LEFT:
			case RIGHT:
				slice = this.getRow(itemCoordinate.row);
				break;
			default:
				break;
		}

		return slice;
	}


	// TODO: slide and jump have al ot of common functionalitu that needs
	// to be extracted
	@SuppressWarnings("PMD.AvoidPrintStackTrace")
	public void slide(Direction moveDirection, int moveSpaces, Coordinate itemCoordinate)
			throws NonMovableItemException, BoardItemNotEmptyException, SlideOutOfBoundsException, SlideHitObstacleException {
		BoardItem itemAtCoordinate = getItem(itemCoordinate);
		
		// Throw an error if does not implement Movable
		if (!(itemAtCoordinate instanceof Slidable)) {
			// TODO: rename to nonslidable
			throw new NonMovableItemException("cannot move a not movable item");
		}

		// Get slice
		List<BoardItem> slice = this.getSlice(moveDirection, itemAtCoordinate);

		// Store initial coordinates
		List<Coordinate> initialCoordinates = 
				itemAtCoordinate.getCoordinates()
				.stream().map(coordinate -> new Coordinate(coordinate))
				.collect(Collectors.toList());
		
		// Move Item
		Slidable movableItem = (Slidable) itemAtCoordinate;
		List<Coordinate> newCoordinates;

		newCoordinates = movableItem.slide(moveDirection, moveSpaces, slice );

		// Clear old coordinates
		for (Coordinate initialCoordinate: initialCoordinates) {
			setEmptyItem(initialCoordinate);
		}

		// Change the board representation
		setItem(newCoordinates, itemAtCoordinate);
	}

	public void jump(Direction jumpDirection, Coordinate rabbitJumpingCoordinate) throws JumpObstacleException, JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException {
		BoardItem itemAtCoordinate = getItem(rabbitJumpingCoordinate);

		jump(jumpDirection, itemAtCoordinate);
	}
	// TODO: merge this method with jumpout
	public void jump(Direction jumpDirection, BoardItem itemAtCoordinate) throws JumpObstacleException,	JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException {

		// Throw an error if does not implement Movable
		if (!(itemAtCoordinate instanceof Slidable)) {
			// TODO: fix this with a nonjumpable error
//			throw new NonMovableItemException("cannot move a not movable item");
		}

		List<BoardItem> slice = this.getSlice(jumpDirection, itemAtCoordinate);

		// Store initial coordinates
		List<Coordinate> initialCoordinates =
				itemAtCoordinate.getCoordinates()
						.stream().map(coordinate -> new Coordinate(coordinate))
						.collect(Collectors.toList());

		// Move Item
		Jumpable jumpableItem = (Jumpable) itemAtCoordinate;
		List<Coordinate> newCoordinates;

		newCoordinates = jumpableItem.jump(jumpDirection, slice );

		// Clear old coordinates
		for (Coordinate initialCoordinate: initialCoordinates) {
			setEmptyItem(initialCoordinate);
		}

		// Change the board representation
		setItem(newCoordinates, itemAtCoordinate);
	}

	public void jumpOut(Direction jumpDirection, Coordinate holeCoordinate) throws JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, JumpObstacleException, BoardItemNotEmptyException, HoleIsEmptyException {
		// Get the item
		BoardItem itemAtCoordinate = getItem(holeCoordinate);

		// Check if it is a hole
		if (!(itemAtCoordinate instanceof Hole)) {
			// TODO: fix this with a different error
			// throw exception if it is not a hole
//			throw new NonMovableItemException("cannot move a not movable item");
		}

		Hole hole = (Hole) itemAtCoordinate;

		Rabbit rabbit = null;
		try {
			rabbit = hole.removeContainingItem();
		} catch (HoleIsEmptyException e) {
		    hole.setContainingItem(rabbit);
		    throw e;
		}

		this.jump(jumpDirection, rabbit);
	}
}
