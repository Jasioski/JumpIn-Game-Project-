package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

public class BoardGui {

	public static final Color BROWN = new Color(153, 102, 0);
	public static final Color DARK_GREEN = new Color(0, 153, 0);
	public static final Color VERY_DARK_GREEN = new Color(0, 102, 0);
	
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] jumpInBoardSquares = new JButton[5][5];
    private JPanel jumpInBoardGui;
    private final JLabel message = new JLabel(
            "Ready to play?");
    private static final String COLS = "ABCDEFGH";

    BoardGui() {
        initializeGui();
        Action newGameAction = new AbstractAction("New") {

            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        };
    }
    
    
    public ImageIcon rabbitBrown = new ImageIcon("../icons/3316539-64 (1).png");    
    public ImageIcon rabbitWhite = new ImageIcon("../icons/3316539-64.png");
    public ImageIcon rabbitGrey = new ImageIcon("../icons/iconfinder_icon_animal_coelho_3316539 (1).png");
    public ImageIcon mushroom = new ImageIcon("../icons/iconfinder_mushroom_fun_addigtion_drug_Shroom_3122423.png");
    public ImageIcon foxHead = new ImageIcon("../icons/iconfinder_fox_4591894.png");
    public ImageIcon foxTail = new ImageIcon("../icons/64.png");
    
    public final void initializeGui() {
        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        tools.add(new JButton("New")); // TODO - add functionality!
        tools.add(new JButton("Save")); // TODO - add functionality!
        tools.add(new JButton("Restore")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(new JButton("Resign")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(message);

        gui.add(new JLabel(""), BorderLayout.LINE_START);

        jumpInBoardGui = new JPanel(new GridLayout(0, 6));
        jumpInBoardGui.setBorder(new LineBorder(Color.BLACK));
        gui.add(jumpInBoardGui);

        // create the JumpIn board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < jumpInBoardSquares.length; ii++) {
            for (int jj = 0; jj < jumpInBoardSquares[ii].length; jj++) {
            	
            	//adding green circle at the centre of the button
                JButton b = new JButton(){
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
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                //Sets the elevated green block
                if (jj % 2 == 0 && ii % 2 == 0) {

                	b.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
                    b.setBackground(DARK_GREEN);
                    //sets the holes
                    if(jj == ii || ((jj == jumpInBoardSquares[ii].length -1) && (ii == 0))
                    		|| ((ii == jumpInBoardSquares.length -1) && (jj == 0))) {
                    	
                    	//adding brown color at the centre of the button
                        b = new JButton(){
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
                	//sets all non-elevated blocks
                    b.setBackground(DARK_GREEN);
                }
                jumpInBoardSquares[jj][ii] = b;
            }
        }

        //fill the board
        jumpInBoardGui.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 5; ii++) {
            jumpInBoardGui.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the bottom row
        for (int ii = 0; ii < 5; ii++) {
            for (int jj = 0; jj < 5; jj++) {
                switch (jj) {
                    case 0:
                        jumpInBoardGui.add(new JLabel("" + (ii + 1),
                                SwingConstants.CENTER));
                    default:
                        jumpInBoardGui.add(jumpInBoardSquares[jj][ii]);
                }
            }
        }
    }
    /**
     * Initializes the pieces on the board
     */
    private final void setupNewGame() {
        message.setText("Make your move!");
        // set up the black pieces
      
        jumpInBoardSquares[0][4].setIcon(rabbitGrey);
        
		/*
		 * for (int ii = 0; ii < STARTING_ROW.length; ii++) {
		 * chessBoardSquares[ii][1].setIcon(new ImageIcon(
		 * chessPieceImages[BLACK][PAWN])); } // set up the white pieces for (int ii =
		 * 0; ii < STARTING_ROW.length; ii++) { chessBoardSquares[ii][6].setIcon(new
		 * ImageIcon( chessPieceImages[WHITE][PAWN])); } for (int ii = 0; ii <
		 * STARTING_ROW.length; ii++) { chessBoardSquares[ii][7].setIcon(new ImageIcon(
		 * chessPieceImages[WHITE][STARTING_ROW[ii]])); }
		 */
    }

    public final JComponent getJumpInBoard() {
        return jumpInBoardGui;
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
            	BoardGui cb =
                        new BoardGui();

                JFrame f = new JFrame("JumpIn'");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}