package project.modelRefactored;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import project.model.Direction;

import com.google.common.base.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private static Logger logger = LogManager.getLogger(Board.class);

    @Test
    void testJumpRightOne() {
        // Slice setup
        // R M E

        //Expected
        //E M R

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 2);

        Board board = new Board(1,3);
        board = board.setItem(initialRabbit);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));

        Board newBoard;
        try {
            newBoard = board.jump(Direction.RIGHT, new Coordinate(0,0));


        //NEW BOARD
        //0, 0 Empty
        assertTrue(newBoard.getItem(new Coordinate(0,0)) instanceof
                EmptyBoardItem, "item at 0, 0 should be empty");
        //0, 1 Mushroom
        assertTrue(newBoard.getItem(new Coordinate(0,1)) instanceof
            Mushroom, "item at 0, 1 should be mushroom");
        //0, 2 new Rabbit
        assertTrue(newBoard.getItem(expectedJumpCoordinate) instanceof
                Rabbit, "item at 0, 2 should be rabbit");

        //OLD BOARD SHOULDN'T CHANGE
        //0, 0 old Rabbit
        assertTrue(board.getItem(new Coordinate(0,0)) instanceof
                Rabbit, "item at 0, 0 should be rabbit");
        // 0, 1 Mushroom
        assertTrue(board.getItem(new Coordinate(0,1)) instanceof
                Mushroom, "item at 0, 0 should be mushroom");
        // 0, 2 Empty
        assertTrue(board.getItem(new Coordinate(0,2)) instanceof
                EmptyBoardItem, "item at 0, 0 should be empty");

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testJumpRightOutOfHole() {
        // Slice setup
        // HR M E

        //Expected
        //H M R

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 2);

        Board board = new Board(1,3);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));

        Hole hole = new Hole(initialCoordinate, Optional.of(initialRabbit));

        board = board.setItem(hole);

        Board newBoard;
        try {
            logger.debug(board.getItem(initialCoordinate).getClass());
            Hole testHole = (Hole) board.getItem(initialCoordinate);
            logger.debug(testHole.containingItem.get().getClass());

            newBoard = board.jump(Direction.RIGHT, initialCoordinate);

            //NEW BOARD
            //0, 0 Hole
            assertTrue(newBoard.getItem(initialCoordinate) instanceof
                    Hole, "item at 0, 0 should be a Hole");
            //0, 1 Mushroom
            assertTrue(newBoard.getItem(new Coordinate(0,1)) instanceof
                    Mushroom, "item at 0, 1 should be mushroom");
            //0, 2 new Rabbit
            assertTrue(newBoard.getItem(expectedJumpCoordinate) instanceof
                    Rabbit, "item at 0, 2 should be rabbit");

            //OLD BOARD SHOULDN'T CHANGE
            //0, 0 old Hole
            Hole oldHole = (Hole) board.getItem(initialCoordinate);

            assertTrue(oldHole instanceof
                    Hole, "item at 0, 0 should be HoleWithRabbit");
            assertTrue(oldHole.containingItem.get() instanceof Rabbit,
                    "item inside old hole should be a Rabbit");
            // 0, 1 Mushroom
            assertTrue(board.getItem(new Coordinate(0,1)) instanceof
                    Mushroom, "item at 0, 0 should be mushroom");
            // 0, 2 Empty
            assertTrue(board.getItem(new Coordinate(0,2)) instanceof
                    EmptyBoardItem, "item at 0, 0 should be empty");

        } catch (Exception e) {
            logger.debug(e.getMessage());
            fail();
        }
    }

    @Test
    void testJumpIntoHole() {
        // Slice setup
        // R M H

        //Expected
        //E M HR

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 2);

        Board board = new Board(1,3);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));

        Hole destinationHole = new Hole(expectedJumpCoordinate,
                Optional.absent());

        board = board.setItem(initialRabbit);
        board = board.setItem(destinationHole);

        Board newBoard;
        try {
            newBoard = board.jump(Direction.RIGHT, initialCoordinate);

            Hole newHole = (Hole) newBoard.getItem(expectedJumpCoordinate);

            //new board should be updated
            assertTrue(newHole instanceof Hole, "0, 2 should be a " +
                    "hole");
            assertTrue(newHole.containingItem.get() instanceof Rabbit,
                    "0, 2 " +
                            "should have a Rabbit");
            assertTrue(newBoard.getItem(initialCoordinate) instanceof
                    EmptyBoardItem, "0, 0 should now be empty");

        } catch (Exception e) {
            logger.debug(e.getMessage());
            fail();
        }
    }

    @Test
    void testJumpOutOfHoleIntoAnother() {
        // Slice setup
        // HR M H

        //Expected
        //H M HR

        Coordinate initialCoordinate = new Coordinate(0, 0);
        Rabbit initialRabbit = new Rabbit(initialCoordinate);
        Coordinate expectedJumpCoordinate = new Coordinate(0, 2);

        Board board = new Board(1,3);
        board = board.setItem(new Mushroom(new Coordinate(0 ,1)));

        Hole originalHole = new Hole(initialCoordinate,
                Optional.of(initialRabbit));
        Hole destinationHole = new Hole(expectedJumpCoordinate,
                Optional.absent());

        board = board.setItem(originalHole);
        board = board.setItem(destinationHole);

        Board newBoard;
        try {
            logger.debug(board.getItem(initialCoordinate).getClass());
            Hole testHole = (Hole) board.getItem(initialCoordinate);
            logger.debug(testHole.containingItem.get().getClass());

            newBoard = board.jump(Direction.RIGHT, initialCoordinate);

            Hole newHole = (Hole) newBoard.getItem(expectedJumpCoordinate);

            //new board should be updated
            assertTrue(newHole instanceof Hole, "0, 2 should be a " +
                    "hole");
            assertTrue(newHole.containingItem.get() instanceof Rabbit,
                    "0, 2 " +
                    "should have a Rabbit");

            Hole oldHole = (Hole) newBoard.getItem(initialCoordinate);
            //old hole should still be there
            assertTrue(oldHole instanceof Hole, "0, 0 should be a " +
                    "hole");
            assertTrue(!oldHole.containingItem.isPresent(), "0, 0 " +
                    "should be empty Hole");

            //old board should remain same
            oldHole = (Hole) board.getItem(initialCoordinate);
            assertTrue(oldHole.containingItem.get() instanceof Rabbit,
                    "0, 0 should be Hole containing Rabbit");

            newHole = (Hole) board.getItem(expectedJumpCoordinate);
            assertTrue(!newHole.containingItem.isPresent(),
                    "0, 2 should be Hole containing Rabbit");

        } catch (Exception e) {
            logger.debug(e.getMessage());
            fail();
        }
    }
}
