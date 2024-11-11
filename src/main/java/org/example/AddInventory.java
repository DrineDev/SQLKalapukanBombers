package org.example;

import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;

import javax.swing.*;
import java.awt.*;

public class AddInventory extends JPanel {
    private ImageIcon foodImage;
    private int quantityAvailable;
    private JLabel quantityLabel;
    private JPanel leftContentPanel;
    private int mealID;


    public AddInventory(int mealID,JPanel leftContentPanel) {
        // get food image niya quantity available sad sa database
        //and assign ang gi pass nga mealID ug leftContentPanel para maka log sa edits didto
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
        quantityLabel.setText("Stocks Available: " + this.quantityAvailable);
        quantityLabel.setBounds(0, 250, 300, 50);
        quantityLabel.setHorizontalAlignment(JLabel.LEFT);

        // Edit button pang abli sa openEditWindow nga pop up
        JButton editButton = new JButton("Edit");
        editButton.setBounds(200, 250, 90, 50);
        editButton.addActionListener(e -> openEditWindow(mealID));

        // Add the components to the panel
        this.add(foodBg);
        this.add(quantityLabel);
        this.add(editButton);
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
        JFrame editWindow = new JFrame("Edit Inventory");
        editWindow.setSize(400, 300);
        editWindow.setLocationRelativeTo(this);
        editWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editWindow.setLayout(null);

        JLabel editLabel = new JLabel("Edit Inventory Details");
        editLabel.setBounds(50, 20, 300, 30);

        JTextField quantityField = new JTextField();
        quantityField.setBounds(50, 70, 100, 30);
        quantityField.setHorizontalAlignment(JTextField.CENTER);
        quantityField.setInputVerifier(new IntegerInputVerifier());

        JLabel errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(50, 100, 300, 30);

        JButton confirmEdit = new JButton("Confirm");
        confirmEdit.setBounds(180, 70, 100, 30);
        //ig click confirm sa pop up
        confirmEdit.addActionListener(e -> {
            if (((IntegerInputVerifier) quantityField.getInputVerifier()).isValid()) {
                int newQuantity = Integer.parseInt(quantityField.getText());
                int oldQuantity = this.quantityAvailable; //pang hold sa karaan quantity para ma log message

                // himo ug log message sa kung unsay gi change
                JLabel logMessage = new JLabel("Updating quantity for meal ID " + mealID +
                        " from " + oldQuantity + " to " + newQuantity);
                leftContentPanel.add(logMessage); // e add sa leftContentPanel katung sa inventoryCRUD
                leftContentPanel.revalidate(); //e update ang leftContentPanel
                leftContentPanel.repaint();

                this.quantityAvailable = newQuantity; // Update the stored value
                editWindow.dispose();
            } else { //di valid ang gisuwat ex. di integer
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

    //helper method kuhag mealID
    public int getMealID() {
        return mealID; // Add mealID as a class field
    }

    //helper method pang update sa quantity label ig human confirm edit sa inventory crud
    public void updateQuantityLabel(int newQuantity) {
        this.quantityAvailable = newQuantity;
        quantityLabel.setText("Stocks Available: " + newQuantity);
    }
}