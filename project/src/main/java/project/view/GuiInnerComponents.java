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
public class GuiInnerComponents {

	//View layer
	/**
	 * Logger that logs results of moves.
	 */
	public static Logger logger = LogManager.getLogger(GuiInnerComponents.class);
	private ItemClickListener listener;
	/**
	 * Panel for the view layer.
	 */
	JPanel boardPanel;

	/**
	 * The border surrounding the board.
	 */
	private EmptyBorder border;

	/**
	 * Creates a gui for the inner components using an initial board state.
	 */
	public GuiInnerComponents(ItemClickListener listener) {
		boardInnerComponents();
		this.listener = listener;
	}

	private void boardInnerComponents() {
		boardPanel = new JPanel(new GridLayout(6, 6));
		boardPanel.setBorder(new LineBorder(Color.BLACK));
		boardPanel.setLayout(new GridLayout(5, 5));
	}

	// update method
	public void updateBoard(Board board) {
		boardPanel.removeAll();

		for (int row = 0; row < board.numberOfRows; row++) {
			for (int column = 0; column < board.numberOfColumns; column++) {
				BoardItem modelItem = board.getItem(new Coordinate(row, column));
				JComponent viewItem = new GUIBoardItem(new Coordinate(row,
						column), modelItem, this.listener);
				this.boardPanel.add(viewItem);
			}
		}

		boardPanel.repaint();
		boardPanel.revalidate();
		// updates the visuals
	}
}
