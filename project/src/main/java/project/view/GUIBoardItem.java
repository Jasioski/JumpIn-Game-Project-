package project.view;

import project.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

//todo - rename this class
public class GUIBoardItem extends JPanel implements ActionListener {
    private project.model.BoardItem item;

    //todo - figure out this one
    private static final Color BROWN = new Color(153, 102, 0);
    private static final Color DARK_GREEN = new Color(0, 153, 0);
    private static final Color VERY_DARK_GREEN = new Color(0, 102, 0);
    private BufferedImage image;
    private JButton iconButton;

    public GUIBoardItem(BoardItem item) {
        this.item = item;
        this.setBackground(DARK_GREEN);


        this.setLayout(new OverlayLayout(this));

        // todo add borders

        // TODO: do all the other items
        // TODO: maybe do colored rabbits

        if (item instanceof project.model.Rabbit) {
            System.out.println("We found a rabbit");
            this.add(new Rabbit());

        }
        else if (item instanceof project.model.Hole) {
            Circle circle = new Circle(BROWN);
            project.model.Hole hole = (project.model.Hole) item;
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
            this.add(new Hole(hole));
        }

        else if (item instanceof EmptyBoardItem) {
            Circle circle = new Circle(VERY_DARK_GREEN);
            this.add(circle);
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        } else if (item instanceof project.model.ElevatedBoardItem) {
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
            ElevatedBoardItem elevatedBoardItem = (ElevatedBoardItem) item;
            this.add(new Elevated(elevatedBoardItem));
        }
        else if (item instanceof project.model.Fox)  {
            System.out.println("found a fox");
            this.add(new Fox());
        }

        else {
            System.out.println("Unsupported type" + item);
        }

        //todo - figure out optimal BG color
        this.setBackground(GuiColor.DARK_GREEN);
        this.setSize(100,100);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //todo - implement action based on what type of boardItem this is
        System.out.println("button press test");
     //   if (this instanceof )
    }

}
