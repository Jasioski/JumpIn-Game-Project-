package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

}
