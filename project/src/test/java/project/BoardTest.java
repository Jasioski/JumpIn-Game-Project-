package project;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
	void testMoveFoxRightOne() {
		// Create a board
		Board board = new Board(5);

		// Create a fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		Fox fox = new Fox(initialHead, initialTail);
		
		// Add to board
		// Initial Layout
		// F F E E E
		// E E E E E
		// E E E E E
		// E E E E E
		try {
			board.setItem(fox.getCoordinates(), fox);
		} catch (BoardItemNotEmptyException e) {
			fail("Exception was thrown");
		}
	
		// Move across the board one unit right
		// Expected Layout
		// E F F E E
		// E E E E E
		// E E E E E
		// E E E E E

		int moveSpaces = 1;
		Direction moveDirection = Direction.RIGHT;
		
		try {
			board.slide(moveDirection, moveSpaces, initialHead);	
		}
		catch (Exception e) {
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
	 * Move fox right by 2 spaces
	 */
	void testMoveFoxRightTwo() {
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
		int moveSpaces = 2;
		Direction moveDirection = Direction.RIGHT;

		try {
			board.slide(moveDirection, moveSpaces, initialHead);
		}
		catch (Exception e) {
			fail("Exception was thrown");
		}

		assertNotEquals(fox, board.getItem(initialHead),
				"The initial head coordinate should no longer have the fox");

		assertTrue(board.getItem(initialHead) instanceof EmptyBoardItem,
				"The initial head coordinate should be an empty item");

		assertNotEquals(fox, board.getItem(initialTail),
				"The initial tail coordinate should no longer have the fox");

		assertTrue(board.getItem(initialTail) instanceof EmptyBoardItem,
				"The initial tail coordinate should be an empty item");

		assertEquals(fox.getHead(), new Coordinate(0, 2),
				"The head should be where the tail was plus one unit right");

		assertEquals(fox.getTail(), new Coordinate(0, 3),
				"The tail should be where the tail was plus two units right");
	}

	@Test
	/**
	 * Move fox right by 4 spaces out of the board
	 * This should not cause any changes to the board as the board should catch the exception
	 * that was thrown by the fox
	 *
	 * The board should not change anything in its own representation
	 * It should throw an exception for the callee to be notified
	 */
	void testMoveFoxRightFourOutOfBoard() {
		// Create a board
		Board board = new Board(5);

		// Create a fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		Fox fox = new Fox(initialHead, initialTail);

		// Add to board
		// Initial Layout
		// F F E E E
		// E E E E E
		// E E E E E
		// E E E E E
		try {
			board.setItem(fox.getCoordinates(), fox);
		} catch (BoardItemNotEmptyException e) {
			fail("Exception was thrown");
		}

		// Move across the board four units right
		// Attempted Layout
		// E E E E F F  <---- the tail ends up off of the board, which is an issue
		// E E E E E
		// E E E E E
		// E E E E E

		int moveSpaces = 4;
		Direction moveDirection = Direction.RIGHT;

		// Store copies of the current state for comparison
		List<Coordinate> initialCoordinates = fox.getCoordinates();

		BoardItem initialItems[][] = new BoardItem[board.getRows()][board.getColumns()];

		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				initialItems[row][column] = board.getItem(row, column);
			}
		}

		assertThrows(SlideOutOfBoundsException.class, () -> {
			board.slide(moveDirection, moveSpaces, initialHead);
		}, "should not be able to move off of the board");

		assertEquals(initialCoordinates, fox.getCoordinates(), "the fox coordinates should not have changed");

		assertEquals(fox, board.getItem(initialHead),
				"The initial head coordinate should still have the fox");

		assertEquals(fox, board.getItem(initialTail),
				"The initial tail coordinate should still have the fox");

		// Make sure nothing in the board has changed
		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				assertEquals(board.getItem(row, column), initialItems[row][column],
						"the item at " + row + ":" + column + "should not have changed");
			}
		}
	}


	@Test
	/**
	 * Move fox right by 1 space into a rabbit
	 * This should not cause any changes to the board as the board should catch the exception
	 * that was thrown by the fox
	 *
	 * The board should not change anything in its own representation
	 * It should throw an exception for the callee to be notified
	 */
	void testMoveFoxRightThroughObstacle() {
		// Create a board
		Board board = new Board(5);

		// Create a fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		Fox fox = new Fox(initialHead, initialTail);

		// Rabbit
		BoardItem rabbit = new Rabbit(0, 2);

		// Add to board
		// Initial Layout
		// F F R E E
		// E E E E E
		// E E E E E
		// E E E E E
		try {
			board.setItem(rabbit.getCoordinates(), rabbit);
			board.setItem(fox.getCoordinates(), fox);
		} catch (BoardItemNotEmptyException e) {
			fail("Exception was thrown");
		}

		// Move across the board one unit right into the rabbit
		// Attempted Layout
		// E F FxR  E E <--- the fox would be at the same place as the rabbit
		// E E E    E E
		// E E E    E E
		// E E E    E E

		int moveSpaces = 4;
		Direction moveDirection = Direction.RIGHT;

		// Store copies of the current state for comparison
		List<Coordinate> initialFoxCoordinates = fox.getCoordinates();
		List<Coordinate> initialRabbitCoordinates = rabbit.getCoordinates();

		BoardItem initialItems[][] = new BoardItem[board.getRows()][board.getColumns()];

		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				initialItems[row][column] = board.getItem(row, column);
			}
		}

		assertThrows(SlideHitObstacleException.class, () -> {
			board.slide(moveDirection, moveSpaces, initialHead);
		}, "should not be able to move off of the board");

		assertEquals(initialFoxCoordinates, fox.getCoordinates(), "the fox coordinates should not have changed");
		assertEquals(fox, board.getItem(initialHead),
				"The initial head coordinate should still have the fox");
		assertEquals(fox, board.getItem(initialTail),
				"The initial tail coordinate should still have the fox");

		assertEquals(initialRabbitCoordinates, rabbit.getCoordinates(), "the rabbit's " +
				"coordinates should not have changed");

		// Make sure nothing in the board has changed
		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				assertEquals(board.getItem(row, column), initialItems[row][column],
						"the item at " + row + ":" + column + "should not have changed");
			}
		}
	}

	@Test
	/**
	 * Move fox right by zero space
	 * This should not cause any changes to the board
	 */
	void testMoveFoxRightZero() {
		// Create a board
		Board board = new Board(5);

		// Create a fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		Fox fox = new Fox(initialHead, initialTail);

		// Add to board
		// Initial Layout
		// F F E E E
		// E E E E E
		// E E E E E
		// E E E E E
		try {
			board.setItem(fox.getCoordinates(), fox);
		} catch (Exception e) {
			fail("Exception was thrown");
		}

		// Move across the board one unit right into the rabbit
		// Expected Layout
		// F F E E E <--- the fox would be at the same place as the rabbit
		// E E E E E
		// E E E E E
		// E E E E E

		int moveSpaces = 0;
		Direction moveDirection = Direction.RIGHT;

		// Store copies of the current state for comparison
		List<Coordinate> initialFoxCoordinates = fox.getCoordinates();

		BoardItem initialItems[][] = new BoardItem[board.getRows()][board.getColumns()];

		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				initialItems[row][column] = board.getItem(row, column);
			}
		}

		try {
			board.slide(moveDirection, moveSpaces, initialHead);
		} catch (Exception e) {
			fail("Exception was thrown");
		}

		assertEquals(initialFoxCoordinates, fox.getCoordinates(), "the fox coordinates should not have changed");

		// Make sure nothing in the board has changed
		for (int row = 0; row < board.getRows(); row++) {
			for (int column = 0; column < board.getColumns(); column++) {
				assertEquals(board.getItem(row, column), initialItems[row][column],
						"the item at " + row + ":" + column + "should not have changed");
			}
		}
	}


	@Test
	/**
	 * Move fox left by 1 space
	 */
	void testMoveFoxLeftOne() {
		// Create a board
		Board board = new Board(5);

		// Create a fox
		Coordinate initialHead = new Coordinate(0, 3);
		Coordinate initialTail = new Coordinate(0, 4);
		Fox fox = new Fox(initialHead, initialTail);

		// Add to board
		// Initial Layout
		// E E E F F
		// E E E E E
		// E E E E E
		// E E E E E
		try {
			board.setItem(fox.getCoordinates(), fox);
		} catch (BoardItemNotEmptyException e) {
			fail("Exception was thrown");
		}

		// Move across the board one unit right
		// Expected Layout
		// E E F F E
		// E E E E E
		// E E E E E
		// E E E E E

		int moveSpaces = 1;
		Direction moveDirection = Direction.LEFT;

		try {
			board.slide(moveDirection, moveSpaces, initialHead);
		}
		catch (Exception e) {
			fail("Exception was thrown");
		}

		assertEquals(fox, board.getItem(initialHead),
				"The initial head coordinate should still have the fox");

		assertNotEquals(fox, board.getItem(initialTail),
				"The tail should still no longer contain the fox");

		assertTrue(board.getItem(initialTail) instanceof EmptyBoardItem,
				"The initial tail coordinate should be an empty item");

		assertEquals(fox.getTail(), initialHead,
				"The tail should be where the head was");

		assertEquals(fox.getHead(), new Coordinate(0, 2),
				"The head should be where the head was minus one unit " +
						"left");
	}

	@Test
	/**
	 * Move fox up by 1 space
	 */
	void testMoveFoxUpOne() {
		// Create a board
		Board board = new Board(5);

		// Create a fox
		Coordinate initialHead = new Coordinate(1, 0);
		Coordinate initialTail = new Coordinate(2, 0);
		Fox fox = new Fox(initialHead, initialTail);

		// Add to board
		// Initial Layout
		// E E E E E
		// F E E E E
		// F E E E E
		// E E E E E
		try {
			board.setItem(fox.getCoordinates(), fox);
		} catch (BoardItemNotEmptyException e) {
			fail("Exception was thrown");
		}

		// Move across the board one unit right
		// Expected Layout
		// F E E E E
		// F E E E E
		// E E E E E
		// E E E E E

		int moveSpaces = 1;
		Direction moveDirection = Direction.UP;

		try {
			board.slide(moveDirection, moveSpaces, initialHead);
		}
		catch (Exception e) {
			fail("Exception was thrown");
		}

		assertEquals(fox, board.getItem(initialHead),
				"The initial head coordinate should still have the fox");

		assertNotEquals(fox, board.getItem(initialTail),
				"The tail should still no longer contain the fox");

		assertTrue(board.getItem(initialTail) instanceof EmptyBoardItem,
				"The initial tail coordinate should be an empty item");

		assertEquals(fox.getTail(), initialHead,
				"The tail should be where the head was");

		assertEquals(fox.getHead(), new Coordinate(0, 0),
				"The head should be where the head was plus one unit up ");
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
			
			board.slide(moveDirection, moveSpaces, coordinate);	
		});
	}

	@Test
	/**
	 * Jump rabbit right over one obstacle
	 */
	void testRabbitRightOverObstacle() {
		// Layout
		//   0  1  2 3
		// 0 RJ RO E E
		// 1 E  E  E
		Board board = new Board(2, 4);

		Coordinate rabbitJumpingCoordinate = new Coordinate(0, 0);
		Rabbit rabbitJumping = new Rabbit(0, 0);
		Rabbit rabbitObstacle = new Rabbit(0, 1);

		Direction jumpDirection = Direction.RIGHT;


		try {
			// Expected Layout
			//    0  1   2  3
			// 0  E  RO  RJ E
			// 1  E  E   E  E
			board.setItem(rabbitJumping.getCoordinates(), rabbitJumping);
			board.setItem(rabbitObstacle.getCoordinates(), rabbitObstacle);
			board.jump(jumpDirection, rabbitJumpingCoordinate);

            Coordinate expectedCoordinate = new Coordinate(0, 2);
            assertEquals(expectedCoordinate, rabbitJumping.getCoordinate(),
                    "the rabbit should be at the new expected coordinate");

            assertNotEquals(rabbitJumping, board.getItem(rabbitJumpingCoordinate),
                    "the old position should no longer contain the rabbit");

            assertTrue(board.getItem(rabbitJumpingCoordinate) instanceof EmptyBoardItem,
                    "the old position should contain an empty item");
		} catch (Exception e) {
		    fail("Exception was thrown");
		}

	}

