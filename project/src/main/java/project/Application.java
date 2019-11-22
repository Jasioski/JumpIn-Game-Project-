package project;

import com.google.common.base.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.*;
import project.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * GUI Application for JumpIn
 * This is the entrypoint for the GUI game
 */
public class Application extends JFrame implements ItemClickListener {

    public static Logger logger = LogManager.getLogger(GuiInnerComponents.class);

    /**
     * State of the board
     */
    private Board board;
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
            initializeGame();
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


    Application() {
        super("JumpIn");

        // Ensure resources are loaded;
        ImageResources.getInstance();

        this.selectedItem = Optional.absent();
        this.destinationItem = Optional.absent();

        initializeFrame();
        initializeGame();
    }

    private void initializeGame() {
        this.board = new DefaultBoard().getBoard();
        this.boardHistory = new BoardHistory(this.board);

        setMessage("Make your move!");
        // TODO: send event of changing board

        this.frame.setBoard(board);
        this.pack();
    }

    private void initializeFrame() {

        toolBar = new ToolBar(this.newGame, this.undo, this.redo);

        // GUI Components
        frame = new ApplicationPanel(toolBar, this);

        // Frame
        this.add(frame.getPanel());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Whether to use the native windowing systems default location
        this.setLocationByPlatform(true);

        this.pack();
        this.setResizable(false);
        this.setVisible(true);
    }

    private void undo() {
		Board recalledMove = boardHistory.getUndoBoard();
		updateBoard(recalledMove);
    }

    private void redo() {
        Board recalledMove = boardHistory.getRedoBoard();
        updateBoard(recalledMove);
    }


    private void updateBoard (Board newBoard) {
        this.board = newBoard;
        this.frame.setBoard(newBoard);

        if (board.currentGameState == GameState.SOLVED) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showMessageDialog(this, "Congratulations! \nYou Won!",
                    "Victory!", 1);
            int playAgain = JOptionPane.showConfirmDialog(this, "Do you want " +
                    "to play again?", "Reset?", dialogButton);
            if (playAgain == JOptionPane.YES_OPTION) {
				this.initializeGame();
            }
        }
    }

    @Override
    public void onItemClick(ItemClickEvent event) {
        logger.debug("Received an item click");

        // We haven't selected an item
        if (!selectedItem.isPresent()) {
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
                Board appliedBoard = this.board.move(selectedItem.get(),
                        destinationItem.get());
                this.boardHistory.addState(appliedBoard);
                updateBoard(appliedBoard);
            } catch (InvalidMoveException e) {
                logger.debug(e);
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
