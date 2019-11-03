package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.*;

public class BoardGUI {

	// Used to set colors for layout
	private static final Color BROWN = new Color(153, 102, 0);
	private static final Color DARK_GREEN = new Color(0, 153, 0);
	private static final Color VERY_DARK_GREEN = new Color(0, 102, 0);

	// TODO: does this need to be a jpanel
	// TODO: separate into different class
	private JPanel outerFrame;
	private JPanel boardPanel;

	private JButton[][] jumpInBoardSquares;
	private JLabel message;
	private static final String COLS = "ABCDEFGH";

	private Map<String,ImageIcon> imageResources;

	public BoardGUI (int width, int height) {
		imageResources = new HashMap<String,ImageIcon>();

		// TODO: pass in a board as argument
		jumpInBoardSquares = new JButton[height][width];

		// todo: extract
		int padding = 3;
		outerFrame = new JPanel(new BorderLayout(padding, padding));

		message = new JLabel();


		initializeGui();

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
			imageResources.put("brownRabbit", new ImageIcon("images" +
					"/brownRabbit.png"));
			imageResources.put("whiteRabbit", new ImageIcon("images" +
					"/whiteRabbit.png"));
			imageResources.put("greyRabbit", new ImageIcon("images" +
					"/greyRabbit.png"));

			imageResources.put("mushroom", new ImageIcon("images" +
					"/mushroom.png"));
			imageResources.put("foxHead", new ImageIcon("images" +
					"/foxHead.png"));
			imageResources.put("foxTail", new ImageIcon("images" +
					"/foxTail.png"));
		} catch (Exception e) {
			// todo: use logger
			System.out.println("Image Loading failed" + e);
			e.printStackTrace();
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

		message.setText("Ready to play?");

		tools.add(message);

		outerFrame.add(new JLabel(""), BorderLayout.LINE_START);

		boardPanel = new JPanel(new GridLayout(6, 6));
		boardPanel.setBorder(new LineBorder(Color.BLACK));
		outerFrame.add(boardPanel);

		// create the JumpIn board squares
		Insets buttonMargin = new Insets(0, 0, 0, 0);
		for (int ii = 0; ii < jumpInBoardSquares.length; ii++) {
			for (int jj = 0; jj < jumpInBoardSquares[ii].length; jj++) {

				// adding green circle at the centre of the button
				JButton b = new JButton() {
					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						int nGap = 10;
						int nXPosition = nGap;
						int nYPosition = nGap;
						int nWidth = getWidth() - nGap * 2;
						int nHeight = getHeight() - nGap * 2;

						g.setColor(VERY_DARK_GREEN);
						g.drawOval(nXPosition, nYPosition, nWidth, nHeight);
						g.fillOval(nXPosition, nYPosition, nWidth, nHeight);
						

					}
				};

				b.setHorizontalAlignment(JLabel.CENTER);
				b.setVerticalAlignment(JLabel.CENTER);
				b.setMargin(buttonMargin);

				// Add 64x64 px in size, so we'll
				// 'fill this in' using icon..
				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
				b.setIcon(icon);
				// Sets the elevated green block
				if (jj % 2 == 0 && ii % 2 == 0) {

					b.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
					b.setBackground(DARK_GREEN);
					// sets the holes
					if (jj == ii || ((jj == jumpInBoardSquares[ii].length - 1) && (ii == 0))
							|| ((ii == jumpInBoardSquares.length - 1) && (jj == 0))) {

						// adding brown color at the centre of the button
						b = new JButton() {
							@Override
							protected void paintComponent(Graphics g) {
								super.paintComponent(g);
								int nGap = 10;
								int nXPosition = nGap;
								int nYPosition = nGap;
								int nWidth = getWidth() - nGap * 2;
								int nHeight = getHeight() - nGap * 2;

								g.setColor(BROWN);
								g.drawOval(nXPosition, nYPosition, nWidth, nHeight);
								g.fillOval(nXPosition, nYPosition, nWidth, nHeight);

							}
						};
						b.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
						b.setBackground(DARK_GREEN);
					}
				} else {
					// sets all non-elevated blocks
					b.setBackground(DARK_GREEN);
				}
				jumpInBoardSquares[jj][ii] = b;
			}
		}

		// fill the board
		boardPanel.add(new JLabel(""));
		// fill the top row
		for (int ii = 0; ii < 5; ii++) {
			boardPanel.add(new JLabel(COLS.substring(ii, ii + 1), SwingConstants.CENTER));
		}
		// fill the bottom row
		for (int ii = 0; ii < 5; ii++) {
			for (int jj = 0; jj < 5; jj++) {
				switch (jj) {
				case 0:
					boardPanel.add(new JLabel("" + (ii + 1), SwingConstants.CENTER));
				default:
					boardPanel.add(jumpInBoardSquares[jj][ii]);
				}
			}
		}
	}

	/**
	 * Initializes the pieces on the board
	 */
	private void setupNewGame() {
		// TODO: all of this should be rewritten based on a model
		message.setText("Make your move!");

		JButton j2=new JButton("");
		j2.setBackground(DARK_GREEN);
		j2.setIcon(imageResources.get("rabbitBrown"));
		jumpInBoardSquares[2][4].add(j2);
		
		
		JButton j3 =new JButton("");
		j3.setBackground(DARK_GREEN);
		j3.setIcon(imageResources.get("mushroom"));
		jumpInBoardSquares[0][4].add(j3);
	}

	public JComponent getJumpInBoard() {
		return boardPanel;
	}

	public JComponent getGui() {
		return outerFrame;
	}
}