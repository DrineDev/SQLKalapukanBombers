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
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.example.SQLQueries.SQLMeal;

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
    private JPanel priceLabel;
    private JLabel totalLabel;
    private JLabel leftSideCheckout;
    private JButton checkoutButton;
    private NavigatorButtonManager navButton;
    private double totalPrice;

    public MainFrameManager() {
        // Exit button
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        // Frame initialization
        mainFrame = new JFrame();
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
        loggingTextArea = new JPanel();
        loggingTextArea.setBounds(0, 0, 170, 265);
        loggingTextArea.setLayout(new BoxLayout(loggingTextArea, BoxLayout.Y_AXIS));

        loggingPriceArea = new JPanel();
        loggingPriceArea.setBounds(180, 0, 60, 265);
        loggingPriceArea.setLayout(new BoxLayout(loggingPriceArea, BoxLayout.Y_AXIS));

        loggingScroll = new JScrollPane();
        loggingScroll.setBounds(10, 10, 235, 265);
        loggingScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        loggingScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        loggingScroll.getVerticalScrollBar().setUI(new customScrollBarUI());
        loggingScroll.getVerticalScrollBar().setPreferredSize(new Dimension(5, 0));
        loggingScroll.getVerticalScrollBar().setUnitIncrement(20);
        loggingScroll.setBorder(null);
        loggingScroll.add(loggingTextArea);
        loggingScroll.add(loggingPriceArea);

        activeIDs = SQLMeal.getActiveMealIds();
        for(Integer activeId : activeIDs) {
            foodItemsPanel.add(new AddFood(activeId, loggingTextArea, loggingPriceArea, priceLabel));
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
        priceLabel = new JPanel();
        priceLabel.setBounds(170,290, 75,22);
        
        ImageIcon checkoutArea = new ImageIcon("pics/checkout box.png");
        leftSideCheckout = new JLabel();
        leftSideCheckout.setIcon(checkoutArea);
        leftSideCheckout.setLayout(null);
        leftSideCheckout.setBounds(35, 195, 250, 320);
        leftSideCheckout.setBounds(35, 195, 250,320);
        leftSideCheckout.add(totalLabel);
        leftSideCheckout.add(priceLabel);
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
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showImageFrame("pics/pop up frame.png");
            }
        });

        navButton = new NavigatorButtonManager();
        navButton.setBounds(12, 7, 206, 360);
        navButton.setBackground(null);

        leftSide.add(navButton);
        leftSide.add(leftSideCategory);
        leftSide.add(leftSideCheckout);
        leftSide.add(checkoutButton);
        mainFrame.add(leftSide, BorderLayout.WEST);
        mainFrame.add(rightSideWhole, BorderLayout.EAST);

        mainFrame.setVisible(true);
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
            foodItemsPanel.add(new AddFood(activeId, loggingTextArea, loggingPriceArea, priceLabel));
        }

        // Refresh the panel
        foodItemsPanel.revalidate();
        foodItemsPanel.repaint();
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
