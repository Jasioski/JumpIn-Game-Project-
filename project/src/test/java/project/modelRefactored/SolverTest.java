package project.modelRefactored;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import project.model.*;
import project.model.exceptions.JumpFailedNoObstacleException;
import project.model.exceptions.JumpFailedOutOfBoundsException;
import project.solverRefactored.Move;
import project.solverRefactored.Solver;
import project.tui.ItemUIRepresentation;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void testRabbitNoMoves() {
        //   0 1 2 3 4
//      0    E E M E E
//      1    E E M E E
//      2    M M R M M
//      3    E E M E E
//      4    E E M E E

        Board board = new Board(5,5);
        board = board.setItem(new Mushroom(0,2));
        board = board.setItem(new Mushroom(1,2));
        board = board.setItem(new Mushroom(3,2));
        board = board.setItem(new Mushroom(4,2));

        board = board.setItem(new Mushroom(2,0));
        board = board.setItem(new Mushroom(2,1));
        board = board.setItem(new Mushroom(2,3));
        board = board.setItem(new Mushroom(2,4));

        Coordinate rabbitCoordinate = new Coordinate(2,2);
        Rabbit rabbit = new Rabbit(rabbitCoordinate);

        board = board.setItem(rabbit);

        List<Move> generatedMoves;

        Solver solver = new Solver();
        generatedMoves = solver.generateMoves(board, rabbitCoordinate);

        assertEquals(generatedMoves.size(), 0, "The list should be empty no legal moves");
    }

    @Test
    void testRabbitOneMoveRight() {
        //   0 1 2 3 4
//      0    E E M E E
//      1    E E M E E
//      2    M M R M E
//      3    E E M E E
//      4    E E M E E

        Board board = new Board(5,5);
        board = board.setItem(new Mushroom(0,2));
        board = board.setItem(new Mushroom(1,2));
        board = board.setItem(new Mushroom(3,2));
        board = board.setItem(new Mushroom(4,2));

        board = board.setItem(new Mushroom(2,0));
        board = board.setItem(new Mushroom(2,1));
        board = board.setItem(new Mushroom(2,3));

        Coordinate rabbitCoordinate = new Coordinate(2,2);
        Rabbit rabbit = new Rabbit(rabbitCoordinate);

        board = board.setItem(rabbit);

        Coordinate expectedMove = new Coordinate(2, 4);

        Solver solver = new Solver();

        List<Move> generatedMoves =
                solver.generateMoves(board, rabbitCoordinate);

        assertEquals(generatedMoves.size(), 1, "there should only be one move");

        Move move = generatedMoves.get(0);

        assertEquals(rabbit, move.item, "should be trying to move the rabbit");
        assertEquals(Direction.RIGHT, move.direction, "move right");
        assertEquals(rabbitCoordinate, move.initial);
        assertEquals(expectedMove, move.ending);

    }


    @Test
    void testRabbitOneMoveLeft() {
        //   0 1 2 3 4
//      0    E E M E E
//      1    E E M E E
//      2    E M R M M
//      3    E E M E E
//      4    E E M E E

        Board board = new Board(5,5);
        board = board.setItem(new Mushroom(0,2));
        board = board.setItem(new Mushroom(1,2));
        board = board.setItem(new Mushroom(3,2));
        board = board.setItem(new Mushroom(4,2));

        board = board.setItem(new Mushroom(2,1));
        board = board.setItem(new Mushroom(2,3));
        board = board.setItem(new Mushroom(2,4));

        Coordinate rabbitCoordinate = new Coordinate(2,2);
        Rabbit rabbit = new Rabbit(rabbitCoordinate);

        board = board.setItem(rabbit);

        Coordinate expectedMove = new Coordinate(2, 0);

        Solver solver = new Solver();

        List<Move> generatedMoves =
                solver.generateMoves(board, rabbitCoordinate);

        assertEquals(generatedMoves.size(), 1, "The list have one move");

        Move move = generatedMoves.get(0);

        assertEquals(rabbit, move.item, "should be trying to move the rabbit");
        assertEquals(Direction.LEFT, move.direction, "move left");
        assertEquals(rabbitCoordinate, move.initial);
        assertEquals(expectedMove, move.ending);

    }


    // TODO: make this test pass
    @Test
    void testRabbitOneMoveLeftOutOfBounds() {
        //   0 1 2 3 4
//      0    E E M E E
//      1    E E M E E
//      2    R M M M M
//      3    E E M E E
//      4    E E M E E

        Board board = new Board(5,5);
        board = board.setItem(new Mushroom(0,2));
        board = board.setItem(new Mushroom(1,2));
        board = board.setItem(new Mushroom(3,2));
        board = board.setItem(new Mushroom(4,2));

        board = board.setItem(new Mushroom(2,1));
        board = board.setItem(new Mushroom(2,2));
        board = board.setItem(new Mushroom(2,3));
        board = board.setItem(new Mushroom(2,4));

        Coordinate rabbitCoordinate = new Coordinate(2,0);
        Rabbit rabbit = new Rabbit(rabbitCoordinate);

        board = board.setItem(rabbit);

        Solver solver = new Solver();

        List<Move> generatedMoves =
                solver.generateMoves(board, rabbitCoordinate);

        assertEquals(generatedMoves.size(), 0, "The list should be empty no legal moves");
        Move move = generatedMoves.get(0);

    }


