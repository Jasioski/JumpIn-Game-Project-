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

public class BoardGUI implements ItemClickListener {

	public static Logger logger = LogManager.getLogger(BoardGUI.class);
	// View Layer
	// TODO: separate into different class
	private JPanel outerFrame;
	private JPanel boardPanel;

	private JLabel message;

	// Model Layer
	private Board board;

	private Coordinate selectedItem;
	private Coordinate destinationItem;

	public BoardGUI (Board board) {
		this.board = board;

		// todo: extract
		int padding = 3;
		outerFrame = new JPanel(new BorderLayout(padding, padding));

		message = new JLabel();


		initializeGui();

		this.setupNewGame();
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
						new GUIBoardItem(new Coordinate(row, column), modelItem,
								this);
				this.boardPanel.add(viewItem);
			}
		}

		boardPanel.repaint();
		boardPanel.revalidate(); //updates the visuals
        if (board.getCurrentGameState() == GameState.SOLVED) {
            JOptionPane.showMessageDialog(boardPanel, "Congratulations! \nYou Won!");
        }
	}

	/**
	 * Initializes the pieces on the board
	 */
	private void setupNewGame() {
		message.setText("Make your move!");
		this.board = new DefaultBoard();

		this.updateBoard();
	}

	@Override
	public void onItemClick(ItemClickEvent event) {
		logger.info(event.coordinate);
		if(selectedItem == null) {
			selectedItem = event.coordinate;
			logger.trace("set selected");
		}
		else {
		    if (event.coordinate.equals(selectedItem)) {
		        logger.trace("selected same as destination");
		        return;
		    }
			destinationItem = event.coordinate;

			logger.trace("set destination");
			try {
				logger.info("attempting");
				board.move(selectedItem, destinationItem); //try moving the selected item to destination
				logger.info("successful");
			} catch (Exception e) {
				logger.error(e);
				JOptionPane.showMessageDialog(boardPanel, e.getMessage(), "Exception!", 0);
			}
			finally { //clear the selections and update board
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