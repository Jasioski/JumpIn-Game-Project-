package project;

import javax.swing.*;

public class Application {

    //todo: private static int
    static int WIDTH = 5;
    static int HEIGHT = 5;

    public static void main(String[] args) {

        ImageResources.getResources();
        BoardGUI boardGUI = new BoardGUI(WIDTH,HEIGHT);

        // Create the baord
        JFrame frame = new JFrame("JumpIn'");

        frame.add(boardGUI.getGui());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Whether to use the native windowing systems default location
        frame.setLocationByPlatform(true);

        // Removes padding of the frame
        frame.pack();

        // Don't allow resizing
        frame.setResizable(false);

        // Show the application
        frame.setVisible(true);
    }
};
