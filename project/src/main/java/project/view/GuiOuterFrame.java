package project.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import project.modelRefactored.Board;

/**
 * 
 * GuiOuterFrame deals with the initialization of the the GUI board outer
 * components including the JToolBar i.e., menu bar. Makes call to the class
 * GuiInnerComponents initialize and render the inner components of GUI
 * 
 *
 */
public class GuiOuterFrame {

	JFrame frame;
	protected int padding;
	
	JPanel outerFrame;
	private JToolBar tools;
	private GuiInnerComponents boardInnerComponents;

	GuiOuterFrame(Board board) {
		tools = new JToolBar();
		boardInnerComponents = new GuiInnerComponents(board);
		//padding value for the BorderLayout
		padding = 3;
		outerFrame = new JPanel(new BorderLayout(padding, padding));
		
		outerFrame.setBorder(boardInnerComponents.boardBorderSetup());

		tools.setFloatable(false);
		// Add the tools dialog to the beginning of the frame
		outerFrame.add(tools, BorderLayout.PAGE_START);
		tools.add(boardInnerComponents.newGameAction);
		tools.add(boardInnerComponents.undoLastMove);
		tools.add(boardInnerComponents.redoLastMove);

		tools.addSeparator();

		tools.add(boardInnerComponents.getMessage());
		outerFrame.add(tools, BorderLayout.NORTH);
		outerFrame.add(boardInnerComponents.boardPanel);
		createFrame();
	}

	protected void createFrame() {

		// Create the canvas
		frame = new JFrame("JumpIn'");
		frame.add(outerFrame);
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
