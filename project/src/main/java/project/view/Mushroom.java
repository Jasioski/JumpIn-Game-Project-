package project.view;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Mushroom extends ButtonBoardItem {

    public Mushroom(boolean renderBackground) {
        //todo - switch images back to image icon in map??
        super(renderBackground);
        BufferedImage image = ImageResources.getInstance().getResources().get("mushroom");
        ImageIcon icon = new ImageIcon(image);
        this.iconButton.setIcon(icon);
    }

    public Mushroom() {
        this(true);
    }
}
