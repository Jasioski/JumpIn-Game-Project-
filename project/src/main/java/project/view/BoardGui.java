package project.view;

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
	
	public BoardGui(Board board){
		this.board = board;
		outerFrame = new GuiOuterFrame(this.board);
	}
}

