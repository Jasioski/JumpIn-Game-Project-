package project.view;

import project.modelRefactored.*;
import project.model.GameState;

import java.awt.*;
import java.awt.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.model.exceptions.*;
import project.solverRefactored.Solver;
import javax.swing.*;
import javax.swing.border.*;

/**
 * 
 * GuiInnerComponents initializes with the inner components of the GUI i.e., the board setup
 *
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
	private BoardHistory boardHistory;

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
		message = new JLabel();
		message.setText("Make your move!");
		this.boardHistory = new BoardHistory(board);
		boardInnerComponents();

		this.updateBoard();
	}

	private void boardInnerComponents() {
		boardPanel = new JPanel(new GridLayout(6, 6));
		boardPanel.setBorder(new LineBorder(Color.BLACK));
		boardPanel.setLayout(new GridLayout(5, 5));
	}

	// update method
	private void updateBoard() {
		boardPanel.removeAll();

		for (int row = 0; row < this.board.numberOfRows; row++) {
			for (int column = 0; column < this.board.numberOfColumns; column++) {
				BoardItem modelItem = this.board.getItem(new Coordinate(row, column));
				JComponent viewItem = new GUIBoardItem(new Coordinate(row, column), modelItem, this);
				this.boardPanel.add(viewItem);
			}
		}

		boardPanel.repaint();
		boardPanel.revalidate();
		// updates the visuals
		if (board.currentGameState == GameState.SOLVED) {
			int dialogButton = JOptionPane.YES_NO_OPTION;
			JOptionPane.showMessageDialog(boardPanel, "Congratulations! \nYou Won!", "Victory!", 1);
			int playAgain = JOptionPane.showConfirmDialog(boardPanel, "Do you want to play again?", "Reset?", dialogButton);
			if (playAgain == JOptionPane.YES_OPTION) {
				this.setupNewGame();
			}
		}
	}

	/**
	 * Initializes the pieces on the board
	 */
	void setupNewGame() {
		message.setText("Make your move!");
		DefaultBoard defaultBoard = new DefaultBoard();
		board = defaultBoard.getDefaultBoard();
		this.boardHistory = new BoardHistory(board);

		this.updateBoard();
	}

	public JLabel getMessage() {
		return this.message;
	}

	public EmptyBorder boardBorderSetup() {
		//padding for the board itself
		padding = 5;
		border = new EmptyBorder(padding, padding, padding, padding);
		return border;
	}

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
				Board newBoard = board.move(selectedItem, destinationItem); // try moving the selected item to destination
				boardHistory.addState(newBoard);
				board = newBoard;
				Solver.solve(board);
				logger.trace("successful");
			} catch (InvalidMoveException e) {
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
		Board recalledMove = boardHistory.getUndoBoard();
		this.board = recalledMove;
		updateBoard();
	}

	/**
	 * Redoes a move after an undo, retrieving it from the move history.
	 */
	public void redoMove(){
		try {
			Board recalledMove = boardHistory.getRedoBoard();
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
	Action undoLastMove = new AbstractAction("Undo") {
		@Override
		public void actionPerformed(ActionEvent e) {
			undoMove();
		}
	};

	// Linked to the button to perform a redo
	Action redoLastMove = new AbstractAction("Redo") {

		@Override
		public void actionPerformed(ActionEvent e) {
			redoMove();
		}
	};

}
