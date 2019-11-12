package project.solver;

import project.DefaultBoard;
import project.model.Board;
import project.model.BoardItem;
import project.model.Coordinate;
import project.model.Rabbit;

import java.util.ArrayList;
import java.util.List;

public class Solver {

    Board board;

    public Solver (){
    }

    public List<Move> generateRabbitMoves(Rabbit item) {
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

        if (item instanceof Rabbit) {
            this.generateRabbitMoves((Rabbit) item);
        }

        return moves;
    }


    public static void main(String[] args) {
        Solver solver = new Solver();
        Board board = new DefaultBoard();

        BoardItem item = board.getItem(2,0);
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

