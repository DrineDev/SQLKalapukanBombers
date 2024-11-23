package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.example.Classes.Meal;
import org.example.SQLQueries.SQLMeal;

public class AddMeal extends JFrame {
    private static ImageIcon defaultCheckbox = new ImageIcon("pics/checkbox default.png");
    private static ImageIcon selectedCheckbox = new ImageIcon("pics/check_box.png");
    private JTextArea nameTextArea;
    private JTextArea descriptionTextField;
    private JTextArea ingredientsTextField;
    private JTextArea nutritionalFactsTextField;
    private JTextArea servingSizeTextField;
    private JCheckBox vegetarianCheckBox;
    private JCheckBox nonVegetarianCheckBox;
    private JCheckBox breakfastCheckBox;
    private JCheckBox lunchCheckBox;
    private JCheckBox dinnerCheckBox;
    private JButton exitButton;
    private JButton addButton;
    private JButton insertImageButton;
    private Meal tempMeal;
    private BufferedImage selectedImage;
    private File storedImage;

    private JCheckBox spicyCheckBox;

    public AddMeal() throws IOException {
        initializeGUI();
        selectedImage = null;
        storedImage = null;
        tempMeal = new Meal();
    }

    private void initializeGUI() {
        initializeJFrame();

        nameTextArea = createNameTextArea();
        JLabel nameLabel = createLabel("Name");

        ingredientsTextField = createIngredientsTextField();
        JLabel ingredientsLabel = createLabel("Ingredients");

        descriptionTextField = createDescriptionTextField();
        JLabel descriptionLabel = createLabel("Description");

        nutritionalFactsTextField = createNutritionalFactsTextField();
        JLabel nutritionalFactsLabel = createLabel("Nutritional Facts");

        servingSizeTextField = createServingSizeTextField();
        JLabel servingSizeLabel = createLabel("Serving Size");

        vegetarianCheckBox = createVegetarianCheckBox();
        nonVegetarianCheckBox = createNonVegetarianCheckBox();
        JLabel categoryLabel = createLabel("Category");

        breakfastCheckBox = createBreakfastCheckBox();
        lunchCheckBox = createLunchCheckBox();
        dinnerCheckBox = createDinnerCheckBox();
        JLabel typeLabel = createLabel("Type");

        spicyCheckBox = createIsSpicyCheckBox();

        exitButton = createExitIcon();
        addButton = createAddButton();
        insertImageButton = createInsertButton();

        JPanel mainPanel = new JPanel(new GridBagLayout());

        FlowLayout FlowLayoutLeft = new FlowLayout(FlowLayout.LEFT, 0, 0);
        FlowLayout FlowLayoutCenter = new FlowLayout(FlowLayout.CENTER, 0, 0);

        // NAME PANEL
        JPanel nameTextPanel = new JPanel(FlowLayoutLeft);
        JPanel nameLabelPanel = new JPanel(FlowLayoutLeft);
        JScrollPane nameScrollPane = new JScrollPane(nameTextArea);
        nameScrollPane.setPreferredSize(new Dimension(430, 30));
        nameScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        nameScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        nameTextPanel.add(nameScrollPane);
        nameLabelPanel.add(nameLabel);

        // INGREDIENTS PANEL
        JPanel ingredientsTextPanel = new JPanel(FlowLayoutLeft);
        JPanel ingredientsLabelPanel = new JPanel(FlowLayoutLeft);
        JScrollPane ingredientsScrollPane = new JScrollPane(ingredientsTextField);
        ingredientsScrollPane.setPreferredSize(new Dimension(430, 104));
        ingredientsScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        ingredientsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ingredientsTextPanel.add(ingredientsScrollPane);
        ingredientsLabelPanel.add(ingredientsLabel);

        // DESCRIPTION PANEL
        JPanel descriptionTextPanel = new JPanel(FlowLayoutLeft);
        JPanel descriptionLabelPanel = new JPanel(FlowLayoutLeft);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextField);
        descriptionScrollPane.setPreferredSize(new Dimension(430, 30));
        descriptionScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        descriptionTextPanel.add(descriptionScrollPane);
        descriptionLabelPanel.add(descriptionLabel);

