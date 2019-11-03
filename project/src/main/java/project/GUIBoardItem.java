package project;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

//todo - rename this class
public class GUIBoardItem extends JPanel implements ActionListener {
    private BoardItem item;

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

        if (item instanceof Rabbit) {
           // ImageIcon icon = (ImageIcon) ImageResources.getResources().get("brownRabbit");
            this.iconButton = new JButton();
            image = ImageResources.getInstance().getResources().get("brownRabbit");

            this.iconButton.setIcon(new ImageIcon(image));
            this.iconButton.setAlignmentY(JComponent.CENTER_ALIGNMENT);
            this.iconButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            this.iconButton.setOpaque(false);
            this.iconButton.setContentAreaFilled(false);
            this.iconButton.setBorderPainted(false);
            this.iconButton.setBackground(VERY_DARK_GREEN);

            this.add(iconButton);
            Circle circle = new Circle(VERY_DARK_GREEN);
            this.add(circle);
        }
        else if (item instanceof Hole) {
            Circle circle = new Circle(BROWN);
            Hole hole = (Hole) item;

            if (hole.getContainingItem().isPresent()) {
                System.out.println("found a rabbit in a hole");
                if (hole.getContainingItem().get() instanceof Rabbit) {
                    image = ImageResources.getInstance().getResources().get("brownRabbit");

                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(image));

                    button.setAlignmentY(JComponent.CENTER_ALIGNMENT);
                    button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false);
                    button.setBackground(VERY_DARK_GREEN);

                    this.add(button);
                }

                if (hole.getContainingItem().get() instanceof Mushroom) {
                    image = ImageResources.getInstance().getResources().get(
                            "mushroom");

                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(image));

                    button.setAlignmentY(JComponent.CENTER_ALIGNMENT);
                    button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false);
                    button.setBackground(VERY_DARK_GREEN);

                    this.add(button);
                }
            }

            this.add(circle);
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));
        }

        else if (item instanceof EmptyBoardItem) {
            Circle circle = new Circle(VERY_DARK_GREEN);
            this.add(circle);
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        } else if (item instanceof ElevatedBoardItem) {
            this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 5));

            ElevatedBoardItem elevatedBoardItem = (ElevatedBoardItem) item;

            if (elevatedBoardItem.getContainingItem().isPresent()) {
                System.out.println("found a rabbit in a hole");
                if (elevatedBoardItem.getContainingItem().get() instanceof Rabbit) {
                    image = ImageResources.getInstance().getResources().get("brownRabbit");

                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(image));

                    button.setAlignmentY(JComponent.CENTER_ALIGNMENT);
                    button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false);
                    button.setBackground(VERY_DARK_GREEN);

                    this.add(button);
                }

                if (elevatedBoardItem.getContainingItem().get() instanceof Mushroom) {
                    image = ImageResources.getInstance().getResources().get(
                            "mushroom");

                    JButton button = new JButton();
                    button.setIcon(new ImageIcon(image));

                    button.setAlignmentY(JComponent.CENTER_ALIGNMENT);
                    button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                    button.setOpaque(false);
                    button.setContentAreaFilled(false);
                    button.setBorderPainted(false);
                    button.setBackground(VERY_DARK_GREEN);

                    this.add(button);
                }
            }
        }
        else if (item instanceof Fox)  {
            System.out.println("found a fox");
            this.iconButton = new JButton();
            image = ImageResources.getInstance().getResources().get(
                    "foxHead");

            this.iconButton.setIcon(new ImageIcon(image));
            this.iconButton.setAlignmentY(JComponent.CENTER_ALIGNMENT);
            this.iconButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            this.iconButton.setOpaque(false);
            this.iconButton.setContentAreaFilled(false);
            this.iconButton.setBorderPainted(false);
            this.iconButton.setBackground(VERY_DARK_GREEN);

            this.add(iconButton);
        }

        else {
            System.out.println("Unsupported type" + item);
        }


        this.setBackground(Color.GREEN);
        this.setSize(100,100);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        System.out.println("clicked a button mofo");
    }

}
