package project.view;

import project.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

//todo - rename this class
public class GUIBoardItem extends JPanel implements ActionListener, MouseListener{
    private project.model.BoardItem item;

    //todo - figure out this one
    private static final Color BROWN = new Color(153, 102, 0);
    private static final Color DARK_GREEN = new Color(0, 153, 0);
    private static final Color VERY_DARK_GREEN = new Color(0, 102, 0);

    ItemClickListener listener;
    private Coordinate coordinate;

    public GUIBoardItem(Coordinate coordinate, BoardItem item,
                        ItemClickListener listener) {
        this.listener = listener;
        this.item = item;
        this.setBackground(DARK_GREEN);

        this.setLayout(new OverlayLayout(this));

        // todo add borders

        // TODO: do all the other items
        // TODO: maybe do colored rabbits

        if (item instanceof project.model.Rabbit) {
            this.coordinate = coordinate;
            this.add(new Rabbit(this));
        }
        else if (item instanceof project.model.Mushroom) {
            this.coordinate = coordinate;
            this.add(new Mushroom());
        }
        else if (item instanceof project.model.Hole) {
            this.coordinate = coordinate;
            project.model.Hole hole = (project.model.Hole) item;
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
            this.add(new Hole(hole));
        }

        else if (item instanceof project.model.ElevatedBoardItem) {
            this.coordinate = coordinate;
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
            ElevatedBoardItem elevatedBoardItem = (ElevatedBoardItem) item;
            this.add(new Elevated(elevatedBoardItem));
        }
        else if (item instanceof EmptyBoardItem) {
            this.coordinate = coordinate;
            Circle circle = new Circle(VERY_DARK_GREEN);
            this.add(circle);
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        }
        else if (item instanceof project.model.Fox)  {
            this.coordinate = coordinate;
            this.add(new Fox());
        }

        else {
            System.out.println("Unsupported type" + item);
        }

        //todo - figure out optimal BG color
        this.setBackground(GuiColor.DARK_GREEN);
        this.setSize(100,100);
        //this.addMouseListener(this);
        this.setListeners(this);
    }

    public void setListeners(JComponent parent) {
        Component children[] = parent.getComponents();

        if (!(children.length == 0)) {
            for (Component child : children) {
                if (child instanceof JComponent) {
                    child.addMouseListener(this);
                    this.setListeners((JComponent) child);
                }
            }
        }
    }

    //todo - fix these duplications
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ItemClickEvent event = new ItemClickEvent(this.coordinate);
        System.out.println("click event generated");
        System.out.println(item);
        this.listener.onItemClick(event);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        ItemClickEvent event = new ItemClickEvent(this.coordinate);
        System.out.println("click event generated");
        System.out.println(item);
        this.listener.onItemClick(event);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        ItemClickEvent event = new ItemClickEvent(this.coordinate);
        System.out.println("click event generated");
        System.out.println(item);
        this.listener.onItemClick(event);

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
