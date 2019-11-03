package project.view;

import javax.swing.*;

public abstract class ButtonBoardItem extends JPanel {
    protected JButton iconButton;
    public ButtonBoardItem(boolean renderItem) {
        System.out.println("Abstract class");
        this.setLayout(new OverlayLayout(this));
        iconButton = new JButton();
        this.iconButton.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        this.iconButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        this.iconButton.setOpaque(false);
        this.iconButton.setContentAreaFilled(false);
        this.iconButton.setBorderPainted(false);
        this.iconButton.setBackground(GuiColor.VERY_DARK_GREEN);
        this.add(iconButton);
        if (renderItem) {
            Circle circle = new Circle(GuiColor.VERY_DARK_GREEN);
            this.add(circle);
        }
        this.setOpaque(false);
    }
}
