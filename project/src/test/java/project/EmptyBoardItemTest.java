package project;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class EmptyBoardItemTest {

	@Test
	public void testConstructor() {
		
		Coordinate coordinate = new Coordinate(0, 1);
		ItemUIRepresentation expectedRepresentation = ItemUIRepresentation.EMPTY;

		EmptyBoardItem emptyItem = new EmptyBoardItem(coordinate);
		
		List<Coordinate> coordinates = emptyItem.getCoordinates();
		
		assertEquals(1, coordinates.size(), "there should only be one"
				+ " coordinate");
		assertEquals(coordinates.get(0), coordinate, "should contain the "
				+ "initial coordinate");
		assertEquals(emptyItem.getUIRepresentation(), expectedRepresentation,
				"representation should be the Empty");
		
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
	
	@Test
	/**	
	 * Setting multiple coordinates with a list of size 1 should not fail
	 */
	void testSetCoordinatesWithSingle() {
		
		Coordinate initialCoordinate = new Coordinate(0, 0);
		Coordinate finalCoordinate = new Coordinate(1, 0);
		List<Coordinate> finalCoordinates = new ArrayList<>();
		
		finalCoordinates.add(finalCoordinate);
		
		EmptyBoardItem item = new EmptyBoardItem(initialCoordinate);
		
		List<Coordinate> initialCoordinates = item.getCoordinates();
		assertEquals(initialCoordinate, initialCoordinates.get(0), 
				"should be initialCoordinate");
		
		item.setCoordinates(finalCoordinates);

		List<Coordinate> newCoordinates = item.getCoordinates();
		
		assertEquals(finalCoordinate, newCoordinates.get(0),
				"the coordinate at index 0 should now be the one we set");
		
		assertEquals(1, newCoordinates.size(),
				"there should only be one coordinate");
	}
	
	@Test
	/**	
	 * Setting multiple coordinates should fail
	 */
	void testSetCoordinatesWithMultiple() {
		
		Coordinate initialCoordinate = new Coordinate(0, 0);
		Coordinate finalCoordinate1 = new Coordinate(1, 0);
		Coordinate finalCoordinate2 = new Coordinate(2, 0);
		
		List<Coordinate> finalCoordinates = new ArrayList<Coordinate>();
		finalCoordinates.add(finalCoordinate1);
		finalCoordinates.add(finalCoordinate2);
		
		EmptyBoardItem item = new EmptyBoardItem(initialCoordinate);
		
		assertThrows(IllegalArgumentException.class, () -> {
			item.setCoordinates(finalCoordinates);
		});
	}
}
