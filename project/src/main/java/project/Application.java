package project;

import project.model.Board;
import project.view.ImageResources;
import project.model.DefaultBoard;
import project.view.GuiOuterFrame;

/**
 * GUI Application for JumpIn
 * This is the entrypoint for the GUI game
 */
public class Application {

    /**
     * State of the board
     */
    private Board board;
    /**
     * Root GUI component
     */
    private GuiOuterFrame frame;

    Application() {
        // Ensure resources are loaded;
        ImageResources.getInstance();

        board = new DefaultBoard().getBoard();

        frame = new GuiOuterFrame(board);
    }

    @SuppressWarnings("PMD")
    public static void main(String[] args) {
        new Application();
    }
}