//	@Test
//	/**
//	 * Jump rabbit right four and outside of board should fail
//	 */

	@Test
	/**
	 * Jump rabbit outside of the hole over an obstacle
	 */
	void testJumpRabbitOutsideOfHole () {
		// Layout
		// 0      1   2  3
		// H(RJ)  RO  E  E
		// E      E   E  E
		Board board = new Board(2, 4);

		Hole hole = new Hole(new Coordinate(0, 0));
		Rabbit rabbitJumping = new Rabbit(0, 0);
		Rabbit rabbitObstacle = new Rabbit(0, 1);

		try {
			// Place rabbit in the hole
			hole.containRabbit(rabbitJumping);
			// Add to board
			board.setItem(hole.getCoordinates(), hole);
			board.setItem(rabbitObstacle.getCoordinates(), rabbitObstacle);

			// Expected Layout
			// 0  1   2  3
			// H  RO  RJ  E
			// E  E   E  E

			// Jump out
			Direction jumpDirection = Direction.RIGHT;
            board.jumpOut(jumpDirection, hole.getCoordinate());

			assertFalse(hole.getContainingItem().isPresent(), "the hole should" +
					"be empty");
			assertEquals(board.getItem(new Coordinate(0, 1))
					,rabbitObstacle,
					"the obstacle should not have moved");

			Coordinate expectedCoordinate = new Coordinate(0, 2);
			assertEquals(board.getItem(expectedCoordinate),
					rabbitJumping,
					"the rabbit should be at the expected location");
			assertEquals(expectedCoordinate, rabbitJumping.getCoordinate(),
					"the rabbit should have had its own reference" +
							"updated");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}

	@Test
	/**
	 * Jump rabbit outside of a hole but into an empty adjacent block
	 * this should fail because the rabbit should be trying to jump over
	 * something first
	 */
	void testJumpRabbitOutsideHoleToEmpty () {
		// Layout
		// 0      1   2  3
		// H(RJ)  E   E  E
		// E      E   E  E
		Board board = new Board(2, 4);

		Hole hole = new Hole(new Coordinate(0, 0));
		Rabbit rabbitJumping = new Rabbit(0, 0);

		try {
			// Place rabbit in the hole
			hole.containRabbit(rabbitJumping);
			// Add to board
			board.setItem(hole.getCoordinates(), hole);


		} catch (Exception e) {
			fail("Exception was thrown");
		}

		// Jump out
		Direction jumpDirection = Direction.RIGHT;
		assertThrows(JumpFailedNoObstacleException.class, () ->  {
			board.jumpOut(jumpDirection, hole.getCoordinate());
		});

		assertTrue(hole.getContainingItem().isPresent(), "the hole should" +
				"not be empty");

		Coordinate expectedCoordinate = new Coordinate(0, 0);
		Hole holeFromBoard = (Hole) board.getItem(expectedCoordinate);
		assertEquals(holeFromBoard.getContainingItem().get(),
				rabbitJumping,
				"the rabbit should still in the hole");

		assertEquals(expectedCoordinate, rabbitJumping.getCoordinate(),
				"the rabbits internal coordinate should not have changed");
	}
}
