package project;

import java.util.ArrayList;	
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The main board class that contains and controls all objects on the board.
 */
public class Board{

	/**
	 * This is the 2D array that contains all the items in the board.
	 */
	private BoardItem[][] items;

	/**
	 * The number of rows that the board contains.
	 */
	private int rows;

	/**
	 * The number of columns that the board contains.
	 */
	private int columns;

	/**
	 * The current state of the game, based on the GameState enum (either in progress or solved).
	 */
	protected GameState currentGameState;

	/**
	 * This method ensures that a given row and column are not negative.
	 * @param rows The given row.
	 * @param columns The given column.
	 * @throws IllegalArgumentException if the rows and columns are negative.
	 */
	private static void validateArguments(int rows, int columns) throws IllegalArgumentException { 
		if (rows <= 0 || columns <= 0) { 
			throw new IllegalArgumentException("Rows and columns must be positive"
					+ "integers");
		}
	}

	/**
	 * Constructor that initializes the board with a specific number of rows and columns.
	 * @param rows The number of rows the board should contain.
	 * @param columns The number of columns the board should contain.
	 */
	public Board(int rows, int columns) {
		//setting GameState
		this.currentGameState = GameState.IN_PROGRESS;
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

	/**
	 *  Constructor that creates the board with the same dimension for the rows and columns.
	 * @param dimension Amount of rows and columns for the board.
	 */
	public Board(int dimension) {		
		this(dimension, dimension);
		//setting the game state
		this.currentGameState = GameState.IN_PROGRESS;
	}

	/**
	 * Gets the number of rows in the board.
	 * @return The number of rows.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Gets the number of columns in the board.
	 * @return The number of columns.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Gets the board item at the specified row and column on the board.
	 * @param row The row of the item being searched for.
	 * @param column The column of the item being searched for.
	 * @return The item at the board position specified.
	 * @throws IllegalArgumentException If the rows and columns are negative or out of range of the board.
	 */
	public BoardItem getItem(int row, int column) throws IllegalArgumentException {
		
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException("row and column must be positive integers");
		}
		
		if (row >= this.rows || column >= this.columns) {
			throw new IllegalArgumentException("row and column must be within the range of the board");
		}
		
		return items[row][column];
	}

	/**
	 * Gets the item at a specific coordinate.
	 * @param coordinate The coordinate of the item being retrieved.
	 * @return The item found at the coordinate.
	 */
	public BoardItem getItem(Coordinate coordinate) {
		return getItem(coordinate.row, coordinate.column);
	}

	/**
	 * Prints out a string representation of the board.
	 * @return The string representation of the board.
	 */
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

	/**
	 * Sets an item at a specific location on the board.
	 * @param row The row where the item should be set.
	 * @param column The column where the item should be set.
	 * @param item The item being placed into the board.
	 * @throws BoardItemNotEmptyException If there is already an item in that location.
	 */
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

	/**
	 * Sets an EmptyBoardItem at a specific coordinate.
	 * @param coordinate The coordinate where the empty item is being placed.
	 */
	public void setEmptyItem(Coordinate coordinate) {
		items[coordinate.row][coordinate.column] = new EmptyBoardItem(coordinate);
	}

	/**
	 * Returns a slice of the board along the given row.
	 * @return The list of items in that row.
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
	 * @return The list of items in the column
	 */
	private List<BoardItem> getColumn(int column) {
		List <BoardItem> slice = new ArrayList<BoardItem>();

		for (int row = 0; row < this.rows; row++) {
			slice.add(items[row][column]);
		}

		return slice;
	}

	/**
	 * Gets a slice in a direction from a specific item on the board.
	 * @param direction The direction of the slice wanted.
	 * @param item The item where the slice is located.
	 * @return The list of the items in that slice.
	 */
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


	// TODO: slide and jump have a lot of common functionality that needs
	// to be extracted
	@SuppressWarnings("PMD.AvoidPrintStackTrace")
	public void slide(Direction moveDirection, int moveSpaces, Coordinate itemCoordinate)
			throws NonSlideableException, BoardItemNotEmptyException, SlideOutOfBoundsException, SlideHitObstacleException, SlideHitElevatedException {
		BoardItem itemAtCoordinate = getItem(itemCoordinate);
		
		

		// Throw an error if does not implement Movable
		if (!(itemAtCoordinate instanceof Slidable)) {
			// TODO: rename to non-slidable
			throw new NonSlideableException("cannot slide a non-slidable item");
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

	public void jump(Direction jumpDirection, Coordinate rabbitJumpingCoordinate) throws JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException {
		BoardItem itemAtCoordinate = getItem(rabbitJumpingCoordinate);

		jump(jumpDirection, itemAtCoordinate);

	}
	// TODO: merge this method with jumpout
	public void jump(Direction jumpDirection, BoardItem itemAtCoordinate) throws JumpFailedNoObstacleException, BoardItemNotEmptyException, JumpFailedOutOfBoundsException {

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
		
		//making a call to function to check the current game state
		updateGameState();
	}

	public void jumpOut(Direction jumpDirection, Coordinate holeCoordinate) throws JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException, HoleIsEmptyException {
		// Get the item
		BoardItem itemAtCoordinate = getItem(holeCoordinate);

		// Check if it is a hole
		if (!(itemAtCoordinate instanceof Hole)) {
			// TODO: fix this with a different error
			// throw exception if it is not a hole
//			throw new NonMovableItemException("cannot move a not movable item");
		}

		Hole hole = (Hole) itemAtCoordinate;

		try {
			Optional<Containable> optionlContainable = hole.getContainingItem();

			if (optionlContainable.isPresent()) {
				if (optionlContainable.get().getClass() != Rabbit.class) {
					throw new IllegalArgumentException("Must be a rabbit that jumps out ");
				}

				Containable containable = hole.removeContainingItem();

				try {
					if (containable.getClass() == Rabbit.class) {
						Rabbit rabbit = (Rabbit) containable;
						this.jump(jumpDirection, rabbit);
					}
					else
					{
						throw new IllegalArgumentException("tried to jump out a non rabbit");
					}
				} catch (JumpFailedOutOfBoundsException | JumpFailedNoObstacleException e){
					System.out.println("some jumping exception");
					try {
						hole.contain(containable);
					} catch (HoleAlreadyHasRabbitException ex) {
						ex.printStackTrace();
					}
					throw e;
				}
			}
		}
		catch(HoleIsEmptyException e)
		{
			throw e;
		}


	}


	/**
	 * Updates the gamestate to won if there are no rabbits remaining on the board.
	 */
	public void updateGameState() {
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				if (items[row][column] instanceof Rabbit) {
					this.currentGameState = GameState.IN_PROGRESS;
					return;
				}
			}
		}
		this.currentGameState = GameState.SOLVED;
	}

	/**
	 * Gets the current gamestate of the board.
	 * @return The current gamestate.
	 */
	public GameState getCurrentGameState() {
		return currentGameState;
	}
}
