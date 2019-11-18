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

import java.util.Objects;

public class Board {

	public final int numberOfRows;
	public final int numberOfColumns;
	private static Logger logger = LogManager.getLogger(Board.class);

	//todo: override and throw exception on map.get
	private PMap<Coordinate, BoardItem> items;

	public final GameState currentGameState;

	/**
	 * Gets the correct slice of the board based on direction
	 *
	 * @param direction  passes in direction the move is to be performed in
	 * @param coordinate coordinate of what is trying ot move
	 * @return rowSlice or columnSlice depending on direction
	 */
	private PMap<Coordinate, BoardItem> getSlice(Direction direction,
												 Coordinate coordinate) {
		if (direction == Direction.LEFT || direction == Direction.RIGHT) {
			return this.getRowSlice(coordinate.row);
		} else {
			return this.getColumnSlice(coordinate.column);
		}
	}

	public Board(int rows, int columns) {
		this(rows, columns, GameState.IN_PROGRESS);
	}

	public Board(int rows, int columns, GameState gameState) {
		this.currentGameState = gameState;
		items = HashTreePMap.empty();
		this.numberOfRows = rows;
		this.numberOfColumns = columns;

		// Initialize Board Items
		for (int row = 0; row < numberOfRows; row++) {
			for (int column = 0; column < numberOfColumns; column++) {
				Coordinate currentCoordinate = new Coordinate(row, column);
				BoardItem itemToAdd = new EmptyBoardItem(currentCoordinate);
				items = items.plus(currentCoordinate, itemToAdd);
			}
		}
	}

	private Board(Board board) {
		this.currentGameState = GameState.IN_PROGRESS;
		this.numberOfRows = board.numberOfRows;
		this.numberOfColumns = board.numberOfColumns;

		this.items = board.items;
	}

	private Board(Board board, GameState gameState) {
		this.currentGameState = gameState;
		this.numberOfRows = board.numberOfRows;
		this.numberOfColumns = board.numberOfColumns;

		this.items = board.items;
	}

	/**
	 * Set an item on a board while preserving purity
	 *
	 * @param item the item you want to add to the board
	 * @return a board which is a result of the applied transformation
	 */
	public Board setItem(BoardItem item) {
		Board modifiedBoard = new Board(this);

		if (item.coordinate.isLeft()) {
			Coordinate coordinate = item.coordinate.left().get();
			modifiedBoard.items = modifiedBoard.items.plus(coordinate, item);
		}

		//TODO: clean up this code
		if (item.coordinate.isRight()) {
			Pair<Coordinate, Coordinate> coordinate =
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
			PMap<Coordinate, BoardItem> slice =
					board.getSlice(direction, coordinate);

			Pair<Coordinate, Coordinate> originalCoords =
					Pair.pair(fox.getHead(), fox.getTail());

			EmptyBoardItem emptyHead =
					new EmptyBoardItem(originalCoords.left());

			EmptyBoardItem emptyTail =
					new EmptyBoardItem(originalCoords.right());

			board = board.setItem(emptyHead);
			board = board.setItem(emptyTail);

			Fox newFox = fox.slide(slice,
					moveSpaces, direction);

			board = board.setItem(newFox);
		} else {
			throw new NonSlideableException("Must be Fox to slide");
		}

		board = board.updateGameState();
		return board;
	}

	public Board jump(Direction direction, Coordinate coordinate)
			throws InvalidMoveException {

		//TODO refactor to return after if's
		Board board = new Board(this);
		BoardItem item = this.items.get(coordinate);
		Either<Rabbit, ContainerItem> rabbitOrHole;

		PMap<Coordinate, BoardItem> slice = this.getSlice(direction,
				coordinate);

		if (item instanceof Rabbit) {
			Rabbit rabbit = (Rabbit) item;
			rabbitOrHole = rabbit.jump(direction, slice);

			EmptyBoardItem empty = new EmptyBoardItem(coordinate);
			board = board.setItem(empty);

			if (rabbitOrHole.isLeft()) {
				board = board.setItem(rabbitOrHole.left().get());
			} else {
				board = board.setItem(rabbitOrHole.right().get());
			}
		} else if (item instanceof ContainerItem) {
			ContainerItem containerItem = (ContainerItem) item;

			Pair<ContainerItem, Either<Rabbit, ContainerItem>> holeAndJumped
					= containerItem.jump(direction, slice);

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
		} else {
			throw new InvalidMoveException("Must be a rabbit to jump!");
		}

		board = board.updateGameState();
		return board;
	}

