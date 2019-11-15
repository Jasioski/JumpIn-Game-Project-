package project.solverRefactored;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.modelRefactored.*;

import java.util.List;
import java.util.ArrayList;

public class Solver {
    Board board;

    //Use logger
    public static Logger logger = LogManager.getLogger(Solver.class);

    public Solver() {

    }

    public List<Move> generateMoves(Board board, Coordinate coordinate) {
        logger.trace("generate moves");
        List<Move> legalMoves = new ArrayList<>();
        BoardItem item = board.getItem(coordinate);

        if (item instanceof Rabbit) {
            return generateMovesRabbit(board, coordinate);
        } //end rabbit

//        if (item instanceof Fox) { //todo: create fox class with a final variable orientation.
//            return generateMovesFox(board, coordinate);
//        }

        return legalMoves;
    }

    public List<Move> generateMovesFox(Board board, Coordinate coordinate) {
        logger.trace("generate moves");
        List<Move> legalMoves = new ArrayList<>();
        BoardItem item = board.getItem(coordinate);

//        if ((Fox) item.orientation == Orientation.HORIZONTAL) { //todo: should this be .equals??
//
//            //slide left
//            for (int nextColumn = coordinate, nextColumn < board.numberOfColumns; )
//        } else {
//
//        }

        return legalMoves;
    }

    public List<Move> generateMovesRabbit(Board board, Coordinate coordinate) {
        logger.trace("generate moves");
        List<Move> legalMoves = new ArrayList<>();
        BoardItem item = board.getItem(coordinate);

        //check up jump
        Coordinate nextItem = new Coordinate(coordinate.row - 1, coordinate.column);
        if (board.getItem(nextItem).isObstacle()) { //rabbits must jump over obstacle to move
            for (int row = coordinate.row - 2; row >= 0; row--) {
                nextItem = new Coordinate(row, nextItem.column);
                if (!board.getItem(nextItem).isObstacle()) {
                    Move move = new Move(item, Direction.UP, coordinate, nextItem);
                    legalMoves.add(move);
                    break;
                }
            }
        }

        //check down jump
        nextItem = new Coordinate(coordinate.row + 1, coordinate.column);
        if (board.getItem(nextItem).isObstacle()) { //rabbits must jump over obstacle to move
            for (int row = coordinate.row + 2; row < board.numberOfRows; row++) {
                nextItem = new Coordinate(row, nextItem.column);
                if (!board.getItem(nextItem).isObstacle()) {
                    Move move = new Move(item, Direction.DOWN, coordinate, nextItem);
                    legalMoves.add(move);
                    break;
                }
            }
        }

        //check left jump
        nextItem = new Coordinate(coordinate.row, coordinate.column - 1);
        if (board.getItem(nextItem).isObstacle()) { //rabbits must jump over obstacle to move
            for (int column = coordinate.column - 2; column >= 0; column--) {
                nextItem = new Coordinate(nextItem.row, column);
                if (!board.getItem(nextItem).isObstacle()) {
                    Move move = new Move(item, Direction.LEFT, coordinate, nextItem);
                    legalMoves.add(move);
                    break;
                }
            }
        }

        //check right jump
        nextItem = new Coordinate(coordinate.row, coordinate.column + 1);
        if (board.getItem(nextItem).isObstacle()) { //rabbits must jump over obstacle to move
            for (int column = coordinate.column + 2; column < board.numberOfColumns; column++) {
                nextItem = new Coordinate(nextItem.row, column);
                if (!board.getItem(nextItem).isObstacle()) {
                    Move move = new Move(item, Direction.LEFT, coordinate, nextItem);
                    legalMoves.add(move);
                    break;
                }
            }
        }
        return legalMoves;
    }

}
