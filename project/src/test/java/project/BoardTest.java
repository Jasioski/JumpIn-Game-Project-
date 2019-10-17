package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
				assertEquals(item.getRow(), row, "row of the item should"
						+ "be the same as its row index in the array");
				assertEquals(item.getColumn(), column, "column of the item should"
						+ "be the same as its column index in the array");
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
		
		// Check that the board is initialized correctly
		for (int row = 0; row < dimension; row++) {
			for (int column = 0; column < dimension; column++) {
				BoardItem item = board.getItem(row, column);
				assertEquals(item.getRow(), row, "row should be the same"
						+ "as its row index in the array");
				assertEquals(item.getColumn(), column, "column should be "
						+ "the same as its column index in the array");
			}
		}

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
		
		int initialRow = 0;
		int initialColumn = 0;
		
		BoardItem item = new BoardItem(0, 0, 'B');
		
		int movedRow = 1;
		int movedColumn = 1;
		
		board.setItem(movedRow, movedColumn, item);
		
		assertEquals(item, board.getItem(movedRow, movedColumn),
				"the item at " + movedRow + ":" + movedColumn + 
				" should be the one we set");
		
		assertEquals(movedRow, item.getRow(), "the row should be the "
				+ "one we moved to");
		assertEquals(movedColumn, item.getColumn(), "the column should be the "
				+ "one we moved to");
	}
	

	
//	@Test
//	/**
//	 * Should not be able to set an item if the place is non empty
//	 */
//	void testItem() {
//		Board board = new Board(5);
//		int row = 0;
//		int column = 0;
//		
//		board.setItem(0, 0, new Rabbit(0, 0));
//	}
	
}
