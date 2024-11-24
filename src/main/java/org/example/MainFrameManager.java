package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.example.Classes.Order;
import org.example.Classes.OrderItem;
import org.example.Classes.SharedData;
import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;
import org.example.SQLQueries.SQLOrder;
import org.example.SQLQueries.SQLOrderItems;


public class MainFrameManager extends JFrame {
    private JFrame mainFrame;
    private JButton exitButton;
    private JPanel loggingTextArea;
    private JPanel loggingPriceArea;
    private JPanel rightSideWhole;
    private JPanel exitPanel;
    private JPanel rightSideBottom;
    private JPanel foodItemsPanel;
    private JScrollPane scrollPane;
    private JLabel leftSide;
    private JLabel leftSideCategory;
    private JScrollPane loggingScroll;
    private JCheckBox vegetarianButton;
    private JCheckBox nonVegetarianButton;
    private JCheckBox spicyButton;
    private JCheckBox breakfastButton;
    private JCheckBox lunchButton;
    private JCheckBox dinnerButton;
    private List<Integer> activeIDs;
    private JPanel pricePanel;
    private JLabel totalLabel;
    private JLabel leftSideCheckout;
    private JPanel checkoutContentPanel;
    private JButton checkoutButton;
    private NavigatorButtonManager navButton;
    private JLabel totalPriceLabel;
    private double totalPrice;

    public MainFrameManager() {
        // Frame initialization
        mainFrame = new JFrame();
        mainFrame.setSize(1000, 600);
        mainFrame.setUndecorated(true);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        ExitAndLogoutButtonFrame exit = new ExitAndLogoutButtonFrame(mainFrame);
        exit.setVisible(false);

        // Exit button
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> exit.setVisible(true));

        // Frame initialization
        mainFrame = new JFrame("Kalapukan Bombers Foods - Add Order");
        mainFrame.setSize(1000, 600);
        mainFrame.setUndecorated(true);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        // Right side tibuok
        rightSideWhole = new JPanel();
        rightSideWhole.setLayout(new BorderLayout());
        rightSideWhole.setPreferredSize(new Dimension(680, 2000));
        rightSideWhole.setBackground(Color.white);
        rightSideWhole.setBorder(new EmptyBorder(0, 20, 0, 0)); // Add left padding

        // Panel for exit button
        exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(Color.white);
        exitPanel.add(exitButton);
        rightSideWhole.add(exitPanel, BorderLayout.NORTH);

        // Bottom area of right side
        rightSideBottom = new JPanel();
        rightSideBottom.setBackground(Color.white);
        rightSideBottom.setLayout(new BoxLayout(rightSideBottom, BoxLayout.Y_AXIS)); // Use BoxLayout for stacking

        // Scrollable panel for food items
        foodItemsPanel = new JPanel();
        foodItemsPanel.setBackground(Color.white);
        foodItemsPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns with spacing

        scrollPane = new JScrollPane(foodItemsPanel);
        scrollPane.setPreferredSize(new Dimension(680, 500));
        scrollPane.setBackground(Color.white);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(null);

        rightSideBottom.add(scrollPane);
        rightSideWhole.add(rightSideBottom, BorderLayout.CENTER);

        // Left side
        ImageIcon leftSideArea = new ImageIcon("pics/checkout area.png");
        leftSide = new JLabel();
        leftSide.setIcon(leftSideArea);
        leftSide.setLayout(null);

        ImageIcon defaultCheckbox = new ImageIcon("pics/checkbox default.png");
        ImageIcon selectedCheckbox = new ImageIcon("pics/check_box.png");
        ImageIcon categoryArea = new ImageIcon("pics/category area.png");
        leftSideCategory = new JLabel();

        // logging text for checkout
        setupCheckoutArea();

        activeIDs = SQLMeal.getActiveMealIds();
        for(Integer activeId : activeIDs) {
            foodItemsPanel.add(new AddFood(activeId, loggingTextArea, loggingPriceArea, this));
        }

