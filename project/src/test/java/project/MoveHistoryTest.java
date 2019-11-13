package project;

import org.junit.jupiter.api.*;
import project.model.Board;
import project.model.Coordinate;
import project.model.MoveHistory;

import static org.junit.jupiter.api.Assertions.*;

public class MoveHistoryTest {

    @Test
    void instantiateHistory(){
        try {
            Board board = new DefaultBoard();
            MoveHistory history = new MoveHistory(board);
            assertEquals(history.getCurrentMove(), 0);
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }

    @Test
    void changeCurrentMove(){
        try {
            Board board = new DefaultBoard();
            MoveHistory history = new MoveHistory(board);
            history.setCurrentMove(4);
            assertEquals(history.getCurrentMove(), 4);
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }

    @Test
    void addAndRecallBoard(){
        try{
            Board board = new DefaultBoard();
            MoveHistory history = new MoveHistory(board);

            history.addState(board);
            assertEquals(history.getUndoBoard(), board);
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }

    @Test
    void recallLastTwoBoards(){
        try {
            Board board1 = new DefaultBoard();
            Board compareBoard1 = new DefaultBoard();
            MoveHistory history = new MoveHistory(board1);

            board1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            compareBoard1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            history.addState(board1);

            board1.move(new Coordinate(1, 1), new Coordinate(2, 1));
            history.addState(board1);

            assertEquals(history.getUndoBoard(), compareBoard1);
            assertEquals(history.getUndoBoard(), new DefaultBoard());
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }

    @Test
    void undoAndRedo(){
        try {
            Board board1 = new DefaultBoard();
            Board compareBoard1 = new DefaultBoard();
            Board compareBoard2 = new DefaultBoard();
            MoveHistory history = new MoveHistory(board1);

            board1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            compareBoard1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            compareBoard2.move(new Coordinate(3, 3), new Coordinate(3, 1));
            history.addState(board1);

            board1.move(new Coordinate(1, 1), new Coordinate(2, 1));
            compareBoard2.move(new Coordinate(1, 1), new Coordinate(2, 1));
            history.addState(board1);

            assertEquals(history.getUndoBoard(), compareBoard1);
            assertEquals(history.getRedoBoard(), compareBoard2);

            assertEquals(history.getUndoBoard(), compareBoard1);
            assertEquals(history.getUndoBoard(), new DefaultBoard());

            assertEquals(history.getRedoBoard(), compareBoard1);
            assertEquals(history.getRedoBoard(), compareBoard2);
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }

    @Test
    void redoMultipleTimes(){
        try {
            Board board1 = new DefaultBoard();
            Board compareBoard1 = new DefaultBoard();
            Board compareBoard2 = new DefaultBoard();
            MoveHistory history = new MoveHistory(board1);

            board1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            compareBoard1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            compareBoard2.move(new Coordinate(3, 3), new Coordinate(3, 1));
            history.addState(board1);

            board1.move(new Coordinate(1, 1), new Coordinate(2, 1));
            compareBoard2.move(new Coordinate(1, 1), new Coordinate(2, 1));
            history.addState(board1);

            assertEquals(history.getUndoBoard(), compareBoard1);
            assertEquals(history.getRedoBoard(), compareBoard2);
            assertEquals(history.getRedoBoard(), compareBoard2);
            assertEquals(history.getRedoBoard(), compareBoard2);
            assertEquals(history.getUndoBoard(), compareBoard1);
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }

    @Test
    void undoMultipleTimes(){
        try {
            Board board1 = new DefaultBoard();
            Board compareBoard1 = new DefaultBoard();
            MoveHistory history = new MoveHistory(board1);

            board1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            compareBoard1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            history.addState(board1);

            board1.move(new Coordinate(1, 1), new Coordinate(2, 1));
            history.addState(board1);

            assertEquals(history.getUndoBoard(), compareBoard1);
            assertEquals(history.getUndoBoard(), new DefaultBoard());
            assertEquals(history.getUndoBoard(), new DefaultBoard());
            assertEquals(history.getUndoBoard(), new DefaultBoard());
            assertEquals(history.getRedoBoard(), compareBoard1);
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }
}