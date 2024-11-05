package org.example.SQLQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
