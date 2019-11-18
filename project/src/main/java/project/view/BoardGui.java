package project.view;

import project.modelRefactored.DefaultBoard;
import project.modelRefactored.Board;

/**
 * This class initializes the GUI of the JumpIn' game.
 * Default constructor takes a Board and passes it to the class GuiOuterFrame.
 */
public class BoardGui {
	/**
	 * The board used in the gui.
	 */
	protected Board board;

	/**
	 * The outer frame of the gui.
	 */
	protected GuiOuterFrame outerFrame;

	/**
	 * Creates the gui, instantiating the default board and creating the gui's frame.
	 */
	public BoardGui() {
		ImageResources.getInstance();
		DefaultBoard defaultBoard = new DefaultBoard();
		board = defaultBoard.getDefaultBoard();

		outerFrame = new GuiOuterFrame(this.board);
	}
}