//
//    // TODO: refactor tests into unit and end to end tests
//
//    @Test
//    void testRabbitMovesDown() {
//        // TODO: add comments explaining this test. the test is really good
//        //       but it took me a while to figure out what
//        DefaultBoard board = new DefaultBoard();
//        System.out.println(board);
//        BoardItem item = board.getItem(0, 3);
//        List<Move> generatedMoves;
//        List<Move> correctMoves = new ArrayList<>();
//
//        Move correctMove = new Move(item, Direction.DOWN);
//        correctMoves.add(correctMove);
//
//        Solver solver = new Solver();
//        generatedMoves = solver.generateMoves(board, item);
//
//        assertEquals(generatedMoves.size(), correctMoves.size(), "The lists should have same size");
//
//        for (int i = 0; i < generatedMoves.size(); i++) {
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
//    }
//
//    @Test
//    void testRabbitMovesUp() {
//        DefaultBoard board = new DefaultBoard();
//        BoardItem item = board.getItem(4, 2);
//        List<Move> generatedMoves;
//        List<Move> correctMoves = new ArrayList<>();
//
//        Move correctMove = new Move(item, Direction.UP);
//        correctMoves.add(correctMove);
//
//        Solver solver = new Solver();
//        generatedMoves = solver.generateMoves(board, item);
//
//        assertEquals(generatedMoves.size(), correctMoves.size(), "The lists should have same size");
//
//        for (int i = 0; i < generatedMoves.size(); i++) {
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
//    }
//
//    @Test
//    void testRabbitMovesRight() {
//        DefaultBoard board = new DefaultBoard();
//        BoardItem item = board.getItem(2, 0);
//        try {
//            board.slide(Direction.DOWN, 1, new Coordinate(1, 1));
//        } catch(Exception e) {
//            System.out.println("SLIDE IS BROKEN NOT THIS TESTS RESPONSIBILITY");
//            fail();
//        }
//
//        List<Move> generatedMoves;
//        List<Move> correctMoves = new ArrayList<>();
//
//        Move correctMove = new Move(item, Direction.RIGHT);
//        correctMoves.add(correctMove);
//
//        Solver solver = new Solver();
//        generatedMoves = solver.generateMoves(board, item);
//
//        assertEquals(generatedMoves.size(), correctMoves.size(), "The lists should have same size");
//
//        for (int i = 0; i < generatedMoves.size(); i++) {
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
//    }
//
//    @Test
//    void testRabbitMovesLeft() {
//        DefaultBoard board = new DefaultBoard();
//        BoardItem item = board.getItem(0, 4);
//
//        List<Move> generatedMoves;
//        List<Move> correctMoves = new ArrayList<>();
//
//        Move correctMove = new Move(item, Direction.LEFT);
//        correctMoves.add(correctMove);
//
//        Solver solver = new Solver();
//        generatedMoves = solver.generateMoves(board, item);
//
//        assertEquals(generatedMoves.size(), correctMoves.size(), "The lists should have same size");
//
//        for (int i = 0; i < generatedMoves.size(); i++) {
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
//    }
}