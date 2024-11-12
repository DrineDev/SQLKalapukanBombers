package org.example;

import org.example.Classes.Meal;
import org.example.Classes.SharedData;
import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventoryCRUD {
    // Constants
    private static final Color PRIMARY_COLOR = new Color(248, 146, 137);
    private static final Dimension FRAME_SIZE = new Dimension(1000, 600);
    private static final Dimension LEFT_PANEL_SIZE = new Dimension(320, 600);
    private static final Dimension RIGHT_PANEL_SIZE = new Dimension(680, 2000);
    private static final Dimension SCROLL_PANE_SIZE = new Dimension(680, 500);
    private static final Dimension LEFT_SCROLL_PANE_SIZE = new Dimension(280, 400);
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    // UI Components
    private final JFrame mainFrame;
    private final JButton confirmButton;
    private final JPanel leftContentPanel;
    private final Map<Integer, AddInventory> foodItemComponents;
    private final NavigatorButtonInventory navButton;

    public InventoryCRUD() {
        this.foodItemComponents = new HashMap<>();
        this.mainFrame = createMainFrame();
        this.navButton = createNavigationButton();
        this.leftContentPanel = new JPanel();
        this.confirmButton = createConfirmButton();

        initializeGUI();
    }

    private JFrame createMainFrame() {
        JFrame frame = new JFrame();
        frame.setSize(FRAME_SIZE);
        frame.setUndecorated(true);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        return frame;
    }

    private NavigatorButtonInventory createNavigationButton() {
        NavigatorButtonInventory nav = new NavigatorButtonInventory();
        nav.addOrderButtonListener(e -> {
            SwingUtilities.invokeLater(MainFrameManager::new);
            mainFrame.dispose();
        });
        return nav;
    }

    private void initializeGUI() {
        configureLeftContentPanel();

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(rightPanel, BorderLayout.EAST);
        mainFrame.setVisible(true);
    }

    private void configureLeftContentPanel() {
        leftContentPanel.setLayout(new BoxLayout(leftContentPanel, BoxLayout.Y_AXIS));
        leftContentPanel.setBackground(Color.WHITE);
    }

    private JPanel createLeftPanel() {
        JPanel container = new JPanel(new BorderLayout());
        container.setPreferredSize(LEFT_PANEL_SIZE);
        container.setBackground(PRIMARY_COLOR);

        // Navigation Panel
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(PRIMARY_COLOR);
        navPanel.add(navButton);

        // Content Panel
        JPanel contentPanel = createLeftContentPanel();

        container.add(navPanel, BorderLayout.NORTH);
        container.add(contentPanel, BorderLayout.CENTER);

        return container;
    }

    private JPanel createLeftContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(PRIMARY_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JPanel scrollContainer = new JPanel();
        scrollContainer.setLayout(new BoxLayout(scrollContainer, BoxLayout.Y_AXIS));
        scrollContainer.setBackground(PRIMARY_COLOR);
        scrollContainer.setBorder(new EmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = createScrollPane(leftContentPanel, LEFT_SCROLL_PANE_SIZE);
        JPanel buttonPanel = createButtonPanel();

        scrollContainer.add(scrollPane);
        scrollContainer.add(buttonPanel);
        contentPanel.add(scrollContainer);

        return contentPanel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(RIGHT_PANEL_SIZE);
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(0, 20, 0, 0));

        // Exit Button Panel
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(Color.WHITE);
        exitPanel.add(createExitButton());
        panel.add(exitPanel, BorderLayout.NORTH);

        // Food Items Panel
        JPanel foodItemsPanel = createFoodItemsPanel();
        JScrollPane scrollPane = createScrollPane(foodItemsPanel, SCROLL_PANE_SIZE);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(scrollPane);

        panel.add(bottomPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createFoodItemsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.WHITE);

        loadInventoryItems(panel);

        return panel;
    }

    private void loadInventoryItems(JPanel panel) {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT Meal_ID FROM INVENTORY")) {

            while (rs.next()) {
                int mealID = rs.getInt("Meal_ID");
                AddInventory addInventory = new AddInventory(mealID, leftContentPanel);
                panel.add(addInventory);
                foodItemComponents.put(mealID, addInventory);
            }
        } catch (SQLException e) {
            handleDatabaseError("Error loading inventory items", e);
        }
    }

    private void handleDatabaseError(String message, Exception e) {
        e.printStackTrace();
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(mainFrame,
                        message + ": " + e.getMessage(),
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE)
        );
    }

    private JScrollPane createScrollPane(JComponent view, Dimension size) {
        JScrollPane scrollPane = new JScrollPane(view);
        scrollPane.setPreferredSize(size);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        // Configure scroll bars
        configureScrollBar(scrollPane.getVerticalScrollBar(), new Dimension(8, 0));
        configureScrollBar(scrollPane.getHorizontalScrollBar(), new Dimension(8, 4));

        // Add border if needed
        if (view instanceof JPanel && !(view.getLayout() instanceof GridLayout)) {
            scrollPane.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(Color.GRAY, 1),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            scrollPane.getViewport().setBackground(Color.WHITE);
            scrollPane.setMaximumSize(size);
        }

        return scrollPane;
    }

    private void configureScrollBar(JScrollBar scrollBar, Dimension size) {
        scrollBar.setUI(new customScrollBarUI());
        scrollBar.setPreferredSize(size);
        scrollBar.setUnitIncrement(20);
    }

    private JButton createConfirmButton() {
        JButton button = new JButton("Confirm");
        button.setPreferredSize(new Dimension(100, 30));
        button.setMaximumSize(new Dimension(280, 30));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        button.addActionListener(e -> updateInventory());
        addButtonHoverEffect(button);

        return button;
    }

    private void addButtonHoverEffect(JButton button) {
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
    }

    private JButton createExitButton() {
        JButton button = new JButton();
        try {
            ImageIcon exitIcon = new ImageIcon("pics/exit button.png");
            if (exitIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                button.setIcon(exitIcon);
            }
        } catch (Exception e) {
            System.err.println("Error loading exit button image: " + e.getMessage());
        }

        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.addActionListener(e -> dispose());

        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(PRIMARY_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        buttonPanel.add(confirmButton);
        return buttonPanel;
    }

    private void updateInventory() {
        try {
            handleUpdates();
            handleDeletions();
            refreshUI();
        } catch (Exception e) {
            handleDatabaseError("Error updating inventory", e);
        }
    }

    private void handleUpdates() throws SQLException {
        for (Meal updatedMeal : SharedData.getUpdatedMeals()) {
            SQLMeal.editMeal(
                    updatedMeal.getMealId(),
                    updatedMeal.getName(),
                    updatedMeal.getCategory(),
                    updatedMeal.getType(),
                    updatedMeal.getIngredients(),
                    updatedMeal.getDescription(),
                    updatedMeal.getServingSize(),
                    updatedMeal.getImage(),
                    updatedMeal.getIsSpicy()
            );

            updateInventoryQuantity(updatedMeal.getMealId());
        }
    }

    private void updateInventoryQuantity(int mealID) throws SQLException {
        Optional.ofNullable(foodItemComponents.get(mealID))
                .ifPresent(component -> {
                    int newQuantity = component.getQuantityAvailable();
                    SQLInventory.setQuantityAvailable(mealID, newQuantity);
                    component.updateQuantityLabel(newQuantity);
                });
    }

    private void handleDeletions() throws SQLException {
        Component[] components = leftContentPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().startsWith("DELETE:Meal_ID:")) {
                String logMessage = ((JLabel) comp).getText();
                int mealID = Integer.parseInt(logMessage.split(":")[2]);

                SQLMeal.deleteMeal(mealID);
                SQLInventory.deleteInventory(mealID);

                removeComponents(mealID, comp);
            }
        }
    }

    private void removeComponents(int mealID, Component logLabel) {
        AddInventory inventoryComp = foodItemComponents.get(mealID);
        if (inventoryComp != null) {
            leftContentPanel.remove(inventoryComp);
            leftContentPanel.remove(logLabel);
            foodItemComponents.remove(mealID);
        }
    }

    private void refreshUI() {
        leftContentPanel.revalidate();
        leftContentPanel.repaint();

        Container parent = leftContentPanel.getParent();
        while (parent != null) {
            parent.revalidate();
            parent.repaint();
            parent = parent.getParent();
        }
    }

    public void dispose() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
    }
}