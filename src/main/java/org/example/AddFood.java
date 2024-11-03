package org.example;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

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
//  public Meal(String name, String type, String description, String ingredients,
//              String servingSize, String imagePath, String category) {
//    this.name = name;
//    this.type = type;
//    this.description = description;
//    this.ingredients = ingredients;
//    this.servingSize = servingSize;
//    this.imagePath = imagePath;
//    this.category = category;
//    idNumber = instanceNumber;
//    instanceNumber++;
//  }
  public static Meal getMealInfo(int Meal_ID) {
    String query = "SELECT Name, Category, Type, Ingredients, Description, Serving_Size, Image_Path FROM MEALS WHERE MEAL_ID = ?";

    Meal meal = null;

    try (Connection connection = DriverManager.getConnection(DB_URL)) {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      {
        preparedStatement.setInt(1, Meal_ID);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
          meal = new Meal(
              resultSet.getString("Name"),
              resultSet.getString("Category"),
              resultSet.getString("Type"),
              resultSet.getString("Ingredients"),
              resultSet.getString("Serving_Size"),
              resultSet.getString("Image_Path"),
              resultSet.getString("Category"));
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return meal;
  }
}
