package project.view;

import project.model.Mushroom;
import project.model.Rabbit;

import javax.swing.*;
import java.awt.*;

public abstract class ContainerItem extends JPanel {

    public ContainerItem(project.model.ContainerItem model, Color color) {
        this.setLayout(new OverlayLayout(this));
        this.setOpaque(false);
        this.setBackground(Color.green);
        //check if empty
        if (model.getContainingItem().isPresent()) {
            project.model.Containable containable = model.getContainingItem().get();
            if (containable instanceof Rabbit) {
                // todo fix the null
                this.add(new project.view.Rabbit(false, null));
            }
            if (containable instanceof Mushroom) {
                this.add(new project.view.Mushroom(false));
            }
        }
        Circle circle = new Circle(color);
        this.add(circle);
    }
}
