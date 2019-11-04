package project.view;

import project.DefaultBoard;
import project.model.Board;
import project.model.BoardItem;
import project.model.Coordinate;
import project.model.GameState;

import java.awt.*;
import java.awt.event.ActionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import javax.swing.border.*;

/**
 * 
 * This class initializes the GUI of the JumpIn' game
 * Default component takes a Board and passes it to the class GuiOuterFrame
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

