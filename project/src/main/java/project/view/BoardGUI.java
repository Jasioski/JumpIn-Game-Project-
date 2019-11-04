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

public class BoardGUI {
	protected Board board;
	protected GuiOuterFrame outerFrame;
	
	public BoardGUI(Board board){
		this.board = board;
		outerFrame = new GuiOuterFrame(this.board);
	}

	
}
