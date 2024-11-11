package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MediaTracker;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.example.Classes.Meal;
import org.example.Classes.SharedData;
import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;

public class InventoryCRUD {
    private static final Color PRIMARY_COLOR = new Color(248, 146, 137);
    private static final Dimension FRAME_SIZE = new Dimension(1000, 600);
    private static final Dimension LEFT_PANEL_SIZE = new Dimension(320, 600);
    private static final Dimension RIGHT_PANEL_SIZE = new Dimension(680, 2000);
    private static final Dimension SCROLL_PANE_SIZE = new Dimension(680, 500);
    private static final Dimension LEFT_SCROLL_PANE_SIZE = new Dimension(280, 400);

    private JFrame mainFrame;
    private JButton exitButton;
    private JButton confirmButton;
    private JPanel leftContentPanel;
    private Map<Integer, AddInventory> foodItemComponents;
    private NavigatorButtonInventory navButton;

    public InventoryCRUD() {
        foodItemComponents = new HashMap<>();
        initializeGUI();
    }

    private void initializeGUI() {
        initializeFrame();
        initializeNavButton();
        JPanel leftSide = createLeftPanel();
        JPanel rightSideWhole = createRightPanel();

        mainFrame.add(leftSide, BorderLayout.WEST);
        mainFrame.add(rightSideWhole, BorderLayout.EAST);
        mainFrame.setVisible(true);
    }

    private void initializeFrame() {
        mainFrame = new JFrame();
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
        navPanel.add(navButton);  // Add the class field navButton

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

        exitButton = createExitButton();
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(Color.WHITE);
        exitPanel.add(exitButton);
        rightSideWhole.add(exitPanel, BorderLayout.NORTH);

        JPanel foodItemsPanel = createFoodItemsPanel();
        JScrollPane scrollPane = createScrollPane(foodItemsPanel, SCROLL_PANE_SIZE);

        JPanel rightSideBottom = new JPanel();
        rightSideBottom.setLayout(new BoxLayout(rightSideBottom, BoxLayout.Y_AXIS));
        rightSideBottom.add(scrollPane);

        rightSideWhole.add(rightSideBottom, BorderLayout.CENTER);

        return rightSideWhole;
    }

    private JPanel createFoodItemsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.WHITE);

        try {
            // Get all meals from inventory
            java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:sqlite:SQL/database.db");
            java.sql.Statement stmt = conn.createStatement();
            java.sql.ResultSet rs = stmt.executeQuery("SELECT Meal_ID FROM INVENTORY");

            while (rs.next()) {
                int mealID = rs.getInt("Meal_ID");
                AddInventory addInventory = new AddInventory(mealID, leftContentPanel);
                panel.add(addInventory);
                foodItemComponents.put(mealID, addInventory);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame,
                    "Error loading inventory items: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return panel;
    }

    private void updateInventory() {
        Component[] components = leftContentPanel.getComponents();
        boolean updatesPerformed = false;

        for (Meal updatedMeal : SharedData.getUpdatedMeals()) {
            try {
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

                // Update the quantity in the inventory table
                int mealID = updatedMeal.getMealId();
                AddInventory inventoryComponent = foodItemComponents.get(mealID);
                if (inventoryComponent != null) {
                    int newStockQuantity = inventoryComponent.getQuantityAvailable();
                    SQLInventory.setQuantityAvailable(mealID, newStockQuantity);
                    inventoryComponent.updateQuantityLabel(newStockQuantity);
                    updatesPerformed = true;
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Error updating inventory: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return; // Exit the method if an error occurs
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
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(8,4));
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);

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
        button.addActionListener(e -> dispose());
        return button;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(PRIMARY_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0));
        buttonPanel.add(confirmButton);
        return buttonPanel;
    }

    private void initializeNavButton() {
        navButton = new NavigatorButtonInventory();
        // Add listeners for the navigation buttons
        navButton.addOrderButtonListener(e -> {
            SwingUtilities.invokeLater(MainFrameEmployee::new);
            mainFrame.dispose(); //open order window niya e dispose ni nga window
        });

        navButton.addInventoryButtonListener(e -> {
            // already inventory so empty ra siya
        });
    }
    public void dispose() {
        if (mainFrame != null) {
            mainFrame.dispose();
        }
    }
}
