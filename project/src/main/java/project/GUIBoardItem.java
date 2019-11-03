package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

//todo - rename this class
public class GUIBoardItem extends JButton implements ActionListener {
    private BoardItem item;

    //todo - figure out this one
    private static final Color BROWN = new Color(153, 102, 0);
    private static final Color DARK_GREEN = new Color(0, 153, 0);
    private static final Color VERY_DARK_GREEN = new Color(0, 102, 0);
    private BufferedImage image;

    public GUIBoardItem(BoardItem item) {
        this.item = item;
        this.setBackground(DARK_GREEN);

        // TODO: do all the other items
        // TODO: maybe do colored rabbits
        if (item.getClass() == Rabbit.class) {
           // ImageIcon icon = (ImageIcon) ImageResources.getResources().get("brownRabbit");
            image = ImageResources.getInstance().getResources().get("brownRabbit");
           // this.setIcon(icon);

        }

        this.addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) { //this is adding green circle at center of button
        int nGap = 10;
        int nXPosition = nGap;
        int nYPosition = nGap;
        int nWidth = getWidth() - nGap * 2;
        int nHeight = getHeight() - nGap * 2;

        g.setColor(VERY_DARK_GREEN);
        g.drawOval(nXPosition, nYPosition, nWidth, nHeight);
        g.fillOval(nXPosition, nYPosition, nWidth, nHeight);
        super.paintComponent(g);

        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("clicked a button mofo");
    }

}
