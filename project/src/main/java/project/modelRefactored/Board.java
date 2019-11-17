package project.modelRefactored;

import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pcollections.HashTreePMap;
import org.pcollections.PMap;
import project.model.Direction;
import project.model.GameState;
import project.model.exceptions.NonSlideableException;
import project.model.exceptions.SlideHitObstacleException;
import project.model.exceptions.SlideWrongOrientationException;

public class Board {

	public final int numberOfRows;
	public final int numberOfColumns;
	private static Logger logger = LogManager.getLogger(Board.class);

	//todo: override and throw exception on map.get
	private PMap<Coordinate, BoardItem> items;

	protected GameState currentGameState;

	public Board(int rows, int columns) {
		this.currentGameState = GameState.IN_PROGRESS;
		items = HashTreePMap.empty();
		this.numberOfRows = rows;
		this.numberOfColumns = columns;

		// Initialize Board Items
		for (int row = 0; row < numberOfRows; row ++) {
			for (int column = 0; column < numberOfColumns; column++) {
				Coordinate currentCoordinate = new Coordinate(row, column);
				BoardItem itemToAdd = new EmptyBoardItem(currentCoordinate);
				items = items.plus(currentCoordinate, itemToAdd);
			}
		}
	}

	private Board (Board board) {
		this.currentGameState = GameState.IN_PROGRESS;
		this.numberOfRows = board.numberOfRows;
		this.numberOfColumns = board.numberOfColumns;

		this.items = board.items;
	}

	/**
	 * Set an item on a board while preserving purity
	 * @param item the item you want to add to the board
	 * @return a board which is a result of the applied transformation
	 */
	public Board setItem(BoardItem item) {
        Board modifiedBoard = new Board(this);

		if (item.coordinate.isLeft()) {
			logger.trace("found left item");
			Coordinate coordinate = item.coordinate.left().get();
			modifiedBoard.items = modifiedBoard.items.plus(coordinate, item);
		}

		//TODO: clean up this code
		if (item.coordinate.isRight()) {
			logger.trace("found right type");
			Pair<Coordinate,Coordinate> coordinate =
					item.coordinate.right().get();

        	modifiedBoard.items = modifiedBoard.items.plus(coordinate.left(), item);
			modifiedBoard.items = modifiedBoard.items.plus(coordinate.right(), item);
		}

		return modifiedBoard;
    }

	public BoardItem getItem(Coordinate coordinate) {
		return this.items.get(coordinate);
	}

	public PMap<Coordinate, BoardItem> getItems() {
		return items;
	}

	public PMap<Coordinate, BoardItem> getColumnSlice(int column) {
		PMap<Coordinate, BoardItem> slice = HashTreePMap.empty();

		for (int row = 0; row < numberOfRows; row++) {
			Coordinate coordinate = new Coordinate(row, column);
			slice = slice.plus(coordinate, getItem(coordinate));
		}

		return slice;
	}

	public PMap<Coordinate, BoardItem> getRowSlice(int row) {
		PMap<Coordinate, BoardItem> slice = HashTreePMap.empty();

		for (int column = 0; column < numberOfColumns; column++) {
			Coordinate coordinate = new Coordinate(row, column);
			slice = slice.plus(coordinate, getItem(coordinate));
		}

		return slice;
	}

	public Board slide(Direction direction, int moveSpaces,
					   Coordinate coordinate) throws InvalidMoveException,
			SlideHitObstacleException, SlideWrongOrientationException,
			NonSlideableException {

		Board board = new Board(this);
		BoardItem item = this.items.get(coordinate);

		//if item is a fox, perform the slide
		if (item instanceof Fox) {
			Fox fox = (Fox) item;
			Pair<Coordinate, Coordinate> originalCoords =
					Pair.pair(fox.getHead(), fox.getTail());

			EmptyBoardItem emptyHead =
					new EmptyBoardItem(originalCoords.left());

			EmptyBoardItem emptyTail =
					new EmptyBoardItem(originalCoords.right());

			board = board.setItem(emptyHead);
			board = board.setItem(emptyTail);

			Fox newFox = fox.slide(board.getRowSlice(coordinate.row),
					moveSpaces,	direction);

			board = board.setItem(newFox);
		} else {
			throw new NonSlideableException("Must be Fox to slide");
		}
		return board;
	}

