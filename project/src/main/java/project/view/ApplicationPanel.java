package project.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

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

	public ApplicationPanel(Board board) {

		GuiInnerComponents boardInnerComponents = new GuiInnerComponents(board);

		outerFrame = new JPanel(new BorderLayout(PADDING, PADDING));

		// TODO: this should not be changing here
		// the inner components should not be affecting the outer
		outerFrame.setBorder(boardInnerComponents.boardBorderSetup());

		// Add the tools dialog to the beginning of the frame

		// TODO: Refactor tool bar into its own classjK;w
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);

		outerFrame.add(tools, BorderLayout.PAGE_START);

		tools.add(boardInnerComponents.newGameAction);
		tools.add(boardInnerComponents.undoLastMove);
		tools.add(boardInnerComponents.redoLastMove);

		tools.addSeparator();

		// TODO: this two way binding should not be happening
		tools.add(boardInnerComponents.getMessage());
		outerFrame.add(boardInnerComponents.boardPanel);
	}

	public JPanel getPanel() {
		return this.outerFrame;
	}
}
