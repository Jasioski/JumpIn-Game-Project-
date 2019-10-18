package project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
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
	
	//@Test
	/**
	 * Should not be able to add item to the hole if there is already one inside
	 */
	
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
	
	@Test
	/**
	 * Get hole coordinate as collection
	 */
	void testGetCoordinates() {
		Coordinate coordinate = new Coordinate(0, 0);
		Hole hole = new Hole(coordinate);
		
		List<Coordinate> coordinates = hole.getCoordinates();
		
		assertEquals(coordinate, coordinates.get(0),
				"the coordinate should be the one that was set");
		assertEquals(1, coordinates.size(),
				"there should only be one coordinate");
	}
	
	@Test
	/**
	 * Should not be able to remove the containing item if it is empty
	 */
	void testRemoveContainingItemWhenEmpty() {
		Coordinate holeCoordinate = new Coordinate(0, 0);
		Hole hole = new Hole(holeCoordinate);
		
		assertThrows(HoleIsEmptyException.class, () -> {
			hole.removeContainingItem();
		});
	}
	
	@Test
	/**
	 * Remove the containing item when it exists
	 */
	void testRemoveContainingItem() {
		Coordinate holeCoordinate = new Coordinate(0, 0);
		Hole hole = new Hole(holeCoordinate);
		
		Rabbit item = new Rabbit(1,1);
		
		hole.setContainingItem(item);
		
		BoardItem containingItem;
		
		try {
			containingItem = hole.removeContainingItem();
			assertEquals(containingItem, item, "should get the same item back");
			assertTrue(hole.getContainingItem().isEmpty(), 
					"the hole should now be empty");
		} catch (HoleIsEmptyException e) {
			fail("Exception was thrown");
		}
		
	}
}
