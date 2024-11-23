package org.example;

import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MealInfoMainFrame extends JFrame {
    MealInfoMainFrame(int mealId) {
        this.setUndecorated(true);
        this.setBackground(Color.WHITE);
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // TOP PANEL - Title and Exit Button (unchanged)
        JLayeredPane topPanel = new JLayeredPane();
        topPanel.setPreferredSize(new Dimension(300, 35));

        JLabel topLabel = new JLabel(new ImageIcon("pics/Meal Info.png"));
        topLabel.setBounds(0, 0, 300, 35);

        JButton exitButton = new JButton(new ImageIcon("pics/exit button.png"));
        exitButton.setBounds(270, 5, 30, 30);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> this.dispose());

        topPanel.add(topLabel, JLayeredPane.DEFAULT_LAYER);
        topPanel.add(exitButton, JLayeredPane.PALETTE_LAYER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.gridwidth = 1;
        this.add(topPanel, gbc);

        // MIDDLE PANEL - Main Info with dynamic sizing
        JPanel middlePanel = new JPanel(new GridBagLayout());
        middlePanel.setForeground(Color.WHITE);
        middlePanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(SQLMeal.getName(mealId));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create a helper method to create formatted text areas
        JTextArea[] textAreas = {
                createTextArea("Description: " + SQLMeal.getDescription(mealId)),
                createTextArea("Ingredients: " + SQLMeal.getIngredients(mealId)),
                createTextArea("Nutritional Fact: " + SQLMeal.getNutritionFact(mealId)),
                createTextArea("Serving Size: " + SQLMeal.getServingSize(mealId)),
                createTextArea("Stocks: " + SQLInventory.getQuantityAvailable(mealId))
        };

        // Category Panel
        JPanel categoryPanel = createCategoryPanel(mealId);
        JPanel typePanel = createTypePanel(mealId);

        // Add components to middle panel with proper constraints
        GridBagConstraints mc = new GridBagConstraints();
        mc.gridx = 0;
        mc.fill = GridBagConstraints.HORIZONTAL;
        mc.weightx = 1;
        mc.insets = new Insets(5, 10, 5, 10);  // Add padding

        // Add name label
        mc.gridy = 0;
        middlePanel.add(nameLabel, mc);

        // Add text areas
        for (int i = 0; i < textAreas.length; i++) {
            if (i == 4) { // FOR RED COLOR WHEN STOCKS LESS THAN 5
                if(SQLInventory.getQuantityAvailable(mealId) <= 5) {
                    textAreas[i].setForeground(Color.RED);
                }
            }
            mc.gridy = i + 1;
            middlePanel.add(textAreas[i], mc);
        }

        // Add category and type panels
        mc.gridy = textAreas.length + 1;
        middlePanel.add(categoryPanel, mc);
        mc.gridy = textAreas.length + 2;
        middlePanel.add(typePanel, mc);

        // Add middle panel to frame
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        this.add(middlePanel, gbc);

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.setPreferredSize(new Dimension(300, 35));

        JLabel infoLabel = new JLabel("Price: " + SQLInventory.getPrice(mealId) + " PHP");
        GridBagConstraints bottomGbc = new GridBagConstraints();
        bottomGbc.gridx = 0;
        bottomGbc.gridy = 0;
        bottomGbc.anchor = GridBagConstraints.CENTER;
        bottomPanel.add(infoLabel, bottomGbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 0;
        this.add(bottomPanel, gbc);

        this.pack();
        this.setMinimumSize(new Dimension(300, this.getHeight()));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Helper method to create properly formatted text areas
    private JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setWrapStyleWord(true);  // Wrap at word boundaries
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("Inter", Font.PLAIN, 14));

        // Calculate preferred size based on content
        int preferredWidth = 280;  // Slightly less than frame width to account for margins
        FontMetrics fm = textArea.getFontMetrics(textArea.getFont());
        int numberOfLines = calculateNumberOfLines(text, fm, preferredWidth);
        int preferredHeight = fm.getHeight() * numberOfLines;

        textArea.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

        return textArea;
    }

    // Helper method to calculate number of lines needed for text
    private int calculateNumberOfLines(String text, FontMetrics fm, int width) {
        int lines = 1;
        String[] words = text.split("\\s+");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            if (fm.stringWidth(currentLine + " " + word) <= width) {
                currentLine.append(" ").append(word);
            } else {
                lines++;
                currentLine = new StringBuilder(word);
            }
        }

        return lines;
    }

    // Helper method to create category panel
    private JPanel createCategoryPanel(int mealId) {
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoryPanel.setBackground(Color.WHITE);

        JCheckBox vegetarianCheckBox = createCheckBox("Vegetarian");
        JCheckBox nonVegetarianCheckBox = createCheckBox("Non-Vegetarian");

        if(SQLMeal.getCategory(mealId).equals("Non-Vegetarian")) {
            nonVegetarianCheckBox.setSelected(true);
        } else {
            vegetarianCheckBox.setSelected(true);
        }

        categoryPanel.add(vegetarianCheckBox);
        categoryPanel.add(nonVegetarianCheckBox);

        return categoryPanel;
    }

    // Helper method to create type panel
    private JPanel createTypePanel(int mealId) {
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.setBackground(Color.WHITE);

        JCheckBox breakfastCheckBox = createCheckBox("Breakfast");
        JCheckBox lunchCheckBox = createCheckBox("Lunch");
        JCheckBox dinnerCheckBox = createCheckBox("Dinner");

        String mealType = SQLMeal.getType(mealId);
        switch(mealType) {
            case "Breakfast": breakfastCheckBox.setSelected(true); break;
            case "Lunch": lunchCheckBox.setSelected(true); break;
            default: dinnerCheckBox.setSelected(true);
        }

        typePanel.add(breakfastCheckBox);
        typePanel.add(lunchCheckBox);
        typePanel.add(dinnerCheckBox);

        return typePanel;
    }

    // Helper method to create checkboxes
    private JCheckBox createCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setSelectedIcon(new ImageIcon("pics/check_box.png"));
        checkBox.setIcon(new ImageIcon("pics/checkbox default.png"));
        checkBox.setFocusPainted(false);
        checkBox.setBorderPainted(false);
        checkBox.setContentAreaFilled(false);
        checkBox.setEnabled(false);
        checkBox.setBorder(new EmptyBorder(0, 5, 0, 5));
        return checkBox;
    }
}
