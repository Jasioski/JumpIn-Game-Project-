package project.solver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.DefaultBoard;
import project.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Solver {

    Board board;

    public static Logger logger = LogManager.getLogger(Solver.class);

    public Solver (){
    }

    public List<Move> generateRabbitMoves(Board board, Rabbit item) {
        List<Move> moves =  new ArrayList<>();

        logger.trace("generating rabbit moves");

        // solve this and gimme the moves
        // check up
        try { //todo: board.jump finished the jump. Need to implement an attempt jump so that it doesnt complete the jump on the board.
            board.jump(Direction.UP, item.getCoordinate());
            Move move = new Move(item, Direction.UP);
            moves.add(move);
        } catch(Exception e) {
            logger.trace("Cannot move up");
        }

        // check down
        try { //todo: board.jump finished the jump. Need to implement an attempt jump so that it doesnt complete the jump on the board.
            board.jump(Direction.DOWN, item.getCoordinate());
            Move move = new Move(item, Direction.DOWN);
            moves.add(move);
        } catch(Exception e) {
            logger.trace("cannot move down");
        }

        // check right
        try { //todo: board.jump finished the jump. Need to implement an attempt jump so that it doesnt complete the jump on the board.
            board.jump(Direction.RIGHT, item.getCoordinate());
            Move move = new Move(item, Direction.RIGHT);
            moves.add(move);
        } catch(Exception e) {
            logger.trace(e.getMessage());
            logger.trace("cannot move right");
        }

        // check left
        try { //todo: board.jump finished the jump. Need to implement an attempt jump so that it doesnt complete the jump on the board.
            board.jump(Direction.LEFT, item.getCoordinate());
            Move move = new Move(item, Direction.LEFT);
            moves.add(move);
        } catch(Exception e) {
            logger.trace("cannot move left");
        }

        return moves;
    }

    public List<Move> generateFoxMoves(Board board, Fox item) {
        List<Move> moves = new ArrayList<>();

        logger.trace("generating fox moves");

        //check left for head
        for (int column = item.getHead().column - 1; column >= 0; column--) {
            int moveSpaces = item.getHead().column - column;
            try { //todo: board.slide finished the slide. Need to implement an attempt slide so that it doesnt complete the slide on the board.
                board.slide(Direction.LEFT, moveSpaces, item.getHead());
                Move move = new Move(item, Direction.LEFT, item.getHead(), new Coordinate(item.getHead().row, item.getHead().column - column));
                moves.add(move);
            } catch (Exception e) {
                logger.trace("cannot slide left by " + moveSpaces);
                break;
            }
        }

        //check right for head
        for (int column = item.getHead().column + 1; column < board.getColumns(); column++) {
            int moveSpaces = column - item.getHead().column;
            try { //todo: board.slide finished the slide. Need to implement an attempt slide so that it doesnt complete the slide on the board.
                board.slide(Direction.RIGHT, moveSpaces, item.getHead());
                Move move = new Move(item, Direction.RIGHT, item.getHead(), new Coordinate(item.getHead().row, column - item.getHead().column));
                moves.add(move);
            } catch (Exception e) {
                logger.trace("cannot slide right by " + moveSpaces);
                break;
            }
        }

        //check up for head
        for (int row = item.getHead().row - 1; row > 0; row--) {
						int moveSpaces = item.getHead().row - row;
						try { //todo: board.slide finished the slide. Need to implement an attempt slide so that it doesnt complete the slide on the board.
                board.slide(Direction.UP, moveSpaces, item.getHead());
                Move move = new Move(item, Direction.UP, item.getHead(), new Coordinate(item.getHead().row - row, item.getHead().column));
                moves.add(move);
            } catch (Exception e) {
                logger.trace("cannot slide up by " + moveSpaces);
                break;
            }
        }

        //check down for head
        for (int row = item.getHead().row + 1; row < board.getRows(); row++) {
								int moveSpaces = row - item.getHead().row;
            try { //todo: board.slide finished the slide. Need to implement an attempt slide so that it doesnt complete the slide on the board.
                board.slide(Direction.DOWN, moveSpaces, item.getHead());
                Move move = new Move(item, Direction.DOWN, item.getHead(), new Coordinate(row - item.getHead().row, item.getHead().column));
                moves.add(move);
            } catch (Exception e) {
                logger.trace("cannot slide down by " + moveSpaces);
                break;
            }
        }
        return moves;
    }

    public List<Move> generateMoves(Board board, BoardItem item) {
        List<Move> moves =  new ArrayList<>();
        if (item instanceof ElevatedBoardItem) {

            //todo: add movability method for when a rabbit or a fox are on elevated tiles.

            //todo: separate this (Optional.empty to ~Optional.Rabbit~) to check if its a fox as well.
            // note: not sure we need that because foxes are never in elevated?
            if (!(((ElevatedBoardItem) item).containsItem().equals(Optional.empty()))) {
                logger.trace("It's a Rabbit");
               //moves = this.generateRabbitMoves(((ElevatedBoardItem) item).containsItem()); //todo: figure out how to cast this to a rabbit
            }
            logger.trace(((ElevatedBoardItem) item).containsItem());
        }
        logger.trace(item.getClass());

        if (item instanceof Rabbit) {
            logger.trace("It's a Rabbit");
            moves = this.generateRabbitMoves(board, (Rabbit) item);
        }

        if (item instanceof Fox) {
            logger.trace("It's a Fox");
            moves = this.generateFoxMoves(board, (Fox) item);
        }

        return moves;
    }

    public void solver(Board currentBoard) {

        // todo: the current board should not be modifiable
        //       it also should be modified

        // do a move generation
        // take the first move and then do a move generation after applying
        // the first move
        // keep going until all the rabbits are inside the holes
        // or until we reach
    }



    public static void main(String[] args) {
        Solver solver = new Solver();
        Board board = new DefaultBoard();

        List<Move> moves = new ArrayList<Move>();

        for (int r = 0; r < board.getRows(); r++) {
            for (int c = 0; c < board.getColumns(); c++) {
                BoardItem item = board.getItem(r, c);
                List<Move> itemMoves = solver.generateMoves(board, item);
                moves.addAll(itemMoves);
            }
        }

        for (Move move : moves) {
            logger.debug(move);
        }
        logger.debug("Number of generated moves: " + moves.size());
    }
}

