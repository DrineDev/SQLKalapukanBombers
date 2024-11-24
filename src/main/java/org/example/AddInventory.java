package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.example.Classes.Meal;
import org.example.Classes.SharedData;
import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;

public class AddInventory extends JPanel {
    private ImageIcon foodImage;
    private int quantityAvailable;
    private JLabel quantityLabel;
    private JLabel nameLabel;
    private JPanel leftContentPanel;
    private int mealID;
    private Boolean markedForDeletion = false;
    private ExitAndLogoutButtonFrame exit;

    public AddInventory(int mealID, JPanel leftContentPanel) {
        this.mealID = mealID;
        this.leftContentPanel = leftContentPanel;
        this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));
        this.quantityAvailable = SQLInventory.getQuantityAvailable(mealID);
        this.setPreferredSize(new Dimension(300, 300));
        this.setMaximumSize(new Dimension(300,300));
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        // Food background - adjust height to not overlap with name label
        JLabel foodBg = new JLabel();
        foodBg.setBounds(0, 0, 300, 225);  // Reduced height to 225
        foodBg.setIcon(foodImage);

        // Name label
        String mealName = SQLMeal.getMealInfo(mealID).getName();
        nameLabel = new JLabel(mealName);
        nameLabel.setBounds(0, 225, 300, 25);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBackground(new Color(248, 146, 137));
        nameLabel.setOpaque(true);
        nameLabel.setFont(new Font("Inter", Font.BOLD, 14));

        // Quantity label
        quantityLabel = new JLabel();
        quantityLabel.setIcon(new ImageIcon("pics/stock.png"));
        quantityLabel.setText(String.valueOf(this.quantityAvailable));
        quantityLabel.setBounds(5, 250, 200, 50);
        quantityLabel.setHorizontalAlignment(JLabel.LEFT);
        quantityLabel.setFont(new Font("Inter", Font.BOLD, 14));
        quantityLabel.setForeground(new Color(248, 146, 137));

        // Edit button
        ImageIcon edit = new ImageIcon("pics/edit.png");
        JButton editButton = new JButton(edit);
        editButton.setBounds(185, 255, 55, 34);
        editButton.addActionListener(e -> openEditWindow(mealID));

        // Delete button
        ImageIcon delete = new ImageIcon("pics/delete.png");
        JButton deleteButton = new JButton(delete);
        deleteButton.setBounds(245, 255, 55, 34);
        deleteButton.addActionListener(e -> confirmAndDelete(mealID));

        // Add components in correct order
        this.add(foodBg);
        this.add(nameLabel);
        this.add(quantityLabel);
        this.add(editButton);
        this.add(deleteButton);
    }

    public AddInventory(int mealID, String employee) {
        this.mealID = mealID;
        BufferedImage imageData = SQLMeal.getImage(mealID);
        if (imageData != null) {
            this.foodImage = new ImageIcon(imageData);
        } else {
            this.foodImage = new ImageIcon("pics/search.png");
        }
        this.quantityAvailable = SQLInventory.getQuantityAvailable(mealID);

        this.setPreferredSize(new Dimension(300, 300));
        this.setMaximumSize(new Dimension(300,300));
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        // Food background - adjust height to not overlap with name label
        JLabel foodBg = new JLabel();
        foodBg.setBounds(0, 0, 300, 225);  // Reduced height to 225
        // Scale the image to fit
        Image scaledImage = foodImage.getImage().getScaledInstance(300, 225, Image.SCALE_SMOOTH);
        foodBg.setIcon(new ImageIcon(scaledImage));

        // Name label
        String mealName = SQLMeal.getMealInfo(mealID).getName();
        nameLabel = new JLabel(mealName);
        nameLabel.setBounds(0, 225, 300, 25);
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBackground(new Color(248, 146, 137));
        nameLabel.setOpaque(true);
        nameLabel.setFont(new Font("Inter", Font.BOLD, 14));

        // Quantity label
        quantityLabel = new JLabel();
        quantityLabel.setIcon(new ImageIcon("pics/stock.png"));
        quantityLabel.setText(String.valueOf(this.quantityAvailable));
        quantityLabel.setBounds(5, 250, 200, 50);
        quantityLabel.setHorizontalAlignment(JLabel.LEFT);
        quantityLabel.setFont(new Font("Inter", Font.BOLD, 14));
        quantityLabel.setForeground(new Color(248, 146, 137));

        this.add(foodBg);
        this.add(nameLabel);
        this.add(quantityLabel);
    }

        // Pop-up frame for editing inventory
        private void openEditWindow(int mealID) {
            JFrame editWindow = new JFrame("Edit Meal Details");
            editWindow.setSize(800, 600);
            editWindow.setLocationRelativeTo(this);
            editWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            editWindow.setLayout(null);
            editWindow.setUndecorated(true);

            exit = new ExitAndLogoutButtonFrame(editWindow);
            ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
            JButton exitButton = new JButton();
            exitButton.setBounds(750,5,50,50);
            exitButton.setIcon(exitImageIcon);
            exitButton.setContentAreaFilled(false);
            exitButton.setFocusPainted(false);
            exitButton.setBorderPainted(false);
            exitButton.addActionListener(e -> exit.setVisible(true));

            // Set the frame and content pane background to white
            editWindow.getContentPane().setBackground(new Color(248, 146, 137));

            // Create a main panel with white background
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(null);
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setBounds(0, 0, 800, 600);

            // Labels
            JLabel editLabel = new JLabel("Edit Meal Details");
            editLabel.setBounds(50, 20, 300, 30);
            editLabel.setFont(new Font("Inter", Font.BOLD, 16));
            editLabel.setOpaque(true);
            editLabel.setForeground(new Color(248, 146, 137));
            editLabel.setBackground(new Color(255, 255, 255));

            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setBounds(50, 60, 100, 30);
            nameLabel.setOpaque(true);
            nameLabel.setForeground(new Color(248, 146, 137));
            nameLabel.setBackground(new Color(255, 255, 255));

            JTextField nameField = new JTextField();
            nameField.setBounds(160, 60, 200, 30);
            nameField.setBackground(new Color(255,255,255));

            JLabel descriptionLabel = new JLabel("Description:");
            descriptionLabel.setBounds(50, 100, 100, 30);
            descriptionLabel.setOpaque(true);
            descriptionLabel.setForeground(new Color(248, 146, 137));
            descriptionLabel.setBackground(new Color(255, 255, 255));

            JTextArea descriptionArea = new JTextArea();
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            JScrollPane descScrollPane = new JScrollPane(descriptionArea);
            descScrollPane.setBounds(160, 100, 200, 50);
            descScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            descScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
            descScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
            descScrollPane.getVerticalScrollBar().setUnitIncrement(20);
            descriptionArea.setBackground(new Color(255,255,255));

            JLabel ingredientsLabel = new JLabel("Ingredients:");
            ingredientsLabel.setBounds(50, 160, 100, 30);
            ingredientsLabel.setOpaque(true);
            ingredientsLabel.setForeground(new Color(248, 146, 137));
            ingredientsLabel.setBackground(new Color(255, 255, 255));

            JTextArea ingredientsArea = new JTextArea();
            ingredientsArea.setLineWrap(true);
            ingredientsArea.setWrapStyleWord(true);
            JScrollPane ingredScrollPane = new JScrollPane(ingredientsArea);
            ingredScrollPane.setBounds(160, 160, 200, 50);
            ingredScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            ingredScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
            ingredScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
            ingredScrollPane.getVerticalScrollBar().setUnitIncrement(20);
            ingredientsArea.setBackground(new Color(255,255,255));

            JLabel servingSizeLabel = new JLabel("Serving Size:");
            servingSizeLabel.setBounds(50, 220, 100, 30);
            servingSizeLabel.setOpaque(true);
            servingSizeLabel.setForeground(new Color(248, 146, 137));
            servingSizeLabel.setBackground(new Color(255, 255, 255));

            JTextField servingSizeField = new JTextField();
            servingSizeField.setBounds(160, 220, 200, 30);
            servingSizeField.setBackground(new Color(255,255,255));
            servingSizeField.setInputVerifier(new ServingSizeVerifier());

            JLabel categoryLabel = new JLabel("Category:");
            categoryLabel.setBounds(50, 260, 100, 30);
            categoryLabel.setOpaque(true);
            categoryLabel.setForeground(new Color(248, 146, 137));
            categoryLabel.setBackground(new Color(255, 255, 255));

            ImageIcon radioSelected = new ImageIcon("pics/check_box.png");
            ImageIcon radioUnselected = new ImageIcon("pics/checkbox default.png");

            JPanel categoryPanel = new JPanel();
            categoryPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
            categoryPanel.setBounds(160, 260, 240, 30);
            categoryPanel.setBackground(Color.WHITE);

            ButtonGroup categoryGroup = new ButtonGroup();
            JRadioButton nonVegButton = createCustomRadioButton("Non-Vegetarian", radioSelected, radioUnselected);
            JRadioButton vegButton = createCustomRadioButton("Vegetarian", radioSelected, radioUnselected);

            categoryGroup.add(nonVegButton);
            categoryGroup.add(vegButton);

            categoryPanel.add(nonVegButton);
            categoryPanel.add(vegButton);

            JLabel nutritionLabel = new JLabel("Nutrition Facts:");
            nutritionLabel.setBounds(50, 300, 100, 30);
            nutritionLabel.setOpaque(true);
            nutritionLabel.setForeground(new Color(248, 146, 137));
            nutritionLabel.setBackground(new Color(255, 255, 255));

            JTextArea nutritionArea = new JTextArea();
            nutritionArea.setLineWrap(true);
            nutritionArea.setWrapStyleWord(true);
            JScrollPane nutritionScrollPane = new JScrollPane(nutritionArea);
            nutritionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            nutritionScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
            nutritionScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
            nutritionScrollPane.getVerticalScrollBar().setUnitIncrement(20);
            nutritionScrollPane.setBounds(160, 300, 200, 50);
            nutritionArea.setBackground(new Color(255,255,255));

            JLabel stocksLabel = new JLabel("Stocks Available:");
            stocksLabel.setBounds(50, 360, 100, 30);
            stocksLabel.setOpaque(true);
            stocksLabel.setForeground(new Color(248, 146, 137));
            stocksLabel.setBackground(new Color(255, 255, 255));

            JTextField stocksField = new JTextField();
            stocksField.setBounds(160, 360, 200, 30);
            stocksField.setBackground(new Color(255,255,255));
            stocksField.setText(String.valueOf(this.quantityAvailable));
            stocksField.setInputVerifier(new StocksVerifier());

            JLabel priceLabel = new JLabel("Price:");
            priceLabel.setBounds(50, 400, 100, 30);
            priceLabel.setOpaque(true);
            priceLabel.setForeground(new Color(248, 146, 137));
            priceLabel.setBackground(new Color(255, 255, 255));

            JTextField priceField = new JTextField();
            priceField.setBounds(160, 400, 200, 30);
            priceField.setBackground(new Color(255,255,255));
            priceField.setInputVerifier(new PriceVerifier());

            // Type Section - Moved after price
            JLabel typeLabel = new JLabel("Type:");
            typeLabel.setBounds(50, 440, 100, 30);
            typeLabel.setOpaque(true);
            typeLabel.setForeground(new Color(248, 146, 137));
            typeLabel.setBackground(new Color(255, 255, 255));

            JPanel typePanel = new JPanel();
            typePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
            typePanel.setBounds(160, 440, 300, 30);
            typePanel.setBackground(Color.WHITE);

            ButtonGroup typeGroup = new ButtonGroup();
            JRadioButton breakfastButton = createCustomRadioButton("Breakfast", radioSelected, radioUnselected);
            JRadioButton lunchButton = createCustomRadioButton("Lunch", radioSelected, radioUnselected);
            JRadioButton dinnerButton = createCustomRadioButton("Dinner", radioSelected, radioUnselected);

            typeGroup.add(breakfastButton);
            typeGroup.add(lunchButton);
            typeGroup.add(dinnerButton);

            typePanel.add(breakfastButton);
            typePanel.add(lunchButton);
            typePanel.add(dinnerButton);

            ImageIcon checkboxSelected = new ImageIcon("pics/check_box.png");
            ImageIcon checkboxUnselected = new ImageIcon("pics/checkbox default.png");

            JCheckBox spicyCheckBox = createCustomCheckBox("Spicy", checkboxSelected, checkboxUnselected);
            spicyCheckBox.setBounds(160, 480, 100, 30);
            spicyCheckBox.setBackground(Color.WHITE);
            spicyCheckBox.setForeground(new Color(248, 146, 137));

            // Image Panel
            JPanel imagePanel = new JPanel();
            imagePanel.setBounds(400, 60, 300, 300);
            imagePanel.setBackground(Color.WHITE);
            TitledBorder titledBorder = BorderFactory.createTitledBorder("Meal Image");
            titledBorder.setTitleColor(new Color(248, 146, 137));
            imagePanel.setBorder(titledBorder);

            JLabel imageLabel = new JLabel();
            imageLabel.setPreferredSize(new Dimension(280, 280));
            imagePanel.add(imageLabel);

            JButton chooseImageBtn = new JButton("Choose Image");
            ImageIcon imageIcon = new ImageIcon("pics/insert image.png");
            chooseImageBtn.setIcon(imageIcon);
            chooseImageBtn.setBounds(400, 370, imageIcon.getIconWidth(), imageIcon.getIconHeight());
            chooseImageBtn.setMargin(new Insets(0, 0, 0, 0));
            chooseImageBtn.setFocusPainted(false);
            chooseImageBtn.setBorderPainted(false);
            chooseImageBtn.setContentAreaFilled(false);

            // Error Label
            JLabel errorLabel = new JLabel();
            errorLabel.setForeground(Color.RED);
            errorLabel.setBounds(50, 460, 400, 30);

            // Load current meal data
            BufferedImage currentImage = null;
            try {
                Meal meal = SQLMeal.getMealInfo(mealID);
                if (meal != null) {
                    nameField.setText(meal.getName());
                    switch (meal.getType().toLowerCase()) {
                        case "breakfast":
                            breakfastButton.setSelected(true);
                            break;
                        case "lunch":
                            lunchButton.setSelected(true);
                            break;
                        case "dinner":
                            dinnerButton.setSelected(true);
                            break;
                    }
                    descriptionArea.setText(meal.getDescription());
                    ingredientsArea.setText(meal.getIngredients());
                    servingSizeField.setText(meal.getServingSize());
                    // Set category radio button
                    if (meal.getCategory().equalsIgnoreCase("vegetarian")) {
                        vegButton.setSelected(true);
                    } else {
                        nonVegButton.setSelected(true);
                    }
                    nutritionArea.setText(meal.getNutritionFact());
                    spicyCheckBox.setSelected(meal.getIsSpicy());
                    stocksField.setText(String.valueOf(quantityAvailable));
                    priceField.setText(String.format("%.2f", SQLInventory.getPrice(mealID)));
                    currentImage = meal.getImage();
                    if (currentImage != null) {
                        Image scaledImage = currentImage.getScaledInstance(280, 280, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaledImage));
                    }
                }
            } catch (Exception ex) {
                errorLabel.setText("Error loading meal data: " + ex.getMessage());
            }

            // Image chooser action
            final BufferedImage[] selectedImage = {currentImage};
            chooseImageBtn.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));

                if (fileChooser.showOpenDialog(editWindow) == JFileChooser.APPROVE_OPTION) {
                    try {
                        selectedImage[0] = ImageIO.read(fileChooser.getSelectedFile());
                        Image scaledImage = selectedImage[0].getScaledInstance(280, 280, Image.SCALE_SMOOTH);
                        imageLabel.setIcon(new ImageIcon(scaledImage));
                    } catch (IOException ex) {
                        errorLabel.setText("Error loading image: " + ex.getMessage());
                    }
                }
            });

            List<Meal> updatedMeals = new ArrayList<>();
            // Confirm Button
            JButton confirmEdit = new JButton("Save Changes");
            confirmEdit.setIcon(new ImageIcon("pics/Update Meal.png"));
            confirmEdit.setBounds(300, 520, 180, 31);
            confirmEdit.setMargin(new Insets(0, 0, 0, 0));
            confirmEdit.setFocusPainted(false);
            confirmEdit.setBorderPainted(false);
            confirmEdit.setContentAreaFilled(false);
            confirmEdit.setBounds(300, 520, 150, 40);
            confirmEdit.addActionListener(e -> {
                try {
                    // Validate required fields
                    if (nameField.getText().trim().isEmpty()) {
                        errorLabel.setText("Meal name is required");
                        return;
                    }
                    // Get selected type
                    String selectedType = "";
                    if (breakfastButton.isSelected()) selectedType = "Breakfast";
                    else if (lunchButton.isSelected()) selectedType = "Lunch";
                    else if (dinnerButton.isSelected()) selectedType = "Dinner";

                    // Get selected category
                    String selectedCategory = "";
                    if (vegButton.isSelected()) selectedCategory = "Vegetarian";
                    else if (nonVegButton.isSelected()) selectedCategory = "Non-Vegetarian";

                    if (selectedType.isEmpty() || selectedCategory.isEmpty()) {
                        errorLabel.setText("Please select both type and category");
                        return;
                    }
                    float price;
                    try {
                        price = Float.parseFloat(priceField.getText().trim());
                        if (price < 0) {
                            errorLabel.setText("Price cannot be negative");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        errorLabel.setText("Invalid price format");
                        return;
                    }
                    // Update meal using the SQLMeal class
                    Meal updatedMeal = new Meal(
                            mealID,
                            nameField.getText().trim(),
                            selectedType,
                            descriptionArea.getText().trim(),
                            ingredientsArea.getText().trim(),
                            servingSizeField.getText().trim(),
                            selectedImage[0],
                            selectedCategory,
                            "",
                            spicyCheckBox.isSelected()
                    );

                    SharedData.addUpdatedMeal(updatedMeal);

                    // Update stock quantity in the inventory table
                    this.quantityAvailable = Integer.parseInt(stocksField.getText().trim());

                    SQLInventory.editInventory(mealID,(int)price, this.quantityAvailable,SQLInventory.getQuantitySold(mealID));

                    // Add log message
                    JLabel logMessage = new JLabel("Updated meal: " + nameField.getText());
                    leftContentPanel.add(logMessage);
                    leftContentPanel.revalidate();
                    leftContentPanel.repaint();

                    editWindow.dispose();

                } catch (Exception ex) {
                    errorLabel.setText("Error updating meal: " + ex.getMessage());
                }
            });

            // Add components to main panel
            mainPanel.add(exitButton);
            mainPanel.add(editLabel);
            mainPanel.add(nameLabel);
            mainPanel.add(nameField);
            mainPanel.add(typeLabel);
            mainPanel.add(typePanel);
            mainPanel.add(descriptionLabel);
            mainPanel.add(descScrollPane);
            mainPanel.add(ingredientsLabel);
            mainPanel.add(ingredScrollPane);
            mainPanel.add(servingSizeLabel);
            mainPanel.add(servingSizeField);
            mainPanel.add(categoryLabel);
            mainPanel.add(categoryPanel);
            mainPanel.add(nutritionLabel);
            mainPanel.add(nutritionScrollPane);
            mainPanel.add(stocksLabel);
            mainPanel.add(stocksField);
            mainPanel.add(priceLabel);
            mainPanel.add(priceField);
            mainPanel.add(spicyCheckBox);
            mainPanel.add(imagePanel);
            mainPanel.add(chooseImageBtn);
            mainPanel.add(confirmEdit);
            mainPanel.add(errorLabel);

            // Add main panel to frame
            editWindow.add(mainPanel);
            editWindow.setVisible(true);
        }


        private void confirmAndDelete(int mealID) {
            if (markedForDeletion) {
                return;  // Prevent multiple deletion attempts
            }

            // Show confirmation dialog to the user
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this meal? This action cannot be undone.",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    markedForDeletion = true;
                    this.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
                    this.setBackground(new Color(255, 200, 200));  // Mark the panel as 'deleted' visually

                    // Delete from the INVENTORY table (SQLInventory class)
                    SQLInventory.deleteInventory(mealID);

                    // Call the deleteMeal method to delete the meal from the MEALS table
                    SQLMeal.deleteMeal(mealID);

                    // Remove the current panel from the parent container (UI update)
                    Container parentContainer = this.getParent();
                    if (parentContainer != null) {
                        parentContainer.remove(this);
                        parentContainer.revalidate();  // Re-layout the container
                        parentContainer.repaint();     // Refresh the UI
                    }

                    // Show success message
                    JOptionPane.showMessageDialog(this, "Meal deleted successfully.", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);

                } catch (Exception e) {
                    markedForDeletion = false;
                    this.setBorder(null);  // Reset the visual mark
                    this.setBackground(null);  // Reset the background color
                    JOptionPane.showMessageDialog(this,
                            "Error deleting meal: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    // Custom input verifier for meal type
    class MealTypeVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField field = (JTextField) input;
            String text = field.getText().trim().toLowerCase();
            boolean isValid = text.equals("breakfast") || text.equals("lunch") || text.equals("dinner");

            if (!isValid) {
                JOptionPane.showMessageDialog(input,
                        "Type must be either 'breakfast', 'lunch', or 'dinner'",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                field.setText("");
            }
            return isValid;
        }
    }

    // Custom input verifier for serving size
    class ServingSizeVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField field = (JTextField) input;
            String text = field.getText().trim();
            if (!text.toLowerCase().endsWith("grams")) {
                JOptionPane.showMessageDialog(input,
                        "Serving size must end with 'grams'",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            try {
                String numberPart = text.substring(0, text.length() - 5).trim();
                Integer.parseInt(numberPart);
                return true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(input,
                        "Serving size must be a number followed by 'grams'",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                field.setText("");
                return false;
            }
        }
    }

    // Custom input verifier for category
    class CategoryVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField field = (JTextField) input;
            String text = field.getText().trim().toLowerCase();
            boolean isValid = text.equals("vegetarian") || text.equals("non-vegetarian");

            if (!isValid) {
                JOptionPane.showMessageDialog(input,
                        "Category must be either 'vegetarian' or 'non-vegetarian'",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                field.setText("");
            }
            return isValid;
        }
    }

    // Custom input verifier for stocks
    class StocksVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField field = (JTextField) input;
            try {
                int value = Integer.parseInt(field.getText().trim());
                if (value < 0) {
                    JOptionPane.showMessageDialog(input,
                            "Stocks cannot be negative",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    field.setText("0");
                    return false;
                }
                return true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(input,
                        "Stocks must be a whole number",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                field.setText("0");
                return false;
            }
        }
    }

    // Custom input verifier for price
    class PriceVerifier extends InputVerifier {
        @Override
        public boolean verify(JComponent input) {
            JTextField field = (JTextField) input;
            try {
                float value = Float.parseFloat(field.getText().trim());
                if (value < 0) {
                    JOptionPane.showMessageDialog(input,
                            "Price cannot be negative",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    field.setText("0.00");
                    return false;
                }
                // Format to 2 decimal places
                field.setText(String.format("%.2f", value));
                return true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(input,
                        "Price must be a valid number",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
                field.setText("0.00");
                return false;
            }
        }
    }

    private JRadioButton createCustomRadioButton(String text, ImageIcon selectedIcon, ImageIcon unselectedIcon) {
        JRadioButton button = new JRadioButton(text);
        button.setIcon(unselectedIcon);
        button.setSelectedIcon(selectedIcon);
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(248, 146, 137));
        button.setFocusPainted(false);
        return button;
    }

    // Helper method to create custom checkbox
    private JCheckBox createCustomCheckBox(String text, ImageIcon selectedIcon, ImageIcon unselectedIcon) {
        JCheckBox checkbox = new JCheckBox(text);
        checkbox.setIcon(unselectedIcon);
        checkbox.setSelectedIcon(selectedIcon);
        checkbox.setBackground(Color.WHITE);
        checkbox.setForeground(new Color(248, 146, 137));
        checkbox.setFocusPainted(false);
        return checkbox;
    }

        //helper method kuhag mealID
        public int getMealID() {return mealID;}

        public boolean isMarkedForDeletion() {return markedForDeletion;}

        public int getQuantityAvailable() {return this.quantityAvailable;}

        public int getMealId(){
            return mealID;
        }

        public void updateQuantityLabel(int newQuantity) {
            if (newQuantity >= 0) {
                this.quantityAvailable = newQuantity;
                quantityLabel.setText("Stocks: " + newQuantity);
            } else {
                throw new IllegalArgumentException("Quantity cannot be negative");
            }
        }
    }
