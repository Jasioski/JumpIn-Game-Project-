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
    void testRabbitNoMoves() {
        DefaultBoard board = new DefaultBoard();
        BoardItem item = board.getItem(2, 0);
        List<Move> generatedMoves;

        Solver solver = new Solver();
        generatedMoves = solver.generateMoves(board, item);

        assertEquals(generatedMoves.size(), 0, "The list should be empty no legal moves");
    }

    @Test
    void testRabbitMovesDown() {
        DefaultBoard board = new DefaultBoard();
        BoardItem item = board.getItem(0, 3);
        List<Move> generatedMoves;
        List<Move> correctMoves = new ArrayList<>();

        Move correctMove = new Move(item, Direction.DOWN);
        correctMoves.add(correctMove);

        Solver solver = new Solver();
        generatedMoves = solver.generateMoves(board, item);

        assertEquals(generatedMoves.size(), correctMoves.size(), "The lists should have same size");

        for (int i = 0; i < generatedMoves.size(); i++) {
            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
        }
    }

    @Test
    void testRabbitMovesUp() {
        DefaultBoard board = new DefaultBoard();
        BoardItem item = board.getItem(4, 2);
        List<Move> generatedMoves;
        List<Move> correctMoves = new ArrayList<>();

        Move correctMove = new Move(item, Direction.UP);
        correctMoves.add(correctMove);

        Solver solver = new Solver();
        generatedMoves = solver.generateMoves(board, item);

        assertEquals(generatedMoves.size(), correctMoves.size(), "The lists should have same size");

        for (int i = 0; i < generatedMoves.size(); i++) {
            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
        }
    }
}