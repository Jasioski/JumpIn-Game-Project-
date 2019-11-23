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
public class Board extends JPanel {

	private ItemClickListener listener;
	private project.model.Board board;

	/**
	 * Creates a gui for the inner components using an initial board state.
	 */
	public Board(ItemClickListener listener, project.model.Board board) {
		super(new GridLayout(board.numberOfRows,
				board.numberOfColumns));

		this.listener = listener;
		this.board = board;

		this.setBorder(new LineBorder(Color.BLACK));
		this.setLayout(new GridLayout(board.numberOfRows,
				board.numberOfColumns + 1));

		render();
	}

	private void render() {

		// Clear GUI State
		this.removeAll();

		// Rebuild GUI
		for (int row = 0; row < board.numberOfRows; row++) {
			for (int column = 0; column < board.numberOfColumns; column++) {
				BoardItem modelItem = board.getItem(new Coordinate(row, column));
				JComponent viewItem = new GUIBoardItem(new Coordinate(row,
						column), modelItem, this.listener);
				this.add(viewItem);
			}
		}

		// Render changes
		repaint();
		revalidate();
	}


	public void updateBoard(project.model.Board board) {
		this.board = board;

		render();
	}
}
