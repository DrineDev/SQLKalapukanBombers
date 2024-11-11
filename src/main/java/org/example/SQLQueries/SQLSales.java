package org.example.SQLQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.Classes.Sales;

public class SQLSales {
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    // Get sales by date range
    public static List<Sales> getSalesByRange(String startDate, String endDate) {
        String query = "SELECT o.Meal_Id, m.Name AS Meal_Name, " +
                      "SUM(o.Meal_Quantity) AS Total_Quantity_Sold, " +
                      "SUM(o.Meal_Quantity * i.Price) AS Total_Revenue " +
                      "FROM ORDERS o " +
                      "JOIN MEALS m ON o.Meal_Id = m.Meal_Id " +
                      "JOIN INVENTORY i ON o.Meal_Id = i.Meal_ID " +
                      "WHERE o.Date BETWEEN ? AND ? " +
                      "GROUP BY o.Meal_Id, m.Name";

        List<Sales> salesReport = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int mealId = resultSet.getInt("Meal_Id");
                String mealName = resultSet.getString("Meal_Name");
                int totalQuantitySold = resultSet.getInt("Total_Quantity_Sold");
                double totalRevenue = resultSet.getDouble("Total_Revenue");

                salesReport.add(new Sales(mealId, mealName, totalQuantitySold, totalRevenue));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesReport;
    }

    // Get top-selling meals within a date range
    public static List<Sales> getTopSellingMeals(String startDate, String endDate, int limit) {
        String query = "SELECT o.Meal_Id, m.Name AS Meal_Name, " +
                      "SUM(o.Meal_Quantity) AS Total_Quantity_Sold, " +
                      "SUM(o.Meal_Quantity * i.Price) AS Total_Revenue " +
                      "FROM ORDERS o " +
                      "JOIN MEALS m ON o.Meal_Id = m.Meal_Id " +
                      "JOIN INVENTORY i ON o.Meal_Id = i.Meal_ID " +
                      "WHERE o.Date BETWEEN ? AND ? " +
                      "GROUP BY o.Meal_Id, m.Name " +
                      "ORDER BY Total_Quantity_Sold DESC " +
                      "LIMIT ?";

        List<Sales> topSellingMeals = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            preparedStatement.setInt(3, limit);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int mealId = resultSet.getInt("Meal_Id");
                String mealName = resultSet.getString("Meal_Name");
                int totalQuantitySold = resultSet.getInt("Total_Quantity_Sold");
                double totalRevenue = resultSet.getDouble("Total_Revenue");

                topSellingMeals.add(new Sales(mealId, mealName, totalQuantitySold, totalRevenue));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topSellingMeals;
    }

    // Get worst-selling meals within a date range
    public static List<Sales> getWorstSellingMeals(String startDate, String endDate, int limit) {
        String query = "SELECT o.Meal_Id, m.Name AS Meal_Name, " +
                      "SUM(o.Meal_Quantity) AS Total_Quantity_Sold, " +
                      "SUM(o.Meal_Quantity * i.Price) AS Total_Revenue " +
                      "FROM ORDERS o " +
                      "JOIN MEALS m ON o.Meal_Id = m.Meal_Id " +
                      "JOIN INVENTORY i ON o.Meal_Id = i.Meal_ID " +
                      "WHERE o.Date BETWEEN ? AND ? " +
                      "GROUP BY o.Meal_Id, m.Name " +
                      "ORDER BY Total_Quantity_Sold ASC " +
                      "LIMIT ?";

        List<Sales> worstSellingMeals = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            preparedStatement.setInt(3, limit);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int mealId = resultSet.getInt("Meal_Id");
                String mealName = resultSet.getString("Meal_Name");
                int totalQuantitySold = resultSet.getInt("Total_Quantity_Sold");
                double totalRevenue = resultSet.getDouble("Total_Revenue");

                worstSellingMeals.add(new Sales(mealId, mealName, totalQuantitySold, totalRevenue));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return worstSellingMeals;
    }
}
