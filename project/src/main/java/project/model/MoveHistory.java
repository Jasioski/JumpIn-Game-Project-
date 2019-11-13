package project.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates and manages the history of the board's moves by serializing its states.
 * Has options to undo and redo, recalling old board states that were serialized.
 */
public class MoveHistory {
    /**
     * The name of the file where boards are serialized.
     */
    public static final String BOARDS_FILENAME = "boards.ser";

    /**
     * The current move that the board is on.
     */
    private int currentMove;

    /**
     * Gets the current move of the board.
     * @return The move that is returned.
     */
    public int getCurrentMove() {
        return currentMove;
    }

    /**
     * Sets the current move of the board.
     * @param currentMove The move to be set.
     */
    public void setCurrentMove(int currentMove) {
        this.currentMove = currentMove;
    }

    /**
     * Creates a move history, taking in an initial board to set as its first state.
     * @param firstBoard The first board to be placed in the move history.
     * @throws IOException If the output stream fails to open.
     */
    public MoveHistory(Board firstBoard) throws IOException {
            //Opens the output stream
            FileOutputStream fileOut = new FileOutputStream(BOARDS_FILENAME);
            ObjectOutputStream boardOut = new ObjectOutputStream(fileOut);

            //Writes the board to the serialized output
            List<Board> boardList = new ArrayList<>();
            boardList.add(firstBoard);
            boardOut.writeObject(boardList);
            currentMove = 0;

            //Closes the output stream
            boardOut.close();
            fileOut.close();
    }

    /**
     * Adds a board state to the history, serializing it and adding it to the list held in the boards file.
     * @param boardState The board being added to the history.
     * @throws IOException If the input/output stream fails.
     * @throws ClassNotFoundException If the ArrayList<Board> class is not found in the file.
     */
    public void addState(Board boardState) throws IOException, ClassNotFoundException {
            currentMove++;

            //Set up input and output streams
            FileInputStream fileIn = new FileInputStream(BOARDS_FILENAME);
            ObjectInputStream boardIn = new ObjectInputStream(fileIn);

            //Read in the list of boards, add a board, then output the new list
            List<Board> readBoards = (ArrayList<Board>) boardIn.readObject();

            boardIn.close();
            fileIn.close();

            FileOutputStream fileOut = new FileOutputStream(BOARDS_FILENAME);
            ObjectOutputStream boardOut = new ObjectOutputStream(fileOut);

            if (!(readBoards.size() == currentMove)) {
                for (int i = readBoards.size() - 1; i >= currentMove; i--){
                    readBoards.remove(i);
                }

            }

            readBoards.add(boardState);
            boardOut.writeObject(readBoards);;
            boardOut.close();
            fileOut.close();
    }

    /**
     * Returns the previous board state in the history, representing an "undo" move.
     * @return The board representing the previous state.
     * @throws IOException If the input/output stream fails.
     * @throws ClassNotFoundException If the ArrayList<Board> class is not found in the file.
     */
    public Board getUndoBoard() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(BOARDS_FILENAME);
        ObjectInputStream boardIn = new ObjectInputStream(fileIn);

        List<Board> readBoards = (ArrayList<Board>) boardIn.readObject();
        boardIn.close();
        fileIn.close();

        Board lastBoard;
        if (currentMove != 0){
            lastBoard = readBoards.get(currentMove - 1);
            currentMove--;
        }
        else {
            lastBoard = readBoards.get(currentMove);
        }
        return lastBoard;
    }

    /**
     * Returns the previous board state that was undone, or the current board if no previous undone boards remain.
     * @return The board representing the redone state.
     * @throws IOException If the input/output stream fails.
     * @throws ClassNotFoundException If the ArrayList<Board> class is not found in the file.
     */
    public Board getRedoBoard() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(BOARDS_FILENAME);
        ObjectInputStream boardIn = new ObjectInputStream(fileIn);

        List<Board> readBoards = (ArrayList<Board>) boardIn.readObject();
        boardIn.close();
        fileIn.close();

        //Increments the move unless it is already at the end of the move list
        if (!(readBoards.size() == currentMove + 1)) {
            currentMove++;
        }
        return readBoards.get(currentMove);
    }
}