	public Board jump(Direction direction, Coordinate coordinate)
			throws InvalidMoveException {

		Board board = new Board(this);
		BoardItem item = this.items.get(coordinate);
		Either<Rabbit, ContainerItem> rabbitOrHole;

		if (item instanceof Rabbit) {
			Rabbit rabbit = (Rabbit) item;
			rabbitOrHole = rabbit.jump(direction,
					this.getRowSlice(coordinate.row));

			EmptyBoardItem empty = new EmptyBoardItem(coordinate);
			board = board.setItem(empty);

			if (rabbitOrHole.isLeft()) {
				board = board.setItem(rabbitOrHole.left().get());
			} else {
				board = board.setItem(rabbitOrHole.right().get());
			}
		}
		else if (item instanceof ContainerItem){
			ContainerItem containerItem = (ContainerItem) item;

			Pair<ContainerItem, Either<Rabbit, ContainerItem>> holeAndJumped = containerItem.jump(direction,
					this.getRowSlice(coordinate.row));

			rabbitOrHole = holeAndJumped.right();

			// sets empty hole
			board = board.setItem(holeAndJumped.left());

			// if its a rabbit
			if (rabbitOrHole.isLeft()) {
				Rabbit newRabbit = rabbitOrHole.left().get();
				board = board.setItem(newRabbit);
			}

			// if its a hole
			else {
				ContainerItem newContainerItem = rabbitOrHole.right().get();
				board = board.setItem(newContainerItem);
			}
		}
		else{
			throw new InvalidMoveException("Must be a rabbit to jump!");
		}

		board.updateGameState();

		return board;
	}

	/**
	 * Updates the gamestate to won if there are no rabbits remaining on the board.
	 */
	public Board updateGameState() {
		Board board = new Board(this);
		for (int row = 0; row < board.numberOfRows; row++) {
			for (int column = 0; column < board.numberOfColumns; column++) {
				// make sure there are no top level rabbits
				if (board.items.get(new Coordinate(row, column)) instanceof Rabbit) {
					board.currentGameState = GameState.IN_PROGRESS;
					return board;
				}
				// make sure there are no rabbits inside elevated positions
				else if (board.items.get(new Coordinate(row, column)) instanceof ElevatedBoardItem) {
					ElevatedBoardItem elevatedBoardItem = (ElevatedBoardItem)
							board.items.get(new Coordinate(row, column));
					if (elevatedBoardItem.containingItem.isPresent()) {
						Containable containable =
								elevatedBoardItem.containingItem.get();

						if (containable instanceof  Rabbit) {
							board.currentGameState = GameState.IN_PROGRESS;
							return board;
						}
					}
				}
			}
		}

		board.currentGameState = GameState.SOLVED;
		return board;
	}

	/**
	 * Gets the current gamestate of the board.
	 * @return The current gamestate.
	 */
	public GameState getCurrentGameState() {
		return currentGameState;
	}
	@Override
		public String toString() {
		String str = "";
		String rowLine = "";

		for (int i = 0; i < numberOfRows; i++) {
			rowLine += "--------";
		}

		String columnLine = "" ;

		// column header
		for (int i = 0; i < numberOfRows; i++) {
			columnLine += "     " + (i + 1 ) + " ";
		}

		columnLine += "\n";

		for (int row = 0; row < numberOfRows; row ++) {

			if (row == 0) {
				str += columnLine;
			}

			str += rowLine;
			str += "\n";

			str += ""+ (row+1);
			for (int column = 0; column < numberOfColumns; column++) {
				BoardItem item =  getItem(new Coordinate(row, column));

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
}
