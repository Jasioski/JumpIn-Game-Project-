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

		Fox fox = new Fox(head, tail);

		assertEquals(fox.getTail(), tail, "tail should be the same");
		assertEquals(fox.getHead(), head, "head should be the same");
		assertEquals(fox.getDisplayCharacter(), expectedDisplayCharacter,
				"the display character should be " + expectedDisplayCharacter);
	}
	
	void testConstructorWithHeadAndTail() {
		Coordinate head = new Coordinate(0, 0);
		Coordinate tail = new Coordinate(1, 0);
		
		Character expectedDisplayCharacter = 'F';
		
		Fox fox = new Fox(head, tail);
		
		assertEquals(fox.getTail(), tail, "tail should be the same");
		assertEquals(fox.getHead(), head, "head should be the same");
		assertEquals(fox.getDisplayCharacter(), expectedDisplayCharacter,
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
	
	@Test
	void testSetHead() {
		Coordinate head = new Coordinate(0, 0);
		Coordinate tail = new Coordinate(1, 0);
		
		Fox fox = new Fox(head, tail);
	}
	
	// TODO: test for trying to set head and tail with coordinates where 
	// there is already something else at the new coordinates

	// TODO: Test for failing to trying to move through an obstacle along the path of
	// sliding
	
	// TODO: Test moving by more than one space
	
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
			List<Coordinate> coordinates = fox.slide(moveDirection, moveSpaces, slice);
		} catch (SlideOutOfBoundsException e) {
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
			List<Coordinate> coordinates = fox.slide(moveDirection, moveSpaces, slice);
		} catch (SlideOutOfBoundsException e) {
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
}
