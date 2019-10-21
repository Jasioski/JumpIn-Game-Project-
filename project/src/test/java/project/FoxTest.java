package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class FoxTest {

	@Test
	void testConstructor() {
		Coordinate head = new Coordinate(0, 0);
		Coordinate tail = new Coordinate(1, 0);
		
		Character expectedDisplayCharacter = 'F';
		ItemUIRepresentation expectedRepresentation = ItemUIRepresentation.FOX;

		Fox fox = new Fox(head, tail);

		assertEquals(fox.getTail(), tail, "tail should be the same");
		assertEquals(fox.getHead(), head, "head should be the same");
		assertEquals(fox.getUIRepresentation(), expectedRepresentation,
				"the display character should be " + expectedRepresentation);
	}
	
	void testConstructorWithHeadAndTail() {
		Coordinate head = new Coordinate(0, 0);
		Coordinate tail = new Coordinate(1, 0);
		
		Character expectedDisplayCharacter = 'F';
		
		Fox fox = new Fox(head, tail);
		
		assertEquals(fox.getTail(), tail, "tail should be the same");
		assertEquals(fox.getHead(), head, "head should be the same");
		assertEquals(fox.getUIRepresentation(), expectedDisplayCharacter,
				"the display character should be " + expectedDisplayCharacter);
	}

	@Test
	/**
	 * A failing condition as the head and tails should not be able to be at the
	 * same place.
	 */
	void testConstructorHeadAtTails() {
		int headRow = 0;
		int headColumn = 0;

		int tailRow = 0;
		int tailColumn = 0;

		assertThrows(IllegalArgumentException.class, () -> {
			new Fox(headRow, headColumn, tailRow, tailColumn);
		});
	}

	@Test
	/**
	 * A failing condition as the head and tail positions must be within one unit
	 * away.
	 */
	void testConstructorHeadFarFromTails() {
		int headRow = 0;
		int headColumn = 0;

		int tailRow = 3;
		int tailColumn = 0;

		assertThrows(IllegalArgumentException.class, () -> {
			new Fox(headRow, headColumn, tailRow, tailColumn);
		});
	}

	@Test
	/**
	 * A failing condition as the head and tail positions must not be diagonal.
	 */
	void testConstructorHeadDiagnoalFromTails() {
		int headRow = 0;
		int headColumn = 0;

		int tailRow = 1;
		int tailColumn = 1;

		assertThrows(IllegalArgumentException.class, () -> {
			new Fox(headRow, headColumn, tailRow, tailColumn);
		});
	}
	
	// TODO: write test
//	@Test
//	void testSetHead() {
//		Coordinate head = new Coordinate(0, 0);
//		Coordinate tail = new Coordinate(1, 0);
//		
//		Fox fox = new Fox(head, tail);
//	}
	
	// TODO: Add test making sure we can't set the fox coordinates to a diagonal
	// this means we cannot move sideways
	
	@Test
	/**
	 * move fox right one unit
	 */
	void testMoveRightOne() {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		
		Fox fox = new Fox(initialHead, initialTail);
		
		// Setup slice
		// index:  0 1 2 3
		// Layout: F F E E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));
		
		// Move Fox
		Direction moveDirection = Direction.RIGHT;
		int moveSpaces = 1;
		
		// Store the results
		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}
		
		// Expected result
		// index:  0 1 2 3
		// Layout: E F F E
		Coordinate newHead = new Coordinate(0, 1);
		Coordinate newTail = new Coordinate(0, 2);
		
		assertEquals(newHead, fox.getHead(), "the head should be at the "
				+ "new location");
		
		assertEquals(newTail, fox.getTail(),
				"the tail should be at the new location");
	}
	
	@Test
	/**
	 * move fox right 2 units
	 */
	void testMoveRightTwo() {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		
		Fox fox = new Fox(initialHead, initialTail);
		
		// Setup slice
		// index:  0 1 2 3
		// Layout: F F E E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));
		
		// Move Fox
		Direction moveDirection = Direction.RIGHT;
		int moveSpaces = 2;
		
		// Store the results
		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			fail("Exception was thrown");
		}
		
		// Expected result
		// index:  0 1 2 3
		// Layout: E E F F
		Coordinate newHead = new Coordinate(0, 2);
		Coordinate newTail = new Coordinate(0, 3);
		
		assertEquals(newHead, fox.getHead(), "the head should be at the "
				+ "new location");

		assertEquals(newTail, fox.getTail(),
				"the tail should be at the new location");
	}
	
	@Test
	/**
	 * Slide fox left one unit
	 */
	void testSlideFoxLeftOne() {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 1);
		Coordinate initialTail = new Coordinate(0, 2);
		
		Fox fox = new Fox(initialHead, initialTail);
		
		// Setup slice
		// index:  0 1 2 3
		// Layout: E F F E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(0, 3));
		
		// Move Fox
		Direction moveDirection = Direction.LEFT;
		int moveSpaces = 1;
		
		// Store the results
		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}
		
		// Expected result
		// index:  0 1 2 3
		// Layout: F F E E
		Coordinate newHead = new Coordinate(0, 0);
		Coordinate newTail = new Coordinate(0, 1);
		
		assertEquals(newHead, fox.getHead(), "the head should be at the "
				+ "new location");
		
		assertEquals(newTail, fox.getTail(),
				"the tail should be at the new location");
	}

	@Test
	/**
	 * Slide fox left two units
	 */
	void testSlideFoxLeftTwo() {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 2);
		Coordinate initialTail = new Coordinate(0, 3);
		
		Fox fox = new Fox(initialHead, initialTail);
		
		// Setup slice
		// index:  0 1 2 3
		// Layout: E E F F
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(0, 1));
		
		// Move Fox
		Direction moveDirection = Direction.LEFT;
		int moveSpaces = 2;
		
		// Store the results
		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}
		
		// Expected result
		// index:  0 1 2 3
		// Layout: F F E E
		Coordinate newHead = new Coordinate(0, 0);
		Coordinate newTail = new Coordinate(0, 1);
		
		assertEquals(newHead, fox.getHead(), "the head should be at the "
				+ "new location");
		
		assertEquals(newTail, fox.getTail(),
				"the tail should be at the new location");
	}

	@Test
	/**
	 * Slide fox up one unit
	 */
	void testSlideUpOne()  {
		// Setup Fox
		Coordinate initialHead = new Coordinate(1, 0);Coordinate initialTail = new Coordinate(2, 0);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// Row   Item:
		// 0     E
		// 1     F
		// 2     F
		// 3     E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(3, 0));

		// Move Fox
		Direction moveDirection = Direction.UP;
		int moveSpaces = 1;

		// Store the results
		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}

		// Expected result
		// Row   Item:
		// 0     F
		// 1     F
		// 2     E
		// 3     E
		Coordinate newHead = new Coordinate(0, 0);
		Coordinate newTail = new Coordinate(1, 0);

		assertEquals(newHead, fox.getHead(), "the head should be at the "
				+ "new location");

		assertEquals(newTail, fox.getTail(),
				"the tail should be at the new location");
	}

	@Test
	/**
	 * Slide fox up two units
	 */
	void testSlideUpTwo()  {
		// Setup Fox
		Coordinate initialHead = new Coordinate(2, 0);
		Coordinate initialTail = new Coordinate(3, 0);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// Row   Item:
		// 0     E
		// 1     E
		// 2     F
		// 3     F
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(1, 0));

		// Move Fox
		Direction moveDirection = Direction.UP;
		int moveSpaces = 2;

		// Store the results
		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}

		// Expected result
		// Row   Item:
		// 0     F
		// 1     F
		// 2     E
		// 3     E
		Coordinate newHead = new Coordinate(0, 0);
		Coordinate newTail = new Coordinate(1, 0);

		assertEquals(newHead, fox.getHead(), "the head should be at the "
				+ "new location");

		assertEquals(newTail, fox.getTail(),
				"the tail should be at the new location");
	}

	@Test
	/**
	 * Slide fox up three units. This should cause the fox to fall out
	 * of the slice
	 */
	void testSlideUpThreeOutOfBounds()  {
		// Setup Fox
		Coordinate initialHead = new Coordinate(2, 0);
		Coordinate initialTail = new Coordinate(3, 0);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// Row   Item:
		// 0     E
		// 1     E
		// 2     F
		// 3     F
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(1, 0));

		// Move Fox
		Direction moveDirection = Direction.UP;
		int moveSpaces = 3;

		// Store the fox coordinates
		List<Coordinate> initialCoordinates = fox.getCoordinates();

		assertThrows(SlideOutOfBoundsException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);
		}, "the fox should not be able to move outside of the slices range");

		assertEquals(initialCoordinates, fox.getCoordinates(),
				"the fox coordinates should not have changed");
	}


	@Test
	/**
	 * Make sure you cannot slide through an obstacle when moving up
	 */
	void testSlideUpThroughObstacle() {

		// Setup Fox
		Coordinate initialHead = new Coordinate(2, 0);
		Coordinate initialTail = new Coordinate(3, 0);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// Row   Item:
		// 0     E
		// 1     R
		// 2     F
		// 3     F
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new Rabbit(1, 0));

		// Move Fox
		Direction moveDirection = Direction.UP;
		int moveSpaces = 1;

		// Store the fox coordinates
		List<Coordinate> initialCoordinates = fox.getCoordinates();

		assertThrows(SlideHitObstacleException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);
		}, "the fox should not be able to move through an " +
				"obstacle");

		assertEquals(initialCoordinates, fox.getCoordinates(),
				"the fox coordinates should not have changed");
	}


	@Test
	/**
	 * Slide fox down one unit
	 */
	void testSlideDownOne()  {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(1, 0);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// Row   Item:
		// 0     F
		// 1     E
		// 2     E
		// 3     E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(2, 0));
		slice.add(new EmptyBoardItem(3, 0));

		// Move Fox
		Direction moveDirection = Direction.DOWN;
		int moveSpaces = 1;

		// Store the results
		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}

		// Expected result
		// Row   Item:
		// 0     E
		// 1     F
		// 2     F
		// 3     E
		Coordinate newHead = new Coordinate(1, 0);
		Coordinate newTail = new Coordinate(2, 0);

		assertEquals(newHead, fox.getHead(), "the head should be at the "
				+ "new location");

		assertEquals(newTail, fox.getTail(),
				"the tail should be at the new location");
	}

	@Test
	/**
	 * Slide fox down two units
	 */
	void testSlideDownTwo()  {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(1, 0);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// Row   Item:
		// 0     F
		// 1     F
		// 2     E
		// 3     E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(2, 0));
		slice.add(new EmptyBoardItem(3, 0));

		// Move Fox
		Direction moveDirection = Direction.DOWN;
		int moveSpaces = 2;

		// Store the results
		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail("Exception was thrown");
		}

		// Expected result
		// Row   Item:
		// 0     E
		// 1     E
		// 2     F
		// 3     F
		Coordinate newHead = new Coordinate(2, 0);
		Coordinate newTail = new Coordinate(3, 0);

		assertEquals(newHead, fox.getHead(), "the head should be at the "
				+ "new location");

		assertEquals(newTail, fox.getTail(),
				"the tail should be at the new location");
	}

	@Test
	/**
	 * Slide fox down three units. This should cause the fox to fall out
	 * of the slice
	 */
	void testSlideDownThreeOutOfBounds()  {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(1, 0);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// Row   Item:
		// 0     F
		// 1     F
		// 2     E
		// 3     E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(2, 0));
		slice.add(new EmptyBoardItem(3, 0));

		// Move Fox
		Direction moveDirection = Direction.DOWN;
		int moveSpaces = 3;

		// Store the fox coordinates
		List<Coordinate> initialCoordinates = fox.getCoordinates();

		assertThrows(SlideOutOfBoundsException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);
		}, "the fox should not be able to move outside of the slices range");

		assertEquals(initialCoordinates, fox.getCoordinates(),
				"the fox coordinates should not have changed");
	}

	@Test
	/**
	 * Make sure you cannot slide through an obstacle when moving up
	 */
	void testSlideDownThroughObstacle() {

		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(1, 0);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// Row   Item:
		// 0     F
		// 1     F
		// 2     R
		// 3     E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new Rabbit(2, 0));
		slice.add(new EmptyBoardItem(3, 0));

		// Move Fox
		Direction moveDirection = Direction.DOWN;
		int moveSpaces = 1;

		// Store the fox coordinates
		List<Coordinate> initialCoordinates = fox.getCoordinates();

		assertThrows(SlideHitObstacleException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);
		}, "the fox should not be able to move through an " +
				"obstacle");

		assertEquals(initialCoordinates, fox.getCoordinates(),
				"the fox coordinates should not have changed");
	}

	@Test
	/**
	 * Should not move at all if the spaces is 0
	 */
	void testSlideMoveZero() {
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// index:  0 1 2 3
		// Layout: F F E E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));

		Direction moveDirection = Direction.RIGHT;
		int moveSpaces = 0;

		List<Coordinate> initialCoordinates = fox.getCoordinates();

		try {
			fox.slide(moveDirection, moveSpaces, slice);
		} catch (Exception e) {
			fail("Exception was thrown");
		}

		assertEquals(initialCoordinates, fox.getCoordinates(),
				"the fox coordinates should not have changed");
	}


	@Test
	/**
	 * Should not be able to slide with an empty slice
	 */
	void testSlideEmptySlice() {
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		
		List<BoardItem> slice = new ArrayList<BoardItem>();
		
		Fox fox = new Fox(initialHead, initialTail);
		Direction moveDirection = Direction.RIGHT;
		int moveSpaces = 3;
		
		assertThrows(IllegalArgumentException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);
		}, "should throw if the slice is empty");
	}
	
	@Test
	/**
	 * Should not be able to slide if the slice does not
	 * contain the fox
	 */
	void testSlideSliceDoesNotContainFox() {
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(new EmptyBoardItem(0, 0));
		
		Fox fox = new Fox(initialHead, initialTail);
		Direction moveDirection = Direction.RIGHT;
		int moveSpaces = 3;
		
		assertThrows(IllegalArgumentException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);
		}, "should throw if the slice does not contain the fox");
	}
	
	
	@Test
	/**
	 * Should not be able to move fox out of the bounds of the slice
	 */
	void testMoveRightThreeOutOfBounds() {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		
		Fox fox = new Fox(initialHead, initialTail);
		
		// Setup slice
		// index:  0 1 2 3
		// Layout: F F E E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));
		
		// Move Fox
		Direction moveDirection = Direction.RIGHT;
		int moveSpaces = 3;
		
		// Store the fox coordinates
		List<Coordinate> initialCoordinates = fox.getCoordinates();
		
		assertThrows(SlideOutOfBoundsException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);	
		}, "the fox should not be able to move outside of the slices range");
		
		assertEquals(initialCoordinates, fox.getCoordinates(),
				"the fox coordinates should not have changed");

	}
	
	@Test
	/**
	 * Should not be able to move the fox left of the bounds of the slice
	 */
	void testMoveLeftThreeOutOfBounds() {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 2);
		Coordinate initialTail = new Coordinate(0, 3);
		
		Fox fox = new Fox(initialHead, initialTail);
		
		// Setup slice
		// index:  0 1 2 3
		// Layout: E E F F
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(0, 1));
		
		// Move Fox
		Direction moveDirection = Direction.LEFT;
		int moveSpaces = 3;
		
		// Store the fox coordinates
		List<Coordinate> initialCoordinates = fox.getCoordinates();
		
		assertThrows(SlideOutOfBoundsException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);	
		}, "the fox should not be able to move outside of the slices range");
		
		assertEquals(initialCoordinates, fox.getCoordinates(),
				"the fox coordinates should not have changed");

	}
	
	@Test
	/**
	 * Make sure you cannot slide through an obstacle
	 */
	void testSlideThroughObstacle() {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);
		
		Fox fox = new Fox(initialHead, initialTail);
		
		// Setup slice
		// index:  0 1 2 3
		// Layout: F F R E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new Rabbit(0, 2));
		slice.add(new EmptyBoardItem(0, 3));
		
		// Move Fox
		Direction moveDirection = Direction.RIGHT;
		int moveSpaces = 1;
		
		// Store the fox coordinates
		List<Coordinate> initialCoordinates = fox.getCoordinates();
		
		assertThrows(SlideHitObstacleException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);	
		}, "the fox should not be able to move throgh obstacles");
		
		assertEquals(initialCoordinates, fox.getCoordinates(),
				"the fox coordinates should not have changed");
	}


	@Test
	/**
	 * move fox right one unit into an elevated position
	 */
	void testMoveRightOneToElevated() {
		// Setup Fox
		Coordinate initialHead = new Coordinate(0, 0);
		Coordinate initialTail = new Coordinate(0, 1);

		Fox fox = new Fox(initialHead, initialTail);

		// Setup slice
		// index:  0 1 2 3
		// Layout: F F U E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(fox);
		slice.add(fox);
		slice.add(new ElevatedBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));

		// Move Fox
		Direction moveDirection = Direction.RIGHT;
		int moveSpaces = 1;

		assertThrows(SlideHitElevatedException.class, () -> {
			fox.slide(moveDirection, moveSpaces, slice);
		});

		assertEquals(initialHead, fox.getHead(), "the head should not have" +
				"changed");

		assertEquals(initialTail, fox.getTail(),
				"the tail should not have moved");
	}
}
