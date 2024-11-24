package org.example;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.example.SQLQueries.SQLMeal;

public class InventoryCRUDEmployee extends JFrame {
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";
    private static final Color PRIMARY_COLOR = new Color(248, 146, 137);
    private static final Dimension FRAME_SIZE = new Dimension(1000, 600);
    private static final Dimension SEARCH_FIELD_SIZE = new Dimension(300, 30);
    private static final int GRID_GAP = 20;
    private static final int BORDER_THICKNESS = 20;
    private static final int SCROLL_UNIT_INCREMENT = 20;
    private static final int SCROLL_BLOCK_INCREMENT = 200;

    private JFrame mainFrame;
    private JButton exitButton;
    private NavigatorButtonInventoryEmployee navButton;
    private JTextField searchField;
    private JPanel foodItemsPanel;
    private ExitAndLogoutButtonFrame exit;

    public InventoryCRUDEmployee() {
        initializeGUI();
    }

    private void initializeGUI() {
        initializeFrame();
        exit = new ExitAndLogoutButtonFrame(mainFrame); // Modify this line
        exit.setVisible(false);
        initializeExitButton();
        initializeNavButton();

        JPanel topPanel = createTopPanel();
        JPanel rightSidePanel = createRightSidePanel();

        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(rightSidePanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private void initializeFrame() {
        mainFrame = new JFrame("Kalapukan Bombers Foods - Inventory");
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setUndecorated(true);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }

    private void initializeExitButton() {
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> exit.setVisible(true));
    }

    private void initializeNavButton() {
        navButton = new NavigatorButtonInventoryEmployee();
        navButton.setPreferredSize(new Dimension(50, 50));
        navButton.addOrderButtonListener(e -> {
            SwingUtilities.invokeLater(MainFrameEmployee::new);
            mainFrame.dispose();
        });

        navButton.addInventoryButtonListener(e -> {
            // already in inventory
        });
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(PRIMARY_COLOR);
        topPanel.setBorder(BorderFactory.createMatteBorder(
                BORDER_THICKNESS, 0, 0, 0, PRIMARY_COLOR));

        // Left panel for navigator button
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(navButton);

        // Center panel for search
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(createSearchPanel());

        // Right panel for exit button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(exitButton);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setOpaque(false);

        // Create search field with specified size
        searchField = new JTextField();
        searchField.setPreferredSize(SEARCH_FIELD_SIZE);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));


        searchPanel.add(searchField);

        // Add document listener for search functionality
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterItems();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterItems();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterItems();
            }
        });

        return searchPanel;
    }

    private void filterItems() {
        String searchText = searchField.getText().toLowerCase().trim();

        // Remove all current components
        foodItemsPanel.removeAll();

        // Re-add only matching items
        List<Integer> mealIds = SQLMeal.getAllMealIds();
        for (Integer mealId : mealIds) {
            String mealName = getMealNameFromDatabase(mealId);

            if (mealName != null && mealName.toLowerCase().contains(searchText)) {
                foodItemsPanel.add(new AddInventory(mealId, "employee"));
            }
        }

        // Refresh the panel
        foodItemsPanel.revalidate();
        foodItemsPanel.repaint();
    }

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

    private JPanel createRightSidePanel() {
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BorderLayout());
        rightSidePanel.setBackground(Color.WHITE);
        rightSidePanel.setBorder(new EmptyBorder(
                BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS));

        JScrollPane scrollPane = createScrollPane(createFoodItemsPanel());
        rightSidePanel.add(scrollPane, BorderLayout.CENTER);

        return rightSidePanel;
    }

    private JPanel createFoodItemsPanel() {
        foodItemsPanel = new JPanel();
        foodItemsPanel.setLayout(new GridLayout(0, 3, GRID_GAP, GRID_GAP));
        foodItemsPanel.setBackground(Color.WHITE);

        List<Integer> mealIds = SQLMeal.getAllMealIds();

        for (Integer mealId : mealIds) {
            foodItemsPanel.add(new AddInventory(mealId, "employee"));
        }

        if (mealIds.isEmpty()) {
            JLabel noItemsLabel = new JLabel("No food items found in inventory");
            noItemsLabel.setHorizontalAlignment(JLabel.CENTER);
            foodItemsPanel.add(noItemsLabel);
        }

        return foodItemsPanel;
    }

    private JScrollPane createScrollPane(JPanel contentPanel) {
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new customScrollBarUI());
        verticalScrollBar.setUnitIncrement(SCROLL_UNIT_INCREMENT);
        verticalScrollBar.setBlockIncrement(SCROLL_BLOCK_INCREMENT);

        return scrollPane;
    }
}