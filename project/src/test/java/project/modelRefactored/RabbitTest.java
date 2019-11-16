package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Either;
import io.atlassian.fugue.Pair;
import org.junit.jupiter.api.Test;
import org.pcollections.Empty;
import project.model.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public final class RabbitTest {

    @Test
    void constructor () {
        Coordinate coordinate = new Coordinate(0, 0);
        Rabbit rabbit = new Rabbit(coordinate);

        assertNotNull(rabbit, "should not be null");
        assertTrue(rabbit.coordinate.isLeft(), "should be a left" +
                " type");
    }

    // TODO: implement obstacle?

    @Test
    void testJumpRightOne () {
        // Slice setup
        // Initial
        // R M E
        // Result
        // E M R

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 2);

        Board board = new Board(1,3);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));

        // Perform jump
        Rabbit newRabbit = null;
        try {
            newRabbit = initialRabbit.jump(Direction.RIGHT,
                    board.getRowSlice(initialCoordinate.column)).left().get();
        } catch (InvalidMoveException e) {
            fail();
        }

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // Check the new rabbit coordinates
        assertNotNull(initialRabbit);
        assertNotEquals(initialRabbit, newRabbit, "the new rabbit should be " +
                "different to the old coordinate");
        Coordinate coordinate = newRabbit.coordinate.left().get();
        assertEquals(expectedJumpCoordinate, coordinate, "we should have " +
                "jumped to our expected coordinates");

    }

    @Test
    void testJumpRightTwo () {
        // Slice setup
        // Initial
        // R M M E
        // Result
        // E M M R

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 3);

        Board board = new Board(1,4);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));
        board = board.setItem(new Mushroom(new Coordinate(0 ,2)));

        // Perform jump
        Rabbit newRabbit = null;
        try {
            newRabbit = initialRabbit.jump(Direction.RIGHT,
                    board.getRowSlice(initialCoordinate.column)).left().get();
        } catch (InvalidMoveException e) {
            fail();
        }

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // Check the new rabbit coordinates
        assertNotNull(initialRabbit);
        assertNotEquals(initialRabbit, newRabbit, "the new rabbit should be " +
                "different to the old coordinate");
        Coordinate coordinate = newRabbit.coordinate.left().get();
        assertEquals(expectedJumpCoordinate, coordinate, "we should have " +
                "jumped to our expected coordinates");

    }

    @Test
    void testJumpRightIntoHole () {
        // Slice setup
        //Initial
        //    R M H
        //Result
        // -> E M H
        // Makes Hole an Obstacle after rabbit is in Hole

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 2);

        Hole hole = new Hole(new Coordinate(0,2), Optional.absent());

        Board board = new Board(1,3);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));
        board = board.setItem(hole);


        // TODO: jump should return a list of items to be set to the board
        // Perform jump
        Either<Rabbit, Hole> returnPair =
                null;
        try {
            returnPair = initialRabbit.jump(Direction.RIGHT,
            board.getRowSlice(initialCoordinate.column));
        } catch (InvalidMoveException e) {
            fail();
        }

        Hole newHole = returnPair.right().get();

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // The original hole should not have changed
        assertFalse(hole.containingItem.isPresent(), "the hole should still " +
                "be empty");

        // The original rabbit should not have changed
        assertEquals(initialRabbit.coordinate.left().get(), initialCoordinate,
                "the " +
                "original rabbit should not have changed");

        // The new returned hole should have the rabbit
        assertTrue(newHole.containingItem.isPresent(), "the hole should not " +
                "be" +
                " empty");

        assertTrue(newHole.containingItem.get() instanceof Rabbit, "rabbit " +
                "should be in the hole");

    }

    @Test
    void testJumpLeftOne () {
        // Slice setup
        // Initial
        // E M R
        // Result
        // R M E

        Coordinate initialCoordinate = new Coordinate(0, 2);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 0);

        Board board = new Board(1,3);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));

        // Perform jump
        Rabbit newRabbit = null;
        try {
            newRabbit = initialRabbit.jump(Direction.LEFT,
                    board.getRowSlice(initialCoordinate.column)).left();
        } catch (InvalidMoveException e) {
            fail();
        }

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // Check the new rabbit coordinates
        assertNotNull(initialRabbit);
        assertNotEquals(initialRabbit, newRabbit, "the new rabbit should be " +
                "different to the old coordinate");
        Coordinate coordinate = newRabbit.coordinate.left().get();
        assertEquals(expectedJumpCoordinate, coordinate, "we should have " +
                "jumped to our expected coordinates");

    }

    @Test
    void testJumpLeftTwo () {
        // Slice setup
        // Initial
        // E M M R
        // Result
        // R M M E

        Coordinate initialCoordinate = new Coordinate(0, 3);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 0);

        Board board = new Board(1,4);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));
        board = board.setItem(new Mushroom(new Coordinate(0 ,2)));

        // Perform jump
        Rabbit newRabbit = null;
        try {
            newRabbit = initialRabbit.jump(Direction.LEFT,
                    board.getRowSlice(initialCoordinate.column)).left();
        } catch (InvalidMoveException e) {
            fail();
        }

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // Check the new rabbit coordinates
        assertNotNull(initialRabbit);
        assertNotEquals(initialRabbit, newRabbit, "the new rabbit should be " +
                "different to the old coordinate");
        Coordinate coordinate = newRabbit.coordinate.left().get();
        assertEquals(expectedJumpCoordinate, coordinate, "we should have " +
                "jumped to our expected coordinates");

    }

    @Test
    void testJumpLeftIntoHole () {
        // Slice setup
        //Initial
        //  H M R
        //Result
        //  H M E <-

        Coordinate initialCoordinate = new Coordinate(2, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 0);

        Hole hole = new Hole(new Coordinate(0,0), Optional.absent());

        Board board = new Board(1,3);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));
        board = board.setItem(hole);

        // Perform jump
        Pair<Rabbit, Optional<Hole>> returnPair =
                null;
        try {
            returnPair = initialRabbit.jump(Direction.LEFT,
                    board.getRowSlice(initialCoordinate.column));
        } catch (InvalidMoveException e) {
            fail();
        }

        Rabbit newRabbit = returnPair.left();
        Hole newHole = returnPair.right().get();

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // The original hole should not have changed
        assertFalse(hole.containingItem.isPresent(), "the hole should still " +
                "be empty");

        // The original rabbit should not have changed
        assertEquals(initialRabbit.coordinate.left().get(), initialCoordinate,
                "the " +
                        "original rabbit should not have changed");

        // The new returned rabbit should be at the correct location
        assertEquals(newRabbit.coordinate.left().get(), expectedJumpCoordinate,
                "the rabbit should be at the expected coordinate");

        // The new returned hole should have the rabbit
        assertTrue(newHole.containingItem.isPresent(), "the hole should not " +
                "be" +
                " empty");
        assertEquals(newHole.containingItem.get(), newRabbit, "the hole " +
                "should contain " +
                "the new rabbit");
    }

    @Test
    void testJumpDownOne () {
        // Slice setup
        //Initial
        // R
        // M
        // E
        //Result
        // E
        // M
        // R

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(2, 0);

        Board board = new Board(3,1);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(1,0)));

        // Perform jump
        Rabbit newRabbit = null;
        try {
            newRabbit = initialRabbit.jump(Direction.DOWN,
                    board.getColumnSlice(initialCoordinate.column)).left();
        } catch (InvalidMoveException e) {
            fail();
        }

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // Check the new rabbit coordinates
        assertNotNull(initialRabbit);
        assertNotEquals(initialRabbit, newRabbit, "the new rabbit should be " +
                "different to the old coordinate");
        Coordinate coordinate = newRabbit.coordinate.left().get();
        assertEquals(expectedJumpCoordinate, coordinate, "we should have " +
                "jumped to our expected coordinates");

    }

    @Test
    void testJumpDownTwo () {
        // Slice setup
        //Initial
        // R
        // M
        // M
        // E
        //Result
        // E
        // M
        // M
        // R

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(3, 0);

        Board board = new Board(4,1);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(1 ,0)));
        board = board.setItem(new Mushroom(new Coordinate(2 ,0)));

        // Perform jump
        Rabbit newRabbit = null;
        try {
            newRabbit = initialRabbit.jump(Direction.DOWN,
                    board.getColumnSlice(initialCoordinate.column)).left();
        } catch (InvalidMoveException e) {
            fail();
        }

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // Check the new rabbit coordinates
        assertNotNull(initialRabbit);
        assertNotEquals(initialRabbit, newRabbit, "the new rabbit should be " +
                "different to the old coordinate");
        Coordinate coordinate = newRabbit.coordinate.left().get();
        assertEquals(expectedJumpCoordinate, coordinate, "we should have " +
                "jumped to our expected coordinates");

    }

    @Test
    void testJumpDownIntoHole () {
        // Slice setup
        //Initial
        //  R
        //  M
        //  H
        //Result
        //  E
        //  M
        //  H

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(2, 0);

        Hole hole = new Hole(new Coordinate(2,0), Optional.absent());

        Board board = new Board(3,1);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(1 ,0)));
        board = board.setItem(hole);

        // Perform jump
        Pair<Rabbit, Optional<Hole>> returnPair =
                null;
        try {
            returnPair = initialRabbit.jump(Direction.DOWN,
                    board.getColumnSlice(initialCoordinate.column));
        } catch (InvalidMoveException e) {
            fail();
        }

        Rabbit newRabbit = returnPair.left();
        Hole newHole = returnPair.right().get();

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // The original hole should not have changed
        assertFalse(hole.containingItem.isPresent(), "the hole should still " +
                "be empty");

        // The original rabbit should not have changed
        assertEquals(initialRabbit.coordinate.left().get(), initialCoordinate,
                "the " +
                        "original rabbit should not have changed");

        // The new returned rabbit should be at the correct location
        assertEquals(newRabbit.coordinate.left().get(), expectedJumpCoordinate,
                "the rabbit should be at the expected coordinate");

        // The new returned hole should have the rabbit
        assertTrue(newHole.containingItem.isPresent(), "the hole should not " +
                "be" +
                " empty");
        assertEquals(newHole.containingItem.get(), newRabbit, "the hole " +
                "should contain " +
                "the new rabbit");

    }

    @Test
    void testJumpUpOne () {
        // Slice setup
        //Initial
        // E
        // M
        // R
        //Result
        // R
        // M
        // E

        Coordinate initialCoordinate = new Coordinate(3, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 0);

        Board board = new Board(3,1);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(1,0)));

        // Perform jump
        Rabbit newRabbit = null;
        try {
            newRabbit = initialRabbit.jump(Direction.UP,
                    board.getColumnSlice(initialCoordinate.column)).left();
        } catch (InvalidMoveException e) {
            fail();
        }

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // Check the new rabbit coordinates
        assertNotNull(initialRabbit);
        assertNotEquals(initialRabbit, newRabbit, "the new rabbit should be " +
                "different to the old coordinate");
        Coordinate coordinate = newRabbit.coordinate.left().get();
        assertEquals(expectedJumpCoordinate, coordinate, "we should have " +
                "jumped to our expected coordinates");

    }

    @Test
    void testJumpUpTwo () {
        // Slice setup
        //Initial
        // E
        // M
        // M
        // R
        //Result
        // R
        // M
        // M
        // E

        Coordinate initialCoordinate = new Coordinate(3, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 0);

        Board board = new Board(4,1);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(1 ,0)));
        board = board.setItem(new Mushroom(new Coordinate(2 ,0)));

        // Perform jump
        Rabbit newRabbit = null;
        try {
            newRabbit = initialRabbit.jump(Direction.UP,
                    board.getColumnSlice(initialCoordinate.column)).left();
        } catch (InvalidMoveException e) {
            fail();
        }

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // Check the new rabbit coordinates
        assertNotNull(initialRabbit);
        assertNotEquals(initialRabbit, newRabbit, "the new rabbit should be " +
                "different to the old coordinate");
        Coordinate coordinate = newRabbit.coordinate.left().get();
        assertEquals(expectedJumpCoordinate, coordinate, "we should have " +
                "jumped to our expected coordinates");
    }

    @Test
    void testJumpUpIntoHole () {
        // Slice setup
        //Initial
        //  H
        //  M
        //  R
        //Result
        //  H
        //  M
        //  E

        Coordinate initialCoordinate = new Coordinate(2, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 0);

        Hole hole = new Hole(new Coordinate(0,0), Optional.absent());

        Board board = new Board(3,1);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(1 ,0)));
        board = board.setItem(hole);

        // Perform jump
        Pair<Rabbit, Optional<Hole>> returnPair =
                null;
        try {
            returnPair = initialRabbit.jump(Direction.UP,
                    board.getColumnSlice(initialCoordinate.column));
        } catch (InvalidMoveException e) {
            fail();
        }

        Rabbit newRabbit = returnPair.left();
        Hole newHole = returnPair.right().get();

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // The original hole should not have changed
        assertFalse(hole.containingItem.isPresent(), "the hole should still " +
                "be empty");

        // The original rabbit should not have changed
        assertEquals(initialRabbit.coordinate.left().get(), initialCoordinate,
                "the " +
                        "original rabbit should not have changed");

        // The new returned rabbit should be at the correct location
        assertEquals(newRabbit.coordinate.left().get(), expectedJumpCoordinate,
                "the rabbit should be at the expected coordinate");

        // The new returned hole should have the rabbit
        assertTrue(newHole.containingItem.isPresent(), "the hole should not " +
                "be" +
                " empty");
        assertEquals(newHole.containingItem.get(), newRabbit, "the hole " +
                "should contain " +
                "the new rabbit");
    }



}
