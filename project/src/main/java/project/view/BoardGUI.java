package project.view;

import project.DefaultBoard;
import project.model.Board;
import project.model.BoardItem;
import project.model.exceptions.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.*;

public class BoardGUI implements ItemClickListener {

	// View Layer
	// TODO: separate into different class
	private JPanel outerFrame;
	private JPanel boardPanel;

	private JLabel message;

	// Model Layer
	private Board board;

	private BoardItem selectedItem;
	private BoardItem destinationItem;

	public BoardGUI (Board board) {
		this.board = board;

		// todo: extract
		int padding = 3;
		outerFrame = new JPanel(new BorderLayout(padding, padding));

		message = new JLabel();


		initializeGui();

		this.setupNewGame();

		// Reset board
		Action newGameAction = new AbstractAction("New") {

			@Override
			public void actionPerformed(ActionEvent e) {
				setupNewGame();
			}
		};
	}

	public void loadImages() {
		try {
		} catch (Exception e) {
		}
	}

	public void initializeGui() {
		// create the images for the pieces
        loadImages();

		// set up the main GUI
		int padding = 5;
		EmptyBorder border = new EmptyBorder(padding, padding, padding,
				padding);
		outerFrame.setBorder(border);

		// TODO: move the toolbar code into its own class
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);

		// Add the tools dialog to the beginning of the frame
		outerFrame.add(tools, BorderLayout.PAGE_START);

		Action newGameAction = new AbstractAction("New") {

			@Override
			public void actionPerformed(ActionEvent e) {
				setupNewGame();
			}
		};
		tools.add(newGameAction);

		tools.addSeparator();

		tools.add(message);

		outerFrame.add(new JLabel(""), BorderLayout.LINE_START);

		//todo - Figure out what is going on here...
		boardPanel = new JPanel(new GridLayout(6, 6));
		boardPanel.setBorder(new LineBorder(Color.BLACK));
		outerFrame.add(boardPanel);

		boardPanel.setLayout(new GridLayout(5, 5));
	}

	// update method
	private void updateBoard() {
		boardPanel.removeAll();

		for (int row = 0; row < this.board.getRows(); row++) {
			for (int column = 0; column < this.board.getColumns(); column++) {
				BoardItem modelItem = this.board.getItem(row, column);
				JComponent viewItem =
						new GUIBoardItem(modelItem, this);

				this.boardPanel.add(viewItem);
			}
		}

		boardPanel.repaint();
		boardPanel.revalidate();
	}

	/**
	 * Initializes the pieces on the board
	 */
	private void setupNewGame() {
		message.setText("Make your move!");

		this.board = new DefaultBoard();

		this.updateBoard();
	}

	public void onItemClick(ItemClickEvent event) {
		System.out.println(event.item);
		if(selectedItem == null) {
			selectedItem = event.item;
			System.out.println("set selected");
		}
		else {
			destinationItem = event.item;

			System.out.println("set destination");
			try {
				System.out.println("attempting");
				board.move(selectedItem, destinationItem);
				System.out.println("successful");
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				selectedItem = null;
				destinationItem = null;
				this.updateBoard();
			}
		}

	}

	public JComponent getGui() {
		return outerFrame;
	}
}