package project.view;

import project.ApplicationMode;

import javax.swing.*;
import java.awt.*;

public class ToolBar extends JPanel {

    private JLabel message;

    public ToolBar(Action newGame, Action undo, Action redo,
                   Action switchMode, ApplicationMode mode) {

        JToolBar tools = new JToolBar();
        tools.setFloatable(false);


        if (mode != ApplicationMode.GAME_PLAY) {
            undo.setEnabled(false);
            redo.setEnabled(false);
        }

        tools.add(newGame);
        tools.add(undo);
        tools.add(redo);
        tools.add(switchMode);

        tools.addSeparator();

        message = new JLabel();
        tools.add(message);

        this.add(tools, BorderLayout.PAGE_START);
    }

    public void setMessage(String msg) {
        message.setText(msg);
    }


}
