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

    private static void editMeal(int mealId, String name, String category, String type, String ingredients, String description, String serving, BufferedImage image, boolean isSpicy) {
        String updateSQL = "UPDATE MEALS SET  Name = ?, Category = ?, Type = ?, Ingredients = ?, Description = ?, Serving_Size = ?, Image = ?, Is_Spicy = ? WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, ingredients);
            preparedStatement.setString(5, description);
            preparedStatement.setString(6, serving);

            // Convert image to byte array if it is not null
            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] imageBytes = baos.toByteArray();
                preparedStatement.setBytes(7, imageBytes);
            } else {
                preparedStatement.setNull(7, java.sql.Types.BLOB); // Handle null image case
            }

            preparedStatement.setBoolean(8, isSpicy);
            preparedStatement.setInt(9, mealId); // Set Meal_ID for the WHERE clause

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

    private static String getStringValue(String query, int mealId) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mealId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1); // Get the first column value
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    private static BufferedImage getImage(int mealId) {
        BufferedImage image = null;
        String query = "SELECT Image FROM MEALS WHERE Meal_Id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mealId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("Image");
                if (imageBytes != null) {
                    try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
                        image = ImageIO.read(bis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    private static boolean getIsSpicy(int mealId) {
        String query = "SELECT Is_Spicy FROM MEALS WHERE Meal_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mealId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean(1); // Get the first column value
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private static int getMealId(String name) {
        String query = "SELECT Meal_Id FROM MEALS WHERE Name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1); // Get the first column value
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    // Getter methods that use the refactored getStringValue method
    public static String getName(int mealId) {
        return getStringValue("SELECT Name FROM MEALS WHERE Meal_Id = ?", mealId);
    }

    public static String getCategory(int mealId) {
        return getStringValue("SELECT Category FROM MEALS WHERE Meal_Id = ?", mealId);
    }

    public static String getType(int mealId) {
        return getStringValue("SELECT Type FROM MEALS WHERE Meal_Id = ?", mealId);
    }

    public static String getDescription(int mealId) {
        return getStringValue("SELECT Description FROM MEALS WHERE Meal_Id = ?", mealId);
    }

    public static String getIngredients(int mealId) {
        return getStringValue("SELECT Ingredients FROM MEALS WHERE Meal_Id = ?", mealId);
    }

    public static String getServingSize(int mealId) {
        return getStringValue("SELECT Serving_Size FROM MEALS WHERE Meal_Id = ?", mealId);
    }

    private static void setStringValue(String query, int mealId, String value) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, mealId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setImage(int mealId, BufferedImage image) {
        String query = "UPDATE MEALS SET Image = ? WHERE Meal_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            if (image != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] imageBytes = baos.toByteArray();
                preparedStatement.setBytes(1, imageBytes);
            } else {
                preparedStatement.setNull(1, java.sql.Types.BLOB);
            }
            preparedStatement.setInt(2, mealId);
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setIsSpicy(int mealId, boolean isSpicy) {
        String query = "UPDATE MEALS SET Is_Spicy = ? WHERE Meal_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setBoolean(1, isSpicy);
            preparedStatement.setInt(2, mealId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setMealId(String name, int mealId) {
        String query = "UPDATE MEALS SET Meal_Id = ? WHERE Name = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, mealId);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Setter methods for each column
    private static void setName(int mealId, String name) {
        setStringValue("UPDATE MEALS SET Name = ? WHERE Meal_Id = ?", mealId, name);
    }

    private static void setCategory(int mealId, String category) {
        setStringValue("UPDATE MEALS SET Category = ? WHERE Meal_Id = ?", mealId, category);
    }

    private static void setType(int mealId, String type) {
        setStringValue("UPDATE MEALS SET Type = ? WHERE Meal_Id = ?", mealId, type);
    }

    private static void setDescription(int mealId, String description) {
        setStringValue("UPDATE MEALS SET Description = ? WHERE Meal_Id = ?", mealId, description);
    }

    private static void setIngredients(int mealId, String ingredients) {
        setStringValue("UPDATE MEALS SET Ingredients = ? WHERE Meal_Id = ?", mealId, ingredients);
    }

    private static void setServingSize(int mealId, String servingSize) {
        setStringValue("UPDATE MEALS SET Serving_Size = ? WHERE Meal_Id = ?", mealId, servingSize);
    }
}
