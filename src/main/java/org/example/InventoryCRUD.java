package org.example;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.example.Classes.Meal;
import org.example.Classes.SharedData;
import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;


public class InventoryCRUD {
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";
    private static final Color PRIMARY_COLOR = new Color(248, 146, 137);
    private static final Dimension FRAME_SIZE = new Dimension(1000, 600);
    private static final Dimension LEFT_PANEL_SIZE = new Dimension(320, 600);
    private static final Dimension RIGHT_PANEL_SIZE = new Dimension(680, 2000);
    private static final Dimension SCROLL_PANE_SIZE = new Dimension(680, 500);
    private static final Dimension LEFT_SCROLL_PANE_SIZE = new Dimension(280, 400);
    private static final Dimension SEARCH_FIELD_SIZE = new Dimension(200, 30);

    private JFrame mainFrame;
    private JButton exitButton;
    private JButton confirmButton;
    private JButton addButton;
    private JTextField searchField;
    private JPanel foodItemsPanel;
    public JPanel leftContentPanel;
    private ExitAndLogoutButtonFrame exit; // Add this field
    private Map<Integer, AddInventory> foodItemComponents;
    private NavigatorButtonInventory navButton;

    public InventoryCRUD() {
        foodItemComponents = new HashMap<>();
        initializeGUI();
    }

    private void initializeGUI() {
        initializeFrame();
        initializeNavButton();
        exit = new ExitAndLogoutButtonFrame(mainFrame);
        exit.setVisible(false);
        JPanel leftSide = createLeftPanel();
        JPanel rightSideWhole = createRightPanel();
        exit.setVisible(false);

        mainFrame.add(leftSide, BorderLayout.WEST);
        mainFrame.add(rightSideWhole, BorderLayout.EAST);
        mainFrame.setVisible(true);
    }

    private void initializeFrame() {
        mainFrame = new JFrame("Kalapukan Bombers Foods - Inventory");
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setUndecorated(true);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

    }

    private JPanel createLeftPanel() {
        // Main container with BorderLayout
        JPanel leftContainer = new JPanel(new BorderLayout());
        leftContainer.setPreferredSize(LEFT_PANEL_SIZE);
        leftContainer.setBackground(PRIMARY_COLOR);

        // Create the navigation panel at the top
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(PRIMARY_COLOR);
        navPanel.add(navButton); // Add the class field navButton

        // Create the main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(PRIMARY_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Create scroll pane container
        JPanel scrollPaneContainer = new JPanel();
        scrollPaneContainer.setLayout(new BoxLayout(scrollPaneContainer, BoxLayout.Y_AXIS));
        scrollPaneContainer.setBackground(PRIMARY_COLOR);
        scrollPaneContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Set up content panel for inventory items
        leftContentPanel = new JPanel();
        leftContentPanel.setLayout(new BoxLayout(leftContentPanel, BoxLayout.Y_AXIS));
        leftContentPanel.setBackground(Color.WHITE);

        // Create scroll pane
        JScrollPane leftScrollPane = createScrollPane(leftContentPanel, LEFT_SCROLL_PANE_SIZE);

        // Create button panel
        addButton = createAddButton();
        confirmButton = createConfirmButton();
        JPanel buttonPanel = createButtonPanel();

        // Build component hierarchy
        scrollPaneContainer.add(leftScrollPane);
        scrollPaneContainer.add(buttonPanel);
        contentPanel.add(scrollPaneContainer);

        // Add components to the main container
        leftContainer.add(navPanel, BorderLayout.NORTH);
        leftContainer.add(contentPanel, BorderLayout.CENTER);

        return leftContainer;
    }

