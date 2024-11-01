package org.example;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageLabel extends JLabel {
    private ImageIcon imageIcon;

    public ImageLabel(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        setPreferredSize(new Dimension(300, 250)); // Set preferred size
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageIcon != null) {
            // Scale the image to fit the label
            Image scaledImage = imageIcon.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(scaledImage, 0, 0, this);
        }
    }

    public void setImageIcon(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
        repaint(); // Repaint the label when the image changes
    }
}