package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import project.model.*;
import project.model.exceptions.JumpFailedNoObstacleException;
import project.model.exceptions.JumpFailedOutOfBoundsException;
import project.tui.ItemUIRepresentation;

class RabbitTest {

	@Test
	void testConstructor() {
		Coordinate coordinate = new Coordinate(0, 1);
		ItemUIRepresentation expectedRepresentation = ItemUIRepresentation.RABBIT;

		Rabbit rabbit = new Rabbit(coordinate);

		Coordinate retrievedCoordinate = rabbit.getCoordinate();

		assertEquals(coordinate, retrievedCoordinate, "the coordinate should"
				+ "have been set");

		assertEquals(rabbit.getUIRepresentation(), expectedRepresentation,
				"the rabbit should have the correct representation");
	}

	@Test
	/**
	 * Set a single coordinate
	 * this overrides the initial coordinate
	 */
	void testSetCoordinate() {

		Coordinate initialCoordinate = new Coordinate(0, 0);
		Coordinate finalCoordinate = new Coordinate(1, 0);

		Rabbit item = new Rabbit(initialCoordinate);

		List<Coordinate> initialCoordinates = item.getCoordinates();
		assertEquals(initialCoordinate, initialCoordinates.get(0),
				"should be initialCoordinate");

		item.setCoordinate(finalCoordinate);

		List<Coordinate> finalCoordinates = item.getCoordinates();

		assertEquals(finalCoordinate, finalCoordinates.get(0),
				"the coordinate at index 0 should now be the one we set");

		assertEquals(1, finalCoordinates.size(),
				"there should only be one coordinate");
	}

	@Test
	/**
	 * Setting more than one coordinate should throw an exception
	 */
	void testSetCoordinatesOnlyOne() {

		Coordinate initialCoordinate = new Coordinate(0, 0);
		ArrayList<Coordinate> finalCoordinates = new ArrayList<Coordinate>();

		finalCoordinates.add(new Coordinate(1, 0));

		Rabbit item = new Rabbit(initialCoordinate);

		item.setCoordinates(finalCoordinates);

		List<Coordinate> newCoordinates = item.getCoordinates();

		assertEquals(finalCoordinates.get(0), newCoordinates.get(0),
				"the coordinate at index 0 should now be the one we set");

		assertEquals(1, finalCoordinates.size(),
				"there should only be one coordinate");
	}

	@Test
	/**
	 * Setting more than one coordinate should throw an exception
	 */
	void testSetCoordinatesMoreThanOne() {

		Coordinate initialCoordinate = new Coordinate(0, 0);
		ArrayList<Coordinate> finalCoordinates = new ArrayList<Coordinate>();

		finalCoordinates.add(new Coordinate(1, 0));
		finalCoordinates.add(new Coordinate(2, 0));

		Rabbit item = new Rabbit(initialCoordinate);

		assertThrows(IllegalArgumentException.class, () -> {
			item.setCoordinates(finalCoordinates);
		});
	}

	@Test
	/**
	 * Jump right over one rabbit 
	 *
	 */
	void testJumpRightOne() {

		Rabbit rabbitJumping = new Rabbit(0, 0);
		Rabbit rabbitObstacle = new Rabbit(0, 1);

		// Setup slice
		// index:  0 1 2 3
		// Layout: RJ RO E E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(rabbitObstacle);
		slice.add(new EmptyBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));

		// Jump Rabbit
		Direction moveDirection = Direction.RIGHT;

		try {
			List<Coordinate> newCoordinates = rabbitJumping.jump(moveDirection, slice);
			// Expected result
			// index:  0 1 2 3
			// Layout: E R0 RJ E

			Coordinate newRabbitJumpingCoordinate = new Coordinate(0, 2);
			assertEquals(newRabbitJumpingCoordinate ,rabbitJumping.getCoordinate(), "the rabbit should be at"
					+ "the new location" );

			assertEquals(newRabbitJumpingCoordinate, newCoordinates.get(0), "The method should return new coordinate for"
					+ "the rabbit");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}


