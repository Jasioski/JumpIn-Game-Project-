package project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;

public class ImageResources {

    private static ImageResources imageResources;
    private static Map<String, BufferedImage> imagesBank;

    private ImageResources() {

        imagesBank = new HashMap<>();

        try {

            InputStream stream = this.getClass().getResourceAsStream(
                    "/images" +
                "/brownRabbit" +
                    ".png");

            BufferedImage image = ImageIO.read(stream);

            imagesBank.put("brownRabbit", image);

            imagesBank.put("whiteRabbit", ImageIO.read(this.getClass().getResourceAsStream("/images" +
                    "/whiteRabbit.png")));
            imagesBank.put("greyRabbit", ImageIO.read(this.getClass().getResourceAsStream("/images" +
                    "/greyRabbit.png")));

            imagesBank.put("mushroom", ImageIO.read(this.getClass().getResourceAsStream("/images" +
                    "/mushroom.png")));
            imagesBank.put("foxHead", ImageIO.read(this.getClass().getResourceAsStream("/images" +
                    "/foxHead.png")));
            imagesBank.put("foxTail", ImageIO.read(this.getClass().getResourceAsStream("/images" +
                    "/foxTail.png")));
        }

        catch(Exception e) {
            // todo: use logger
            System.out.println("Image Loading failed" + e);
            e.printStackTrace();
        }
    }

    public static ImageResources getInstance() {

        if (imageResources == null) {
            imageResources = new ImageResources();

        }

        return imageResources;
    }

    public Map<String, BufferedImage> getResources() {
        return imagesBank;
    }

}
