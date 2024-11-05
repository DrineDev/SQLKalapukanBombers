package org.example;

import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;

import javax.swing.*;
import java.awt.*;

public class AddInventory extends JPanel {
    private ImageIcon foodImage;
    private int quantityAvailable;
    private final int HOVER_DELAY = 300; // Delay in milliseconds

    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public AddInventory(int mealID) {
        // Get food image
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

        // Edit button
        JButton editButton = new JButton("Edit");
        editButton.setBounds(200, 250, 90, 50);
        editButton.addActionListener(e -> openEditWindow(mealID, quantityLabel));

        // Add the components to the panel
        this.add(foodBg);
        this.add(quantityLabel);
        this.add(editButton);
    }

    // Pop-up frame for editing inventory
    private void openEditWindow(int mealID, JLabel quantityLabel) {
        JFrame editWindow = new JFrame("Edit Inventory");
        editWindow.setSize(400, 300);
        editWindow.setLocationRelativeTo(this);
        editWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editWindow.setLayout(null);

        JLabel editLabel = new JLabel("Edit Inventory Details");
        editLabel.setBounds(50, 20, 300, 30);

        // Text field for quantity
        JTextField quantityField = new JTextField();
        quantityField.setBounds(50, 70, 100, 30);
        quantityField.setHorizontalAlignment(JTextField.CENTER);
        quantityField.setInputVerifier(new IntegerInputVerifier());

        // Error label to display if input is not an integer
        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(50, 100, 300, 30);

        // Confirm Edit button
        JButton confirmEdit = new JButton("Confirm");
        confirmEdit.setBounds(180, 70, 100, 30);
        confirmEdit.addActionListener(e -> {
            if (((IntegerInputVerifier) quantityField.getInputVerifier()).isValid()) {
                int newQuantity = Integer.parseInt(quantityField.getText());
                try {
                    SQLInventory.setQuantityAvailable(mealID, newQuantity);
                    quantityLabel.setText("Stocks Available: " + newQuantity);
                    editWindow.dispose();
                } catch (Exception ex) {
                    errorLabel.setText("Error updating quantity: " + ex.getMessage());
                }
            } else {
                errorLabel.setText("Please enter a valid integer value.");
            }
        });

        editWindow.add(editLabel);
        editWindow.add(quantityField);
        editWindow.add(confirmEdit);
        editWindow.add(errorLabel);
        editWindow.setVisible(true);
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
}