        // NUTRITIONAL FACTS PANEL
        JPanel nutritionalFactsTextPanel = new JPanel(FlowLayoutLeft);
        JPanel nutritionalFactsLabelPanel = new JPanel(FlowLayoutLeft);
        JScrollPane nutritionalFactsScrollPane = new JScrollPane(nutritionalFactsTextField);
        nutritionalFactsScrollPane.setPreferredSize(new Dimension(430, 50));
        nutritionalFactsScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        nutritionalFactsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        nutritionalFactsTextPanel.add(nutritionalFactsScrollPane);
        nutritionalFactsLabelPanel.add(nutritionalFactsLabel);

        // SERVING SIZE PANEL
        JPanel servingSizeTextPanel = new JPanel(FlowLayoutLeft);
        JPanel servingSizeLabelPanel = new JPanel(FlowLayoutLeft);
        JScrollPane servingSizeScrollPane = new JScrollPane(servingSizeTextField);
        servingSizeScrollPane.setPreferredSize(new Dimension(350, 50));
        servingSizeScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        servingSizeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        servingSizeTextPanel.add(servingSizeScrollPane);
        servingSizeTextPanel.add(spicyCheckBox);
        servingSizeLabelPanel.add(servingSizeLabel);

        // CATEGORY PANEL
        JPanel categoryPanel = new JPanel(FlowLayoutLeft);
        categoryPanel.add(categoryLabel);
        categoryPanel.add(vegetarianCheckBox);
        categoryPanel.add(nonVegetarianCheckBox);

        // TYPE PANEL
        JPanel typePanel = new JPanel(FlowLayoutLeft);
        typePanel.add(typeLabel);
        typePanel.add(breakfastCheckBox);
        typePanel.add(lunchCheckBox);
        typePanel.add(dinnerCheckBox);

        // EXIT ICON PANEL
        JPanel exitButtonPanel = new JPanel(FlowLayoutLeft);
        exitButtonPanel.add(exitButton);

        // CONFIRM BUTTON PANEL
        JPanel confirmButtonPanel = new JPanel(FlowLayoutCenter);
        confirmButtonPanel.add(insertImageButton);
        confirmButtonPanel.add(addButton);

        // Setting up GridBagConstraints
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHEAST; // Top-left position
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(exitButtonPanel, c);

