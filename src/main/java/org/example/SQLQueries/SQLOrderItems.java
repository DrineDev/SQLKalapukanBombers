package org.example.SQLQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.example.Classes.OrderItem;

public class SQLOrderItems {
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    // Add a new order item
    public static int addOrderItem(int orderId, int mealId, int quantity, double unitPrice) {
        String insertSQL = "INSERT INTO ORDER_ITEMS (Order_Id, Meal_Id, Quantity, Unit_Price, Subtotal) VALUES (?, ?, ?, ?, ?)";
        double subtotal = quantity * unitPrice;
        int generatedItemId = -1;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, mealId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDouble(4, unitPrice);
            preparedStatement.setDouble(5, subtotal);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    generatedItemId = rs.getInt(1);
                }
                updateOrderTotal(orderId);
                System.out.println("Order item added successfully with ID: " + generatedItemId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedItemId;
    }

    // Edit an order item
    public static void editOrderItem(int orderItemId, int quantity, double unitPrice) {
        String updateSQL = "UPDATE ORDER_ITEMS SET Quantity = ?, Unit_Price = ?, Subtotal = ? WHERE Order_Item_Id = ?";
        double subtotal = quantity * unitPrice;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setDouble(2, unitPrice);
            preparedStatement.setDouble(3, subtotal);
            preparedStatement.setInt(4, orderItemId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // Get the order ID to update its total
                int orderId = getOrderIdByOrderItem(orderItemId);
                if (orderId != -1) {
                    updateOrderTotal(orderId);
                }
                System.out.println("Order item updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete an order item
    public static void deleteOrderItem(int orderItemId) {
        int orderId = getOrderIdByOrderItem(orderItemId);
        String deleteSQL = "DELETE FROM ORDER_ITEMS WHERE Order_Item_Id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, orderItemId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                if (orderId != -1) {
                    updateOrderTotal(orderId);
                }
                System.out.println("Order item deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all items for a specific order
    public static List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String query = "SELECT * FROM ORDER_ITEMS WHERE Order_Id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                items.add(new OrderItem(
                    resultSet.getInt("Order_Item_Id"),
                    resultSet.getInt("Order_Id"),
                    resultSet.getInt("Meal_Id"),
                    resultSet.getInt("Quantity"),
                    resultSet.getDouble("Unit_Price"),
                    resultSet.getDouble("Subtotal")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Update quantity for an order item
    public static void updateQuantity(int orderItemId, int quantity) {
        String updateSQL = "UPDATE ORDER_ITEMS SET Quantity = ?, Subtotal = Unit_Price * ? WHERE Order_Item_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, orderItemId);

            preparedStatement.executeUpdate();
            
            // Update the order total
            int orderId = getOrderIdByOrderItem(orderItemId);
            if (orderId != -1) {
                updateOrderTotal(orderId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMealQuantity(int orderId, int mealId, int quantity) {
        String updateSQL = "UPDATE ORDER_ITEMS SET Quantity = ?, Subtotal = Unit_Price * ? WHERE Order_Id = ? AND Meal_Id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setInt(3, orderId);
            preparedStatement.setInt(4, mealId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                // Update the total amount for the order after modifying the meal quantity
                updateOrderTotal(orderId);
                System.out.println("Meal quantity updated successfully for Order ID: " + orderId + ", Meal ID: " + mealId);
            } else {
                System.out.println("No records found to update for Order ID: " + orderId + ", Meal ID: " + mealId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get Order ID from Order Item ID
    private static int getOrderIdByOrderItem(int orderItemId) {
        String query = "SELECT Order_Id FROM ORDER_ITEMS WHERE Order_Item_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderItemId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("Order_Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Helper method to update order total
    private static void updateOrderTotal(int orderId) {
        String updateSQL = "UPDATE ORDERS SET Total_Amount = (SELECT SUM(Subtotal) FROM ORDER_ITEMS WHERE Order_Id = ?) WHERE Order_Id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, orderId);
            preparedStatement.setInt(2, orderId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}