package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.swing.*;
import javax.swing.Timer;
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
    private static final Dimension SEARCH_FIELD_SIZE = new Dimension(300, 30);
    private static final Dimension SORT_BUTTON_SIZE = new Dimension(120, 30);

    private JFrame mainFrame;
    private JButton exitButton;
    private JButton confirmButton;
    private JButton addButton;
    private JTextField searchField;
    private JPanel foodItemsPanel;
    public JPanel leftContentPanel;
    private ExitAndLogoutButtonFrame exit;
    private List<AddInventory> allFoodItems;
    private JButton sortStockButton;
    private JButton sortAlphaButton;
    private SortState stockSortState = SortState.DEFAULT;
    private SortState alphaSortState = SortState.DEFAULT;

    private enum SortState {
        DEFAULT,
        ASCENDING,
        DESCENDING
    }

    private Map<Integer, AddInventory> foodItemComponents;
    private NavigatorButtonInventory navButton;

    public InventoryCRUD() {
        foodItemComponents = new HashMap<>();
        allFoodItems = new ArrayList<>();
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
        button.setPreferredSize(new Dimension(80, 30));
        button.setMaximumSize(new Dimension(280, 30));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon("pics/add panel.png"));
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
        button.setPreferredSize(new Dimension(80, 30));
        button.setMaximumSize(new Dimension(280, 30));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setIcon(new ImageIcon("pics/confirm.png"));
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
        rightSideWhole.setBackground(PRIMARY_COLOR);
        rightSideWhole.setBorder(new EmptyBorder(0, 20, 0, 0));

        JPanel topPanel = createTopPanel();
        rightSideWhole.add(topPanel, BorderLayout.NORTH);

        foodItemsPanel = createFoodItemsPanel();
        JScrollPane scrollPane = createScrollPane(foodItemsPanel, SCROLL_PANE_SIZE);
        scrollPane.setBorder(new EmptyBorder(10, 15, 0, 0));
        scrollPane.setBackground(Color.WHITE);

        JPanel rightSideBottom = new JPanel();
        rightSideBottom.setLayout(new BoxLayout(rightSideBottom, BoxLayout.Y_AXIS));
        rightSideBottom.add(scrollPane);

        rightSideWhole.add(rightSideBottom, BorderLayout.CENTER);

        return rightSideWhole;
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(PRIMARY_COLOR);
        topPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Create a container for search and sort controls
        JPanel controlsContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        controlsContainer.setBackground(PRIMARY_COLOR);

        // Add search panel
        JPanel searchPanel = createSearchPanel();
        controlsContainer.add(searchPanel);

        // Add sort buttons
        JPanel sortButtonsPanel = createSortButtonsPanel();
        controlsContainer.add(sortButtonsPanel);

        // Create exit button container
        JPanel exitButtonContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        exitButtonContainer.setBackground(PRIMARY_COLOR);
        exitButton = createExitButton();
        exitButtonContainer.add(exitButton);

        // Add components to top panel
        topPanel.add(controlsContainer, BorderLayout.WEST);
        topPanel.add(exitButtonContainer, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createSortButtonsPanel() {
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sortPanel.setBackground(PRIMARY_COLOR);

        sortStockButton = createSortButton("Sort by Stock");
        sortAlphaButton = createSortButton("Sort by Name");

        sortStockButton.addActionListener(e -> handleStockSort());
        sortAlphaButton.addActionListener(e -> handleAlphaSort());

        sortPanel.add(sortStockButton);
        sortPanel.add(sortAlphaButton);

        return sortPanel;
    }

    private JButton createSortButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(SORT_BUTTON_SIZE);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

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

    private void handleStockSort() {
        stockSortState = switch (stockSortState) {
            case DEFAULT -> SortState.ASCENDING;
            case ASCENDING -> SortState.DESCENDING;
            case DESCENDING -> SortState.DEFAULT;
        };
        alphaSortState = SortState.DEFAULT; // Reset other sort state
        updateSortButtonText();
        filterItems();
    }

    private void handleAlphaSort() {
        alphaSortState = switch (alphaSortState) {
            case DEFAULT -> SortState.ASCENDING;
            case ASCENDING -> SortState.DESCENDING;
            case DESCENDING -> SortState.DEFAULT;
        };
        stockSortState = SortState.DEFAULT; // Reset other sort state
        updateSortButtonText();
        filterItems();
    }

    private void updateSortButtonText() {
        sortStockButton.setText("Sort by Stock " + getSortIndicator(stockSortState));
        sortAlphaButton.setText("Sort by Name " + getSortIndicator(alphaSortState));
    }

    private String getSortIndicator(SortState state) {
        return switch (state) {
            case ASCENDING -> "↑";
            case DESCENDING -> "↓";
            default -> "";
        };
    }

    private void filterItems() {
        String rawSearchText = searchField.getText().toLowerCase().trim();
        final String searchText = rawSearchText.equals("search") ? "" : rawSearchText;

        // Create a filtered list
        List<AddInventory> filteredItems = allFoodItems.stream()
                .filter(item -> {
                    String mealName = SQLMeal.getName(item.getMealId());
                    return mealName != null && mealName.toLowerCase().contains(searchText);
                })
                .sorted((item1, item2) -> {
                    // Apply sorting based on active sort state
                    if (stockSortState != SortState.DEFAULT) {
                        int compare = Integer.compare(
                                item1.getQuantityAvailable(),
                                item2.getQuantityAvailable()
                        );
                        return stockSortState == SortState.ASCENDING ? compare : -compare;
                    } else if (alphaSortState != SortState.DEFAULT) {
                        String name1 = SQLMeal.getName(item1.getMealId());
                        String name2 = SQLMeal.getName(item2.getMealId());
                        int compare = name1.compareToIgnoreCase(name2);
                        return alphaSortState == SortState.ASCENDING ? compare : -compare;
                    }
                    return 0; // Default order
                })
                .toList();

        // Update the panel
        foodItemsPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        for (AddInventory item : filteredItems) {
            gbc.gridx = (gbc.gridx + 1) % 2;
            foodItemsPanel.add(item, gbc);
        }

        foodItemsPanel.revalidate();
        foodItemsPanel.repaint();
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setBackground(PRIMARY_COLOR);

        searchField = new JTextField("Search");
        searchField.setPreferredSize(SEARCH_FIELD_SIZE);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));

        searchField.setForeground(Color.GRAY); // Make placeholder text gray
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK); // Change text color to white when typing
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });


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

        searchPanel.add(searchField);
        return searchPanel;
    }

    //e update ang food items panel based sa search text

    private JPanel createFoodItemsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));
        panel.setBackground(Color.WHITE);

        try {
            List<Integer> mealIds = SQLMeal.getAllMealIds();

            for (int mealId : mealIds) {
                AddInventory addInventory = new AddInventory(mealId, leftContentPanel);
                panel.add(addInventory);
                foodItemComponents.put(mealId, addInventory);
                allFoodItems.add(addInventory); // Store in our list
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
