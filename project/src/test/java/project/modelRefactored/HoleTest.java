package project.modelRefactored;

import com.google.common.base.Optional;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HoleTest {

    @Test
    void testConstructor() {
        Optional<Containable> containable = Optional.absent();
        Coordinate holeCoordinate = new Coordinate(0, 0);
        Hole hole = new Hole(holeCoordinate,
                containable);

        assertEquals(hole.containingItem, containable,
                "the hole should have the item");

        Coordinate instanceCoordinate = hole.coordinate.left().get();

        assertEquals(holeCoordinate, instanceCoordinate,
                "the hole should have the correct coordinate");
    }


    @Test
    void testConstructorWithItem() {
        Containable containableItem = new Rabbit(new Coordinate(0,0));
        Optional<Containable> containable = Optional.of(containableItem);

        Coordinate holeCoordinate = new Coordinate(0, 0);
        Hole hole = new Hole(holeCoordinate,
                containable);

        assertEquals(hole.containingItem, containable,
                "the hole should have the item");

        Coordinate instanceCoordinate = hole.coordinate.left().get();

        Containable item = hole.containingItem.get();
        BoardItem boardItem = (BoardItem) item;

        assertEquals(holeCoordinate, boardItem.coordinate.left().get(),
                "the containableItem should have the correct coordinate");
    }


    @Test
    void testIsObstacleWithoutItem() {

        Coordinate holeCoordinate = new Coordinate(0, 0);
        Hole hole = new Hole(holeCoordinate,
                Optional.absent());

        assertFalse(hole.isObstacle(),
                "the hole is not an obstacle when empty");
    }

    // TODO: write test for when the hole contains something which is not an
    //  obstacle. I am not sure if this invariant event exists, someone needs
    //  to look into this

    @Test
    void testIsObstacleWithItem() {
        Rabbit rabbit = new Rabbit(new Coordinate(0,0));
        Optional<Containable>  containableRabbit = Optional.of(rabbit);

        Coordinate holeCoordinate = new Coordinate(0, 0);
        Hole hole = new Hole(holeCoordinate,
                containableRabbit);

        Containable item = hole.containingItem.get();

        assertTrue(hole.isObstacle(),
                "the hole is an obstacle when containing a rabbit");
    }

    
}
