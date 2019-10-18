package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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


		BoardItem item = new EmptyBoardItem(initialCoordinate);
		
		try {
			board.setItem(finalCoordinate, item);
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
	
	
	@Test
	/**
	 * Should not be able to set an item if the coordinate already contains
	 * an item
	 */
	void testItem() {
		Board board = new Board(5);
		Coordinate badCoordinate = new Coordinate(0, 0);
		
		try {
			board.setItem(badCoordinate, new Rabbit(badCoordinate));
		} catch (BoardItemNotEmptyException e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}
		
		assertThrows(BoardItemNotEmptyException.class, () -> {
			board.setItem(badCoordinate, new Rabbit(badCoordinate));
		});
	}
	
}
