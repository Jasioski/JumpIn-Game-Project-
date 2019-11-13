package project.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import project.model.Board;

/**
 * GuiOuterFrame deals with the initialization of the the GUI board outer
 * components including the JToolBar i.e., menu bar. Makes call to the class
 * GuiInnerComponents initialize and render the inner components of GUI
 */
public class GuiOuterFrame {
	/**
	 * The frame used in the gui.
	 */
	private JFrame frame;

	/**
	 * The padding used in the borderlayout.
	 */
	protected int padding;

	/**
	 * The jpanel for the outer frame of the gui.
	 */
	private JPanel outerFrame;

	/**
	 * The toolbar that contains the game's menu options.
	 */
	private JToolBar tools;

	/**
	 * The GuiInnerComponents object that contains the inner part of the gui and handles the board.
	 */
	private GuiInnerComponents boardInnerComponents;

	/**
	 * Initializes the outer frame of a GUI using a given model board object.
	 * @param board The board that the gui should be initialized with.
	 */
	public GuiOuterFrame(Board board) {
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
		tools.add(boardInnerComponents.undoLastAction);
		tools.add(boardInnerComponents.redoLastAction);

		tools.addSeparator();

		tools.add(boardInnerComponents.getMessage());
		outerFrame.add(tools, BorderLayout.NORTH);
		outerFrame.add(boardInnerComponents.boardPanel);
		//boardInnerComponents.setupNewGame();
		createFrame();
	}

	/**
	 * Creates the Jframe containing all the contents of the gui.
	 */
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
