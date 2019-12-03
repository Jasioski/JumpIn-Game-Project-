package project.view;

import javax.swing.*;
import java.awt.*;

/**
 * The toolbar at the top of the GUI containing the files.
 */
public class ToolBar extends JPanel {

    /**
     * The message at the top of the toolbar.
     */
    private JLabel message;

    /**
     *  Creates a toolbar with all of its Action options.
     * @param newGame The newgame action.
     * @param undo The undo action.
     * @param redo The redo action.
     * @param save The save action.
     * @param load The load action.
     */
    public ToolBar(Action newGame, Action undo, Action redo, Action save, Action load) {

        JToolBar tools = new JToolBar();
        tools.setFloatable(false);

        tools.addSeparator();

        tools.add(newGame);
        tools.add(undo);
        tools.add(redo);
        tools.add(save);
        tools.add(load);

        message = new JLabel();
        tools.add(message);

        this.add(tools, BorderLayout.PAGE_START);
    }

    /**
     * Sets the message shown in the toolbar.
     * @param msg The message being set.
     */
    public void setMessage(String msg) {
        message.setText(msg);
    }

}
