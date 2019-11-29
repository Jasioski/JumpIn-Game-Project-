package project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MushroomTest {

    @Test
    void testMushroomMovable () {
        Mushroom mushroom = new Mushroom(0,0);
        BoardItem item = mushroom;
        assertFalse(item instanceof Movable);
    }

    @Test
    void testMushroomObstacle () {
        Mushroom mushroom = new Mushroom(0,0);
        BoardItem item = mushroom;
        assertTrue(item.isObstacle());
    }
}
