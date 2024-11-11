package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.example.Classes.Meal;
import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;


public class AddInventory extends JPanel {
    private ImageIcon foodImage;
    private int quantityAvailable;
    private JLabel quantityLabel;
    private JPanel leftContentPanel;
    private int mealID;
    private Boolean markedForDeletion=false;


    public AddInventory(int mealID, JPanel leftContentPanel) {
        this.mealID = mealID;
        this.leftContentPanel = leftContentPanel;
        this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));
        this.quantityAvailable = SQLInventory.getQuantityAvailable(mealID);

        this.setPreferredSize(new Dimension(300, 300));
        this.setLayout(null);

        // Food background
        JLabel foodBg = new JLabel();
        foodBg.setBounds(0, 0, 300, 250);
        foodBg.setIcon(foodImage);

        // Label to display quantity available
        quantityLabel = new JLabel();
        quantityLabel.setText("Stocks: " + this.quantityAvailable);
        quantityLabel.setBounds(0, 250, 200, 50);
        quantityLabel.setHorizontalAlignment(JLabel.LEFT);

        // Edit button
        ImageIcon edit = new ImageIcon("pics/edit.png");
        JButton editButton = new JButton(edit);
        editButton.setBounds(180, 255, 62, 34);
        editButton.addActionListener(e -> openEditWindow(mealID));

        // Delete button
        ImageIcon delete = new ImageIcon("pics/delete.png");
        JButton deleteButton = new JButton(delete);
        deleteButton.setBounds(250, 255, 62, 34);
        deleteButton.addActionListener(e -> confirmAndDelete(mealID));

        // Add the components to the panel
        this.add(foodBg);
        this.add(quantityLabel);
        this.add(editButton);
        this.add(deleteButton);
    }

    //add inventory pang employee way edit display ra
    public AddInventory(int mealID, String employee) {
        // Get food image
        this.mealID = mealID;
        this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));
        this.quantityAvailable = SQLInventory.getQuantityAvailable(mealID);

        this.setPreferredSize(new Dimension(300, 300));
        this.setLayout(null);

        // Food background
        JLabel foodBg = new JLabel();
        foodBg.setBounds(0, 0, 300, 250);
        foodBg.setIcon(foodImage);

        // Label to display quantity available
        JLabel quantityLabel = new JLabel();
        quantityLabel.setText("Stocks Available: " + this.quantityAvailable);
        quantityLabel.setBounds(0, 250, 300, 50);
        quantityLabel.setHorizontalAlignment(JLabel.LEFT);

        // Add the components to the panel
        this.add(foodBg);
        this.add(quantityLabel);
    }

    // Pop-up frame for editing inventory
    private void openEditWindow(int mealID) {
        JFrame editWindow = new JFrame("Edit Meal Details");
        editWindow.setSize(800, 600);
        editWindow.setLocationRelativeTo(this);
        editWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editWindow.setLayout(null);

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

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setBounds(50, 100, 100, 30);
        typeLabel.setOpaque(true);
        typeLabel.setForeground(new Color(248, 146, 137));
        typeLabel.setBackground(new Color(255, 255, 255));

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(50, 140, 100, 30);
        descriptionLabel.setOpaque(true);
        descriptionLabel.setForeground(new Color(248, 146, 137));
        descriptionLabel.setBackground(new Color(255, 255, 255));

        JLabel ingredientsLabel = new JLabel("Ingredients:");
        ingredientsLabel.setBounds(50, 200, 100, 30);
        ingredientsLabel.setOpaque(true);
        ingredientsLabel.setForeground(new Color(248, 146, 137));
        ingredientsLabel.setBackground(new Color(255, 255, 255));

        JLabel servingSizeLabel = new JLabel("Serving Size:");
        servingSizeLabel.setBounds(50, 260, 100, 30);
        servingSizeLabel.setOpaque(true);
        servingSizeLabel.setForeground(new Color(248, 146, 137));
        servingSizeLabel.setBackground(new Color(255, 255, 255));

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(50, 300, 100, 30);
        categoryLabel.setOpaque(true);
        categoryLabel.setForeground(new Color(248, 146, 137));
        categoryLabel.setBackground(new Color(255, 255, 255));

        JLabel nutritionLabel = new JLabel("Nutrition Facts:");
        nutritionLabel.setBounds(50, 340, 100, 30);
        nutritionLabel.setOpaque(true);
        nutritionLabel.setForeground(new Color(248, 146, 137));
        nutritionLabel.setBackground(new Color(255, 255, 255));

        JLabel stocksLabel = new JLabel("Stocks Available:");
        stocksLabel.setBounds(50, 400, 100, 30);
        stocksLabel.setOpaque(true);
        stocksLabel.setForeground(new Color(248, 146, 137));
        stocksLabel.setBackground(new Color(255, 255, 255));

        // Text Fields and Areas
        JTextField nameField = new JTextField();
        nameField.setBounds(160, 60, 200, 30);
        nameField.setBackground(new Color(255,255,255));

        JTextField typeField = new JTextField();
        typeField.setBounds(160, 100, 200, 30);
        typeField.setBackground(new Color(255,255,255));

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setBounds(160, 140, 200, 50);
        descScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        descScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        descScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        descriptionArea.setBackground(new Color(255,255,255));

        JTextArea ingredientsArea = new JTextArea();
        ingredientsArea.setLineWrap(true);
        ingredientsArea.setWrapStyleWord(true);
        JScrollPane ingredScrollPane = new JScrollPane(ingredientsArea);
        ingredScrollPane.setBounds(160, 200, 200, 50);
        ingredScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        ingredScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        ingredScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        ingredScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        ingredientsArea.setBackground(new Color(255,255,255));

        JTextField servingSizeField = new JTextField();
        servingSizeField.setBounds(160, 260, 200, 30);
        servingSizeField.setBackground(new Color(255,255,255));

        JTextField categoryField = new JTextField();
        categoryField.setBounds(160, 300, 200, 30);
        categoryField.setBackground(new Color(255,255,255));

        JTextArea nutritionArea = new JTextArea();
        nutritionArea.setLineWrap(true);
        nutritionArea.setWrapStyleWord(true);
        JScrollPane nutritionScrollPane = new JScrollPane(nutritionArea);
        nutritionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        nutritionScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        nutritionScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        nutritionScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        nutritionScrollPane.setBounds(160, 340, 200, 50);
        nutritionArea.setBackground(new Color(255,255,255));

        JTextField stocksField = new JTextField();
        stocksField.setBounds(160, 400, 200, 30);
        stocksField.setBackground(new Color(255,255,255));
        stocksField.setText(String.valueOf(this.quantityAvailable));

        // Spicy Checkbox
        JCheckBox spicyCheckBox = new JCheckBox("Spicy");
        spicyCheckBox.setBounds(160, 440, 100, 30);
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
                typeField.setText(meal.getType());
                descriptionArea.setText(meal.getDescription());
                ingredientsArea.setText(meal.getIngredients());
                servingSizeField.setText(meal.getServingSize());
                categoryField.setText(meal.getCategory());
                nutritionArea.setText(meal.getNutritionFact());
                spicyCheckBox.setSelected(meal.getIsSpicy());
                stocksField.setText(String.valueOf(quantityAvailable));

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
        confirmEdit.setIcon(new ImageIcon("pics/update meal.png"));
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
                quantityAvailable=Integer.parseInt(stocksField.getText().trim());
                // Update meal using the SQLMeal class
                Meal updatedMeal = new Meal(
                    mealID,
                    nameField.getText().trim(),
                    typeField.getText().trim(),
                    descriptionArea.getText().trim(),
                    ingredientsArea.getText().trim(),
                    servingSizeField.getText().trim(),
                    selectedImage[0],
                    categoryField.getText().trim(),
                    "", spicyCheckBox.isSelected()
                );

                SharedData.addUpdatedMeal(updatedMeal);

                // Update stock quantity in the inventory table
                int newStockQuantity = Integer.parseInt(stocksField.getText().trim());
                    SQLInventory.setQuantityAvailable(mealID, newStockQuantity);

                // Add log message
                JLabel logMessage = new JLabel("Updated meal: " + nameField.getText());
                leftContentPanel.add(logMessage);
                leftContentPanel.revalidate();
                leftContentPanel.repaint();

                // Update the quantity label in the AddInventory panel
                quantityLabel.setText("Stocks: " + newStockQuantity);

                editWindow.dispose();

            } catch (Exception ex) {
                errorLabel.setText("Error updating meal: " + ex.getMessage());
            }
        });

        // Add components to main panel
        mainPanel.add(editLabel);
        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(typeLabel);
        mainPanel.add(typeField);
        mainPanel.add(descriptionLabel);
        mainPanel.add(descScrollPane);
        mainPanel.add(ingredientsLabel);
        mainPanel.add(ingredScrollPane);
        mainPanel.add(servingSizeLabel);
        mainPanel.add(servingSizeField);
        mainPanel.add(categoryLabel);
        mainPanel.add(categoryField);
        mainPanel.add(nutritionLabel);
        mainPanel.add(nutritionScrollPane);
        mainPanel.add(stocksLabel);
        mainPanel.add(stocksField);
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
            return; // Already marked for deletion
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this meal?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // Mark for deletion and add log message
            markedForDeletion = true;

            // Add visual indication that item is marked for deletion
            this.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            this.setBackground(new Color(255, 200, 200));

            // Log the pending deletion
            JLabel logMessage = new JLabel("DELETE:Meal_ID:" + mealID);
            leftContentPanel.add(logMessage);
            leftContentPanel.revalidate();
            leftContentPanel.repaint();
        }
    }

    // Custom input verifier to allow only integer values
    private static class IntegerInputVerifier extends InputVerifier {
        private boolean valid;

        @Override
        public boolean verify(JComponent input) {
            JTextField textField = (JTextField) input;
            String text = textField.getText();
            try {
                Integer.parseInt(text);
                valid = true;
                return true;
            } catch (NumberFormatException e) {
                valid = false;
                return false;
            }
        }

        public boolean isValid() {
            return valid;
        }
    }

    //helper method kuhag mealID
    public int getMealID() {return mealID;}
    //helper method kuhag markedForDeletion
    public boolean isMarkedForDeletion() {return markedForDeletion;}
    //helper method pang update sa quantity label ig human confirm edit sa inventory crud
    public void updateQuantityLabel(int newQuantity) {
        this.quantityAvailable = newQuantity;
        quantityLabel.setText("Stocks Available: " + newQuantity);
    }
}
