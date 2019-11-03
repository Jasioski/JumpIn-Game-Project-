package project;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ImageResources {

    private static Map<String, ImageIcon> imageResources;

    public static Map getResources() {

        if (imageResources == null) {
            imageResources = new HashMap<>();

            try {
                imageResources.put("brownRabbit", new ImageIcon("images" +
                        "/brownRabbit.png"));
                imageResources.put("whiteRabbit", new ImageIcon("images" +
                        "/whiteRabbit.png"));
                imageResources.put("greyRabbit", new ImageIcon("images" +
                        "/greyRabbit.png"));

                imageResources.put("mushroom", new ImageIcon("images" +
                        "/mushroom.png"));
                imageResources.put("foxHead", new ImageIcon("images" +
                        "/foxHead.png"));
                imageResources.put("foxTail", new ImageIcon("images" +
                        "/foxTail.png"));
            }

            catch(Exception e) {
                // todo: use logger
                System.out.println("Image Loading failed" + e);
                e.printStackTrace();
            }
        }

        return imageResources;
    }

}
