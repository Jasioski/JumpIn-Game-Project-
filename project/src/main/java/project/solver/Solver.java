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
        try {
            board.jump(Direction.UP, item.getCoordinate());
            Move move = new Move(item, Direction.UP);
            moves.add(move);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // check down
        try {
            board.jump(Direction.DOWN, item.getCoordinate());
            Move move = new Move(item, Direction.DOWN);
            moves.add(move);
        } catch(Exception e) {
            System.out.println(e.getMessage() + "TESTS");
        }

        // check right
        try {
            board.jump(Direction.RIGHT, item.getCoordinate());
            Move move = new Move(item, Direction.RIGHT);
            moves.add(move);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // check left
        try {
            board.jump(Direction.LEFT, item.getCoordinate());
            Move move = new Move(item, Direction.LEFT);
            moves.add(move);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(moves.size());
        return moves;
    }

    public List<Move> generateMoves(Board board, BoardItem item) {
        List<Move> moves =  new ArrayList<>();
        if (item instanceof ElevatedBoardItem) {

            //todo: separate this (Optional.empty to ~Optional.Rabbit~) to check if its a fox as well.
            if (!(((ElevatedBoardItem) item).containsItem().equals(Optional.empty()))) {
                System.out.println("It's a Rabbit");
               // this.generateRabbitMoves(((ElevatedBoardItem) item).containsItem()); //todo: figure out how to cast this to a rabbit
            }
            System.out.println(((ElevatedBoardItem) item).containsItem());
        }
        System.out.println(item.getClass());

        if (item instanceof Rabbit) {
            System.out.println("It's a Rabbit");
            this.generateRabbitMoves(board, (Rabbit) item);
        }

        return moves;
    }


    public static void main(String[] args) {
        Solver solver = new Solver();
        Board board = new DefaultBoard();

        BoardItem item = board.getItem(0,3);

        List<Move> moves = solver.generateMoves(board, item);
        for (Move move : moves) {
            System.out.println(move.initial);
        }
        System.out.println(moves);
    }
}

class Move {
    BoardItem item;
    Coordinate initial;
    Coordinate ending;
    Direction direction;

    public Move(BoardItem item, Direction direction) {
        this.item = item;
        this.direction = direction;
    }

    public Move(BoardItem item, Coordinate initial, Coordinate ending) { //for slideable need end coords
        this.item = item;
        this.initial = initial;
        this.ending = ending;
    }
}

