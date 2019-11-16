package project.modelRefactored;

import org.junit.jupiter.api.Test;
import project.model.Direction;

import com.google.common.base.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

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
            //TODO: Debug prints. Remove after finished jump out of hole method
            System.out.println(board.getItem(initialCoordinate).getClass());
            Hole testHole = (Hole) board.getItem(initialCoordinate);
            System.out.println(testHole.containingItem.get().getClass());

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
            //TODO: Debug prints. Remove after finished jump out of hole method
            System.out.println(e.getMessage());
            fail();
        }
    }
}
