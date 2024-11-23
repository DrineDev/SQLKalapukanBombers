package org.example.SQLQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLInventory {

    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public static void addInventory(int Meal_Id, int Quantity_Available, float Price, int Quantity_Sold) {
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

    public static void editInventory(int Meal_ID, int Price, int Quantity_Available, int Quantity_Sold) {
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

    public static void deleteInventory(int Meal_Id) {
        String deleteSQL = "DELETE FROM INVENTORY WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, Meal_Id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // GETTERS & SETTERS
    public static int getQuantityAvailable(int Meal_Id) {
        String query = "SELECT Quantity_Available FROM INVENTORY WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Meal_Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("Quantity_Available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static float getPrice(int Meal_Id) {
        String query = "SELECT Price FROM INVENTORY WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Meal_Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getFloat("Price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }
    public static int getQuantitySold(int Meal_Id) {
        String query = "SELECT Quantity_Sold FROM INVENTORY WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Meal_Id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("Quantity_Sold");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setQuantityAvailable(int Meal_Id, int Quantity_Available) {
        String query = "UPDATE INVENTORY SET Quantity_Available = ? WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Quantity_Available);
            preparedStatement.setInt(2, Meal_Id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void setPrice(int Meal_Id, float Price) {
        String query = "UPDATE INVENTORY SET Price = ? WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setFloat(1, Price);
            preparedStatement.setInt(2, Meal_Id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setQuantitySold(int Meal_Id, int Quantity_Sold) {
        String query = "UPDATE INVENTORY SET Quantity_Sold = ? WHERE Meal_ID = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Quantity_Sold);
            preparedStatement.setInt(2, Meal_Id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean mealSold(int Meal_Id, int Quantity) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            // Get current quantities
            int currentAvailable = getQuantityAvailable(Meal_Id);
            int currentSold = getQuantitySold(Meal_Id);

            // Check if enough quantity is available
            if (currentAvailable < Quantity) {
                System.out.println("Error: Not enough quantity available");
                return false;
            }

            // Calculate new quantities
            int newAvailable = currentAvailable - Quantity;
            int newSold = currentSold + Quantity;

            // Update both quantities in a single transaction
            connection.setAutoCommit(false);
            try {
                // Update Quantity_Available
                String updateAvailable = "UPDATE INVENTORY SET Quantity_Available = ? WHERE Meal_ID = ?";
                PreparedStatement psAvailable = connection.prepareStatement(updateAvailable);
                psAvailable.setInt(1, newAvailable);
                psAvailable.setInt(2, Meal_Id);
                psAvailable.executeUpdate();

                // Update Quantity_Sold
                String updateSold = "UPDATE INVENTORY SET Quantity_Sold = ? WHERE Meal_ID = ?";
                PreparedStatement psSold = connection.prepareStatement(updateSold);
                psSold.setInt(1, newSold);
                psSold.setInt(2, Meal_Id);
                psSold.executeUpdate();

                connection.commit();
                System.out.println("Sale recorded successfully");
                return true;

            } catch (SQLException e) {
                connection.rollback();
                System.out.println("Error recording sale: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
