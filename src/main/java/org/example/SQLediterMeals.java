package org.example;

import javax.swing.*;
import java.sql.*;

public class SQLediterMeals {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/C202301036/IdeaProjects/untitled/SQL/database.db";
    public static void addMeal(int Meal_ID, float price, String name, String Category, String Type, String Ingredients, String description, String serving) {
        String insertSQL = "INSERT INTO MEALS (Meal_ID, price, name, Category, Type, Ingredients, description, serving) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, Meal_ID);
            preparedStatement.setFloat(2, price);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, Category);
            preparedStatement.setString(5, Type);
            preparedStatement.setString(6, Ingredients);
            preparedStatement.setString(7, description);
            preparedStatement.setString(8, serving);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editMeal(int Meal_ID, float price, String name, String Category, String Type, String Ingredients, String description, String serving) {
        String updateSQL = "UPDATE MEALS SET Price = ?, Name = ?, Category = ?, Type = ?, Ingredients = ?, Description = ?, Serving_Size = ?  WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, Meal_ID);
            preparedStatement.setFloat(2, price);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, Category);
            preparedStatement.setString(5, Type);
            preparedStatement.setString(6, Ingredients);
            preparedStatement.setString(7, description);
            preparedStatement.setString(8, serving);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteMeal(int id) {
        String deleteSQL = "DELETE FROM MEALS WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listMeals() {
        String query = "SELECT * FROM MEALS";
        StringBuilder userList = new StringBuilder("Users:\n");
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                userList.append("Meal ID: ").append(resultSet.getInt("Meal_ID"))
                        .append(", Price: ").append(resultSet.getString("Price"))
                        .append(", Name: ").append(resultSet.getString("Name"))
                        .append(", Category: ").append(resultSet.getString("Category"))
                        .append(", Type: ").append(resultSet.getString("Type"))
                        .append(", Ingredients: ").append(resultSet.getString("Ingredients"))
                        .append(", Description: ").append(resultSet.getString("Description"))
                        .append(", Serving size: ").append(resultSet.getString("Serving_Size")).append("\n");
            }
            JOptionPane.showMessageDialog(null, userList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
