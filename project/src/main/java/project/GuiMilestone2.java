package project;
import project.view.GUIBoardItem;

import javax.swing.*;
import java.awt.*;

public class GuiMilestone2 extends JFrame {
    JPanel p = new JPanel();
    DefaultBoard board = new DefaultBoard();

    public GuiMilestone2(int rows, int columns) {
        // Grid Layout Shape
        super("JumpIn GUI");
        setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200,850);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        p.setLayout(new GridLayout(rows, columns)); //5x5 grid by default
        p.setSize(1200, 800);

//        initializeBoard();

        add(p);
    }
//
//    //TODO: implement initialize board logic (similar to how we did it in main of the textbased game)
//    private void initializeBoard() {
//        //foo code. Wont be used in final version
//        for (int row = 0; row < board.getColumns(); row++) {
//            for (int column = 0; column < board.getColumns(); column++) {
//                GUIBoardItem button = new GUIBoardItem(board.getItem(row, column));
//                button.setPreferredSize(new Dimension((p.getWidth() / board.getColumns()), (p.getHeight() / board.getRows())));
//                p.add(button);
//            }
//        }
//    }

    //TODO: figure out if we should have the gui implemented in a similar fashion to lab5 or have a different implementation for it?
    @SuppressWarnings("PMD.UseVarargs")
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GuiMilestone2(5, 5);
            }
        });
    }
}
