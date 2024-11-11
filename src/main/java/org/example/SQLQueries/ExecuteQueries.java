package org.example.SQLQueries;

import java.util.List;

import org.example.Classes.Sales;

/* FOR EDLWEE
mvn exec:java -Dexec.mainClass=org.example.SQLQueries.ExecuteQueries
 */
public class ExecuteQueries {
        
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";
    public static void main(String[] args) {

        // Example date range
        String startDate = "2024-01-01";
        String endDate = "2024-12-31";

        // Get the sales data by range
        List<Sales> salesReport = SQLSales.getSalesByRange(startDate, endDate);
        System.out.println("Sales Report:");
        salesReport.forEach(System.out::println);

        // Get the top-selling meals (top 5) within the date range
        List<Sales> topSellingMeals = SQLSales.getTopSellingMeals(startDate, endDate, 5);
        System.out.println("\nTop Selling Meals:");
        topSellingMeals.forEach(System.out::println);

        // Get the worst-selling meals (bottom 5) within the date range
        List<Sales> worstSellingMeals = SQLSales.getWorstSellingMeals(startDate, endDate, 5);
        System.out.println("\nWorst Selling Meals:");
        worstSellingMeals.forEach(System.out::println);

    }
}
