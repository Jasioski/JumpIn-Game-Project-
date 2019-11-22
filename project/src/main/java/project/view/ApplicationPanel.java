package project.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.tools.Tool;

import project.model.Board;

/**
 * 
 * GuiOuterFrame deals with the initialization of the the GUI board outer
 * components including the JToolBar i.e., menu bar. Makes call to the class
 * GuiInnerComponents initialize and render the inner components of GUI
 * 
 *
 */
public class ApplicationPanel {

	/**
	 * Used for visual layout
	 */
	private static final int PADDING = 3;

	/**
	 * Root panel to be attached to a higher level component
	 */
	private	JPanel outerFrame;

	private GuiInnerComponents boardInnerComponents;

	public ApplicationPanel(ToolBar toolBar, ItemClickListener listener) {

		boardInnerComponents = new GuiInnerComponents(listener);

		outerFrame = new JPanel(new BorderLayout(PADDING, PADDING));

		// TODO: this should not be changing here
		// the inner components should not be affecting the outer
		outerFrame.setBorder(new EmptyBorder(PADDING, PADDING, PADDING,
				PADDING));

		outerFrame.add(toolBar, BorderLayout.PAGE_START);

		outerFrame.add(boardInnerComponents.boardPanel);
	}

	public JPanel getPanel() {
		return this.outerFrame;
	}

	public void setBoard(Board board) {
		this.boardInnerComponents.updateBoard(board);
	}

}
