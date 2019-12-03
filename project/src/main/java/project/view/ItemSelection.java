package project.view;

import javax.swing.*;

public class ItemSelection {

    public void show(JFrame frame) {

        String[] possibilities = {
                "Empty Item",
                "Rabbit",
                "Mushroom",
                "Empty Hole",
                "Empty Elevated position",
                "Hole with a Rabbit",
                "Elevated with a rabbit",
                "Hole wiht a mushroom",
                "Elevated with a mushroom"
        };


        JOptionPane.showInputDialog(frame, "What board piece would you like " +
                "to place here?", "Item selector", JOptionPane.PLAIN_MESSAGE,
                null,possibilities, "Empty Item");

    }

}
