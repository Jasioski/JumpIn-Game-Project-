package project.view;

import project.model.*;
import project.model.GameState;

import java.awt.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.*;

/**
 * 
 * GuiInnerComponents initializes with the inner components of the GUI i.e., the board setup
 *
 */
public class Board {

	private ItemClickListener listener;
	/**
	 * Panel for the view layer.
	 */
	JPanel boardPanel;

	/**
	 * Creates a gui for the inner components using an initial board state.
	 */
	public Board(ItemClickListener listener) {
		this.listener = listener;

		boardPanel = new JPanel(new GridLayout(6, 6));
		boardPanel.setBorder(new LineBorder(Color.BLACK));
		boardPanel.setLayout(new GridLayout(5, 5));
	}

	public void updateBoard(project.model.Board board) {
		// Clear GUI State
		boardPanel.removeAll();

		// Rebuild GUI
		for (int row = 0; row < board.numberOfRows; row++) {
			for (int column = 0; column < board.numberOfColumns; column++) {
				BoardItem modelItem = board.getItem(new Coordinate(row, column));
				JComponent viewItem = new GUIBoardItem(new Coordinate(row,
						column), modelItem, this.listener);
				this.boardPanel.add(viewItem);
			}
		}

		// Render changes
		boardPanel.repaint();
		boardPanel.revalidate();
	}
}
