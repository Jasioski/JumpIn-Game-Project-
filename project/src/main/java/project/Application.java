package project;

import com.google.common.base.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.*;
import project.view.*;
import project.view.Board;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * GUI Application for JumpIn
 * This is the entrypoint for the GUI game
 */
public class Application extends JFrame implements ItemClickListener {

    public static Logger logger = LogManager.getLogger(Board.class);

    /**
     * State of the board
     */
    private project.model.Board board;
    /**
     * Root GUI component
     */
    private ApplicationPanel frame;

    private BoardHistory boardHistory;

    /**
     * Holds the buttons and label
     */
    private ToolBar toolBar;

    private Optional<Coordinate> selectedItem;
    private Optional<Coordinate> destinationItem;


    private Action newGame = new AbstractAction("New") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            newGame();
        }
    };

    private Action undo = new AbstractAction("Undo") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            undo();
        }
    };

    private Action redo = new AbstractAction("Redo") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            redo();
        }
    };
    private Action save = new AbstractAction("Save") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            save();
        }
    };
    private Action load = new AbstractAction("Load") {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            load();
        }
    };

    private void newGame() {
        initializeGame();
        setMessage("Make your move!");
        updateBoard();
    }


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

    // TODO: Switch to event model
    // this is fine for now, will address in other GUI tasks

    private void initializeGame() {
        this.board = new DefaultBoard().getBoard();
        this.boardHistory = new BoardHistory(this.board);
    }

    private void initializeFrame() {
        toolBar = new ToolBar(this.newGame, this.save, this.load, this.undo, this.redo);

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

    private void undo() {
		project.model.Board recalledMove = boardHistory.getUndoBoard();
		updateBoard(recalledMove);
    }

    private void redo() {
        project.model.Board recalledMove = boardHistory.getRedoBoard();
        updateBoard(recalledMove);
    }
    private void save() {
     //   project.model.Board recalledMove = boardHistory.getRedoBoard();
    //    updateBoard(recalledMove);
    }
    private void load() {
     //   project.model.Board recalledMove = boardHistory.getRedoBoard();
      //  updateBoard(recalledMove);
    }


    private void updateBoard () {
        updateBoard(this.board);
    }

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


    private void setMessage(String msg) {
        toolBar.setMessage(msg);
    }


    @SuppressWarnings("PMD")
    public static void main(String[] args) {
        new Application();
    }
}
