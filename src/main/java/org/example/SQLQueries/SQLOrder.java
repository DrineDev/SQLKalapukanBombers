package org.example.SQLQueries;

import org.example.Classes.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLOrder {
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    // Add a new order
    public static int addOrder(String orderDate, String status, double totalAmount) {
        String insertSQL = "INSERT INTO ORDERS (Order_Date, Status, Total_Amount) VALUES (?, ?, ?)";
        int generatedOrderId = -1;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, orderDate);
            preparedStatement.setString(2, status);
            preparedStatement.setDouble(3, totalAmount);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    generatedOrderId = rs.getInt(1);
                }
                System.out.println("Order added successfully with ID: " + generatedOrderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedOrderId;
    }

    // Edit an existing order
    public static void editOrder(int orderId, String orderDate, String status, double totalAmount) {
        String updateSQL = "UPDATE ORDERS SET Order_Date = ?, Status = ?, Total_Amount = ? WHERE Order_Id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, orderDate);
            preparedStatement.setString(2, status);
            preparedStatement.setDouble(3, totalAmount);
            preparedStatement.setInt(4, orderId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an order
    public static void deleteOrder(int orderId) {
        String deleteSQL = "DELETE FROM ORDERS WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, orderId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get order details
    public static Order getOrder(int orderId) {
        String query = "SELECT * FROM ORDERS WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Order(
                        resultSet.getInt("Order_Id"),
                        resultSet.getString("Order_Date"),  // passing LocalDateTime to the constructor
                        resultSet.getString("Status"),
                        resultSet.getDouble("Total_Amount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update order status
    public static void updateOrderStatus(int orderId, String status) {
        String updateSQL = "UPDATE ORDERS SET Status = ? WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update order total amount
    public static void updateOrderTotal(int orderId, double totalAmount) {
        String updateSQL = "UPDATE ORDERS SET Total_Amount = ? WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setDouble(1, totalAmount);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getNextOrderId() {
        String getSQL = "SELECT COALESCE(MAX(Order_Id), 0) + 1 AS NextOrderId FROM ORDERS";
        int nextOrderId = 1; // Default to 1 in case the table is empty

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(getSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                nextOrderId = resultSet.getInt("NextOrderId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nextOrderId;
    }

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders"; // Adjust table name as needed

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                Order order = new Order(
                        rs.getInt("Order_Id"),
                        rs.getString("Order_Date"),
                        rs.getString("Status"),
                        rs.getDouble("Total_Amount")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider throwing or handling the exception appropriately
        }

        return orders;
    }

}