package org.example;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class AddFood extends JLabel {
  private ImageIcon foodImage;
  private ImageIcon hoverImage;
  private Timer hoverTimer;
  private final int HOVER_DELAY = 300; // Delay in milliseconds

  private static final String DB_URL = "jdbc:sqlite:SQL/databse.db";

  public AddFood(ImageIcon foodImage, ImageIcon hoverImage) {
    this.foodImage = foodImage;
    this.hoverImage = hoverImage;

    // Set the preferred size
    this.setPreferredSize(new Dimension(300, 300));
    this.setIcon(foodImage); // Set initial icon

    // Mouse listener for hover effect
    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent evt) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
          hoverTimer.stop(); // Stop any existing timer
        }
        hoverTimer = new Timer(HOVER_DELAY, e -> setIcon(hoverImage));
        hoverTimer.setRepeats(false); // Ensure it only runs once
        hoverTimer.start(); // Start the timer
      }

      @Override
      public void mouseExited(MouseEvent evt) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
          hoverTimer.stop(); // Stop the timer if it's running
        }
        setIcon(foodImage); // Reset to original image
      }
    });
  }

}