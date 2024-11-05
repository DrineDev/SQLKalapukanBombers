package org.example.SQLQueries;

import javax.swing.*;
import java.sql.*;

public class SQLInventory {

    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    private static void addInventory(int Meal_Id, int Quantity_Available, float Price, int Quantity_Sold) {
        String insertSQL = "INSERT INTO INVENTORY (Meal_ID, Quantity_Available, Price, Quantity_Sold) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, Meal_Id);
            preparedStatement.setInt(2, Quantity_Available);
            preparedStatement.setFloat(3, Price);
            preparedStatement.setInt(4, Quantity_Sold);

            preparedStatement.executeUpdate();

            System.out.println("Meal added to inventory.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editInventory(int Meal_ID, int Price, int Quantity_Available, int Quantity_Sold) {
        String updateSQL = "UPDATE INVENTORY SET Price = ?, Quantity_Available = ?, Quantity_Sold = ? WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setFloat(1, Price);
            preparedStatement.setInt(2, Quantity_Available);
            preparedStatement.setInt(3, Quantity_Sold);
            preparedStatement.setInt(4, Meal_ID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteInventory(int Meal_Id) {
        String deleteSQL = "DELETE FROM INVENTORY WHERE Meal_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, Meal_Id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