        vegetarianButton = new JCheckBox("Vegetarian");
        vegetarianButton.setFocusPainted(false);
        vegetarianButton.setBorderPainted(false);
        vegetarianButton.setContentAreaFilled(false);
        vegetarianButton.setBorder(new EmptyBorder(0, 15, 0, 0));
        vegetarianButton.setIcon(defaultCheckbox);
        vegetarianButton.setSelectedIcon(selectedCheckbox);

        nonVegetarianButton = new JCheckBox("Non-Vegetarian");
        nonVegetarianButton.setFocusPainted(false);
        nonVegetarianButton.setBorderPainted(false);
        nonVegetarianButton.setContentAreaFilled(false);
        nonVegetarianButton.setBorder(new EmptyBorder(0, 15, 0, 0));
        nonVegetarianButton.setIcon(defaultCheckbox);
        nonVegetarianButton.setSelectedIcon(selectedCheckbox);

        spicyButton = new JCheckBox("Spicy");
        spicyButton.setFocusPainted(false);
        spicyButton.setBorderPainted(false);
        spicyButton.setContentAreaFilled(false);
        spicyButton.setBorder(new EmptyBorder(0, 15, 0, 0));
        spicyButton.setIcon(defaultCheckbox);
        spicyButton.setSelectedIcon(selectedCheckbox);

        breakfastButton = new JCheckBox("Breakfast");
        breakfastButton.setFocusPainted(false);
        breakfastButton.setBorderPainted(false);
        breakfastButton.setContentAreaFilled(false);
        breakfastButton.setIcon(defaultCheckbox);
        breakfastButton.setSelectedIcon(selectedCheckbox);
        breakfastButton.setBorder(new EmptyBorder(0, 20, 0, 0));

        lunchButton = new JCheckBox("Lunch");
        lunchButton.setFocusPainted(false);
        lunchButton.setBorderPainted(false);
        lunchButton.setContentAreaFilled(false);
        lunchButton.setIcon(defaultCheckbox);
        lunchButton.setSelectedIcon(selectedCheckbox);
        lunchButton.setBorder(new EmptyBorder(0, 20, 0, 0));

        dinnerButton = new JCheckBox("Dinner");
        dinnerButton.setFocusPainted(false);
        dinnerButton.setBorderPainted(false);
        dinnerButton.setContentAreaFilled(false);
        dinnerButton.setIcon(defaultCheckbox);
        dinnerButton.setSelectedIcon(selectedCheckbox);
        dinnerButton.setBorder(new EmptyBorder(0, 20, 0, 0));

        vegetarianButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (vegetarianButton.isSelected()) {
                    nonVegetarianButton.setSelected(false);
                }
                updateFoodItemsPanel();
            }
        });

        nonVegetarianButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (nonVegetarianButton.isSelected()) {
                    vegetarianButton.setSelected(false);
                }
                updateFoodItemsPanel();
            }
        });

        spicyButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateFoodItemsPanel();
            }
        });

        breakfastButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (breakfastButton.isSelected()) {
                    lunchButton.setSelected(false);
                    dinnerButton.setSelected(false);
                }
                updateFoodItemsPanel();
            }
        });

        lunchButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (lunchButton.isSelected()) {
                    breakfastButton.setSelected(false);
                    dinnerButton.setSelected(false);
                }

                updateFoodItemsPanel();
            }
        });

        dinnerButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (dinnerButton.isSelected()) {
                    breakfastButton.setSelected(false);
                    lunchButton.setSelected(false);
                }
                updateFoodItemsPanel();
            }
        });

        leftSideCategory.setIcon(categoryArea);
        leftSideCategory.setLayout(new GridLayout(3, 3));
        leftSideCategory.setBounds(35, 65, 250, 115);
        leftSideCategory.add(vegetarianButton);
        leftSideCategory.add(breakfastButton);
        leftSideCategory.add(nonVegetarianButton);
        leftSideCategory.add(lunchButton);
        leftSideCategory.add(spicyButton);
        leftSideCategory.add(dinnerButton);

        totalLabel = new JLabel("Total: ");
        totalLabel.setBounds(10, 290, 90,22);
        totalLabel.setBackground(Color.white);
        pricePanel = new JPanel();
        pricePanel.setBounds(170,290, 75,22);
        pricePanel.setBackground(Color.white);
        totalPriceLabel = new JLabel("₱0.00");
        pricePanel.add(totalPriceLabel);
        pricePanel.setBackground(Color.white);

        ImageIcon checkoutArea = new ImageIcon("pics/checkout box.png");
        leftSideCheckout = new JLabel();
        leftSideCheckout.setIcon(checkoutArea);
        leftSideCheckout.setLayout(null);
        leftSideCheckout.setBounds(35, 195, 250, 320);
        leftSideCheckout.setBounds(35, 195, 250,320);
        leftSideCheckout.add(totalLabel);
        leftSideCheckout.add(pricePanel);
        leftSideCheckout.add(loggingScroll);

        // maybe add another jscrollpane because if order is too much, you ahve to
        // scroll it

        ImageIcon checkoutButtonImage = new ImageIcon("pics/checkout button.png");
        checkoutButton = new JButton();
        checkoutButton.setIcon(checkoutButtonImage);
        checkoutButton.setBounds(35, 535, 110, 30);
        checkoutButton.setContentAreaFilled(false);
        checkoutButton.setFocusPainted(false);
        checkoutButton.setBorder(null);
        checkoutButton.addActionListener(e -> 
        {
                    // Check if there are any items in the order
            if (SharedData.order.getOrderItems().isEmpty()) {
                showImageFrame("pics/empty order warning.png");
                return;
            }

            try {
                // Check inventory and collect unavailable items
                StringBuilder unavailableItems = new StringBuilder();
                boolean sufficientInventory = true;
                
                for (OrderItem orderItem : SharedData.order.getOrderItems()) {
                    int availableQuantity = SQLInventory.getQuantityAvailable(orderItem.getMealId());
                    String mealName = SQLMeal.getName(orderItem.getMealId()); 
                    
                    if (availableQuantity < orderItem.getQuantity()) {
                        sufficientInventory = false;
                        unavailableItems.append("• ").append(mealName)
                                    .append(" (Ordered: ").append(orderItem.getQuantity())
                                    .append(", Available: ").append(availableQuantity)
                                    .append(")\n");
                    }
                }

                if (!sufficientInventory) {
                    // Create a custom warning panel
                    JWindow warningWindow = new JWindow();
                    warningWindow.setSize(400, 300);
                    warningWindow.setLocationRelativeTo(mainFrame);
                    
                    JPanel warningPanel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            // Draw your background image
                            ImageIcon bgImage = new ImageIcon("pics/inventory warning bg.png");
                            g.drawImage(bgImage.getImage(), 0, 0, this);
                            
                            // Set up text properties
                            g.setColor(Color.BLACK);
                            
                            // Draw the warning message
                            String[] lines = unavailableItems.toString().split("\n");
                            int y = 45; // Starting y position
                            for (String line : lines) {
                                g.drawString(line, 30, y);
                                y += 18; // Line spacing
                            }
                        }
                    };
                    
                    warningPanel.setPreferredSize(new Dimension(400, 300));
                    warningWindow.setContentPane(warningPanel);
                    
                    // Set rounded corners
                    Float shape = new RoundRectangle2D.Float(0, 0, 400, 300, 18, 18);
                    warningWindow.setShape(shape);
                    
                    // Show warning with fade effect
                    warningWindow.setOpacity(0.0f);
                    warningWindow.setVisible(true);
                    
                    // Fade in
                    Timer fadeInTimer = new Timer(20, null);
                    fadeInTimer.addActionListener(new ActionListener() {
                        float opacity = 0.0f;
                        
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            opacity += 0.05f;
                            if (opacity >= 1.0f) {
                                opacity = 1.0f;
                                fadeInTimer.stop();
                                // Wait before starting fade out
                                new Timer(3200, evt -> startFadeOut(warningWindow)).start();
                            }
                            warningWindow.setOpacity(opacity);
                        }
                    });
                    fadeInTimer.start();
                    
                    // Clear the checkout area
                    clearCheckoutArea();
                    return;
                }

                // Rest of the checkout process
                int orderId = SQLOrder.addOrder(
                        SharedData.order.getOrderDate().toString(),
                        SharedData.order.getStatus(),
                        SharedData.order.getTotalAmount()
                );

                if (orderId != -1) {
                    SharedData.order.setOrderId(orderId);
                    boolean allUpdatesSuccessful = true;
            
                    for (OrderItem orderItem : SharedData.order.getOrderItems()) {
                        // Separate the operations and check them individually
                        int orderItemResult = SQLOrderItems.addOrderItem(
                                orderId,
                                orderItem.getMealId(),
                                orderItem.getQuantity(),
                                orderItem.getSubtotal()
                        );
                        
                        boolean inventoryResult = SQLInventory.mealSold(
                                orderItem.getMealId(),
                                orderItem.getQuantity()
                        );
            
                        // Check if both operations were successful
                        if (orderItemResult == -1 || !inventoryResult) {
                            allUpdatesSuccessful = false;
                            break;
                        }
                    }
            
                    if (allUpdatesSuccessful) {
                        showImageFrame("pics/pop up frame.png");
                        clearCheckoutArea();
                    } else {
                        showImageFrame("pics/error.png");
                    }
                } else {
                    showImageFrame("pics/error.png");
                }
            } catch (Exception ex) {
                showImageFrame("pics/error.png");
                ex.printStackTrace();
            }
        });

        navButton = new NavigatorButtonManager("MainMenu");
        navButton.setBounds(12, 7, 206, 420);
        navButton.setBackground(null);

        leftSide.add(navButton);
        leftSide.add(leftSideCategory);
        leftSide.add(leftSideCheckout);
        leftSide.add(checkoutButton);
        mainFrame.add(leftSide, BorderLayout.WEST);
        mainFrame.add(rightSideWhole, BorderLayout.EAST);

        LocalDateTime time = LocalDateTime.now();
        SharedData.order = new Order(time, "Pending");

        mainFrame.setVisible(true);
    }
    private void clearCheckoutArea() {
        SharedData.clearOrder();
        loggingTextArea.removeAll();
        loggingPriceArea.removeAll();
        totalPrice = 0.0;
        totalPriceLabel.setText("₱0.00");
    
        loggingTextArea.revalidate();
        loggingTextArea.repaint();
        loggingPriceArea.revalidate();
        loggingPriceArea.repaint();
    }

    private void setupCheckoutArea() {
        // Create a panel that will contain both the text and price areas
        checkoutContentPanel = new JPanel();
        checkoutContentPanel.setLayout(new BorderLayout());
        checkoutContentPanel.setBackground(Color.WHITE);

        // Setup logging text area
        loggingTextArea = new JPanel();
        loggingTextArea.setLayout(new BoxLayout(loggingTextArea, BoxLayout.Y_AXIS));
        loggingTextArea.setBackground(Color.WHITE);

        // Setup logging price area
        loggingPriceArea = new JPanel();
        loggingPriceArea.setLayout(new BoxLayout(loggingPriceArea, BoxLayout.Y_AXIS));
        loggingPriceArea.setBackground(Color.WHITE);

        // Create a panel to hold both logging areas side by side
        JPanel loggingContainer = new JPanel();
        loggingContainer.setLayout(new BorderLayout());
        loggingContainer.setBackground(Color.WHITE);
        loggingContainer.add(loggingTextArea, BorderLayout.CENTER);
        loggingContainer.add(loggingPriceArea, BorderLayout.EAST);

        // Setup the scroll pane with proper settings
        loggingScroll = new JScrollPane(loggingContainer);
        loggingScroll.setBounds(10, 10, 235, 265);
        loggingScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        loggingScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        loggingScroll.getVerticalScrollBar().setUI(new customScrollBarUI());
        loggingScroll.getVerticalScrollBar().setPreferredSize(new Dimension(5, 0));
        loggingScroll.getVerticalScrollBar().setUnitIncrement(20);
        loggingScroll.setBorder(null);
        loggingScroll.setBackground(Color.WHITE);
        loggingScroll.getViewport().setBackground(Color.WHITE);

        // Setup total price area
        JPanel totalContainer = new JPanel(new BorderLayout());
        totalContainer.setBackground(Color.WHITE);
        totalContainer.setBorder(new EmptyBorder(5, 10, 5, 10));

        totalLabel = new JLabel("Total: ");
        pricePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pricePanel.setBackground(Color.WHITE);
        totalPriceLabel = new JLabel("₱0.00");
        pricePanel.add(totalPriceLabel);

        totalContainer.add(totalLabel, BorderLayout.WEST);
        totalContainer.add(pricePanel, BorderLayout.EAST);

        // Add components to the checkout content panel
        checkoutContentPanel.add(loggingScroll, BorderLayout.CENTER);
        checkoutContentPanel.add(totalContainer, BorderLayout.SOUTH);

        // Setup the checkout area
        leftSideCheckout = new JLabel(new ImageIcon("pics/checkout box.png"));
        leftSideCheckout.setLayout(new BorderLayout());
        leftSideCheckout.setBounds(35, 195, 250, 320);
        leftSideCheckout.add(checkoutContentPanel, BorderLayout.CENTER);
    }

    private void updateFoodItemsPanel() {
        // Remove existing components
        Component[] componentList = foodItemsPanel.getComponents();
        for (Component c : componentList) {
            if (c instanceof AddFood)
                foodItemsPanel.remove(c);
        }
        foodItemsPanel.revalidate();
        foodItemsPanel.repaint();

        // Loop through activeIDs to apply the filters
        for (Integer activeId : activeIDs) {
            boolean isVegetarian = vegetarianButton.isSelected();
            boolean isNonVegetarian = nonVegetarianButton.isSelected();
            boolean isSpicy = spicyButton.isSelected();
            boolean isBreakfast = breakfastButton.isSelected();
            boolean isLunch = lunchButton.isSelected();
            boolean isDinner = dinnerButton.isSelected();

            // Skip items that don't match the selected vegetarian/non-vegetarian filter
            if (isVegetarian && !SQLMeal.getCategory(activeId).equals("Vegetarian"))
                continue;
            if (isNonVegetarian && !SQLMeal.getCategory(activeId).equals("Non-Vegetarian"))
                continue;

            // Skip items that don't match the selected spicy filter
            if (isSpicy && !SQLMeal.getIsSpicy(activeId))
                continue;

            // Skip items that don't match the selected meal type filter
            if (isBreakfast && !SQLMeal.getType(activeId).equals("Breakfast"))
                continue;
            if (isLunch && !SQLMeal.getType(activeId).equals("Lunch"))
                continue;
            if (isDinner && !SQLMeal.getType(activeId).equals("Dinner"))
                continue;

            // Add the filtered item to the panel
            foodItemsPanel.add(new AddFood(activeId, loggingTextArea, loggingPriceArea, this));
        }

        // Refresh the panel
        foodItemsPanel.revalidate();
        foodItemsPanel.repaint();
    }

    public void updateTotalPrice(double price) {
        totalPrice += price;
        totalPriceLabel.setText(String.format("₱%.2f", totalPrice));
        pricePanel.revalidate();
        pricePanel.repaint();
    }

    // shit below is the same w/ login page popup after login (fadein adn fadeout)
    // TODO : REFACTOR... SAME FUNCTIONS IN ADDFOOD.JAVA
    private void showImageFrame(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();

        JWindow imageWindow = new JWindow();
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        imageWindow.setSize(width, height);
        imageWindow.setLocationRelativeTo(mainFrame);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));
        imageWindow.setContentPane(panel);

        Float shape = new RoundRectangle2D.Float(0, 0, width, height, 17, 17);
        imageWindow.setShape(shape);

        imageWindow.setOpacity(0.0f);
        imageWindow.setVisible(true);

        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(new ActionListener() {
            float opacity = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeInTimer.stop();
                    new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            startFadeOut(imageWindow);
                        }
                    }).start();
                }
                imageWindow.setOpacity(opacity);
                imageWindow.repaint();
            }
        });
        fadeInTimer.start();
    }

    private void startFadeOut(JWindow imageWindow) {
        Timer fadeOutTimer = new Timer(20, null);
        fadeOutTimer.addActionListener(new ActionListener() {
            float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    imageWindow.dispose();
                    fadeOutTimer.stop();
                }
                imageWindow.setOpacity(opacity);
                imageWindow.repaint();
            }
        });
        fadeOutTimer.start();
    }
}
