package project;

import project.model.Board;
import project.view.ImageResources;
import project.model.DefaultBoard;
import project.view.ApplicationPanel;

import javax.swing.*;

/**
 * GUI Application for JumpIn
 * This is the entrypoint for the GUI game
 */
public class Application extends JFrame {

    /**
     * State of the board
     */
    private Board board;
    /**
     * Root GUI component
     */
    private ApplicationPanel frame;

    Application() {
        super("JumpIn");

        // Ensure resources are loaded;
        ImageResources.getInstance();

        // Setup model
        board = new DefaultBoard().getBoard();

        initializeFrame();
    }

    private void initializeFrame() {
        // GUI Components
        frame = new ApplicationPanel(board);

        // Frame
        this.add(frame.getPanel());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Whether to use the native windowing systems default location
        this.setLocationByPlatform(true);

        // Removes padding of the frame
        this.pack();

        // Don't allow resizing
        this.setResizable(false);

        // Show the application
        this.setVisible(true);
    }


    @SuppressWarnings("PMD")
    public static void main(String[] args) {
        new Application();
    }
}
