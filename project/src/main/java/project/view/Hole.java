package project.view;

import java.awt.*;

public class Hole extends ContainerItem {
    private static Color color = GuiColor.BROWN;

    public Hole(project.model.ContainerItem model) {
        super(model, color);
    }
}
