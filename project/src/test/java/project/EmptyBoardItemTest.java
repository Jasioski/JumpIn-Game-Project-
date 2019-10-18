package project;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class EmptyBoardItemTest {

	@Test
	public void testConstructor() {
		
		Coordinate coordinate = new Coordinate(0, 1);
		Character type = 'E';
		
		EmptyBoardItem emptyItem = new EmptyBoardItem(coordinate);
		
		List<Coordinate> coordinates = emptyItem.getCoordinates();
		
		assertEquals(1, coordinates.size(), "there should only be one"
				+ " coordinate");
		assertEquals(coordinates.get(0), coordinate, "should contain the "
				+ "initial coordinate");
		assertEquals(emptyItem.getDisplayCharacter(), type, 
				"type should be the same");
		
	}
	
	@Test
	/**	
	 * Set a single coordinate
	 * this overrides the initial coordinate
	 */
	void testSetCoordinate() {
		
		Coordinate initialCoordinate = new Coordinate(0, 0);
		Coordinate finalCoordinate = new Coordinate(1, 0);
		
		EmptyBoardItem item = new EmptyBoardItem(initialCoordinate);
		
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
}
