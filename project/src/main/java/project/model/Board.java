package project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.exceptions.*;

/**
 * The main board class that contains and controls all objects on the board.
 */
public class Board {

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

	private static Logger logger = LogManager.getLogger(Board.class);

	/**
	 * This method ensures that a given row and column are not negative.
	 * @param rows The given row.
	 * @param columns The given column.
	 * @throws IllegalArgumentException if the rows and columns are negative.
	 */
	private static void validateArguments(int rows, int columns) throws IllegalArgumentException {
		if (rows <= 0 || columns <= 0) {

			throw new IllegalArgumentException("Rows and columns must be positive"
					+ "integers.");
		}
	}

	/**
	 * Constructor that initializes the board with a specific number of rows and columns.
	 * @param rows The number of rows the board should contain.
	 * @param columns The number of columns the board should contain.
	 */
	public Board(int rows, int columns) {
		// Setting the initial game state
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
			throw new IllegalArgumentException("row and column must be integers greater than 0.");
		}

		if (row >= this.rows || column >= this.columns) {
			throw new IllegalArgumentException("row and column must be within the range of the board.");
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

		String rowLine = "";

		for (int i = 0; i < rows; i++) {
			rowLine += "--------";
		}

		String columnLine = "" ;

		// column header
		for (int i = 0; i < rows; i++) {
			columnLine += "     " + (i + 1 ) + " ";
		}

		columnLine += "\n";

		for (int row = 0; row < rows; row ++) {

			if (row == 0) {
				str += columnLine;
			}

			str += rowLine;
			str += "\n";

			str += ""+ (row+1);
			for (int column = 0; column < columns; column++) {
				BoardItem item = items[row][column];

				str += " | ";
				//test code
				if (item.toString().length() == 10) {
					str += " " + item.toString() + " ";
				}
				else if (item.toString().length() == 11) {
					str += " " + item.toString();
				}
				else {
					logger.error("badly sized ui text");
				}

				str += " ";
			}

			str += " |\n";
		}

		str += rowLine;

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
			throw new IllegalArgumentException("Coordinates cannot be empty.");
		}

		// Check that the item at the coordinates are not an empty item
		for (Coordinate coordinate: coordinates) {
			BoardItem itemCoord = items[coordinate.row][coordinate.column];

			Class itemClass = itemCoord.getClass();

			// If it is not a board item or a container item
			if (itemClass != EmptyBoardItem.class) {
				if (!(itemCoord instanceof ContainerItem)) {
					throw new BoardItemNotEmptyException(
							"Cannot set an item on the "
									+ "board if there is already a non empty item in the"
									+ "slot");
				}

				// We have a container now
				else {
					ContainerItem containerItem = (ContainerItem) itemCoord;
					if (item instanceof Containable) {

						Containable containable = (Containable) item;
						try {
							containerItem.contain(containable);
							return;
						} catch (HoleAlreadyHasRabbitException e) {
							// TODO: fix error handling
							this.logger.error(e);
						}
					} else {
						throw new BoardItemNotEmptyException("The coordinates have already been occupied.");
					}
				}
			}
		}

		// Set the coordinates
		for (Coordinate coordinate: coordinates) {
			items[coordinate.row][coordinate.column] = item;
		}

