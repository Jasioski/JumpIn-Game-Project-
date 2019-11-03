package project.view;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Rabbit extends ButtonBoardItem {

    public Rabbit(boolean renderBackground) {
        //todo - switch images back to image icon in map??
        super(renderBackground);
        BufferedImage image = ImageResources.getInstance().getResources().get("brownRabbit");
        ImageIcon icon = new ImageIcon(image);
        this.iconButton.setIcon(icon);
    }

    public Rabbit() {
        this(true);
    }
}
