package project.view;

import project.DefaultBoard;
import project.model.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.exceptions.*;

import javax.swing.*;
import javax.swing.border.*;

/**
 * GuiInnerComponents initializes with the inner components of the GUI i.e., the board setup
 */
public class GuiInnerComponents implements ItemClickListener {
	//View layer
	/**
	 * Logger that logs results of moves.
	 */
	public static Logger logger = LogManager.getLogger(GuiInnerComponents.class);

	/**
	 * Panel for the view layer.
	 */
	JPanel boardPanel;

	/**
	 * Message displayed at the top of the page.
	 */
	private JLabel message;

	/**
	 * The border surrounding the board.
	 */
	private EmptyBorder border;

	// Model Layer
	/**
	 * The board being represented by the gui.
	 */
	private Board board;

	/**
	 * The coordinate of the item currently selected.
	 */
	private Coordinate selectedItem;

	/**
	 * The coordinate of the destination selected.
	 */
	private Coordinate destinationItem;

	/**
	 * The history of board states.
	 */
	private MoveHistory moveHistory;

	/**
	 * The padding used by the board's border.
	 */
	private int padding;

	/**
	 * Creates a gui for the inner components using an initial board state.
	 * @param board The initial state of the board.
	 */
	public GuiInnerComponents(Board board) {
		this.board = board;
		this.message = new JLabel();
		this.message.setText("Make your move!");
		try {
			this.moveHistory = new MoveHistory(board);
		}
		catch (IOException e){
			logger.error(e);
		}
		boardInnerComponents();
		this.updateBoard();
	}

	/**
	 * Creates the inner components containing the board.
	 */
	private void boardInnerComponents() {
		boardPanel = new JPanel(new GridLayout(6, 6));
		boardPanel.setBorder(new LineBorder(Color.BLACK));
		boardPanel.setLayout(new GridLayout(5, 5));
	}

	/**
	 * Updates the contents of the GUI in order to reflect the model held in board.
	 */
	private void updateBoard() {
		boardPanel.removeAll();

		for (int row = 0; row < this.board.getRows(); row++) {
			for (int column = 0; column < this.board.getColumns(); column++) {
				BoardItem modelItem = this.board.getItem(row, column);
				JComponent viewItem = new GUIBoardItem(new Coordinate(row, column), modelItem, this);
				this.boardPanel.add(viewItem);
			}
		}

		boardPanel.repaint();
		boardPanel.revalidate();
		// updates the visuals
		if (board.getCurrentGameState() == GameState.SOLVED) {
			int dialogButton = JOptionPane.YES_NO_OPTION;
			JOptionPane.showMessageDialog(boardPanel, "Congratulations! \nYou Won!", "Victory!", 1);
			int playAgain = JOptionPane.showConfirmDialog(boardPanel, "Do you want to play again?", "Reset?", dialogButton);
			if (playAgain == JOptionPane.YES_OPTION) {
				this.setupNewGame();
			}
		}
	}

	/**
	 * Initializes the pieces on the board.
	 */
	void setupNewGame() {
		message.setText("Make your move!");
		this.board = new DefaultBoard();
		try {
			this.moveHistory = new MoveHistory(board);
		}
		catch (IOException e){
			logger.error(e);
		}
		this.updateBoard();
	}

	/**
	 * Returns the message shown at the top of the screen.
	 * @return The message at the top of the screen.
	 */
	public JLabel getMessage() {
		return this.message;
	}

	/**
	 * Sets up the border around the inner component gui.
	 * @return The EmptyBorder object that was set up.
	 */
	public EmptyBorder boardBorderSetup() {
		//padding for the board itself
		padding = 5;
		border = new EmptyBorder(padding, padding, padding, padding);
		return border;
	}

	/**
	 * Handles an item on the board being clicked, either setting it as the selected item or the destination item.
	 * This then performs a move on the board model and updates the GUI.
	 * @param event The event sent by the clicked item.
	 */
	@Override
	public void onItemClick(ItemClickEvent event) {
		logger.trace(event.coordinate);
		if (selectedItem == null) {
			selectedItem = event.coordinate;
			logger.trace("set selected");
		} else {
			if (event.coordinate.equals(selectedItem)) {
				logger.trace("selected same as destination");
				return;
			}
			destinationItem = event.coordinate;

			logger.trace("set destination");
			try {
				logger.trace("attempting");
				board.move(selectedItem, destinationItem); // try moving the selected item to destination
				moveHistory.addState(board);
				logger.trace("successful");
			} catch (NonMovableItemException | BoardItemNotEmptyException |  SlideOutOfBoundsException | NonSlideableException |  SlideHitElevatedException |
					NonJumpableException | SlideHitObstacleException | JumpFailedNoObstacleException | HoleIsEmptyException | JumpFailedOutOfBoundsException | IllegalArgumentException e) {
				logger.debug(e);
				JOptionPane.showMessageDialog(boardPanel, e.getMessage(), "Exception!", 0);
			} catch (Exception e) {
				logger.error(e);
			} finally { // clear the selections and update board
				selectedItem = null;
				destinationItem = null;
				this.updateBoard();
			}
		}
	}

	/**
	 * Undoes a move, retrieving it from the move history.
	 */
	public void undoMove(){
		try {
			Board recalledMove = moveHistory.getUndoBoard();
			this.board = recalledMove;
			updateBoard();
		}
		catch(Exception e){
			logger.error(e);
		}
	}

	/**
	 * Redoes a move after an undo, retrieving it from the move history.
	 */
	public void redoMove(){
		try {
			Board recalledMove = moveHistory.getRedoBoard();
			this.board = recalledMove;
			updateBoard();
		}
		catch(Exception e){
			logger.error(e);
		}
	}

	// Linked to the button to reset the board
	Action newGameAction = new AbstractAction("New") {
		@Override
		public void actionPerformed(ActionEvent e) {
			setupNewGame();
		}
	};

	// Linked to the button to perform an undo
	Action undoLastAction = new AbstractAction("Undo") {
		@Override
		public void actionPerformed(ActionEvent e) {
			undoMove();
		}
	};

	// Linked to the button to perform a redo
	Action redoLastAction = new AbstractAction("Redo") {

		@Override
		public void actionPerformed(ActionEvent e) {
			redoMove();
		}
	};


}
