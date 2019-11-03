package project.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageResources {

    private static Logger logger = LogManager.getLogger(ImageResources.class);
    private static ImageResources imageResources;
    private static Map<String, BufferedImage> imagesBank;

    private ImageResources() {
        imagesBank = new HashMap<>();
        try {
            imagesBank.put("brownRabbit", ImageIO.read(this.getClass().getResourceAsStream("/images" +
                    "/brownRabbit.png")));
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
            logger.error("Image Loading failed" + e);
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
