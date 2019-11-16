package project.solverRefactored;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.Direction;
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
        Fox item = (Fox) board.getItem(coordinate);
        Coordinate destination = new Coordinate(coordinate.row, coordinate.column);

        /* //CHECK THIS LOGIC
        if (item.orientation  == Orientation.HORIZONTAL) {
            // .equals??
            int nextColumn;
            //check slide left
            for (nextColumn = coordinate.column - 1; nextColumn >= 0; nextColumn--) { //todo: bug to slide right fox piece left
                if (board.getItem(destination).isObstacle()) { //can't slide into obstacle
                    if (!(destination.column == coordinate.column)) { //can't be first iteration of loop
                        Move move = new Move(item, Direction.LEFT, coordinate, destination);
                        legalMoves.add(move);
                    }
                    break;
                }
                destination = new Coordinate(coordinate.row, nextColumn);
            }

            //check slide right
            for (nextColumn = coordinate.column + 1; nextColumn < board.numberOfColumns; nextColumn++) { //todo: bug to slide left fox piece right
                if (board.getItem(destination).isObstacle()) { //can't slide into obstacle
                    if (!(destination.column == coordinate.column)) { //can't be first iteration of loop
                        Move move = new Move(item, Direction.RIGHT, coordinate, destination);
                        legalMoves.add(move);
                    }
                    break;
                }
                destination = new Coordinate(coordinate.row, nextColumn);
            }

        } else {
            int nextRow;
            //check slide up
            for (nextRow = coordinate.row - 1; nextRow >= 0; nextRow--) { //todo: bug to slide up fox piece down
                if (board.getItem(destination).isObstacle()) { //can't slide into obstacle
                    if (!(destination.row == coordinate.row)) { //can't be first iteration of loop
                        Move move = new Move(item, Direction.UP, coordinate, destination);
                        legalMoves.add(move);
                    }
                    break;
                }
                destination = new Coordinate(coordinate.row, nextRow);
            }

            //check slide down
            for (nextRow = coordinate.row + 1; nextRow < board.numberOfRows; nextRow++) { //todo: bug to slide down fox piece up
                if (board.getItem(destination).isObstacle()) { //can't slide into obstacle
                    if (!(destination.row == coordinate.row)) { //can't be first iteration of loop
                        Move move = new Move(item, Direction.DOWN, coordinate, destination);
                        legalMoves.add(move);
                    }
                    break;
                }
                destination = new Coordinate(coordinate.row, nextRow);
            }
        }

*/
        return legalMoves;
    }

    public List<Move> generateMovesRabbit(Board board, Coordinate coordinate) {
        logger.trace("generate moves");
        List<Move> legalMoves = new ArrayList<>();
        BoardItem item = board.getItem(coordinate);
        Coordinate nextItem;

        //check up jump
        nextItem = new Coordinate(coordinate.row - 1, coordinate.column);
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
