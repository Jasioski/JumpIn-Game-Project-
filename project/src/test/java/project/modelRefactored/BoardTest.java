package project.modelRefactored;

import org.junit.jupiter.api.Test;
import project.model.Direction;

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
        assertTrue(newBoard.getItem(new Coordinate(0,2)) instanceof
                EmptyBoardItem, "item at 0, 2 should be rabbit");

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
//
//        //Return type of Jump. Rabbit not null
//        assertNotNull(newRabbit, "rabbit should not be null");

        } catch (Exception e) {
            fail();
        }
    }
}
