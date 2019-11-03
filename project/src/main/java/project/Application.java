package project;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {

        ImageResources.getInstance();
        BoardGUI boardGUI = new BoardGUI(new DefaultBoard());

        // Create the baord
        JFrame frame = new JFrame("JumpIn'");

        frame.add(boardGUI.getGui());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Whether to use the native windowing systems default location
        frame.setLocationByPlatform(true);

        // Removes padding of the frame
        frame.pack();

        // Don't allow resizing
//        frame.setResizable(false);

        // Show the application
        frame.setVisible(true);
    }
};
