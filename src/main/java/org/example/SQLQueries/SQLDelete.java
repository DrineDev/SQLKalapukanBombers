package org.example.SQLQueries;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDelete {
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public static void dropAllTables() {
        // Due to foreign key constraints, tables need to be dropped in the correct order
        String[] dropQueries = {
            "DROP TABLE IF EXISTS SALES",
            "DROP TABLE IF EXISTS ORDER_ITEMS",
            "DROP TABLE IF EXISTS ORDERS",
            "DROP TABLE IF EXISTS INVENTORY",
            "DROP TABLE IF EXISTS MEALS",
            "DROP TABLE IF EXISTS USERS"
        };

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            
            // Enable foreign key support for SQLite
            statement.execute("PRAGMA foreign_keys = ON");
            
            for (String query : dropQueries) {
                statement.execute(query);
                System.out.println("Executed: " + query);
            }
            
            System.out.println("All tables dropped successfully!");

        } catch (SQLException e) {
            System.err.println("Error dropping tables: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Individual table drop methods if needed
    public static void dropSalesTable() {
        executeDropQuery("DROP TABLE IF EXISTS SALES");
    }

    public static void dropOrderItemsTable() {
        executeDropQuery("DROP TABLE IF EXISTS ORDER_ITEMS");
    }

    public static void dropOrdersTable() {
        executeDropQuery("DROP TABLE IF EXISTS ORDERS");
    }

    public static void dropInventoryTable() {
        executeDropQuery("DROP TABLE IF EXISTS INVENTORY");
    }

    public static void dropMealsTable() {
        executeDropQuery("DROP TABLE IF EXISTS MEALS");
    }

    public static void dropUsersTable() {
        executeDropQuery("DROP TABLE IF EXISTS USERS");
    }

    private static void executeDropQuery(String query) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            
            statement.execute("PRAGMA foreign_keys = ON");
            statement.execute(query);
            System.out.println("Executed: " + query);

        } catch (SQLException e) {
            System.err.println("Error executing query: " + query);
            System.err.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
