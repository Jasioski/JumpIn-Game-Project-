package project.view;

import project.model.DefaultBoard;
import project.model.Board;

/**
 * 
 * This class initializes the GUI of the JumpIn' game
 * Default constructor takes a Board and passes it to the class GuiOuterFrame
 */
public class BoardGui {
	protected Board board;
	protected GuiOuterFrame outerFrame;
	
	public BoardGui() {
		ImageResources.getInstance();
		DefaultBoard defaultBoard = new DefaultBoard();
		board = defaultBoard.getBoard();

		outerFrame = new GuiOuterFrame(this.board);
	}
}

