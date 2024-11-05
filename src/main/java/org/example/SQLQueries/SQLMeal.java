package org.example.SQLQueries;

import org.example.Meal;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.*;

public class SQLMeal {

    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public static void addMeal(Meal meal) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "INSERT INTO meals (Name, Type, Description, ingredients, Serving_Size, Category, Nutrition_Fact, Image, Is_Spicy) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, meal.getName());
                pstmt.setString(2, meal.getType());
                pstmt.setString(3, meal.getDescription());
                pstmt.setString(4, meal.getIngredients());
                pstmt.setString(5, meal.getServingSize());
                pstmt.setString(6, meal.getCategory());
                pstmt.setString(7, meal.getNutritionFact());
                pstmt.setBoolean(9, meal.getIsSpicy());

                // Convert image to byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(meal.getImage(), "png", baos);
                byte[] imageBytes = baos.toByteArray();
                pstmt.setBytes(8, imageBytes);

                pstmt.executeUpdate();

                System.out.println("Meal added.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void editMeal(int mealId, float price, String name, String category, String type, String ingredients, String description, String serving, BufferedImage image, boolean isSpicy) {
        String updateSQL = "UPDATE MEALS SET Price = ?, Name = ?, Category = ?, Type = ?, Ingredients = ?, Description = ?, Serving_Size = ?, Image = ?, Is_Spicy = ? WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setFloat(1, price);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, type);
            preparedStatement.setString(5, ingredients);
            preparedStatement.setString(6, description);
            preparedStatement.setString(7, serving);

            // Convert image to byte array if it is not null
            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] imageBytes = baos.toByteArray();
                preparedStatement.setBytes(8, imageBytes);
            } else {
                preparedStatement.setNull(8, java.sql.Types.BLOB); // Handle null image case
            }

            preparedStatement.setBoolean(9, isSpicy);
            preparedStatement.setInt(10, mealId); // Set Meal_ID for the WHERE clause

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Meal updated successfully.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMeal(int Meal_Id) {
        String deleteSQL = "DELETE FROM MEALS WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, Meal_Id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Meal getMealInfo(int Meal_ID) {
        String query = "SELECT Name, Category, Type, Ingredients, Description, Serving_Size, Image, Nutrition_Fact, Is_Spicy FROM MEALS WHERE Meal_Id = ?";

        Meal meal = null;

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Meal_ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("Name");
                String type = resultSet.getString("Type");
                String description = resultSet.getString("Description");
                String ingredients = resultSet.getString("Ingredients");
                String servingSize = resultSet.getString("Serving_Size");
                String category = resultSet.getString("Category");
                String nutritionFact = resultSet.getString("Nutrition_Fact");
                boolean isSpicy = resultSet.getBoolean("Is_Spicy");

                // Load image from the database
                BufferedImage image = null;
                byte[] imageBytes = resultSet.getBytes("Image");
                if (imageBytes != null) {
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
                        image = ImageIO.read(bis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                meal = new Meal(name, type, description, ingredients, servingSize, image, category, nutritionFact, isSpicy);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return meal;
    }
}
