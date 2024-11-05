package org.example.SQLQueries;

import org.example.Meal;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

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

//    public static void addIsSpicyColumn() {
//        try (Connection conn = DriverManager.getConnection(DB_URL);
//            Statement stmt =  conn.createStatement()) {
//            // Add the Is_Spicy column to the meals table
//            String sql = "ALTER TABLE meals ADD COLUMN Is_Spicy INTEGER DEFAULT 0";
//            stmt.execute(sql);
//            System.out.println("Column 'Is_Spicy' added successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
