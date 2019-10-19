package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class RabbitTest {

	@Test
	void testConstructor() {
		Coordinate coordinate = new Coordinate(0, 1);
		Character expectedDisplayCharacter = 'R';
		
		Rabbit rabbit = new Rabbit(coordinate);
		
		Coordinate retrievedCoordinate = rabbit.getCoordinate();
		
		assertEquals(coordinate, retrievedCoordinate, "the coordinate should"
				+ "have been set");
		
		assertEquals(rabbit.getDisplayCharacter(), expectedDisplayCharacter,
				"the display character should be " + expectedDisplayCharacter);
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
		slice.add(new EmptyBoardItem(0, 0));
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

}
