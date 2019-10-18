package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BoardTest {

	@Test
	void testConstructor() {
		int rows = 5;
		int columns = 5;
		
		Board board = new Board(rows, columns);
		
		assertEquals(board.getRows(), rows, "rows should be the same");
		assertEquals(board.getColumns(), columns, "columns should be the same");
		
		// Check that the board is initialized correctly
				for (int row = 0; row < rows; row++) {
					for (int column = 0; column < columns; column++) {
						BoardItem item = board.getItem(row, column);
						
						Coordinate coordinate = item.getCoordinates().get(0);
						
						assertEquals(coordinate, new Coordinate(row, column),
								"coordinate should equal the row and column it is "
								+ "stored at");
					}
				}
		
	}
	
	@Test
	/**	
	 * Constructor with single argument as dimension
	 * Should create a square board
	 */
	void testConstructorwithDimension() {
		int dimension = 5;
		Board board = new Board(dimension);
		
		assertEquals(board.getRows(), dimension, "rows should equal dimension");
		assertEquals(board.getColumns(), dimension, "columns should equal"
				+ "dimension");
	}

	@Test
	/**	
	 * Board should not be constructed with non natural numbers
	 */
	void testConstructorWithBadDimensions () {
		assertThrows(IllegalArgumentException.class, () -> {
			new Board(-1, -1);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Board(-1, 0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Board(-1);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new Board(0);
		});
	}
	
	@Test
	/**
	 * Failing test case where getItem should only accept positive
	 * rows and columns
	 */
	void testGetItemWithNegativeArguments () {
		Board board = new Board(5);
		
		assertThrows(IllegalArgumentException.class, () -> {
			board.getItem(-1, -1);	
		});
	}
	
	@Test
	/**
	 * Should not be able to retrieve a board item outside of the length 
	 * and width
	 */
	void tesGetItemWithOutBound () {
		int dimension = 5;
		
		Board board = new Board(dimension);
		
		// Negative
		assertThrows(IllegalArgumentException.class, () -> {
			board.getItem(dimension, dimension);
		});
	}
	
	@Test
	/**
	 * Set an item on the board given the row, column and reference to the item
	 * to be set
	 * Make sure the item's internal state of the row and column are updated
	 */
	void testSetItem() {
		Board board = new Board(5);
		
		Coordinate initialCoordinate = new Coordinate(0, 0);
		Coordinate finalCoordinate = new Coordinate(1, 1);

		List<Coordinate> finalCoordinates = new ArrayList<Coordinate>();
		finalCoordinates.add(finalCoordinate);

		BoardItem item = new EmptyBoardItem(initialCoordinate);
		
		try {
			board.setItem(finalCoordinates, item);
		} catch (BoardItemNotEmptyException e) {
			fail("Exception was thrown");
		}
		
		BoardItem setItem = board.getItem(finalCoordinate);
		
		assertEquals(item, setItem, 
				"The item should be the one we set");
		
		assertEquals(finalCoordinate, setItem.getCoordinates().get(0), 
				"The item should have the new coordinate");
				
		assertEquals(1, setItem.getCoordinates().size(), 
			"The item should only have one coordinate");
	}
	
	// TODO: should fail when trying to set outside the board dimensions
	
	
	@Test
	/**
	 * Should not be able to set an item if the coordinate already contains
	 * an item
	 */
	void testSetItemWhenAnItemExists() {
		Board board = new Board(5);
		Coordinate badCoordinate = new Coordinate(0, 0);
		List<Coordinate> badCoordinates = new ArrayList<Coordinate>();
		badCoordinates.add(badCoordinate);
		
		try {
			board.setItem(badCoordinates, new Rabbit(badCoordinate));
		} catch (BoardItemNotEmptyException e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}
		
		assertThrows(BoardItemNotEmptyException.class, () -> {
			board.setItem(badCoordinates, new Rabbit(badCoordinate));
		});
	}
	
	@Test
	/**
	 * Should not be able to set an item if coordinates are empty
	 */
	void testSetItemEmptyCoordinates() {
		Board board = new Board(5);
		List<Coordinate> emptyCoordinates = new ArrayList<Coordinate>();

		assertThrows(IllegalArgumentException.class, () -> {
			board.setItem(emptyCoordinates, new Rabbit(0, 0));
		});
	}
	
	@Test
	/**
	 * Move fox right by 1 space
	 */
	void testMoveFox() {
		// Create a board
		Board board = new Board(5);
	
		
		// Create a fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		Fox fox = new Fox(initialHead, initialTail);
		
		// Add to board
		try {
			board.setItem(fox.getCoordinates(), fox);
		} catch (BoardItemNotEmptyException e) {
			fail("Exception was thrown");
		}
	
		// Move across the board one unit right
		int moveSpaces = 1;
		Direction moveDirection = Direction.RIGHT;
		
		try {
			board.move(moveDirection, moveSpaces, initialHead);	
		}
		catch (NonMovableItemException e) {
			fail("Exception was thrown");
		}
		
		assertNotEquals(fox, board.getItem(initialHead),
				"The initial head coordinate should no longer have the fox");
		
		assertTrue(board.getItem(initialHead) instanceof EmptyBoardItem,
				"The initial head coordinate should be an empty item");
		
		assertEquals(fox, board.getItem(initialTail),
				"The tail should still contain the fox");
		
		assertEquals(fox.getHead(), initialTail,
				"The head should be where the tail was");

		assertEquals(fox.getTail(), new Coordinate(0, 2),
				"The tail should be where the tail was plus one unit right");
	}
	
	@Test
	/**
	 * Should not allow moving a non-movable item
	 */
	void testMoveNonMovable() {
		// Create a board
		Board board = new Board(5);
	
		// Create a hole
		Coordinate coordinate = new Coordinate(0, 0);
		Hole hole = new Hole(coordinate);

		// Add to board
		try {
			board.setItem(hole.getCoordinates(), hole);
		} catch (BoardItemNotEmptyException e) {
			fail("Exception was thrown");
		}

		// Trying to move should throw
		assertThrows(NonMovableItemException.class, () -> {
			int moveSpaces = 1;
			Direction moveDirection = Direction.RIGHT;
			
			board.move(moveDirection, moveSpaces, coordinate);	
		});
	}
	
	
}
