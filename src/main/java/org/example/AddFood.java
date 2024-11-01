package org.example;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AddFood extends JPanel {
    private ImageLabel foodLabel; // Use the custom ImageLabel
    private ImageIcon foodImage;
    private ImageIcon hoverImage;

    public AddFood(ImageIcon foodImage, ImageIcon hoverImage) {
        this.foodImage = foodImage;
        this.hoverImage = hoverImage;

        // Parent container
        this.setPreferredSize(new Dimension(300, 300));
        this.setLayout(new GridLayout(2, 1, 0, 0)); // 2 rows, 1 column

        // Food label
        foodLabel = new ImageLabel(foodImage); // Use the custom ImageLabel

        // Mouse listener for hover effect
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                foodLabel.setImageIcon(hoverImage); // Change icon immediately on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                foodLabel.setImageIcon(foodImage); // Reset to original image
            }
        });

        // Button to order quantity
        JButton orderButton = new JButton("Order");
        orderButton.setPreferredSize(new Dimension(100, 50)); // Set preferred size

        // Add components to the panel
        this.add(foodLabel);
        this.add(orderButton);
    }
}