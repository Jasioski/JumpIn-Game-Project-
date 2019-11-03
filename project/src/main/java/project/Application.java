package project;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        BoardGUI boardGUI = new BoardGUI();

        // Create the baord
        JFrame frame = new JFrame("JumpIn'");

        frame.add(boardGUI.getGui());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Whether to use the native windowing systems default location
        frame.setLocationByPlatform(true);

        // Removes padding of the frame
        frame.pack();

        // ensures the minimum size is enforced.
        frame.setVisible(true);
    }
};
