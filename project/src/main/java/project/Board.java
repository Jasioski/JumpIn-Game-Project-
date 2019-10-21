package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Board{

	private BoardItem[][] items;

	private int rows;
	private int columns;
	protected GameState currentGameState;
	private static Logger logger = LogManager.getLogger(Board.class);

	private static void validateArguments(int rows, int columns) throws IllegalArgumentException {
		if (rows <= 0 || columns <= 0) {
			throw new IllegalArgumentException("Rows and columns must be positive"
					+ "integers");
		}
	}

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

	public Board(int dimension) {		
		this(dimension, dimension);
		//setting the game state
		this.currentGameState = GameState.IN_PROGRESS;

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

		String rowLine = "";
		for (int i = 0; i < rows; i++) {
			rowLine += "-------";
		}
			// TODO: refactor
		for (int row = 0; row < rows; row ++) {

			str += rowLine;
			str += "\n";

			for (int column = 0; column < columns; column++) {
				BoardItem item = items[row][column];
				str += "| ";

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
			BoardItem itemCoord =
					items[coordinate.row][coordinate.column];

			Class itemClass = itemCoord.getClass();

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
						throw new BoardItemNotEmptyException("trying to set a" +
								" non containable on a container");
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

	public void setItem(BoardItem item) throws BoardItemNotEmptyException {
		setItem(item.getCoordinates(), item);
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

	public void jump(Direction jumpDirection, Coordinate rabbitJumpingCoordinate) throws JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException, HoleIsEmptyException {
		BoardItem itemAtCoordinate = getItem(rabbitJumpingCoordinate);

		jump(jumpDirection, itemAtCoordinate);

	}
	// TODO: merge this method with jumpout
	public void jump(Direction jumpDirection, BoardItem itemAtCoordinate) throws JumpFailedNoObstacleException, BoardItemNotEmptyException, JumpFailedOutOfBoundsException, HoleIsEmptyException {

		if (itemAtCoordinate instanceof ContainerItem) {
			this.jumpOut(jumpDirection, ((ContainerItem) itemAtCoordinate).getCoordinate());
			return;
		}

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

		newCoordinates = jumpableItem.jump(jumpDirection, slice);

		// Clear old coordinates
		for (Coordinate initialCoordinate: initialCoordinates) {
			setEmptyItem(initialCoordinate);
		}

		// Change the board representation
		if (!newCoordinates.isEmpty()) {
			setItem(newCoordinates, itemAtCoordinate);
		}
		
		//making a call to function to check the current game state
		updateGameState();
	}

	private void jumpOut(Direction jumpDirection,
						   Coordinate holeCoordinate) throws JumpFailedOutOfBoundsException, JumpFailedNoObstacleException, BoardItemNotEmptyException, HoleIsEmptyException {
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
					throw new IllegalArgumentException("Must be a rabbit that jumps out ");
				}

				Containable containable = containerItem.removeContainingItem();

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
					try {
						containerItem.contain(containable);
					} catch (HoleAlreadyHasRabbitException ex) {
						this.logger.error(ex);
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
	
	//Itterates over the board, if no rabbits found, game state change to
	//won else, game state is set to in progress
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

	public GameState getCurrentGameState() {
		return currentGameState;
	}
}
