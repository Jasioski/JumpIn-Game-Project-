package project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIBoardItem extends JButton implements ActionListener {
    private BoardItem item;

    public GUIBoardItem(BoardItem item) {
        this.item = item;


        // TODO: do all the other items
        // TODO: maybe do colored rabbits
        if (item.getClass() == Rabbit.class) {
            ImageIcon icon = (ImageIcon) ImageResources.getResources().get("brownRabbit");
            this.setIcon(icon);
        }

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("clicked a button mofo");
    }

}
