package org.example;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class InventoryCRUDEmployee extends JFrame {
    private JFrame mainFrame;
    private JButton exitButton;

    public InventoryCRUDEmployee() {
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

        // Top panel with thick border and exit button
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(248, 146, 137));
        topPanel.setBorder(BorderFactory.createMatteBorder(20, 0, 0, 0, new Color(248, 146, 137))); // Thick top border
        topPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(exitButton);

        // Right side panel
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BorderLayout());
        rightSidePanel.setBackground(Color.white);
        rightSidePanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding

        // Scrollable panel for food items
        JPanel foodItemsPanel = new JPanel();
        foodItemsPanel.setLayout(new GridLayout(0, 2, 20, 20));
        foodItemsPanel.setBackground(Color.white);
        foodItemsPanel.add(new AddInventory(1,"employee"));
        foodItemsPanel.add(new AddInventory(2,"employee"));
        foodItemsPanel.add(new AddInventory(3,"employee"));
        foodItemsPanel.add(new AddInventory(4,"employee"));
        foodItemsPanel.add(new AddInventory(5,"employee"));
        foodItemsPanel.add(new AddInventory(6,"employee"));
        foodItemsPanel.add(new AddInventory(7,"employee"));
        foodItemsPanel.add(new AddInventory(8,"employee"));
        foodItemsPanel.add(new AddInventory(9,"employee"));
        foodItemsPanel.add(new AddInventory(10,"employee"));
        foodItemsPanel.add(new AddInventory(11,"employee"));

        JScrollPane scrollPane = new JScrollPane(foodItemsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        rightSidePanel.add(scrollPane, BorderLayout.CENTER);

        // Add the top panel and right side panel to the main frame
        mainFrame.add(topPanel, BorderLayout.NORTH);
        mainFrame.add(rightSidePanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }
}
