package project.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import project.DefaultBoard;
import project.model.Board;

public class GuiOuterFrame {
	
	JFrame frame;
	protected int padding ;
	private EmptyBorder border;
	JPanel outerFrame;
	private JToolBar tools; 
	private JLabel message;
	private Board board;
	private GuiInnerComponents boardInnerComponnets;
	
	GuiOuterFrame(Board board){
		this.board = board;
		tools = new JToolBar();
		message = new JLabel();
		boardInnerComponnets = new GuiInnerComponents(board);
		
		padding = 3;		
		outerFrame = new JPanel(new BorderLayout(padding, padding));
		
		padding = 5;
		border = new EmptyBorder(padding, padding, padding,	padding);
		outerFrame.setBorder(border);
		
		tools.setFloatable(false);
		// Add the tools dialog to the beginning of the frame
		outerFrame.add(tools, BorderLayout.PAGE_START);
		tools.add(boardInnerComponnets.newGameAction);

		tools.addSeparator();

		tools.add(message);

		//outerFrame.add(new JLabel("test123"), BorderLayout.LINE_START);
		outerFrame.add(tools, BorderLayout.NORTH);
		outerFrame.add(boardInnerComponnets.boardPanel);
		boardInnerComponnets.setupNewGame();
		createFrame();
	}
	protected void createFrame() {

        // Create the canvas
        frame = new JFrame("JumpIn'");
        frame.add(outerFrame);
        //frame.add(boardInnerComponnets.getGui());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Whether to use the native windowing systems default location
        frame.setLocationByPlatform(true);

        // Removes padding of the frame
        frame.pack();

        // Don't allow resizing
       frame.setResizable(false);

        // Show the application
        frame.setVisible(true);
	}
}
