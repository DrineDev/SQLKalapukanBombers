package org.example.SQLQueries;

import org.example.Meal;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class SQLMeal {

    public static void addMeal(Meal meal) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:meals.db")) {
            String sql = "INSERT INTO meals (name, type, description, ingredients, servingSize, category, nutritionFact, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, meal.getName());
                pstmt.setString(2, meal.getType());
                pstmt.setString(3, meal.getDescription());
                pstmt.setString(4, meal.getIngredients());
                pstmt.setString(5, meal.getServingSize());
                pstmt.setString(6, meal.getCategory());
                pstmt.setString(7, meal.getNutritionFact());

                // Convert image to byte array
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(meal.getImage(), "png", baos);
                byte[] imageBytes = baos.toByteArray();
                pstmt.setBytes(8, imageBytes);

                pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
