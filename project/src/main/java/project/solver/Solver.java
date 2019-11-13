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

    public List<Move> generateRabbitMoves(Rabbit item) {
        System.out.println("item wabbit");
        List<Move> moves =  new ArrayList<>();


        // solve this and gimme the moves
        // check up

        // check down

        // check right

        // check left


        return moves;
    }

    public List<Move> generateMoves(BoardItem item) {
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
            this.generateRabbitMoves((Rabbit) item);
        }

        return moves;
    }


    public static void main(String[] args) {
        Solver solver = new Solver();
        Board board = new DefaultBoard();

        BoardItem item = board.getItem(0,3);

        List<Move> moves = solver.generateMoves(item);
        System.out.println(moves);
    }
}

class Move {
    BoardItem item;
    Coordinate initial;
    Coordinate ending;

    public Move(BoardItem item, Coordinate initial, Coordinate ending) {
        this.item = item;
        this.initial = initial;
        this.ending = ending;
    }
}

