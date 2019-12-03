package project.view;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {

    private JLabel message;

    public ToolBar(Action newGame, Action undo, Action redo,
                   Action createBoard) {

        JToolBar tools = new JToolBar();
        tools.setFloatable(false);

        tools.addSeparator();

        tools.add(newGame);
        tools.add(undo);
        tools.add(redo);
        tools.add(createBoard);

        message = new JLabel();
        tools.add(message);

        this.add(tools, BorderLayout.PAGE_START);
    }

    public void setMessage(String msg) {
        message.setText(msg);
    }


}
