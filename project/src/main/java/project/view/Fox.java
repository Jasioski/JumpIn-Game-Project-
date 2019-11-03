package project.view;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Fox extends ButtonBoardItem {

    public Fox() {
        //todo - switch images back to image icon in map??
        // todo fix  null
        super(false,null);
        BufferedImage image = ImageResources.getInstance().getResources().get("foxHead");
        ImageIcon icon = new ImageIcon(image);
        this.iconButton.setIcon(icon);
    }
}
