package org.example.SQLQueries;

import javax.swing.*;
import java.sql.*;

public class SQLUser {

    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public static void addUser(String username, String password, String role) {
        String insertSQL = "INSERT INTO USERS (username, password, role) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User '" + username + "' added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void editUser(int id, String username, String password, String role) {
        String updateSQL = "UPDATE Users SET username = ?, password = ?, role = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();

            System.out.println("User edit successful.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUser(int id) {
        String deleteSQL = "DELETE FROM Users WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("User delete successful.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM USERS WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
