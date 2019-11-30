package project.model;

import io.atlassian.fugue.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

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

        ContainerItem containerItem = new Hole(initialCoordinate, Optional.of(initialRabbit));

        board = board.setItem(containerItem);

        Board newBoard;
        try {
            logger.debug(board.getItem(initialCoordinate).getClass());
            ContainerItem testContainerItem = (ContainerItem) board.getItem(initialCoordinate);
            logger.debug(testContainerItem.containingItem.get().getClass());

            newBoard = board.jump(Direction.RIGHT, initialCoordinate);

            //NEW BOARD
            //0, 0 Hole
            assertTrue(newBoard.getItem(initialCoordinate) instanceof
                    ContainerItem, "item at 0, 0 should be a Hole");
            //0, 1 Mushroom
            assertTrue(newBoard.getItem(new Coordinate(0,1)) instanceof
                    Mushroom, "item at 0, 1 should be mushroom");
            //0, 2 new Rabbit
            assertTrue(newBoard.getItem(expectedJumpCoordinate) instanceof
                    Rabbit, "item at 0, 2 should be rabbit");

            //OLD BOARD SHOULDN'T CHANGE
            //0, 0 old Hole
            ContainerItem oldContainerItem = (ContainerItem) board.getItem(initialCoordinate);

            assertTrue(oldContainerItem instanceof
                    ContainerItem, "item at 0, 0 should be HoleWithRabbit");
            assertTrue(oldContainerItem.containingItem.get() instanceof Rabbit,
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

        ContainerItem destinationContainerItem = new Hole(expectedJumpCoordinate,
                Optional.absent());

        board = board.setItem(initialRabbit);
        board = board.setItem(destinationContainerItem);

        Board newBoard;
        try {
            newBoard = board.jump(Direction.RIGHT, initialCoordinate);

            ContainerItem newContainerItem = (ContainerItem) newBoard.getItem(expectedJumpCoordinate);

            //new board should be updated
            assertTrue(newContainerItem instanceof ContainerItem, "0, 2 should be a " +
                    "hole");
            assertTrue(newContainerItem.containingItem.get() instanceof Rabbit,
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

        ContainerItem originalContainerItem = new Hole(initialCoordinate,
                Optional.of(initialRabbit));
        ContainerItem destinationContainerItem = new Hole(expectedJumpCoordinate,
                Optional.absent());

        board = board.setItem(originalContainerItem);
        board = board.setItem(destinationContainerItem);

        Board newBoard;
        try {
            logger.debug(board.getItem(initialCoordinate).getClass());
            ContainerItem testContainerItem = (ContainerItem) board.getItem(initialCoordinate);
            logger.debug(testContainerItem.containingItem.get().getClass());

            newBoard = board.jump(Direction.RIGHT, initialCoordinate);

            ContainerItem newContainerItem = (ContainerItem) newBoard.getItem(expectedJumpCoordinate);

            //new board should be updated
            assertTrue(newContainerItem instanceof ContainerItem, "0, 2 should be a " +
                    "hole");
            assertTrue(newContainerItem.containingItem.get() instanceof Rabbit,
                    "0, 2 " +
                    "should have a Rabbit");

            ContainerItem oldContainerItem = (ContainerItem) newBoard.getItem(initialCoordinate);
            //old hole should still be there
            assertTrue(oldContainerItem instanceof ContainerItem, "0, 0 should be a " +
                    "hole");
            assertTrue(!oldContainerItem.containingItem.isPresent(), "0, 0 " +
                    "should be empty Hole");

            //old board should remain same
            oldContainerItem = (ContainerItem) board.getItem(initialCoordinate);
            assertTrue(oldContainerItem.containingItem.get() instanceof Rabbit,
                    "0, 0 should be Hole containing Rabbit");

            newContainerItem = (ContainerItem) board.getItem(expectedJumpCoordinate);
            assertTrue(!newContainerItem.containingItem.isPresent(),
                    "0, 2 should be Hole containing Rabbit");

        } catch (Exception e) {
            logger.debug(e.getMessage());
            fail();
        }
    }

    @Test
    void testXMLEmptyBoard() {
        Board board = new Board(0, 0);

        assertEquals("<Board></Board>", board.toXML(),
                "Should be empty board XML.");
    }

    @Test
    void testXMLBoardWithMushroom() {
        Board board = new Board(1, 1);
        Mushroom mushroom = new Mushroom(0, 0);

        board = board.setItem(mushroom);

        assertEquals("<Board><Mushroom><Coordinate row=0 " +
                "column=0/></Mushroom></Board>", board.toXML(), "XML " +
                "representations should be equal");
    }

    @Test
    void testXMLBoardWithRabbit() {
        Board board = new Board(1, 1);
        Rabbit rabbit = new Rabbit(0, 0);

        board = board.setItem(rabbit);

        assertEquals("<Board><Rabbit><Coordinate row=0 " +
                "column=0/></Rabbit></Board>", board.toXML(), "XML " +
                "representations should be equal");
    }

    @Test
    void testXMLBoardWithFox() {
        Board board = new Board(1, 2);
        Coordinate head = new Coordinate(0, 0);
        Coordinate tail = new Coordinate(0, 1);

        Pair<Coordinate, Coordinate> coordinates = Pair.pair(head, tail);

        Fox fox = new Fox(coordinates);
        BoardItem item = fox;

        board = board.setItem(item);

        assertEquals("<Board><Fox><CoordinatePair headRow=0 " +
                "headColumn=0 tailRow=0 tailColumn=1/></Fox></Board>",
                board.toXML(), "XML representations should be equal");
    }
}
