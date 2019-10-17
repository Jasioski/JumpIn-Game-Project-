package project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTest {

	@Test
	void testConstructor() {
		int rows = 5;
		int columns = 5;
		
		Board board = new Board(rows, columns);
		
		assertEquals(board.getRows(), rows);
		assertEquals(board.getColumns(), columns);
		
		// Check that the board is initialized correctly
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				BoardItem item = board.getItem(row, column);
				assertEquals(item.getRow(), row);
				assertEquals(item.getColumn(), column);
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
		
		assertEquals(board.getRows(), dimension);
		assertEquals(board.getColumns(), dimension);
		
		// Check that the board is initialized correctly
		for (int row = 0; row < dimension; row++) {
			for (int column = 0; column < dimension; column++) {
				BoardItem item = board.getItem(row, column);
				assertEquals(item.getRow(), row);
				assertEquals(item.getColumn(), column);
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
	
}
