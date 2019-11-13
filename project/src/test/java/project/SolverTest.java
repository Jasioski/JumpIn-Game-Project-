package project;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;
import project.model.*;
import project.model.exceptions.JumpFailedNoObstacleException;
import project.model.exceptions.JumpFailedOutOfBoundsException;
import project.solver.Move;
import project.solver.Solver;
import project.tui.ItemUIRepresentation;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    @Test
    void testRabbitMovesDown() {
        DefaultBoard board = new DefaultBoard();
        BoardItem item = board.getItem(0, 3);
        List<Move> generatedMoves = new ArrayList<>();
        List<Move> correctMoves = new ArrayList<>();

        Move correctMove = new Move(item, Direction.DOWN);
        correctMoves.add(correctMove);

        Solver solver = new Solver();
        generatedMoves = solver.generateMoves(board, item);

        assertEquals(generatedMoves.size(), correctMoves.size(), "The lists should have same size");
        assertEquals(generatedMoves.get(0).direction, correctMoves.get(0).direction, "The rabbit should have one legal move: Jump Down");
        assertEquals(generatedMoves.get(0).item, correctMoves.get(0).item, "The rabbit should have one legal move: Jump Down");

//        for (int i = 0; i < generatedMoves.size(); i++) {
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
        //todo: fix the test code below to make testing cleaner
//        if (!(new HashSet<>(generatedMoves).equals(new HashSet<>(correctMoves)))) {
//            System.out.println("FAIL!");
//            fail();
//        }
        //assertEquals(generatedMoves, correctMoves, "The rabbit should have one legal move: Jump Down");

    }

    //todo: add @Test here
    void testRabbitMovesUp() {
        DefaultBoard board = new DefaultBoard();
        BoardItem item = board.getItem(4, 2);
        List<Move> generatedMoves = new ArrayList<>();
        List<Move> correctMoves = new ArrayList<>();

        Move correctMove = new Move(item, Direction.UP);
        correctMoves.add(correctMove);

        Solver solver = new Solver();
        generatedMoves = solver.generateMoves(board, item);

        assertEquals(generatedMoves.size(), correctMoves.size(), "The lists should have same size");
        assertEquals(generatedMoves.get(0).direction, correctMoves.get(0).direction, "The rabbit should have one legal move: Jump Down");
        assertEquals(generatedMoves.get(0).item, correctMoves.get(0).item, "The rabbit should have one legal move: Jump Down");

        //todo: fix the test code below to make testing cleaner
//        if (!(new HashSet<>(generatedMoves).equals(new HashSet<>(correctMoves)))) {
//            System.out.println("FAIL!");
//            fail();
//        }
        //assertEquals(generatedMoves, correctMoves, "The rabbit should have one legal move: Jump Down");

    }
}