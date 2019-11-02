package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JINButton extends JButton implements ActionListener {
    ImageIcon fox;
    ImageIcon rabbit;
    ImageIcon hole;
    ImageIcon empty;
    ImageIcon elevated;
    ImageIcon mushroom;

    public JINButton(String s) {
        super("ButtonTest" + s);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //TODO: implement handler that acts different based on what ImageIcon it is
    }
}
