package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLediterUser {
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";
    public static void addUser(String username, String password, String role) {
        String insertSQL = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.executeUpdate();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listUsers() {
        String query = "SELECT * FROM Users";
        StringBuilder userList = new StringBuilder("Users:\n");
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                userList.append("ID: ").append(resultSet.getInt("id"))
                        .append(", Username: ").append(resultSet.getString("username"))
                        .append(", Role: ").append(resultSet.getString("role")).append("\n");
            }
            JOptionPane.showMessageDialog(null, userList.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
