package project.solverRefactored;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.Direction;
import project.model.GameState;
import project.model.exceptions.NonSlideableException;
import project.model.exceptions.SlideHitObstacleException;
import project.model.exceptions.SlideWrongOrientationException;
import project.modelRefactored.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public class Solver {
    Board board;

    //Use logger
    public static Logger logger = LogManager.getLogger(Solver.class);

    public Solver() {

    }

    private static void printMoves(List<Move> moves) {
        for (Move move: moves) {
            logger.trace(move);
        }
    }


    // TODO: delete ALLL THe old code
    private static Board applyMove(Board board, Move move) throws
            InvalidMoveException, NonSlideableException,
            SlideWrongOrientationException, SlideHitObstacleException {
        try {
            return board.move(move.initial, move.ending);
        } catch (Exception e) {
            logger.error("received an error when applying a move");
            throw e;
        }
    }

    final static int MAX_DEPTH = 30;

    public static void solve (Board board) {
        HashSet<Board> boardHistory = new HashSet<>();

        solve(board, 0);
    }


    public static void solve (Board board, int depth) {

        if (depth > MAX_DEPTH) {
            logger.debug("hit max depth on search");
            return;
        }

        if (board.currentGameState == GameState.IN_PROGRESS) {
            // board is the root of the tree

            List<Move> moves = generateMoves(board);

            logger.debug("generated moves: " + moves.size());

            Collections.shuffle(moves);

            for (Move move: moves) {
                try {
                    Board newBoard = applyMove(board, move);

                    System.out.println(board);

                    Thread.sleep(1000);

                    solve(newBoard, depth + 1);
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }

        else {
            logger.debug("Game solved");

            return;
        }

    }

    public static List<Move> generateMoves(Board board) {
        logger.trace("generate moves");
        List<Move> legalMoves = new ArrayList<>();


        for (int row = 0; row < board.numberOfRows; row++) {
            for (int column = 0; column < board.numberOfRows; column++) {

                Coordinate coordinate = new Coordinate(row, column);
                BoardItem item = board.getItem(coordinate);

                if (item instanceof Rabbit) {
                    List<Move> rabbitMoves = generateMovesRabbit(board,
                            coordinate);
                    logger.trace("rabbit moves generated: " + rabbitMoves.size());

                    legalMoves.addAll(rabbitMoves);
                }

                if (item instanceof ContainerItem) {
                    if (((ContainerItem) item).containingItem.isPresent()) {
                        ContainerItem containerItem = (ContainerItem) item;

                        Containable containable =
                                containerItem.containingItem.get();

                        if (containable instanceof Rabbit)  {
                            List<Move> rabbitMoves = generateMovesRabbit(board,
                                    coordinate);
                            logger.trace("rabbit moves generated: " + rabbitMoves.size());

                            legalMoves.addAll(rabbitMoves);
                        }
                    }
                }

                else if (item instanceof Fox) {
                    List<Move> foxMoves = generateMovesFox(board, coordinate);
                    logger.trace("fox moves generated: " + foxMoves.size());
                    legalMoves.addAll(foxMoves);
                }
            }
        }

        return legalMoves;
    }

    public static List<Move> generateMovesFox(Board board,
                                            Coordinate coordinate) {
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
                destination = new Coordinate(coordinate.row, nextColumn);

                // If we don't hit an obstacle
                if (!board.getItem(destination).isObstacle()) {
                    Move move = new Move(item, Direction.LEFT, coordinate, destination);
                    legalMoves.add(move);
                }
                else {
                    break;
                }
            }

            //check slide right
            //todo: bug to slide left fox piece right
            for (nextColumn = coordinate.column + 1; nextColumn < board.numberOfColumns; nextColumn++) {
                destination = new Coordinate(coordinate.row, nextColumn);

                // If we don't hit an obstacle
                if (!board.getItem(destination).isObstacle()) {
                    Move move = new Move(item, Direction.RIGHT, coordinate, destination);
                    legalMoves.add(move);
                } else {
                    break;
                }
            }

        } else if(item.orientation == Orientation.VERTICAL) {
            int nextRow;
            //check slide up
            for (nextRow = coordinate.row - 1; nextRow >= 0; nextRow--) {
                //todo: bug to slide up fox piece down
                destination = new Coordinate(nextRow, coordinate.column);

                if (!board.getItem(destination).isObstacle()) {
                    Move move = new Move(item, Direction.UP, coordinate, destination);
                    legalMoves.add(move);
                }
                else {
                    break;
                }
            }

            //check slide down
            for (nextRow = coordinate.row + 1; nextRow < board.numberOfRows; nextRow++) {
                //todo: bug to slide down fox piece up
                destination = new Coordinate(nextRow, coordinate.column);

                if (!board.getItem(destination).isObstacle()) { //can't slide
                    // into obstacle
                    Move move = new Move(item, Direction.DOWN, coordinate, destination);
                    legalMoves.add(move);
                }
                else {
                    break;
                }
            }
        }

        return legalMoves;
    }

    private static boolean isCoordinateInBoard(Coordinate coordinate,
                                            Board board) {
        // Subtract 1 for zero indexing
        int maxRows = board.numberOfRows - 1;
        int maxColumns = board.numberOfColumns - 1;

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


    public static List<Move> generateMovesRabbit(Board board,
                                            Coordinate coordinate) {
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
