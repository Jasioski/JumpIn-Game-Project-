package project.view;

import project.DefaultBoard;
import project.model.Board;

/**
 * 
 * This class initializes the GUI of the JumpIn' game
 * Default constructor takes a Board and passes it to the class GuiOuterFrame
 *
 */
public class BoardGui {
	protected Board board;
	protected GuiOuterFrame outerFrame;
	
	public BoardGui() {
		ImageResources.getInstance();
		this.board = new DefaultBoard();
		outerFrame = new GuiOuterFrame(this.board);
	}
}

