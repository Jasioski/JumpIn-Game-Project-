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

	public static Logger logger =
            LogManager.getLogger(GuiInnerComponents.class);

	// View Layer
	JPanel boardPanel;

	private JLabel message;

	// Model Layer
	private Board board;
	private EmptyBorder border;
	private Coordinate selectedItem;
	private Coordinate destinationItem;

	private int padding; 
	public GuiInnerComponents(Board board) {
		this.board = board;
		message = new JLabel();
		boardInnerComponents();

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

		this.updateBoard();
	}

	public JLabel getMessage() {
		return this.message;
	}

	EmptyBorder boardBorderSetup() {
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
				board = board.move(selectedItem, destinationItem); // try moving the selected item to destination
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

	// Reset board
	Action newGameAction = new AbstractAction("New") {

		@Override
		public void actionPerformed(ActionEvent e) {
			setupNewGame();
		}
	};

}
