package org.example;

import javax.swing.*;
import java.sql.*;

public class SQLediterInventory {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/C202301036/IdeaProjects/untitled/SQL/database.db";
    public static void addInventory(int Inventory_ID, int Meal_ID, int Quantity_Available) {
        String insertSQL = "INSERT INTO INVENTORY (Inventory_ID, Meal_ID, Quantity_Available) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setInt(1, Inventory_ID);
            preparedStatement.setInt(2, Meal_ID);
            preparedStatement.setInt(3, Quantity_Available);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editInventory(int Inventory_ID, int Meal_ID, int Quantity_Available) {
        String updateSQL = "UPDATE INVENTORY SET Meal_ID = ?, Quantity_Available = ? WHERE Inventory_ID = ?";
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
