package org.example;

import org.example.SQLQueries.SQLInventory;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryCRUDEmployee extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(248, 146, 137);
    private static final Dimension FRAME_SIZE = new Dimension(1000, 600);
    private static final int GRID_GAP = 20;
    private static final int BORDER_THICKNESS = 20;
    private static final int SCROLL_UNIT_INCREMENT = 20;
    private static final int SCROLL_BLOCK_INCREMENT = 200;

    private JFrame mainFrame;
    private JButton exitButton;
    private NavigatorButtonEmployee navButton;

    //himo sa GUI
    public InventoryCRUDEmployee() {
        initializeGUI();
    }
    //function muhimo sa GUI
    private void initializeGUI() {
        initializeFrame();
        initializeExitButton();
        initializeNavButton();

        JPanel topPanel = createTopPanel();
        JPanel rightSidePanel = createRightSidePanel();

        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(rightSidePanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
    //mu initialize sa frame kung asa gibutang ang gui borderlayout siya so north east west gang
    private void initializeFrame() {
        mainFrame = new JFrame();
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setUndecorated(true);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
    }
    //initialize sa exit button ibutang siya sa right part sa top panel
    private void initializeExitButton() {
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));
    }
    //initialize sa navbutton ibutang siya sa left part sa top panel
    private void initializeNavButton() {
        navButton = new NavigatorButtonEmployee();
        // Add listeners for the navigation buttons
        navButton.addOrderButtonListener(e -> {

            mainFrame.dispose(); // Close current window
            // Open order window if naa natay order window
        });

        navButton.addInventoryButtonListener(e -> {
            // already inventory so empty ra siya
        });
    }
    //top panel contains nav and exit button
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(PRIMARY_COLOR);
        topPanel.setBorder(BorderFactory.createMatteBorder(
                BORDER_THICKNESS, 0, 0, 0, PRIMARY_COLOR
        ));

        // Use BorderLayout for the top panel
        topPanel.setLayout(new BorderLayout());

        // Create left panel for navigator button
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        leftPanel.add(navButton);

        // Create right panel for exit button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(exitButton);

        // Add both panels to the top panel
        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        return topPanel;
    }
    //rightside panel pero basically everything under sa top panel
    private JPanel createRightSidePanel() {
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BorderLayout());
        rightSidePanel.setBackground(Color.WHITE);
        rightSidePanel.setBorder(new EmptyBorder(
                BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS
        ));
        //isud ang scrollpane sa kani nga panel
        JScrollPane scrollPane = createScrollPane(createFoodItemsPanel());
        rightSidePanel.add(scrollPane, BorderLayout.CENTER);

        return rightSidePanel;
    }
    //pang get sa mga meals nga naa sa inventory
    // (para di fixed 11 ang size sa meals ig add kay mu adjust ra siya)
    private List<Integer> getActiveMealIds() {
        List<Integer> mealIds = new ArrayList<>();
        String dbUrl = "jdbc:sqlite:SQL/database.db";

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            String query = "SELECT Meal_ID FROM INVENTORY ORDER BY Meal_ID";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    mealIds.add(rs.getInt("Meal_ID"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(mainFrame,
                    "Error retrieving meal IDs: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return mealIds;
    }
    //fooditems nga panel ra jud
    private JPanel createFoodItemsPanel() {
        JPanel foodItemsPanel = new JPanel();
        foodItemsPanel.setLayout(new GridLayout(0, 2, GRID_GAP, GRID_GAP));
        foodItemsPanel.setBackground(Color.WHITE);

        List<Integer> mealIds = getActiveMealIds();

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
    //scrollpane para butanganan sa fooditemspanel
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