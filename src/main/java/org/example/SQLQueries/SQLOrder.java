package org.example.SQLQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLOrder {

    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public static void addOrder(int Order_Id, int Meal_Id, int Meal_Quantity, String Date) {
        String insertSQL = "INSERT INTO ORDERS (Order_Id, Meal_Id, Meal_Quantity, Date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, Order_Id);
            preparedStatement.setInt(2, Meal_Id);
            preparedStatement.setInt(3, Meal_Quantity);
            preparedStatement.setString(4, Date);
        
            int rowsAffected = preparedStatement.executeUpdate();  // This returns the number of rows inserted

            if (rowsAffected > 0) {
                System.out.println("Order added successfully!");
            } else {
                System.out.println("Failed to add the order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editOrder(int Order_Id, int Meal_Id, int Meal_Quantity, String Date) {
        String updateSQL = "UPDATE ORDERS SET Meal_Id = ?, Meal_Quantity = ?, Date = ? WHERE Order_Id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, Meal_Id);
            preparedStatement.setInt(2, Meal_Quantity);
            preparedStatement.setString(3, Date);

            System.out.println("Order edit successful.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setMealId(int Order_Id, int Meal_Id) {
        String updateSQL = "UPDATE ORDERS SET Meal_Id = ? WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, Meal_Id);
            preparedStatement.setInt(2, Order_Id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setMealQuantity(int Order_Id, int Meal_Quantity) {
        String updateSQL = "UPDATE ORDERS SET Meal_Quantity = ? WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, Meal_Quantity);
            preparedStatement.setInt(2, Order_Id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setOrderDate(int Order_Id, String Date) {
        String updateSQL = "UPDATE ORDERS SET Date = ? WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, Date);
            preparedStatement.setInt(2, Order_Id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getMealId(int Order_Id) {
        String query = "SELECT Meal_Id FROM ORDERS WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Order_Id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Meal_Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getMealQuantity(int Order_Id) {
        String query = "SELECT Meal_Quantity FROM ORDERS WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Order_Id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Meal_Quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getOrderDate(int Order_Id) {
        String query = "SELECT Date FROM ORDERS WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Order_Id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("Date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