	@Test
	/**
	 * Jump right two units over a fox
	 */
	void testJumpRightTwoOverFox() {

		Rabbit rabbitJumping = new Rabbit(0, 0);
		Fox foxObstacle = new Fox(0, 1, 0, 2);

		// Setup slice
		// index:  0  1  2  3
		// Layout: RJ FO FO E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(foxObstacle);
		slice.add(new EmptyBoardItem(0, 3));

		// Jump Rabbit
		Direction moveDirection = Direction.RIGHT;

		try {
			List<Coordinate> newCoordinates = rabbitJumping.jump(moveDirection, slice);
			// Expected result
			// index:  0 1  2  3
			// Layout: E FO FO RJ

			Coordinate newRabbitJumpingCoordinate = new Coordinate(0, 3);
			assertEquals(newRabbitJumpingCoordinate ,rabbitJumping.getCoordinate(), "the rabbit should be at"
					+ "the new location" );

			assertEquals(newRabbitJumpingCoordinate, newCoordinates.get(0), "The method should return new coordinate for"
					+ "the rabbit");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}

	@Test
	/**
	 * Jump right should not do anything if there is no obstacle
	 */
	void testJumpRightOneNoObstacle() {
		Rabbit rabbitJumping = new Rabbit(0, 0);

		// Setup slice
		// index:  0 1 2 3
		// Layout: RJ E E E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(new EmptyBoardItem(0, 1));
		slice.add(new EmptyBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));

		// Jump Rabbit
		Direction moveDirection = Direction.RIGHT;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedNoObstacleException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump over an empty item");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}


	@Test
	/**
	 * Jump right should not do anything if there is only an elevated item
	 */
	void testJumpRightOneElevated() {
		Rabbit rabbitJumping = new Rabbit(0, 0);

		// Setup slice
		// index:  0  1  2 3
		// Layout: RJ UE E E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(new ElevatedBoardItem(0, 1));
		slice.add(new EmptyBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));

		// Jump Rabbit
		Direction moveDirection = Direction.RIGHT;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedNoObstacleException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump over an empty item");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}

	@Test
	/**
	 * Try jumping four units over a fox and a rabbit
	 * this should cause an error because this will cause
	 * the rabbit to jump outside of the slice
	 */
	void testJumpRightFourOutOfBounds() {

		Rabbit rabbitJumping = new Rabbit(0, 0);
		Fox foxObstacle = new Fox(0, 1, 0, 2);
		Rabbit rabbitObstacle = new Rabbit(0, 3);

		// Setup slice
		// index:  0  1  2  3
		// Layout: RJ FO FO RO
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(foxObstacle);
		slice.add(rabbitObstacle);

		// Jump Rabbit
		Direction moveDirection = Direction.RIGHT;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedOutOfBoundsException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump outside of the slice");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}

	@Test
	/**
	 * Jump left over one rabbit 
	 *
	 */
	void testJumpLeftOne() {

		Rabbit rabbitJumping = new Rabbit(0, 2);
		Rabbit rabbitObstacle = new Rabbit(0, 1);

		// Setup slice
		// index:  0 1 2 3
		// Layout: E RO RJ E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(rabbitObstacle);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(0, 3));

		// Jump Rabbit
		Direction moveDirection = Direction.LEFT;

		try {
			List<Coordinate> newCoordinates = rabbitJumping.jump(moveDirection, slice);
			// Expected result
			// index:  0 1 2 3
			// Layout: RJ R0 E E

			Coordinate newRabbitJumpingCoordinate = new Coordinate(0, 0);
			assertEquals(newRabbitJumpingCoordinate ,rabbitJumping.getCoordinate(), "the rabbit should be at"
					+ "the new location" );

			assertEquals(newRabbitJumpingCoordinate, newCoordinates.get(0), "The method should return new coordinate for"
					+ "the rabbit");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}

	@Test
	/**
	 * Jump down over one rabbit 
	 *
	 */
	void testJumpDownOne() {

		Rabbit rabbitJumping = new Rabbit(0, 0);
		Rabbit rabbitObstacle = new Rabbit(1, 0);

		// Setup slice
		// RJ 	0 0
		// RO	1 0 
		// E	2 0
		// E  	3 0
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(rabbitObstacle);
		slice.add(new EmptyBoardItem(2,0));
		slice.add(new EmptyBoardItem(3, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.DOWN;

		try {
			List<Coordinate> newCoordinates = rabbitJumping.jump(moveDirection, slice);
			// Expected result
			// E	0 0
			// RO	1 0 
			// RJ	2 0
			// E  	3 0

			Coordinate newRabbitJumpingCoordinate = new Coordinate(2, 0);
			assertEquals(newRabbitJumpingCoordinate ,rabbitJumping.getCoordinate(), "the rabbit should be at"
					+ "the new location" );

			assertEquals(newRabbitJumpingCoordinate, newCoordinates.get(0), "The method should return new coordinate for"
					+ "the rabbit");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}

	@Test
	/**
	 * Jump up over one rabbit 
	 *
	 */
	void testJumpUpOne() {

		Rabbit rabbitJumping = new Rabbit(2, 0);
		Rabbit rabbitObstacle = new Rabbit(1, 0);

		// Setup slice
		// E 	0 0
		// RO	1 0 
		// RJ	2 0
		// E  	3 0
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(rabbitObstacle);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(3, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.UP;

		try {
			List<Coordinate> newCoordinates = rabbitJumping.jump(moveDirection, slice);
			// Expected result
			// RJ	0 0
			// RO	1 0 
			// E	2 0
			// E  	3 0

			Coordinate newRabbitJumpingCoordinate = new Coordinate(0, 0);
			assertEquals(newRabbitJumpingCoordinate ,rabbitJumping.getCoordinate(), "the rabbit should be at"
					+ "the new location" );

			assertEquals(newRabbitJumpingCoordinate, newCoordinates.get(0), "The method should return new coordinate for"
					+ "the rabbit");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}


	@Test
	/**
	 * Jump left two units over a fox
	 */
	void testJumpLeftTwoOverFox() {

		Rabbit rabbitJumping = new Rabbit(0, 3);
		Fox foxObstacle = new Fox(0, 1, 0, 2);

		// Setup slice
		// index:  0  1  2  3
		// Layout: E FO FO RJ
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(foxObstacle);
		slice.add(new EmptyBoardItem(0, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.LEFT;

		try {
			List<Coordinate> newCoordinates = rabbitJumping.jump(moveDirection, slice);
			// Expected result
			// index:  0 1  2  3
			// Layout: RJ FO FO E

			Coordinate newRabbitJumpingCoordinate = new Coordinate(0, 0);
			assertEquals(newRabbitJumpingCoordinate, rabbitJumping.getCoordinate(), "the rabbit should be at"
					+ "the new location");

			assertEquals(newRabbitJumpingCoordinate, newCoordinates.get(0), "The method should return new coordinate for"
					+ "the rabbit");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}

	@Test
	/**
	 * Jump left should not do anything if there is no obstacle
	 */
	void testJumpLeftOneNoObstacle() {
		Rabbit rabbitJumping = new Rabbit(0, 1);

		// Setup slice
		// index:  0 1 2 3
		// Layout: E RJ E E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(0, 2));
		slice.add(new EmptyBoardItem(0, 3));

		// Jump Rabbit
		Direction moveDirection = Direction.LEFT;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedNoObstacleException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump over an empty item");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}


	@Test
	/**
	 * Jump left should not do anything if there is only an elevated item
	 */
	void testJumpLeftOneElevated() {
		Rabbit rabbitJumping = new Rabbit(0, 2);

		// Setup slice
		// index:  0  1  2 3
		// Layout: E UE RJ E
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(new ElevatedBoardItem(0, 1));
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(0, 3));

		// Jump Rabbit
		Direction moveDirection = Direction.LEFT;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedNoObstacleException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump over an empty item");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}

	@Test
	/**
	 * Try jumping four units over a fox and a rabbit
	 * this should cause an error because this will cause
	 * the rabbit to jump outside of the slice
	 */
	void testJumpLeftFourOutOfBounds() {

		Rabbit rabbitJumping = new Rabbit(0, 0);
		Fox foxObstacle = new Fox(0, 1, 0, 2);
		Rabbit rabbitObstacle = new Rabbit(0, 3);

		// Setup slice
		// index:  0  1  2  3
		// Layout: RJ FO FO RO
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(foxObstacle);
		slice.add(rabbitObstacle);

		// Jump Rabbit
		Direction moveDirection = Direction.RIGHT;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedOutOfBoundsException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump outside of the slice");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}


	@Test
	/**
	 * Jump Down two units over a fox
	 */
	void testJumpDownTwoOverFox() {

		Rabbit rabbitJumping = new Rabbit(0, 0);
		Fox foxObstacle = new Fox(1, 0, 2, 0);

		// Setup slice
		// RJ 	0 0
		// FO	1 0
		// FO	2 0
		// E  	3 0
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(foxObstacle);
		slice.add(new EmptyBoardItem(3, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.DOWN;

		try {
			List<Coordinate> newCoordinates = rabbitJumping.jump(moveDirection, slice);
			// Setup slice
			// E 	0 0
			// FO	1 0
			// FO	2 0
			// RJ	3 0

			Coordinate newRabbitJumpingCoordinate = new Coordinate(3, 0);
			assertEquals(newRabbitJumpingCoordinate ,rabbitJumping.getCoordinate(), "the rabbit should be at"
					+ "the new location" );

			assertEquals(newRabbitJumpingCoordinate, newCoordinates.get(0), "The method should return new coordinate for"
					+ "the rabbit");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}

	@Test
	/**
	 * Jump Down should not do anything if there is no obstacle
	 */
	void testJumpDownOneNoObstacle() {
		Rabbit rabbitJumping = new Rabbit(0, 0);

		// Setup slice
		// RJ 	0 0
		// E	1 0
		// E	2 0
		// E  	3 0
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(new EmptyBoardItem(3, 0));
		slice.add(new EmptyBoardItem(1, 0));
		slice.add(new EmptyBoardItem(2, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.DOWN;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedNoObstacleException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump over an empty item");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}


	@Test
	/**
	 * Jump Down should not do anything if there is only an elevated item
	 */
	void testJumpDownOneElevated() {
		Rabbit rabbitJumping = new Rabbit(1, 0);

		// Setup slice
		// E 	0 0
		// RJ	1 0
		// UE	2 0
		// E  	3 0
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(new ElevatedBoardItem(2, 0));
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(3, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.DOWN;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedNoObstacleException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump over an empty item");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}

	@Test
	/**
	 * Try jumping four units over a fox and a rabbit
	 * this should cause an error because this will cause
	 * the rabbit to jump outside of the slice
	 */
	void testJumpDownFourOutOfBounds() {

		Rabbit rabbitJumping = new Rabbit(0, 0);
		Fox foxObstacle = new Fox(1, 0, 2, 0);
		Rabbit rabbitObstacle = new Rabbit(3, 0);

		// Setup slice
		// RJ 	0 0
		// FO	1 0
		// FO	2 0
		// RO  	3 0

		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(foxObstacle);
		slice.add(rabbitObstacle);

		// Jump Rabbit
		Direction moveDirection = Direction.DOWN;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedOutOfBoundsException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump outside of the slice");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}


	@Test
	/**
	 * Jump up two units over a fox
	 */
	void testJumpUpTwoOverFox() {

		Rabbit rabbitJumping = new Rabbit(3, 0);
		Fox foxObstacle = new Fox(1, 0, 2, 0);

		// Setup slice
		// E 	0 0
		// FO	1 0
		// FO	2 0
		// RJ  	3 0
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(foxObstacle);
		slice.add(new EmptyBoardItem(0, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.UP;

		try {
			List<Coordinate> newCoordinates = rabbitJumping.jump(moveDirection, slice);
			// Setup slice
			// RJ 	0 0
			// FO	1 0
			// FO	2 0
			// E  	3 0

			Coordinate newRabbitJumpingCoordinate = new Coordinate(0, 0);
			assertEquals(newRabbitJumpingCoordinate ,rabbitJumping.getCoordinate(), "the rabbit should be at"
					+ "the new location" );

			assertEquals(newRabbitJumpingCoordinate, newCoordinates.get(0), "The method should return new coordinate for"
					+ "the rabbit");

		} catch (Exception e) {
			fail("Exception was thrown");
		}
	}

	@Test
	/**
	 * Jump up should not do anything if there is no obstacle
	 */
	void testJumpUpOneNoObstacle() {
		Rabbit rabbitJumping = new Rabbit(3, 0);

		// Setup slice
		// E 	0 0
		// E	1 0
		// E	2 0
		// RJ  	3 0
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(1, 0));
		slice.add(new EmptyBoardItem(2, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.UP;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedNoObstacleException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump over an empty item");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}


	@Test
	/**
	 * Jump UP should not do anything if there is only an elevated item
	 */
	void testJumpUpOneElevated() {
		Rabbit rabbitJumping = new Rabbit(3, 0);

		// Setup slice
		// E 	0 0
		// E	1 0
		// UE	2 0
		// RJ  	3 0
		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(new ElevatedBoardItem(2, 0));
		slice.add(new EmptyBoardItem(0, 0));
		slice.add(new EmptyBoardItem(1, 0));

		// Jump Rabbit
		Direction moveDirection = Direction.UP;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedNoObstacleException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump over an empty item");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}

	@Test
	/**
	 * Try jumping four units over a fox and a rabbit
	 * this should cause an error because this will cause
	 * the rabbit to jump outside of the slice
	 */
	void testJumpUpFourOutOfBounds() {

		Rabbit rabbitJumping = new Rabbit(3, 0);
		Fox foxObstacle = new Fox(1, 0, 2, 0);
		Rabbit rabbitObstacle = new Rabbit(0, 0);

		// Setup slice
		// RO 	0 0
		// FO	1 0
		// FO	2 0
		// RJ  	3 0

		List<BoardItem> slice = new ArrayList<BoardItem>();
		slice.add(rabbitJumping);
		slice.add(foxObstacle);
		slice.add(rabbitObstacle);

		// Jump Rabbit
		Direction moveDirection = Direction.UP;

		// Store original coordinates
		List<Coordinate> originalCoordinates = rabbitJumping.getCoordinates();

		assertThrows(JumpFailedOutOfBoundsException.class, () -> {
			rabbitJumping.jump(moveDirection, slice);
		}, "An error should have been thrown because the rabbit should" +
				"not be able to jump outside of the slice");

		// the rabbit should not have moved
		assertEquals(originalCoordinates, rabbitJumping.getCoordinates());
	}
}
