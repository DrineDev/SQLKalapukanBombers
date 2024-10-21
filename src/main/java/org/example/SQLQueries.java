package org.example;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class SQLQueries {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/c202301062/IdeaProjects/javaworks2/SQL/database.db";

    public static void main(String[] args) {
//        createUsersTable();
//        createMealsTable();
//        createOrderTable();
//        createSalesTable();
//        createPromotionsTable();
//        createInventoryTable();

        // Test adding a user
//        addUser("JamesDamayo", "XavierViduya", "admin");
        if(authenticateUser("JamesDamayo", "XavierViduya"))
            System.out.println("worki");
        else
            System.out.println("no worki");
//
//        // Test adding a meal
//        addMeals(1, 12.99f, "Pasta", "Main Course", "Italian", "Pasta, Sauce", "Delicious pasta with sauce", "1 Plate");
//
//        // Test adding an order
//        addOrder(1, 1, 2, "2024-10-14", "John Doe");
//
//        // Test adding a sale
//        addSales(1, 1, 1, 2, 25); // Assuming 25 is the revenue
//
//        // Test adding a promotion
//        addPromotions("Pasta", 10, 1, "Pasta", "None");
//
//        // Test adding inventory
//        addInventory(1, 1, 100);
    }

    private static void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT NOT NULL UNIQUE, "
                + "password TEXT NOT NULL, "
                + "role TEXT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'Users' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createMealsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS MEALS ("
                + "Meal_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Price FLOAT NOT NULL, "
                + "Name TEXT NOT NULL UNIQUE, "
                + "Category TEXT NOT NULL, "
                + "Type TEXT NOT NULL, "
                + "Ingredients TEXT NOT NULL, "
                + "Description TEXT NOT NULL, "
                + "Serving_Size TEXT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'Meals' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createOrderTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ORDERS ("
                + "Order_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Meal_ID INTEGER NOT NULL, "
                + "Meal_Quantity INTEGER NOT NULL, "
                + "Date TEXT NOT NULL, "
                + "Customer_Name TEXT NOT NULL, "
                + "FOREIGN KEY (Meal_ID) REFERENCES MEALS(Meal_ID))";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'Order' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createSalesTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS SALES ("
                + "Sale_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Order_ID INTEGER NOT NULL, "
                + "Meal_ID INTEGER NOT NULL,"
                + "Quantity INTEGER NOT NULL,"
                + "Revenue INTEGER NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'Sales' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createPromotionsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS PROMOTIONS ("
                + "To_Be_Promoted TEXT PRIMARY KEY, "
                + "Quantity_Sold INTEGER NOT NULL, "
                + "Meal_ID INTEGER NOT NULL,"
                + "Top_Seller TEXT NOT NULL,"
                + "Worst_Seller TEXT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'Order' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createInventoryTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS INVENTORY ("
                + "Inventory_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Meal_ID INTEGER NOT NULL, "
                + "Quantity_Available INTEGER NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'Order' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addUser(String username, String password, String role) {
        String insertSQL = "INSERT INTO Users (username, password, role) VALUES (?, ?, ?)";

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

    private static void addMeals(int Meal_ID, float Price, String Name, String Category, String Type, String Ingredients, String Description, String Serving_Size) {
        String insertSQL = "INSERT INTO MEALS (Meal_ID, Price, Name, Category, Type, Ingredients, Description, Serving_Size) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, Meal_ID);
            preparedStatement.setFloat(2, Price);
            preparedStatement.setString(3, Name);
            preparedStatement.setString(4, Category);
            preparedStatement.setString(5, Type);
            preparedStatement.setString(6, Ingredients);
            preparedStatement.setString(7, Description);
            preparedStatement.setString(8, Serving_Size);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Meal '" + Meal_ID + "' added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addOrder(int Order_ID, int Meal_ID, int Meal_Quantity, String Date, String Customer_Name) {
        String insertSQL = "INSERT INTO ORDERS (Order_ID, Meal_ID, Meal_Quantity, Date, Customer_Name) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, Order_ID);
            preparedStatement.setInt(2, Meal_ID);
            preparedStatement.setInt(3, Meal_Quantity);
            preparedStatement.setString(4, Date);
            preparedStatement.setString(5, Customer_Name);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order '" + Order_ID + "' added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addSales(int Sale_ID, int Order_ID, int Meal_ID, int Quantity, int Revenue) {
        String insertSQL = "INSERT INTO SALES (Sale_ID, Order_ID, Meal_ID, Quantity, Revenue) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, Sale_ID);
            preparedStatement.setInt(2, Order_ID);
            preparedStatement.setInt(3, Meal_ID);
            preparedStatement.setInt(4, Quantity);
            preparedStatement.setInt(5, Revenue);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Sale '" + Sale_ID + "' added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addPromotions(String To_Be_Promoted, int Quantity_Sold, int Meal_ID, String Top_Seller, String Worst_Seller) {
        String insertSQL = "INSERT INTO PROMOTIONS (To_Be_Promoted, Quantity_Sold, Meal_ID, Top_Seller, Worst_Seller) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, To_Be_Promoted);
            preparedStatement.setInt(2, Quantity_Sold);
            preparedStatement.setInt(3, Meal_ID);
            preparedStatement.setString(4, Top_Seller);
            preparedStatement.setString(5, Worst_Seller);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Promotion '" + To_Be_Promoted + "' added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addInventory(int Inventory_ID, int Meal_ID, int Quantity_Available) {
        String insertSQL = "INSERT INTO INVENTORY (Inventory_ID, Meal_ID, Quantity_Available) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, Inventory_ID);
            preparedStatement.setInt(2, Meal_ID);
            preparedStatement.setInt(3, Quantity_Available);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Inventory '" + Inventory_ID + "' added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

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