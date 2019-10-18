package project;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
