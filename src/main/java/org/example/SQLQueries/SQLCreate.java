package org.example.SQLQueries;

import java.sql.*;

public class SQLCreate {
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public static void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS USERS ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT NOT NULL UNIQUE, "
                + "password TEXT NOT NULL, "
                + "role TEXT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'USERS' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createMealsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS MEALS ("
                + "Meal_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT NOT NULL UNIQUE, "
                + "Category TEXT NOT NULL, "
                + "Type TEXT NOT NULL, "
                + "Ingredients TEXT NOT NULL, "
                + "Description TEXT NOT NULL, "
                + "Serving_Size TEXT NOT NULL, "
                + "Nutrition_Fact TEXT NOT NULL, "
                + "Image TEXT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'MEALS' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createInventoryTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS INVENTORY ("
                + "Meal_ID INTEGER PRIMARY KEY, "
                + "Quantity_Available INTEGER NOT NULL, "
                + "Price FLOAT NOT NULL, "
                + "Quantity_Sold INTEGER NOT NULL DEFAULT 0, "
                + "FOREIGN KEY (Meal_ID) REFERENCES MEALS(Meal_ID))";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'INVENTORY' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createOrdersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ORDERS ("
                + "Order_Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Order_Date TEXT NOT NULL, "
                + "Status TEXT NOT NULL, "
                + "Total_Amount REAL DEFAULT 0)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'ORDERS' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createOrderItemsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ORDER_ITEMS ("
                + "Order_Item_Id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Order_Id INTEGER NOT NULL, "
                + "Meal_Id INTEGER NOT NULL, "
                + "Quantity INTEGER NOT NULL, "
                + "Unit_Price REAL NOT NULL, "
                + "Subtotal REAL NOT NULL, "
                + "FOREIGN KEY (Order_Id) REFERENCES ORDERS(Order_Id) ON DELETE CASCADE, "
                + "FOREIGN KEY (Meal_Id) REFERENCES MEALS(Meal_Id))";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'ORDER_ITEMS' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createSalesTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS SALES ("
            + "Sale_Id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "Order_Id INTEGER NOT NULL, "
            + "Quantity_Sold INTEGER NOT NULL, "
            + "Sale_Date TEXT NOT NULL, "
            + "Revenue REAL, "
            + "FOREIGN KEY (Order_Id) REFERENCES ORDERS(Order_Id))";

        try (Connection connection = DriverManager.getConnection(DB_URL);
            Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'SALES' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