    private JButton createAddButton() {
        JButton button = new JButton("Add");
        button.setPreferredSize(new Dimension(100, 30));
        button.setMaximumSize(new Dimension(280, 30));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        button.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                try {
                    new AddMeal();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
        });
        return button;
    }

    private JButton createConfirmButton() {
        JButton button = new JButton("Confirm");
        button.setPreferredSize(new Dimension(100, 30));
        button.setMaximumSize(new Dimension(280, 30));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        button.addActionListener(e -> {
            try {

                updateInventory();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Error updating inventory: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    private JPanel createRightPanel() {
        JPanel rightSideWhole = new JPanel(new BorderLayout());
        rightSideWhole.setPreferredSize(RIGHT_PANEL_SIZE);
        rightSideWhole.setBackground(Color.WHITE);
        rightSideWhole.setBorder(new EmptyBorder(0, 20, 0, 0));

        JPanel topPanel = createTopPanel();
        rightSideWhole.add(topPanel, BorderLayout.NORTH);

        foodItemsPanel = createFoodItemsPanel();
        JScrollPane scrollPane = createScrollPane(foodItemsPanel, SCROLL_PANE_SIZE);

        JPanel rightSideBottom = new JPanel();
        rightSideBottom.setLayout(new BoxLayout(rightSideBottom, BoxLayout.Y_AXIS));
        rightSideBottom.add(scrollPane);

        rightSideWhole.add(rightSideBottom, BorderLayout.CENTER);

        return rightSideWhole;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(new EmptyBorder(20, 0, 20, 0)); // Adjusted padding

        // Create a container for search panel with FlowLayout
        JPanel searchContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchContainer.setBackground(Color.WHITE);

        // Create and add search panel to container
        JPanel searchPanel = createSearchPanel();
        searchContainer.add(searchPanel);

        // Create exit button container with FlowLayout
        JPanel exitButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        exitButtonContainer.setBackground(Color.WHITE);
        exitButton = createExitButton();
        exitButtonContainer.add(exitButton);

        // Add components to top panel
        topPanel.add(searchContainer, BorderLayout.WEST);
        topPanel.add(exitButtonContainer, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setBackground(Color.WHITE);

        // Create search field with specified size
        searchField = new JTextField();
        searchField.setPreferredSize(SEARCH_FIELD_SIZE);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));



        searchPanel.add(searchField);

        // para mu actually gana ang search
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            //naa kay e add new letter
            public void insertUpdate(DocumentEvent e) {
                filterItems();
            }

            @Override
            //naa kay e remove usa ka letter
            public void removeUpdate(DocumentEvent e) {
                filterItems();
            }

            @Override
            //naa kay e change nga letter
            public void changedUpdate(DocumentEvent e) {
                filterItems();
            }
        });

        return searchPanel;
    }

    //e update ang food items panel based sa search text
    private void filterItems() {
        String searchText = searchField.getText().toLowerCase().trim();

        //loop sa tanan addinventory components sa fooditems panel
        for (Component comp : foodItemsPanel.getComponents()) {
            if (comp instanceof AddInventory) {
                AddInventory item = (AddInventory) comp;
                // kuhaa ang meal id ani sa component
                int mealId = item.getMealId();

                // kuhaa ang mealname gamit ang nakuha nga meal id
                String mealName = getMealNameFromDatabase(mealId);

                //check if di null
                if (mealName != null) {
                    comp.setVisible(mealName.toLowerCase().contains(searchText)); //e set visible ra siya if iya name kay contains searchtext
                }
            }
        }

        foodItemsPanel.revalidate();
        foodItemsPanel.repaint();
    }

    //function mukuha sa mealname sa database gamit ang meal id
    private String getMealNameFromDatabase(int mealId) {
        String mealName = null;
        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(DB_URL);
             java.sql.PreparedStatement stmt = conn.prepareStatement("SELECT Name FROM MEALS WHERE Meal_ID = ?")) {

            stmt.setInt(1, mealId);
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    mealName = rs.getString("Name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,
                    "Error retrieving meal name: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        return mealName;
    }

    private JPanel createFoodItemsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.WHITE);

        try (java.sql.Connection conn = java.sql.DriverManager.getConnection(DB_URL);
                java.sql.Statement stmt = conn.createStatement();
                java.sql.ResultSet rs = stmt.executeQuery("SELECT Meal_ID FROM MEALS ORDER BY Meal_ID")) {

            while (rs.next()) {
                int mealID = rs.getInt("Meal_ID");
                AddInventory addInventory = new AddInventory(mealID, leftContentPanel);
                panel.add(addInventory);
                foodItemComponents.put(mealID, addInventory);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,
                    "Error loading inventory items: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return panel;
    }

    public void deleteMeal(int mealID) {
        int confirmDelete = JOptionPane.showConfirmDialog(mainFrame,
                "Are you sure you want to delete this meal from inventory?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirmDelete == JOptionPane.YES_OPTION) {
            // Step 1: Delete from the database (both the INVENTORY and MEAL tables)
            SQLMeal.deleteMeal(mealID); // Deletes the meal from the MEAL table
            SQLInventory.deleteInventory(mealID); // Deletes the meal from the INVENTORY table

            // Step 2: Remove the meal from the UI (leftContentPanel)
            AddInventory componentToRemove = foodItemComponents.get(mealID);
            if (componentToRemove != null) {
                leftContentPanel.remove(componentToRemove); // Remove the meal component from the panel
                foodItemComponents.remove(mealID); // Remove it from the map
                leftContentPanel.revalidate(); // Revalidate the layout
                leftContentPanel.repaint(); // Repaint the panel to reflect the changes
            }

            // Step 3: Log the action (Optional)
            System.out.println("Meal with ID " + mealID + " has been deleted from inventory.");

            // Step 4: Provide feedback to the user
            JOptionPane.showMessageDialog(mainFrame,
                    "Meal deleted successfully.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private void updateInventory() {
        java.sql.Connection conn = null;
        try {
            conn = java.sql.DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false); // Start transaction

            for (Meal updatedMeal : SharedData.getUpdatedMeals()) {
                // Update meal information
                SQLMeal.editMeal(
                        updatedMeal.getMealId(),
                        updatedMeal.getName(),
                        updatedMeal.getCategory(),
                        updatedMeal.getType(),
                        updatedMeal.getIngredients(),
                        updatedMeal.getDescription(),
                        updatedMeal.getServingSize(),
                        updatedMeal.getImage(),
                        updatedMeal.getIsSpicy());

                // Update inventory quantity
                AddInventory inventoryComponent = foodItemComponents.get(updatedMeal.getMealId());
                if (inventoryComponent != null) {
                    int newStockQuantity = inventoryComponent.getQuantityAvailable();
                    if (newStockQuantity >= 0) {
                        SQLInventory.setQuantityAvailable(updatedMeal.getMealId(), newStockQuantity);
                        inventoryComponent.updateQuantityLabel(newStockQuantity);
                    } else {
                        throw new IllegalArgumentException("Stock quantity cannot be negative");
                    }
                }
            }

            conn.commit(); // Commit transaction
            clearLeftContentPanel();
            SharedData.clearUpdatedMeals(); // Clear the updated meals list after successful update

            JOptionPane.showMessageDialog(mainFrame,
                    "Inventory updated successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            JOptionPane.showMessageDialog(mainFrame,
                    "Error updating inventory: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private JScrollPane createScrollPane(JComponent view, Dimension size) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setPreferredSize(size);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getHorizontalScrollBar().setUI(new customScrollBarUI());
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(8, 4));
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);

        if (view instanceof JPanel && !(view.getLayout() instanceof GridLayout)) {
            scrollPane.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(Color.GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            scrollPane.getViewport().setBackground(Color.WHITE);
            scrollPane.setMaximumSize(size);
        }

        return scrollPane;
    }

    private JButton createExitButton() {
        ImageIcon exitImageIcon;
        try {
            exitImageIcon = new ImageIcon("pics/exit button.png");
            if (exitImageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                throw new Exception("Failed to load exit button image");
            }
        } catch (Exception ex) {
            System.err.println("Error loading exit button image: " + ex.getMessage());
            exitImageIcon = new ImageIcon();
        }

        JButton button = new JButton();
        button.setIcon(exitImageIcon);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> exit.setVisible(true));
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(PRIMARY_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        buttonPanel.add(confirmButton);
        buttonPanel.add(addButton);
        return buttonPanel;
    }

    private void clearLeftContentPanel() {
        leftContentPanel.removeAll(); // Remove all components from the panel
        leftContentPanel.revalidate(); // Revalidate to refresh the layout
        leftContentPanel.repaint(); // Repaint to update the UI
    }

    private void initializeNavButton() {
        navButton = new NavigatorButtonInventory();
    }

    public void dispose() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
    }
}
