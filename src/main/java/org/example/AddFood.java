package org.example;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import org.example.SQLQueries.*;

// TODO: Pick final AddFood function
// TODO: Refactor

public class AddFood extends JPanel {
  private ImageIcon foodImage;
  private ImageIcon hoverImage;
  private String nutritionFact;
  private Timer hoverTimer;
  private final int HOVER_DELAY = 300; // Delay in milliseconds

  private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

  public AddFood(int mealID) {
    // Retrieve food image and nutrition facts from database
    this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));
    this.nutritionFact = SQLMeal.getNutritionFact(mealID);

    this.setPreferredSize(new Dimension(300, 300));
    this.setLayout(null);

    // Food background label
    JLabel foodBg = new JLabel();
    foodBg.setBounds(0, 0, 300, 250);
    foodBg.setIcon(foodImage);

    // Nutrition fact label (displayed on hover)
    JLabel nutritionLabel = new JLabel("<html>" + nutritionFact.replace("\n", "<br>") + "</html>");
    nutritionLabel.setBounds(10, 10, 280, 230);  // Position within the bounds of food label
    nutritionLabel.setForeground(Color.BLACK);    // Set text color
    nutritionLabel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent background
    nutritionLabel.setOpaque(true);
    nutritionLabel.setVisible(false); // Initially hidden

    // Add nutrition label as a child of foodBg for better overlay management
    foodBg.add(nutritionLabel);

    // Mouse listener to show/hide nutrition facts on hover
    foodBg.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent evt) {
        nutritionLabel.setVisible(true); // Show nutrition fact text
      }

      @Override
      public void mouseExited(MouseEvent evt) {
        nutritionLabel.setVisible(false); // Hide nutrition fact text
      }
    });

    // Bottom area for buttons and other elements
    JPanel bottomArea = new JPanel();
    bottomArea.setBounds(0, 250, 300, 50);

    // Increment button
    ImageIcon incre = new ImageIcon("pics/less.png");
    JButton increment = new JButton(incre);
    increment.setBorderPainted(false);
    increment.setFocusPainted(false);
    increment.setContentAreaFilled(false);

    // Decrement button
    ImageIcon decre = new ImageIcon("pics/more.png");
    JButton decrement = new JButton(decre);
    decrement.setBorderPainted(false);
    decrement.setFocusPainted(false);
    decrement.setContentAreaFilled(false);

    // Order button
    ImageIcon order = new ImageIcon("pics/order button.png");
    JButton orderButton = new JButton(order);
    orderButton.setBorderPainted(false);
    orderButton.setFocusPainted(false);
    orderButton.setContentAreaFilled(false);

    // Quantity label
    JLabel amountTextField = new JLabel("0");

    // Add components to the panel
    this.add(foodBg); // Add foodBg with nutritionLabel inside
    bottomArea.add(increment);
    bottomArea.add(amountTextField);
    bottomArea.add(decrement);
    bottomArea.add(orderButton);
    this.add(bottomArea);
  }

  public AddFood(ImageIcon foodImage, ImageIcon hoverImage) {
    this.foodImage = foodImage;
    this.hoverImage = hoverImage;

    this.setPreferredSize(new Dimension(300, 300));
    this.setLayout(null);

    // Food background label
    JLabel foodBg = new JLabel();
    foodBg.setBounds(0, 0, 300, 250);
    foodBg.setIcon(foodImage);

    // Nutrition fact panel
    JPanel nutritionPanel = new JPanel();
    nutritionPanel.setBounds(0, 0, 300, 250);
    nutritionPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent background
    nutritionPanel.setVisible(false);

    foodBg.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent evt) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
          hoverTimer.stop();
        }
        hoverTimer = new Timer(HOVER_DELAY, e -> foodBg.setIcon(hoverImage));
        hoverTimer.setRepeats(false);
        hoverTimer.start();
      }

      @Override
      public void mouseExited(MouseEvent evt) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
          hoverTimer.stop(); // Stop the timer if it's running
        }
        foodBg.setIcon(foodImage);
      }
    });

    ImageIcon bottom = new ImageIcon("pics/Rectangle 16.png");
    JLabel bottomPic = new JLabel(bottom);
    JPanel bottomArea = new JPanel();
    bottomArea.setBounds(0,250,300,50);
    bottomArea.add(bottomPic);

    ImageIcon incre = new ImageIcon("pics/less.png");
    JButton increment = new JButton(incre);
    increment.setBounds(0, 280, 11, 14);
    increment.setBorderPainted(false);
    increment.setFocusPainted(false);
    increment.setContentAreaFilled(false);

    ImageIcon decre = new ImageIcon("pics/more.png");
    JButton decrement = new JButton(decre);
    decrement.setBounds(50, 280, 11, 14);
    decrement.setBorderPainted(false);
    decrement.setFocusPainted(false);
    decrement.setContentAreaFilled(false);

    ImageIcon order = new ImageIcon("pics/order button.png");
    JButton orderButton = new JButton(order);
    orderButton.setBounds(200, 260, 80, 25);
    orderButton.setBorderPainted(false);
    orderButton.setFocusPainted(false);
    orderButton.setContentAreaFilled(false);

    JLabel amountTextField = new JLabel();
    ImageIcon amountField = new ImageIcon();
    int x = 0;
    amountTextField.setText("" + x);

    this.add(foodBg);
    bottomArea.add(increment);
    bottomArea.add(amountTextField);
    bottomArea.add(decrement);
    bottomArea.add(orderButton);
    this.add(bottomArea);
//    this.add(decrement, 3);
//    this.add(amountTextField, 3);
//    this.add(decrement, 1);
//    this.add(orderButton, 1);

  }

}