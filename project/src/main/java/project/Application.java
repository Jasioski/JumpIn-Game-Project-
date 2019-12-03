package project;

import com.google.common.base.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.*;
import project.view.*;
import project.view.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * GUI Application for JumpIn
 * This is the entrypoint for the GUI game
 */
public class Application extends JFrame implements ItemClickListener {

    /**
     * The logger used to log errors.
     */
    public static Logger logger = LogManager.getLogger(Board.class);

    /**
     * State of the board
     */
    private project.model.Board board;
    /**
     * Root GUI component
     */
    private ApplicationPanel frame;

    /**
     * The history of boards in this application.
     */
    private BoardHistory boardHistory;

    /**
     * Holds the buttons and label
     */
    private ToolBar toolBar;

    /**
     * The coordinates of the currently selected item.
     */
    private Optional<Coordinate> selectedItem;

    /**
     * The coordinates where the selected item will go.
     */
    private Optional<Coordinate> destinationItem;

    /**
     * Action that creates a new game.
     */
    private Action newGame = new AbstractAction("New") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            newGame();
        }
    };

    /**
     * Action that undoes the current move.
     */
    private Action undo = new AbstractAction("Undo") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            undo();
        }
    };

    /**
     * Action that redoes the current move.
     */
    private Action redo = new AbstractAction("Redo") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            redo();
        }
    };

    /**
     * Action that saves the current board.
     */
    private Action save = new AbstractAction("Save") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            save();
        }
    };

    /**
     * Action that loads the current board.
     */
    private Action load = new AbstractAction("Load") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            load();
        }
    };

    /**
     * Creates a new game.
     */
    private void newGame() {
        initializeGame();
        setMessage("Make your move!");
        updateBoard();
    }

    /**
     * Creates the application, initializing the board and the frame.
     */
    private Application() {
        super("JumpIn");

        // Ensure resources are loaded;
        ImageResources.getInstance();

        this.selectedItem = Optional.absent();
        this.destinationItem = Optional.absent();

        initializeGame();
        initializeFrame();
        setMessage("Make your move!");
    }

    /**
     * Initializes the game with a new board and boardhistory
     */
    private void initializeGame() {
        this.board = new DefaultBoard().getBoard();
        this.boardHistory = new BoardHistory(this.board);
    }

    /**
     * Initializes the frame of the gui.
     */
    private void initializeFrame() {
        toolBar = new ToolBar(this.newGame, this.undo, this.redo, this.save, this.load);

        // GUI Components
        frame = new ApplicationPanel(toolBar, this, this.board);

        // Frame
        this.add(frame);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Whether to use the native windowing systems default location
        this.setLocationByPlatform(true);

        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Undoes the last move on the board.
     */
    private void undo() {
		project.model.Board recalledMove = boardHistory.getUndoBoard();
		updateBoard(recalledMove);
    }

    /**
     * Redoes the last move that was undone.
     */
    private void redo() {
        project.model.Board recalledMove = boardHistory.getRedoBoard();
        updateBoard(recalledMove);
    }

    /**
     * Saves the current board into a filename given by the user.
     */
    private void save(){
        try {
            String filename = JOptionPane.showInputDialog("What would you like to call your board");
            XMLParser.writeToXMLFile(board, filename);
        }
        catch(IOException e){
            logger.error(e.getMessage());
        }
    }

    /**
     * Loads the board contained in a filename given by the user.
     */
    private void load(){
        try{
            String filename = JOptionPane.showInputDialog("What board would you like to load?");
            board = XMLParser.boardFromXML(filename);
            boardHistory = new BoardHistory(board);
            updateBoard();
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(
                    null, "This file was not found.", "Failure", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Updates the board displayed by the gui.
     */
    private void updateBoard () {
        updateBoard(this.board);
    }

    /**
     * Updates the board using a given board.
     * @param newBoard The board that the gui should be updated to model.
     */
    private void updateBoard (project.model.Board newBoard) {
        this.board = newBoard;
        this.frame.setBoard(newBoard);

        if (board.currentGameState == GameState.SOLVED) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showMessageDialog(this, "Congratulations! \nYou Won!",
                    "Victory!", 1);
            int playAgain = JOptionPane.showConfirmDialog(this, "Do you want " +
                    "to play again?", "Reset?", dialogButton);
            if (playAgain == JOptionPane.YES_OPTION) {
                logger.debug("Restart game");
				newGame();
            }
        }
    }

    /**
     * Sends an event when an item on the gui is clicked.
     * @param event The event sent by the clicked item.
     */
    @Override
    public void onItemClick(ItemClickEvent event) {
        logger.debug("Received an item click");

        // We haven't selected an item
        if (!selectedItem.isPresent()) {

            if (!board.isMovable(event.coordinate)) {
                logger.debug("Non-movable selected");
                return;
            }
            logger.trace("Selected item at coordinate " + event.coordinate);
            selectedItem = Optional.of(event.coordinate);
            return;
        }

        // We have selected an item
        else {
            // If this is the same item already selected
            if (event.coordinate.equals(selectedItem.get())) {
                logger.trace("Tried to select the same item twice");
                return;
            }

            logger.trace("Destination item at coordinate " + event.coordinate);

            destinationItem = Optional.of(event.coordinate);

            // Apply to the board
            try {
                project.model.Board appliedBoard = this.board.move(selectedItem.get(),
                        destinationItem.get());
                this.boardHistory.addState(appliedBoard);
                updateBoard(appliedBoard);
            } catch (InvalidMoveException e) {
                logger.debug(e);
                JOptionPane.showMessageDialog(this, e.getMessage(), "Exception!"
                        , 0);
            } finally {
                // Clear selections
                selectedItem = Optional.absent();
                destinationItem = Optional.absent();
            }
        }
    }

    /**
     * Sets the message in the toolbar.
     * @param msg The message that the toolbar should contain.
     */
    private void setMessage(String msg) {
        toolBar.setMessage(msg);
    }

    @SuppressWarnings("PMD")
    public static void main(String[] args) {
        new Application();
    }
}
