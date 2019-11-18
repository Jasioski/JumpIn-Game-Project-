package project.modelRefactored;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class BoardHistoryTest {

    @Test
    void instantiateHistory(){
        try {
            DefaultBoard defaultBoard = new DefaultBoard();
            Board board = defaultBoard.getDefaultBoard();
            BoardHistory history = new BoardHistory(board);
            assertEquals(history.getCurrentMove(), 0);
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }

    @Test
    void changeCurrentMove(){
        try {
            DefaultBoard defaultBoard = new DefaultBoard();
            Board board = defaultBoard.getDefaultBoard();
            BoardHistory history = new BoardHistory(board);

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
            DefaultBoard defaultBoard = new DefaultBoard();
            Board board = defaultBoard.getDefaultBoard();
            BoardHistory history = new BoardHistory(board);

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
            DefaultBoard defaultBoard = new DefaultBoard();
            Board board1 = defaultBoard.getDefaultBoard();
            BoardHistory history = new BoardHistory(board1);
            Board compareBoard1 = defaultBoard.getDefaultBoard();

            board1 = board1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            compareBoard1 = compareBoard1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            history.addState(board1);

            board1 = board1.move(new Coordinate(1, 1), new Coordinate(2, 1));
            history.addState(board1);

            assertEquals(history.getUndoBoard(), compareBoard1);
            assertEquals(history.getUndoBoard(), defaultBoard.getDefaultBoard());
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }

    @Test
    void undoAndRedo(){
        try {
            DefaultBoard defaultBoard = new DefaultBoard();
            Board board1 = defaultBoard.getDefaultBoard();
            BoardHistory history = new BoardHistory(board1);

            Board compareBoard1 = defaultBoard.getDefaultBoard();
            Board compareBoard2 = defaultBoard.getDefaultBoard();

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
            assertEquals(history.getUndoBoard(), defaultBoard.getDefaultBoard());

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
            DefaultBoard defaultBoard = new DefaultBoard();
            Board board1 = defaultBoard.getDefaultBoard();
            BoardHistory history = new BoardHistory(board1);

            Board compareBoard1 = defaultBoard.getDefaultBoard();
            Board compareBoard2 = defaultBoard.getDefaultBoard();

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
            DefaultBoard defaultBoard = new DefaultBoard();
            Board board1 = defaultBoard.getDefaultBoard();
            BoardHistory history = new BoardHistory(board1);

            Board compareBoard1 = defaultBoard.getDefaultBoard();

            board1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            compareBoard1.move(new Coordinate(3, 3), new Coordinate(3, 1));
            history.addState(board1);

            board1.move(new Coordinate(1, 1), new Coordinate(2, 1));
            history.addState(board1);

            assertEquals(history.getUndoBoard(), compareBoard1);
            assertEquals(history.getUndoBoard(), defaultBoard.getDefaultBoard());
            assertEquals(history.getUndoBoard(), defaultBoard.getDefaultBoard());
            assertEquals(history.getUndoBoard(), defaultBoard.getDefaultBoard());
            assertEquals(history.getRedoBoard(), compareBoard1);
        }
        catch(Exception e){
            Assertions.fail(e);
        }
    }
}