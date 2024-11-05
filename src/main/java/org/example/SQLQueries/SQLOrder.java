package org.example.SQLQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLOrder {


    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    private static void addOrder(int Meal_Id, int Meal_Quantity, String Date) {
        String insertSQL = "INSERT INTO \"ORDER\" (Meal_Id, Meal_Quantity, Date) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, Meal_Id);
            preparedStatement.setInt(2, Meal_Quantity);
            preparedStatement.setString(3, Date);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}