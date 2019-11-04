package project.view;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Rabbit extends ButtonBoardItem {

    public Rabbit(boolean renderBackground) {
        super(renderBackground);
        BufferedImage image = ImageResources.getInstance().getResources().get("brownRabbit");
        ImageIcon icon = new ImageIcon(image);
        this.iconButton.setIcon(icon);
    }

    public Rabbit() {
        this(true);
    }
}
