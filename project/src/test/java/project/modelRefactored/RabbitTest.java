package project.modelRefactored;

import com.google.common.base.Optional;
import io.atlassian.fugue.Either;
import org.junit.jupiter.api.Test;
import project.model.Direction;

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
        // R M E

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
        // R M M E

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


    // TODO: move to integration tests?

    @Test
    void testJumpRightIntoHole () {
        // Slice setup
        //    R M H
        // -> E M H

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 2);

        ContainerItem containerItem = new Hole(new Coordinate(0,2), Optional.absent());

        Board board = new Board(1,3);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));
        board = board.setItem(containerItem);


        // TODO: jump should return a list of items to be set to the board
        // Perform jump
        Either<Rabbit, ContainerItem> returnPair =
                null;
        try {
            returnPair = initialRabbit.jump(Direction.RIGHT,
            board.getRowSlice(initialCoordinate.column));
        } catch (InvalidMoveException e) {
            fail();
        }

        ContainerItem newContainerItem = returnPair.right().get();

        // Make sure the initial rabbit has not been mutated
        Coordinate initialRabbitCoordinate = initialRabbit.coordinate.left().get();
        assertEquals(initialCoordinate, initialRabbitCoordinate, "the initial" +
                " rabbit should not have been mutated" );

        // The original hole should not have changed
        assertFalse(containerItem.containingItem.isPresent(), "the hole should still " +
                "be empty");

        // The original rabbit should not have changed
        assertEquals(initialRabbit.coordinate.left().get(), initialCoordinate,
                "the " +
                "original rabbit should not have changed");

        // The new returned hole should have the rabbit
        assertTrue(newContainerItem.containingItem.isPresent(), "the hole should not " +
                "be" +
                " empty");

        assertTrue(newContainerItem.containingItem.get() instanceof Rabbit, "rabbit " +
                "should be in the hole");

    }
}