        // Center the main content
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 10, 0, 0);

        c.gridy = 1;
        mainPanel.add(nameTextPanel, c);
        c.gridy = 2;
        mainPanel.add(nameLabelPanel, c);
        c.gridy = 3;
        mainPanel.add(ingredientsTextPanel, c);
        c.gridy = 4;
        mainPanel.add(ingredientsLabelPanel, c);
        c.gridy = 5;
        mainPanel.add(descriptionTextPanel, c);
        c.gridy = 6;
        mainPanel.add(descriptionLabelPanel, c);
        c.gridy = 7;
        mainPanel.add(nutritionalFactsTextPanel, c);
        c.gridy = 8;
        mainPanel.add(nutritionalFactsLabelPanel, c);
        c.gridy = 9;
        mainPanel.add(servingSizeTextPanel, c);
        c.gridy = 10;
        mainPanel.add(servingSizeLabelPanel, c);
        c.gridy = 11;
        mainPanel.add(categoryPanel, c);
        c.gridy = 12;
        mainPanel.add(typePanel, c);
        c.gridy = 13;
        mainPanel.add(confirmButtonPanel, c);

        // Add the main panel to the frame content pane
        this.add(mainPanel);
    }
    private void initializeJFrame() {
        // Set basic properties first
        this.setName("Kalapukan Bombers Foods - Add Meal");
        this.setUndecorated(true);
        this.setSize(new Dimension(480, 570));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setBackground(Color.WHITE);
        this.setAlwaysOnTop(true);

        // Set background color - using a slightly visible white instead of fully transparent
        this.setBackground(new Color(255, 255, 255));

        // Pack the frame to ensure proper sizing
        this.pack();

        // Ensure the frame size after packing
        this.setSize(new Dimension(480, 570));

        // Request focus before making visible
        this.requestFocus();

        // Make visible as the last step
        this.setVisible(true);

        // Additional focus requests after visible
        SwingUtilities.invokeLater(() -> {
            this.toFront();
            this.repaint();
        });
    }

    private JTextArea createNameTextArea() {
        JTextArea nameTextArea = new JTextArea();
        nameTextArea.setBackground(new Color(217, 217, 217));
        nameTextArea.setLineWrap(true);
        nameTextArea.setWrapStyleWord(true);
        nameTextArea.setBorder(BorderFactory.createLineBorder(Color.black));

        JScrollPane scrollPane = new JScrollPane(nameTextArea);
        scrollPane.setPreferredSize(new Dimension(430, 30));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return nameTextArea;
    }

    private JTextArea createIngredientsTextField() {
        JTextArea ingredientsTextField = new JTextArea();
        ingredientsTextField.setBackground(new Color(217, 217, 217));
        ingredientsTextField.setLineWrap(true);
        ingredientsTextField.setWrapStyleWord(true);
        ingredientsTextField.setBorder(BorderFactory.createLineBorder(Color.black));

        JScrollPane scrollPane = new JScrollPane(ingredientsTextField);
        scrollPane.setPreferredSize(new Dimension(430, 104));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return ingredientsTextField;
    }

    private JTextArea createDescriptionTextField() {
        JTextArea descriptionTextField = new JTextArea();
        descriptionTextField.setBackground(new Color(217, 217, 217));
        descriptionTextField.setLineWrap(true);
        descriptionTextField.setWrapStyleWord(true);
        descriptionTextField.setBorder(BorderFactory.createLineBorder(Color.black));

        JScrollPane scrollPane = new JScrollPane(descriptionTextField);
        scrollPane.setPreferredSize(new Dimension(430, 30));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return descriptionTextField;
    }

    private JTextArea createNutritionalFactsTextField() {
        JTextArea nutritionalFactsTextField = new JTextArea();
        nutritionalFactsTextField.setBackground(new Color(217, 217, 217));
        nutritionalFactsTextField.setLineWrap(true);
        nutritionalFactsTextField.setWrapStyleWord(true);
        nutritionalFactsTextField.setBorder(BorderFactory.createLineBorder(Color.black));

        JScrollPane scrollPane = new JScrollPane(nutritionalFactsTextField);
        scrollPane.setPreferredSize(new Dimension(430, 50));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return nutritionalFactsTextField;
    }

    private JTextArea createServingSizeTextField() {
        JTextArea servingSizeTextField = new JTextArea();
        servingSizeTextField.setBackground(new Color(217, 217, 217));
        servingSizeTextField.setLineWrap(true);
        servingSizeTextField.setWrapStyleWord(true);
        servingSizeTextField.setBorder(BorderFactory.createLineBorder(Color.black));

        JScrollPane scrollPane = new JScrollPane(servingSizeTextField);
        scrollPane.setPreferredSize(new Dimension(350, 50));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return servingSizeTextField;
    }

    private JCheckBox createVegetarianCheckBox() {
        JCheckBox vegetarianCheckBox = new JCheckBox("Vegetarian");
        vegetarianCheckBox.setFocusPainted(false);
        vegetarianCheckBox.setBorderPainted(false);
        vegetarianCheckBox.setContentAreaFilled(false);
        vegetarianCheckBox.setBorder(new EmptyBorder(0, 25, 0, 0));
        vegetarianCheckBox.setIcon(defaultCheckbox);
        vegetarianCheckBox.setSelectedIcon(selectedCheckbox);
        vegetarianCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (vegetarianCheckBox.isSelected())
                    nonVegetarianCheckBox.setSelected(false);
            }
        });

        return vegetarianCheckBox;
    }

    private JCheckBox createNonVegetarianCheckBox() {
        JCheckBox nonVegetarianCheckBox = new JCheckBox("Non-Vegetarian");
        nonVegetarianCheckBox.setFocusPainted(false);
        nonVegetarianCheckBox.setBorderPainted(false);
        nonVegetarianCheckBox.setContentAreaFilled(false);
        nonVegetarianCheckBox.setBorder(new EmptyBorder(0, 25, 0, 0));
        nonVegetarianCheckBox.setIcon(defaultCheckbox);
        nonVegetarianCheckBox.setSelectedIcon(selectedCheckbox);
        nonVegetarianCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (nonVegetarianCheckBox.isSelected())
                    vegetarianCheckBox.setSelected(false);
            }
        });

        return nonVegetarianCheckBox;
    }

    private JCheckBox createBreakfastCheckBox() {
        JCheckBox breakfastCheckBox = new JCheckBox("Breakfast");
        breakfastCheckBox.setFocusPainted(false);
        breakfastCheckBox.setBorderPainted(false);
        breakfastCheckBox.setContentAreaFilled(false);
        breakfastCheckBox.setBorder(new EmptyBorder(0, 25, 0, 0));
        breakfastCheckBox.setIcon(defaultCheckbox);
        breakfastCheckBox.setSelectedIcon(selectedCheckbox);
        breakfastCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (breakfastCheckBox.isSelected()) {
                    lunchCheckBox.setSelected(false);
                    dinnerCheckBox.setSelected(false);
                }
            }
        });
        return breakfastCheckBox;
    }

    private JCheckBox createLunchCheckBox() {
        JCheckBox lunchCheckBox = new JCheckBox("Lunch");
        lunchCheckBox.setFocusPainted(false);
        lunchCheckBox.setBorderPainted(false);
        lunchCheckBox.setContentAreaFilled(false);
        lunchCheckBox.setBorder(new EmptyBorder(0, 25, 0, 0));
        lunchCheckBox.setIcon(defaultCheckbox);
        lunchCheckBox.setSelectedIcon(selectedCheckbox);
        lunchCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (lunchCheckBox.isSelected()) {
                    breakfastCheckBox.setSelected(false);
                    dinnerCheckBox.setSelected(false);
                }
            }
        });
        return lunchCheckBox;
    }

    private JCheckBox createDinnerCheckBox() {
        JCheckBox dinnerCheckBox = new JCheckBox("Dinner");
        dinnerCheckBox.setFocusPainted(false);
        dinnerCheckBox.setBorderPainted(false);
        dinnerCheckBox.setContentAreaFilled(false);
        dinnerCheckBox.setBorder(new EmptyBorder(0, 25, 0, 0));
        dinnerCheckBox.setIcon(defaultCheckbox);
        dinnerCheckBox.setSelectedIcon(selectedCheckbox);
        dinnerCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (dinnerCheckBox.isSelected()) {
                    breakfastCheckBox.setSelected(false);
                    lunchCheckBox.setSelected(false);
                }
            }
        });
        return dinnerCheckBox;
    }

    private JCheckBox createIsSpicyCheckBox() {
        JCheckBox spicyCheckBox = new JCheckBox("Spicy");
        spicyCheckBox.setFocusPainted(false);
        spicyCheckBox.setBorderPainted(false);
        spicyCheckBox.setContentAreaFilled(false);
        spicyCheckBox.setBorder(new EmptyBorder(0, 25, 0, 0));
        spicyCheckBox.setIcon(defaultCheckbox);
        spicyCheckBox.setSelectedIcon(selectedCheckbox);
        return spicyCheckBox;
    }

    private JButton createExitIcon() {
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
        JButton exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setBounds(0, 0, 20, 20);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> this.dispose());
        return exitButton;
    }

    private JButton createAddButton() {
        ImageIcon addImageIcon = new ImageIcon("pics/Add Meal.png");
        JButton addButton = new JButton();
        addButton.setIcon(addImageIcon);
        addButton.setBounds(0, 0, 20, 20);
        addButton.setContentAreaFilled(false);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validate input
                    if (!validateInput()) {
                        return;
                    }

                    // Set category
                    if (vegetarianCheckBox.isSelected()) {
                        tempMeal.setCategory("Vegetarian");
                    } else if (nonVegetarianCheckBox.isSelected()) {
                        tempMeal.setCategory("Non-Vegetarian");
                    }

                    // Set type
                    if (breakfastCheckBox.isSelected())
                        tempMeal.setType("Breakfast");
                    if (lunchCheckBox.isSelected())
                        tempMeal.setType("Lunch");
                    if (dinnerCheckBox.isSelected())
                        tempMeal.setType("Dinner");

                    // Set other properties
                    tempMeal.setIsSpicy(spicyCheckBox.isSelected());
                    tempMeal.setName(nameTextArea.getText().trim());
                    tempMeal.setIngredients(ingredientsTextField.getText().trim());
                    tempMeal.setDescription(descriptionTextField.getText().trim());
                    tempMeal.setServingSize(servingSizeTextField.getText().trim());
                    tempMeal.setNutritionFact(nutritionalFactsTextField.getText().trim());
                    tempMeal.setImage(selectedImage);

                    // Save meal to database
                    SQLMeal.addMeal(tempMeal);
                    JOptionPane.showMessageDialog(null, "Meal added successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Meal added...");

                    // Clear the form
                    clearForm();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving meal: " + ex.getMessage(), "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        return addButton;
    }

    private void clearForm() {
        vegetarianCheckBox.setSelected(false);
        nonVegetarianCheckBox.setSelected(false);
        breakfastCheckBox.setSelected(false);
        lunchCheckBox.setSelected(false);
        dinnerCheckBox.setSelected(false);
        spicyCheckBox.setSelected(false);
        nameTextArea.setText("");
        ingredientsTextField.setText("");
        descriptionTextField.setText("");
        servingSizeTextField.setText("");
        nutritionalFactsTextField.setText("");
        selectedImage = null;
    }

    // Validate all required inputs
    private boolean validateInput() {
        if (!vegetarianCheckBox.isSelected() && !nonVegetarianCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Please select a category (Vegetarian/Non-Vegetarian).",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!breakfastCheckBox.isSelected() && !lunchCheckBox.isSelected() && !dinnerCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null,
                    "Please select at least one meal type (Breakfast/Lunch/Dinner).", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (nameTextArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a meal name.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (ingredientsTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter ingredients.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (descriptionTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a description.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (servingSizeTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a serving size.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (nutritionalFactsTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter nutritional facts.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (selectedImage == null) {
            JOptionPane.showMessageDialog(null, "Please select an image.", "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private JButton createInsertButton() {
        ImageIcon addImageIcon = new ImageIcon("pics/Insert Image.png");
        JButton addButton = new JButton();
        addButton.setIcon(addImageIcon);
        addButton.setBounds(0, 0, 20, 20);
        addButton.setContentAreaFilled(false);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);

        addButton.addActionListener(new ActionListener() { // Changed to ActionListener for better handling
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose an image");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                        "Images", "(*.jpg, *.jpeg, *.png, *.gif)", "jpg", "jpeg", "png", "gif"));

                int result = fileChooser.showOpenDialog(AddMeal.this); // Use AddMeal.this as parent
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        storedImage = fileChooser.getSelectedFile();
                        selectedImage = ImageIO.read(storedImage);
                        if (selectedImage == null) {
                            throw new IOException("Failed to read image file");
                        }
                        // Optional: Add visual feedback that image was loaded
                        addButton.setToolTipText("Image loaded: " + storedImage.getName());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        // Optional: Show error dialog to user
                        javax.swing.JOptionPane.showMessageDialog(AddMeal.this,
                                "Error loading image: " + ex.getMessage(),
                                "Error",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        return addButton;
    }

    // TEMP FOR REUSE WITH EDITMEAL
    private JButton createUpdateButton() {
        ImageIcon addImageIcon = new ImageIcon("pics/Update Meal.png");
        JButton addButton = new JButton();
        addButton.setIcon(addImageIcon);
        addButton.setBounds(0, 0, 20, 20);
        addButton.setContentAreaFilled(false);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        return addButton;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(100, 30));
        return label;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new AddMeal();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
}
