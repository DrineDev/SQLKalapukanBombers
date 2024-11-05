package org.example.SQLQueries;

import javax.swing.*;
import java.sql.*;

public class SQLInventory {

    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    private static void addInventory(int mealId, int quantityAvailable, float price) {
        String insertSQL = "INSERT INTO INVENTORY (Meal_ID, Quantity_Available, Price) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, mealId);
            preparedStatement.setInt(2, quantityAvailable);
            preparedStatement.setFloat(3, price);

            preparedStatement.executeUpdate();

            System.out.println("Meal added to inventory.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editInventory(int Meal_ID, int Price, int Quantity_Available) {
        String updateSQL = "UPDATE INVENTORY SET Price = ?, Quantity_Available = ? WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setInt(1, Inventory_ID);
            preparedStatement.setInt(2, Meal_ID);
            preparedStatement.setInt(3, Quantity_Available);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteInventory(int id) {
        String deleteSQL = "DELETE FROM INVENTORY WHERE Inventory_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void listInventory() {
        String query = "SELECT * FROM INVENTORY";
        StringBuilder userList = new StringBuilder("Users:\n");
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                userList.append("Inventory ID: ").append(resultSet.getInt("Inventory_ID"))
                        .append(", Meal ID: ").append(resultSet.getString("Meal_ID"))
                        .append(", Quantity Available: ").append(resultSet.getString("Quantity_Available")).append("\n");
            }
            JOptionPane.showMessageDialog(null, userList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
