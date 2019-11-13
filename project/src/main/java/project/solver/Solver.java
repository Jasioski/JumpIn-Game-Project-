package project.solver;

import project.DefaultBoard;
import project.model.*;
import project.model.exceptions.HoleAlreadyHasRabbitException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Solver {

    Board board;

    public Solver (){
    }

    public List<Move> generateRabbitMoves(Board board, Rabbit item) {
        System.out.println("item wabbit");
        List<Move> moves =  new ArrayList<>();


        // solve this and gimme the moves
        // check up
        try { //todo: board.jump finished the jump. Need to implement an attempt jump so that it doesnt complete the jump on the board.
            board.jump(Direction.UP, item.getCoordinate());
            Move move = new Move(item, Direction.UP);
            moves.add(move);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // check down
        try { //todo: board.jump finished the jump. Need to implement an attempt jump so that it doesnt complete the jump on the board.
            board.jump(Direction.DOWN, item.getCoordinate());
            Move move = new Move(item, Direction.DOWN);
            moves.add(move);
        } catch(Exception e) {
            System.out.println(e.getMessage() + "TESTS");
        }

        // check right
        try { //todo: board.jump finished the jump. Need to implement an attempt jump so that it doesnt complete the jump on the board.
            board.jump(Direction.RIGHT, item.getCoordinate());
            Move move = new Move(item, Direction.RIGHT);
            moves.add(move);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // check left
        try { //todo: board.jump finished the jump. Need to implement an attempt jump so that it doesnt complete the jump on the board.
            board.jump(Direction.LEFT, item.getCoordinate());
            Move move = new Move(item, Direction.LEFT);
            moves.add(move);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return moves;
    }

    public List<Move> generateFoxMoves(Board board, Fox item) {
        List<Move> moves = new ArrayList<>();

        //check left for head
        for (int column = item.getHead().column - 1; column >= 0; column--) {
            try { //todo: board.slide finished the slide. Need to implement an attempt slide so that it doesnt complete the slide on the board.
                board.slide(Direction.LEFT, item.getHead().column - column, item.getHead());
                Move move = new Move(item, Direction.LEFT, item.getHead(), new Coordinate(item.getHead().row, item.getHead().column - column));
                moves.add(move);
            } catch (Exception e) {
                break;
            }
        }

        //check right for head
        for (int column = item.getHead().column + 1; column < board.getColumns(); column++) {
            try { //todo: board.slide finished the slide. Need to implement an attempt slide so that it doesnt complete the slide on the board.
                board.slide(Direction.RIGHT, column - item.getHead().column, item.getHead());
                Move move = new Move(item, Direction.RIGHT, item.getHead(), new Coordinate(item.getHead().row, column - item.getHead().column));
                moves.add(move);
            } catch (Exception e) {
                break;
            }
        }

        //check up for head
        for (int row = item.getHead().row - 1; row > 0; row--) {
            try { //todo: board.slide finished the slide. Need to implement an attempt slide so that it doesnt complete the slide on the board.
                board.slide(Direction.UP, item.getHead().row - row, item.getHead());
                Move move = new Move(item, Direction.UP, item.getHead(), new Coordinate(item.getHead().row - row, item.getHead().column));
                moves.add(move);
            } catch (Exception e) {
                break;
            }
        }

        //check down for head
        for (int row = item.getHead().row + 1; row < board.getRows(); row++) {
            try { //todo: board.slide finished the slide. Need to implement an attempt slide so that it doesnt complete the slide on the board.
                board.slide(Direction.DOWN, row - item.getHead().row, item.getHead());
                Move move = new Move(item, Direction.DOWN, item.getHead(), new Coordinate(row - item.getHead().row, item.getHead().column));
                moves.add(move);
            } catch (Exception e) {
                break;
            }
        }
        return moves;
    }

    public List<Move> generateMoves(Board board, BoardItem item) {
        List<Move> moves =  new ArrayList<>();
        if (item instanceof ElevatedBoardItem) {

            //todo: separate this (Optional.empty to ~Optional.Rabbit~) to check if its a fox as well.
            if (!(((ElevatedBoardItem) item).containsItem().equals(Optional.empty()))) {
                System.out.println("It's a Rabbit");
               //moves = this.generateRabbitMoves(((ElevatedBoardItem) item).containsItem()); //todo: figure out how to cast this to a rabbit
            }
            System.out.println(((ElevatedBoardItem) item).containsItem());
        }
        System.out.println(item.getClass());

        if (item instanceof Rabbit) {
            System.out.println("It's a Rabbit");
            moves = this.generateRabbitMoves(board, (Rabbit) item);
        }

        if (item instanceof Fox) {
            System.out.println("It's a Fox");
            moves = this.generateFoxMoves(board, (Fox) item);
        }

        return moves;
    }


    public static void main(String[] args) {
        Solver solver = new Solver();
        Board board = new DefaultBoard();

        BoardItem item = board.getItem(4,2);

        List<Move> moves = solver.generateMoves(board, item);
        for (Move move : moves) {
            System.out.println(item + " : " + move.direction);
        }
        System.out.println(moves.size());
    }
}

