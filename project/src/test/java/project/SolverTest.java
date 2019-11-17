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

//    @Test
//    void testRabbitNoMoves() {
//        DefaultBoard board = new DefaultBoard();
//        BoardItem item = board.getItem(2, 0);
//        List<Move> generatedMoves;
//
//        Solver solver = new Solver();
//        generatedMoves = solver.generateMoves(board, item);
//
//        assertEquals(generatedMoves.size(), 0, "The list should be empty no legal moves");
//    }
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
//    @Test
//    void testFoxSlideRight(){
//        DefaultBoard board = new DefaultBoard();
//        BoardItem item = board.getItem(3,3);
//
//        try{
//            board.slide(Direction.RIGHT,1, new Coordinate(4,4));
//        } catch(Exception e){
//            fail();
//        }
//
//        List<Move> generatedMoves;
//        List<Move> correctMoves = new ArrayList<>();
//
//        Move correctMove = new Move(item, Direction.RIGHT);
//        correctMoves.add(correctMove);
//
//        Solver s = new Solver();
//        generatedMoves = s.generateMoves(board,item);
//
//        assertEquals(generatedMoves.size(), corrrectMoves.size(), "The lists should have the same size");
//
//        for(int i = 0; i < generatedMoves.size(); i++){
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
//    }

//    @Test
//    void testFoxSlideLeft(){
//        DefaultBoard board = new DefaultBoard();
//        BoardItem item = board.getItem(3,3);
//
//        try{
//            board.slide(Direction.LEFT,1, new Coordinate(4,2));
//        } catch(Exception e){
//            fail();
//        }
//
//        List<Move> generatedMoves;
//        List<Move> correctMoves = new ArrayList<>();
//
//        Move correctMove = new Move(item, Direction.LEFT);
//        correctMoves.add(correctMove);
//
//        Solver s = new Solver();
//        generatedMoves = s.generateMoves(board,item);
//
//        assertEquals(generatedMoves.size(), corrrectMoves.size(), "The lists should have the same size");
//
//        for(int i = 0; i < generatedMoves.size(); i++){
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
//    }
//          @Test
//        void testFoxSlideDown(){
//        DefaultBoard board = new DefaultBoard();
//        BoardItem item = board.getItem(1,1);
//
//        try{
//            board.slide(Direction.DOWN,1, new Coordinate(2,1));
//        } catch(Exception e){
//            fail();
//        }
//
//        List<Move> generatedMoves;
//        List<Move> correctMoves = new ArrayList<>();
//
//        Move correctMove = new Move(item, Direction.DOWN);
//        correctMoves.add(correctMove);
//
//        Solver s = new Solver();
//        generatedMoves = s.generateMoves(board,item);
//
//        assertEquals(generatedMoves.size(), corrrectMoves.size(), "The lists should have the same size");
//
//        for(int i = 0; i < generatedMoves.size(); i++){
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
//    }

//        @Test
//        void testFoxSlideUp(){
//        DefaultBoard board = new DefaultBoard();
//        BoardItem item = board.getItem(2,1);
//
//        try{
//            board.slide(Direction.UP,1, new Coordinate(1,1));
//        } catch(Exception e){
//            fail();
//        }
//
//        List<Move> generatedMoves;
//        List<Move> correctMoves = new ArrayList<>();
//
//        Move correctMove = new Move(item, Direction.UP);
//        correctMoves.add(correctMove);
//
//        Solver s = new Solver();
//        generatedMoves = s.generateMoves(board,item);
//
//        assertEquals(generatedMoves.size(), corrrectMoves.size(), "The lists should have the same size");
//
//        for(int i = 0; i < generatedMoves.size(); i++){
//            assertTrue(generatedMoves.get(i).equals(correctMoves.get(i)));
//        }
//    }



}