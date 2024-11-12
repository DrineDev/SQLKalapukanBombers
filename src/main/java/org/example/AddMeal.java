package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AddMeal extends JFrame {
    private static ImageIcon defaultCheckbox = new ImageIcon("pics/checkbox default.png");
    private static ImageIcon selectedCheckbox = new ImageIcon("pics/check_box.png");

    public AddMeal() {
        initializeGUI();
    }

    private void initializeGUI() {
        initializeJFrame();

        JTextField nameTextField = createNameTextField();
        JTextField ingredientsTextField = createIngredientsTextField();
        JTextField descriptionTextField = createDescriptionTextField();
        JTextField nutritionalFactsTextField = createNutritionalFactsTextField();
        JTextField servingSizeTextField = createServingSizeTextField();

        JCheckBox vegetarianCheckBox = createVegetarianCheckBox();
        JCheckBox nonVegetarianCheckBox = createNonVegetarianCheckBox();
        JCheckBox breakfastCheckBox = createBreakfastCheckBox();
        JCheckBox lunchCheckBox = createLunchCheckBox();
        JCheckBox dinnerCheckBox = createDinnerCheckBox();
        
        JButton exitIcon = createExitIcon();

        JLabel spacer = new JLabel();
        spacer.setSize(300,200);

        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 10;
        gbc.gridheight = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(nameTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 10;
        gbc.gridheight = 10;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(ingredientsTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 10;
        gbc.gridheight = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(descriptionTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 10;
        gbc.gridheight = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(nutritionalFactsTextField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(servingSizeTextField, gbc); 
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(spacer, gbc);
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(vegetarianCheckBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(nonVegetarianCheckBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(spacer, gbc);
        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(breakfastCheckBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lunchCheckBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 14;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(dinnerCheckBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        mainPanel.add(exitIcon, gbc);


        this.add(mainPanel);
    }

    private void initializeJFrame() {
        this.setSize(new Dimension(435, 520));
        this.setPreferredSize(new Dimension(435, 520));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(new Color(255, 255, 255));
        this.setLocationRelativeTo(this);
        this.setUndecorated(false);
        this.setVisible(true);
    }

    private JTextField createNameTextField() {
        JTextField nameTextField = new JTextField("Name");
        nameTextField.setPreferredSize(new Dimension(383, 30));
        nameTextField.setBackground(new Color(217, 217, 217));

        return nameTextField;
    }

    private JTextField createIngredientsTextField() {
        JTextField ingredientsTextField = new JTextField("Ingredients");
        ingredientsTextField.setPreferredSize(new Dimension(383, 104));
        ingredientsTextField.setBackground(new Color(217, 217, 217));

        return ingredientsTextField;
    }

    private JTextField createDescriptionTextField() {
        JTextField descriptionTextField = new JTextField("Description");
        descriptionTextField.setPreferredSize(new Dimension(383, 30));
        descriptionTextField.setBackground(new Color(217, 217, 217));

        return descriptionTextField;
    }

    private JTextField createNutritionalFactsTextField() {
        JTextField nutritionalFactsTextField = new JTextField("Nutritional Facts");
        nutritionalFactsTextField.setPreferredSize(new Dimension(384, 50));
        nutritionalFactsTextField.setBackground(new Color(217, 217, 217));

        return nutritionalFactsTextField;
    }

    private JTextField createServingSizeTextField() {
        JTextField servingSizeTextField = new JTextField("Serving Size");
        servingSizeTextField.setPreferredSize(new Dimension(180, 50));
        servingSizeTextField.setBackground(new Color(217, 217, 217));

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

        return dinnerCheckBox;
    }

    private JButton createExitIcon() {
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
        JButton exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setBounds(408, 12, 20, 20);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);

        return exitButton;
    }
}