		item.setCoordinates(coordinates);
	}

	/**
	 * Sets a board item that already has defined coordinates.
	 * @param item The board item being set.
	 * @throws BoardItemNotEmptyException if the coordinate is not empty
	 */
	public void setItem(BoardItem item) throws BoardItemNotEmptyException {
		setItem(item.getCoordinates(), item);
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

	@SuppressWarnings("PMD.AvoidPrintStackTrace")
	public void slide(Direction moveDirection, int moveSpaces, Coordinate itemCoordinate)
			throws NonSlideableException, BoardItemNotEmptyException, SlideOutOfBoundsException, SlideHitObstacleException, SlideHitElevatedException {
		BoardItem itemAtCoordinate = getItem(itemCoordinate);



		// Throw an error if does not implement Movable
		if (!(itemAtCoordinate instanceof Slidable)) {
			throw new NonSlideableException("Cannot slide a non-slidable item.");
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

	public void jump(Direction jumpDirection, Coordinate rabbitJumpingCoordinate) throws JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException, HoleIsEmptyException, NonJumpableException {
		BoardItem itemAtCoordinate = getItem(rabbitJumpingCoordinate);

		jump(jumpDirection, itemAtCoordinate);

	}

	// TODO: merge this method with jumpout
	public void jump(Direction jumpDirection, BoardItem itemAtCoordinate) throws JumpFailedNoObstacleException, BoardItemNotEmptyException, JumpFailedOutOfBoundsException, HoleIsEmptyException, NonJumpableException {

		if (itemAtCoordinate instanceof ContainerItem) {
			this.jumpOut(jumpDirection, ((ContainerItem) itemAtCoordinate).getCoordinate());
			return;
		}

		// Throw an error if does not implement Movable
		if (!(itemAtCoordinate instanceof Jumpable)) {
			throw new NonJumpableException("Cannot jump a non-jumpable item.");
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

		newCoordinates = jumpableItem.jump(jumpDirection, slice);

		// Clear old coordinates if it is not a containable
		for (Coordinate initialCoordinate: initialCoordinates) {
			if (!(this.getItem(initialCoordinate) instanceof ContainerItem)) {
				setEmptyItem(initialCoordinate);
			}
		}

		// Change the board representation
		if (!newCoordinates.isEmpty()) {
			setItem(newCoordinates, itemAtCoordinate);
		}

		//making a call to function to check the current game state
		updateGameState();
	}

	private void jumpOut(Direction jumpDirection,
						 Coordinate holeCoordinate) throws JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException, HoleIsEmptyException, NonJumpableException {
		// Get the item
		BoardItem itemAtCoordinate = getItem(holeCoordinate);

		// Check if it is a hole
		if (!(itemAtCoordinate instanceof ContainerItem)) {
			// TODO: fix this with a different error
			// throw exception if it is not a hole
//			throw new NonMovableItemException("cannot move a not movable item");
		}

		ContainerItem containerItem = (ContainerItem) itemAtCoordinate;

		try {
			Optional<Containable> optionlContainable = containerItem.getContainingItem();

			if (optionlContainable.isPresent()) {
				if (optionlContainable.get().getClass() != Rabbit.class) {
					throw new IllegalArgumentException("Must be a rabbit that jumps out.");
				}

				Containable containable = containerItem.removeContainingItem();

				try {
					if (containable.getClass() == Rabbit.class) {
						Rabbit rabbit = (Rabbit) containable;
						this.jump(jumpDirection, rabbit);
					}
					else
					{
						throw new IllegalArgumentException("Tried to jump out a non rabbit.");
					}
				} catch (JumpFailedOutOfBoundsException | JumpFailedNoObstacleException | NonJumpableException e){
					try {
						containerItem.contain(containable);
					} catch (HoleAlreadyHasRabbitException ex) {
						this.logger.error(ex);
					}
					throw e;
				}
			}
		}

		catch(HoleIsEmptyException | NonJumpableException e)
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
				// make sure there are no top level rabbits
				if (items[row][column] instanceof Rabbit) {
					this.currentGameState = GameState.IN_PROGRESS;
					return;
				}
				// make sure there are no rabbits inside elevated positions
				else if (items[row][column] instanceof ElevatedBoardItem) {
					ElevatedBoardItem elevatedBoardItem = (ElevatedBoardItem)
							items[row][column];
					if ( elevatedBoardItem.getContainingItem().isPresent()) {
						Containable containable =
								elevatedBoardItem.getContainingItem().get();

						if (containable instanceof  Rabbit) {
							this.currentGameState = GameState.IN_PROGRESS;
							return;
						}
					}
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

	public void move(BoardItem itemSelected, BoardItem itemDestination) throws JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException, NonJumpableException, HoleIsEmptyException {
	    //todo - implement logic for other movements
		int rowDistanceMoved = itemDestination.getCoordinates().get(0).row - itemSelected.getCoordinates().get(0).row;
		int colDistanceMoved = itemDestination.getCoordinates().get(0).column - itemSelected.getCoordinates().get(0).column;
		Direction direction;


		System.out.println(rowDistanceMoved);
		System.out.println(colDistanceMoved);

		// todo extract into private method
		if (rowDistanceMoved == 0 && colDistanceMoved == 0) {
			//deselect item logic
			System.out.println("test");
			return;
		}

		else if(!(rowDistanceMoved != 0 && colDistanceMoved != 0)) {
			if (rowDistanceMoved > 0) { //destination is below
				direction = Direction.DOWN;
			}
			else if (rowDistanceMoved < 0) { //destination is above
				direction = Direction.UP;
			}
			else if (colDistanceMoved > 0) { //destination is to the right
				direction = Direction.RIGHT;
			}
			else { //destination is the the left
				direction = Direction.LEFT;
			}
		}
		else {
			return;
		}
		if (itemSelected instanceof Rabbit)  {
			logger.trace("try jumping in direction:" + direction.toString());
	    	this.jump(direction, itemSelected);
		}
	}
}
