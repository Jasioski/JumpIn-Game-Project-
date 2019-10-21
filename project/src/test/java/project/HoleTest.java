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

		ItemUIRepresentation expectedRepresentation = ItemUIRepresentation.HOLE_EMPTY;

		Hole hole = new Hole(coordinate);
		
		assertEquals(coordinate, hole.getCoordinates().get(0),
				"the coordinate should be the one that was set");
		assertEquals(expectedRepresentation, hole.getUIRepresentation(),
				"the display character should be H");
	}
	
	@Test
	/**
	 * Get containingItem should be empty when constructed
	 */
	void testGetContainingItem() {
		
		Coordinate coordinate = new Coordinate(0, 0);
		
		Hole hole = new Hole(coordinate);
		
		Optional<Rabbit> containingItem = hole.getContainingItem();
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
		try {
			hole.containRabbit(new Rabbit(rabbitCoordinate));
		} catch (HoleAlreadyHasRabbitException e) {
			fail("An exception was thrown");
		}

		Optional<Rabbit> containingItem = hole.getContainingItem();
		
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

		try {
			hole.containRabbit(item);
		} catch (HoleAlreadyHasRabbitException e) {
			fail("Exception was thrown");
		}

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

	@Test
	/**
	 * Contain a rabbit inside the hole
	 */
	void testContainRabbit() {
		Hole hole = new Hole(new Coordinate(0, 0));
		Rabbit rabbit = new Rabbit(1,0);

		try {
			hole.containRabbit(rabbit);
		} catch (Exception e) {
		    fail("Exception was thrown");
		}

		assertTrue(hole.getContainingItem().isPresent(), "the hole should" +
				"now contain something");
		assertEquals(rabbit, hole.getContainingItem().get(), "the hole should" +
				"now contain the rabbit");
		assertEquals(hole.getCoordinates(), rabbit.getCoordinates(), "" +
				"the rabbits coordinates should be the same as the hole" );
	}

	@Test
	/**
	 * UI Representation when it contains a rabbit
	 */
	void testGetUIRepresentationRabbit () {
		Hole hole = new Hole(new Coordinate(0, 0));
		Rabbit rabbit = new Rabbit(1,0);

		try {
			hole.containRabbit(rabbit);
		} catch (Exception e) {
			fail("Exception was thrown");
		}

		assertEquals(ItemUIRepresentation.HOLE_OCCUPIED_RABBIT, hole.getUIRepresentation(), "the hole should" +
				"now should now be showing the occupied character");
	}

	@Test
	/**
	 * UI Representation when it is empty
	 */
	void testGetUIRepresentationEmpty () {
		Hole hole = new Hole(new Coordinate(0, 0));

		assertEquals(ItemUIRepresentation.HOLE_EMPTY, hole.getUIRepresentation(), "the whole should" +
				"be empty");

	}

	@Test
	/**
	 * UI Representation when it is empty after removing a rabbit that used to be there
	 */
	void testGetUIRepresentationRabbitRemoved () {
		Hole hole = new Hole(new Coordinate(0, 0));
		Rabbit rabbit = new Rabbit(0, 0);

		try {
			hole.containRabbit(rabbit);
			hole.removeContainingItem();
		}
		catch (Exception e){
			fail("Exception was thrown");
		}

		assertEquals(ItemUIRepresentation.HOLE_EMPTY, hole.getUIRepresentation(), "the whole should" +
				"be empty");

	}

	@Test
    /**
     * Try containing a rabbit in a hole that already has a rabbit
     */
    void testContainRabbitWhenTheHoleAlreadyHasARabbit() {
        Hole hole = new Hole(new Coordinate(0, 0));
		Rabbit rabbitHole = new Rabbit(0,0);

		Coordinate rabbitOutsideCoordinate = new Coordinate(1, 0);
		Rabbit rabbitOutside = new Rabbit(rabbitOutsideCoordinate);

		try {
			hole.containRabbit(rabbitHole);
		} catch (Exception e) {
		    fail("Exception was thrown");
		}

		assertThrows(HoleAlreadyHasRabbitException.class, () -> {
			hole.containRabbit(rabbitOutside);
		});

        assertTrue(hole.getContainingItem().isPresent(), "the hole should" +
                "still contain something");
        assertEquals(rabbitHole, hole.getContainingItem().get(), "the hole " +
				"should" +
                "should still contain the old rabbit");
        assertEquals(rabbitOutsideCoordinate,
				rabbitOutside.getCoordinates().get(0),
                "the rabbit outside should not have moved" );
    }
}
