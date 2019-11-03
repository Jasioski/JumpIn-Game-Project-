package project;

import javax.swing.*;
import java.awt.*;

public class Circle extends JComponent {

    private Color color;

    public Circle (Color color) {
        this.color = color;
    }


    protected void paintComponent(Graphics g) { //this is adding green circle at center of button

        super.paintComponent(g);

        int nGap = 10;
        int nXPosition = nGap;
        int nYPosition = nGap;
        int nWidth = getWidth() - nGap * 2;
        int nHeight = getHeight() - nGap * 2;

        g.setColor(this.color);
        g.drawOval(nXPosition, nYPosition, nWidth, nHeight);
        g.fillOval(nXPosition, nYPosition, nWidth, nHeight);


    }
}