	public Board move(Coordinate itemSelected, Coordinate itemDestination) throws InvalidMoveException, NonSlideableException, SlideHitObstacleException, SlideWrongOrientationException {
		Coordinate deltaCoordinate = this.computeDelta(itemSelected,
				itemDestination);
		Direction direction = this.getDirectionFromDestination(deltaCoordinate);
		BoardItem item = this.getItem(itemSelected);
		Board board = this;

		if (item instanceof Rabbit || item instanceof ContainerItem) {
			logger.trace("try jumping in direction:" + direction.toString());
			board = this.jump(direction, itemSelected);
		}
		if (item instanceof Fox) {
			logger.trace("try sliding fox in direction: " + direction.toString());
			int moveSpaces = deltaCoordinate.row;
			if (deltaCoordinate.row == 0) {
				moveSpaces = deltaCoordinate.column;
			}

			board = this.slide(direction, Math.abs(moveSpaces), itemSelected);
		}
		return board;
	}

	/**
	 * Returns the distance coordinate between two different coordinates.
	 *
	 * @param initial     The intial coordinate that the movement starts from.
	 * @param destination The final coordinate that it should end up at.
	 * @return
	 */
	private Coordinate computeDelta(Coordinate initial,
									Coordinate destination) {
		int row = destination.row - initial.row;
		int column = destination.column - initial.column;

		return new Coordinate(row, column);
	}

	/**
	 * Returns the direction of a move based on a coordinate containing the destination's distance
	 *
	 * @param deltaDistance Coordinate of the distance between the start and end point
	 * @return The direction desired
	 * @throws IllegalArgumentException if the direction is invalid
	 */
	private Direction getDirectionFromDestination(Coordinate deltaDistance) {

		if (deltaDistance.row == 0 && deltaDistance.column == 0) {
			throw new IllegalArgumentException("Cannot move to the same " +
					"position");
		} else if (!(deltaDistance.row != 0 && deltaDistance.column != 0)) {
			if (deltaDistance.row > 0) { //destination is below
				return Direction.DOWN;
			} else if (deltaDistance.row < 0) { //destination is above
				return Direction.UP;
			} else if (deltaDistance.column > 0) { //destination is to the right
				return Direction.RIGHT;
			} else { //destination is the the left
				return Direction.LEFT;
			}
		}

		throw new IllegalArgumentException("Invalid direction");
	}

	/**
	 * Updates the gamestate to won if there are no rabbits remaining on the board.
	 */
	public Board updateGameState() {
		for (int row = 0; row < this.numberOfRows; row++) {
			for (int column = 0; column < this.numberOfColumns; column++) {
				// make sure there are no top level rabbits
				if (this.items.get(new Coordinate(row, column)) instanceof Rabbit) {
					GameState currentGameState = GameState.IN_PROGRESS;

					Board board = new Board(this, currentGameState);

					return board;
				}
				// make sure there are no rabbits inside elevated positions
				else if (this.items.get(new Coordinate(row, column)) instanceof ElevatedBoardItem) {
					ElevatedBoardItem elevatedBoardItem = (ElevatedBoardItem)
							this.items.get(new Coordinate(row, column));
					if (elevatedBoardItem.containingItem.isPresent()) {
						Containable containable =
								elevatedBoardItem.containingItem.get();

						if (containable instanceof Rabbit) {
							GameState currentGameState = GameState.IN_PROGRESS;

							Board board = new Board(this, currentGameState);

							return board;
						}
					}
				}
			}
		}

		GameState currentGameState = GameState.SOLVED;

		Board board = new Board(this, currentGameState);

		return board;
	}

	@Override
	public String toString() {
		String str = "";
		String rowLine = "";

		for (int i = 0; i < numberOfRows; i++) {
			rowLine += "--------";
		}

		String columnLine = "";

		// column header
		for (int i = 0; i < numberOfRows; i++) {
			columnLine += "     " + (i + 1) + " ";
		}

		columnLine += "\n";

		for (int row = 0; row < numberOfRows; row++) {

			if (row == 0) {
				str += columnLine;
			}

			str += rowLine;
			str += "\n";

			str += "" + (row + 1);
			for (int column = 0; column < numberOfColumns; column++) {
				BoardItem item = getItem(new Coordinate(row, column));

				str += " | ";
				//test code
				if (item.toString().length() == 10) {
					str += " " + item.toString() + " ";
				} else if (item.toString().length() == 11) {
					str += " " + item.toString();
				} else {
					logger.error("badly sized ui text");
				}

				str += " ";
			}

			str += " |\n";
		}

		str += rowLine;

		return str;
	}


	@Override
	public int hashCode() {
		return Objects.hash(numberOfRows, numberOfColumns,
				items, currentGameState);
	}

	public boolean equals (Object o) {
		if (this == o) return true;

		return false;
	}

}
