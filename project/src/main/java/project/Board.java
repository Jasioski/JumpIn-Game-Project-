package project;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
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
	 * @param coordinate where to set
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

	@SuppressWarnings("PMD.AvoidPrintStackTrace")
	public void slide(Direction moveDirection, int moveSpaces, Coordinate itemCoordinate)
			throws NonMovableItemException 
	{
		BoardItem itemAtCoordinate = getItem(itemCoordinate);
		
		// Throw an error if does not implement Movable
		if (!(itemAtCoordinate instanceof Slidable)) {
			throw new NonMovableItemException("cannot move a not movable item");
		}

		
		// Get slice
		List<BoardItem> slice = new ArrayList<>();
		
		// TODO: write other directions
		// TODO: write tests
		
		switch (moveDirection) {
			case DOWN:
				break;
			case LEFT:
			case RIGHT:
				// Get all items on row
				int row = itemAtCoordinate.getCoordinates().get(0).row;
				for (int column = 0; column < this.columns; column++) {
					slice.add(items[row][column]);
				}
				break;
			case UP:
				break;
			default:
				break;
				
		}
		
		// Store initial coordinates
		List<Coordinate> initialCoordinates = 
				itemAtCoordinate.getCoordinates()
				.stream().map(coordinate -> new Coordinate(coordinate))
				.collect(Collectors.toList());
		
		// Move Item
		Slidable movableItem = (Slidable) itemAtCoordinate;
		List<Coordinate> newCoordinates;
		try {
			newCoordinates = movableItem.slide(moveDirection, moveSpaces, slice );
			
			// Clear old coordinates
			for (Coordinate initialCoordinate: initialCoordinates) {
				setEmptyItem(initialCoordinate);
			}
			
			// Change the board representation
			setItem(newCoordinates, itemAtCoordinate);
		} catch (BoardItemNotEmptyException e) {
		} catch (SlideFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
