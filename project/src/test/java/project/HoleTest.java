package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class HoleTest {

	@Test
	void testConstructor() {
		Coordinate coordinate = new Coordinate(0, 0);
		
		Character displayCharacter = 'H';
		
		Hole hole = new Hole(coordinate);
		
		assertEquals(coordinate, hole.getCoordinates().get(0),
				"the coordinate should be the one that was set");
		assertEquals(displayCharacter, hole.getDisplayCharacter(),
				"the display character should be H");
	}
	
	@Test
	/**
	 * Get containingItem should be empty when constructed
	 */
	void testGetContainingItem() {
		
		Coordinate coordinate = new Coordinate(0, 0);
		
		Hole hole = new Hole(coordinate);
		
		Optional<BoardItem> containingItem = hole.getContainingItem();
		assertTrue(containingItem.isEmpty(),
				"the containing item should not exist");
	}
	
	/**
	 * Set containing item on the hole and make sure the coordinates are 
	 * the same
	 */	
	@Test
	void testSetContainingItem() {
		
		Coordinate holeCoordinate = new Coordinate(0, 0);
		Coordinate rabbitCoordinate = new Coordinate(1,1);
		
		Hole hole = new Hole(holeCoordinate);
		hole.setContainingItem(new Rabbit(rabbitCoordinate));
		
		Optional<BoardItem> containingItem = hole.getContainingItem();
		
		assertTrue(containingItem.isPresent(),
				"the containing item should exist");
		assertEquals(holeCoordinate, containingItem.get().getCoordinates()
				.get(0),
				"the rabbits coordinates should have been set to the "
				+ "holes coordinates");
	}
	
	@Test
	/**
	 * Get hole coordinate
	 */
	void testGetCoordinate() {
		Coordinate coordinate = new Coordinate(0, 0);
		Hole hole = new Hole(coordinate);
		
		assertEquals(coordinate, hole.getCoordinate(),
				"the coordinate should be the one that was set");
	}
}
