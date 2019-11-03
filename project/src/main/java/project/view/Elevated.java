package project.view;

import java.awt.*;

public class Elevated extends ContainerItem {
    private static Color color = GuiColor.DARK_GREEN;

    public Elevated(project.model.ContainerItem model) {
        super(model, color);
    }
}
