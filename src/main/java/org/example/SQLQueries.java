package org.example;

import java.sql.*;

public class SQLQueries {

    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public static void main(String[] args) {
        // Call methods to create tables or test functionality as needed.
        createUsersTable();
        createMealsTable();
        createInventoryTable();
    }

    private static void createUsersTable() {
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

    private static void createMealsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS MEALS ("
                + "Meal_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "Name TEXT NOT NULL UNIQUE, "
                + "Category TEXT NOT NULL, "
                + "Type TEXT NOT NULL, "
                + "Ingredients TEXT NOT NULL, "
                + "Description TEXT NOT NULL, "
                + "Serving_Size TEXT NOT NULL, "
                + "Image_Path TEXT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'MEALS' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createInventoryTable() {
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

    private static void addUser(String username, String password, String role) {
        String insertSQL = "INSERT INTO USERS (username, password, role) VALUES (?, ?, ?)";

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

    private static void addMeal(Meal meal) {
        String insertSQL = "INSERT INTO MEALS (Name, Category, Type, Ingredients, Description, Serving_Size, Image_Path) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setString(1, meal.getName());
            preparedStatement.setString(2, meal.getCategory());
            preparedStatement.setString(3, meal.getType());
            preparedStatement.setString(4, meal.getIngredients());
            preparedStatement.setString(5, meal.getDescription());
            preparedStatement.setString(6, meal.getServingSize());
            preparedStatement.setString(7, meal.getImagePath());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Meal " + meal.getName() + " added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addInventory(int mealId, int quantityAvailable, float price) {
        String insertSQL = "INSERT INTO INVENTORY (Meal_ID, Quantity_Available, Price) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, mealId);
            preparedStatement.setInt(2, quantityAvailable);
            preparedStatement.setFloat(3, price);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Meal ID " + mealId + " added successfully to inventory!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM USERS WHERE username = ? AND password = ?";

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