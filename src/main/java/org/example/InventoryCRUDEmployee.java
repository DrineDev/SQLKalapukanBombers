package org.example;

import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
    private static final Dimension SORT_BUTTON_SIZE = new Dimension(120, 30);
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

    private JButton sortStockButton;
    private JButton sortAlphaButton;
    private SortState stockSortState = SortState.DEFAULT;
    private SortState alphaSortState = SortState.DEFAULT;

    private enum SortState {
        DEFAULT,
        ASCENDING,
        DESCENDING
    }

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
        navButton.addMainMenuButtonListener(e -> {
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

        // Center panel for search and sort controls
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);

        // Add search panel and sort buttons
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        controlsPanel.setOpaque(false);
        controlsPanel.add(createSearchPanel());
        controlsPanel.add(createSortButtonsPanel());
        centerPanel.add(controlsPanel);

        // Right panel for exit button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(exitButton);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(centerPanel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createSortButtonsPanel() {
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sortPanel.setOpaque(false);

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

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
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

        // Remove all current components
        foodItemsPanel.removeAll();

        // Get all meal IDs and create AddInventory items
        List<AddInventory> items = SQLMeal.getAllMealIds().stream()
                .map(mealId -> new AddInventory(mealId, "employee"))
                .filter(item -> {
                    String mealName = getMealNameFromDatabase(item.getMealId());
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
                        String name1 = getMealNameFromDatabase(item1.getMealId());
                        String name2 = getMealNameFromDatabase(item2.getMealId());
                        int compare = name1.compareToIgnoreCase(name2);
                        return alphaSortState == SortState.ASCENDING ? compare : -compare;
                    }
                    return 0; // Default order
                })
                .toList();

        // Add sorted and filtered items to the panel
        for (AddInventory item : items) {
            foodItemsPanel.add(item);
        }

        // Show "No items found" message if necessary
        if (items.isEmpty()) {
            JLabel noItemsLabel = new JLabel("No food items found in inventory");
            noItemsLabel.setHorizontalAlignment(JLabel.CENTER);
            foodItemsPanel.add(noItemsLabel);
        }

        // Refresh the panel
        foodItemsPanel.revalidate();
        foodItemsPanel.repaint();
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setOpaque(false);

        // Create search field with specified size
        searchField = new JTextField("Search");
        searchField.setPreferredSize(SEARCH_FIELD_SIZE);
        searchField.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.WHITE, 1),
                BorderFactory.createEmptyBorder(2, 5, 2, 5)));

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