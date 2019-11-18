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
        }

        if (item instanceof Fox) {
            return generateMovesFox(board, coordinate);
        }

        return legalMoves;
    }

    public List<Move> generateMovesFox(Board board, Coordinate coordinate) {
        logger.trace("generate moves");
        List<Move> legalMoves = new ArrayList<>();
        Fox item = (Fox) board.getItem(coordinate);
        Coordinate destination = new Coordinate(coordinate.row, coordinate.column);

        if (item.orientation  == Orientation.HORIZONTAL) {
            // .equals??
            int nextColumn;

            //check slide left
            //todo: bug to slide right fox piece left
            for (nextColumn = coordinate.column - 1; nextColumn >= 0; nextColumn--) {
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
            //todo: bug to slide left fox piece right
            for (nextColumn = coordinate.column + 1; nextColumn < board.numberOfColumns; nextColumn++) {
                if (board.getItem(destination).isObstacle()) { //can't slide into obstacle
                    if (!(destination.column == coordinate.column)) { //can't be first iteration of loop
                        Move move = new Move(item, Direction.RIGHT, coordinate, destination);
                        legalMoves.add(move);
                    }
                    break;
                }
                destination = new Coordinate(coordinate.row, nextColumn);
            }

        } else if(item.orientation == Orientation.VERTICAL) {
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

        return legalMoves;
    }

    private boolean isCoordinateInBoard(Coordinate coordinate, Board board) {
        int maxRows = board.numberOfRows;
        int maxColumns = board.numberOfColumns;

        int minRows = 0;
        int minColumns = 0;

        if (coordinate.row < minRows || coordinate.row > maxRows) {
            return false;
        }

        if (coordinate.column < minColumns || coordinate.column > maxColumns) {
            return false;
        }

        return true;
    }


    public List<Move> generateMovesRabbit(Board board, Coordinate coordinate) {
        logger.trace("generate moves");
        List<Move> legalMoves = new ArrayList<>();
        BoardItem item = board.getItem(coordinate);
        Coordinate nextItem;

        // TODO: make sure we don't fall off the board

        //check up jump
        nextItem = new Coordinate(coordinate.row - 1, coordinate.column);

        if (isCoordinateInBoard(nextItem, board)) {
            if (board.getItem(nextItem).isObstacle()) { //rabbits must jump over obstacle to move
                for (int row = coordinate.row - 2; row >= 0; row--) {
                    nextItem = new Coordinate(row, nextItem.column);

                    if (!isCoordinateInBoard(nextItem, board)) {
                        break;
                    }

                    if (!board.getItem(nextItem).isObstacle()) {
                        Move move = new Move(item, Direction.UP, coordinate, nextItem);
                        legalMoves.add(move);
                        break;
                    }
                }
            }
        }

        //check down jump
        nextItem = new Coordinate(coordinate.row + 1, coordinate.column);

        if (isCoordinateInBoard(nextItem, board)) {
            if (board.getItem(nextItem).isObstacle()) { //rabbits must jump over obstacle to move
                for (int row = coordinate.row + 2; row < board.numberOfRows; row++) {
                    nextItem = new Coordinate(row, nextItem.column);

                    if (!isCoordinateInBoard(nextItem, board)) {
                        break;
                    }

                    if (!board.getItem(nextItem).isObstacle()) {
                        Move move = new Move(item, Direction.DOWN, coordinate, nextItem);
                        legalMoves.add(move);
                        break;
                    }
                }
            }
        }

        //check left jump
        nextItem = new Coordinate(coordinate.row, coordinate.column - 1);

        if (isCoordinateInBoard(nextItem, board)) {
            if (board.getItem(nextItem).isObstacle()) {
                //rabbits must jump over obstacle to move
                for (int column = coordinate.column - 2; column >= 0; column--) {
                    nextItem = new Coordinate(nextItem.row, column);

                    if (!isCoordinateInBoard(nextItem, board)) {
                        break;
                    }

                    if (!board.getItem(nextItem).isObstacle()) {
                        Move move = new Move(item, Direction.LEFT, coordinate, nextItem);
                        legalMoves.add(move);
                        break;
                    }
                }
            }
        }

        //check right jump
        nextItem = new Coordinate(coordinate.row, coordinate.column + 1);

        if (isCoordinateInBoard(nextItem, board)) {
            if (board.getItem(nextItem).isObstacle()) {
                //rabbits must jump over obstacle to move
                for (int column = coordinate.column + 2; column < board.numberOfColumns; column++) {
                    nextItem = new Coordinate(nextItem.row, column);

                    if (!isCoordinateInBoard(nextItem, board)) {
                        break;
                    }

                    if (!board.getItem(nextItem).isObstacle()) {
                        Move move = new Move(item, Direction.RIGHT, coordinate,
                                nextItem);
                        legalMoves.add(move);
                        break;
                    }
                }
            }
        }

        return legalMoves;
    }

}
