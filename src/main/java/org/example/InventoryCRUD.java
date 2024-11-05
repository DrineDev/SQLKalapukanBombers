package org.example;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class InventoryCRUD extends JFrame {
    private JFrame mainFrame;
    private JButton exitButton;
    private JButton confirmButton;

    public InventoryCRUD() {
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
        JPanel rightSideWhole = new JPanel();
        rightSideWhole.setLayout(new BorderLayout());
        rightSideWhole.setPreferredSize(new Dimension(680, 2000));
        rightSideWhole.setBackground(Color.white);
        rightSideWhole.setBorder(new EmptyBorder(0, 20, 0, 0)); // Add left padding

        //Panel for exit button
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(Color.white);
        exitPanel.add(exitButton);
        rightSideWhole.add(exitPanel, BorderLayout.NORTH);

        // bottom sa right side ang babaw kay katong exiters
        JPanel rightSideBottom = new JPanel();
        rightSideBottom.setLayout(new BoxLayout(rightSideBottom, BoxLayout.Y_AXIS));

        //Scrollable panel for food items
        JPanel foodItemsPanel = new JPanel();
        foodItemsPanel.setLayout(new GridLayout(0,2,10,10));
        foodItemsPanel.setBackground(Color.white);
        foodItemsPanel.add(new AddInventory(1));
        foodItemsPanel.add(new AddInventory(2));
        foodItemsPanel.add(new AddInventory(3));
        foodItemsPanel.add(new AddInventory(4));
        foodItemsPanel.add(new AddInventory(5));
        foodItemsPanel.add(new AddInventory(6));
        foodItemsPanel.add(new AddInventory(7));
        foodItemsPanel.add(new AddInventory(8));
        foodItemsPanel.add(new AddInventory(9));
        foodItemsPanel.add(new AddInventory(10));
        foodItemsPanel.add(new AddInventory(11));

        JScrollPane scrollPane = new JScrollPane(foodItemsPanel);
        scrollPane.setPreferredSize(new Dimension(680, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);


        rightSideBottom.add(scrollPane);
        rightSideWhole.add(rightSideBottom, BorderLayout.CENTER);

        // Left side
        JPanel leftSide = new JPanel();
        leftSide.setLayout(new BorderLayout());
        leftSide.setPreferredSize(new Dimension(320, 600));
        leftSide.setBackground(new Color(248, 146, 137));

        // Create a container panel for the scrollpane and button with padding
        JPanel scrollPaneContainer = new JPanel();
        scrollPaneContainer.setLayout(new BoxLayout(scrollPaneContainer, BoxLayout.Y_AXIS));
        scrollPaneContainer.setBackground(new Color(248, 146, 137));
        scrollPaneContainer.setBorder(new EmptyBorder(20, 20, 20, 20)); // Add padding on all sides

        // katung panel sa left area
        JPanel leftContentPanel = new JPanel();
        leftContentPanel.setLayout(new BoxLayout(leftContentPanel, BoxLayout.Y_AXIS));
        leftContentPanel.setBackground(Color.WHITE);

        // scrollpane para sa katong panel sa left area
        JScrollPane leftScrollPane = new JScrollPane(leftContentPanel);
        leftScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        leftScrollPane.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Color.GRAY, 1), // Outer border
                BorderFactory.createEmptyBorder(5, 5, 5, 5) // Inner padding
        ));
        leftScrollPane.getViewport().setBackground(Color.WHITE); // mga chuy2 nga edit gipalimpyo nakos gpt kaning mga nas ubos
        leftScrollPane.setPreferredSize(new Dimension(280, 400)); // reduce hayt
        leftScrollPane.setMaximumSize(new Dimension(280, 400)); // add max size ambot lng ngano

        // Create confirm button sa ubos sa changelogs
        confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setMaximumSize(new Dimension(280, 30));
        confirmButton.setBackground(new Color(248, 146, 137));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Create a panel for the confirm button with some top margin pina katong sa exit button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(248, 146, 137));
        buttonPanel.setBorder(new EmptyBorder(15, 0, 0, 0)); // Add top margin
        buttonPanel.add(confirmButton);

        // Add components to the container
        scrollPaneContainer.add(leftScrollPane);
        scrollPaneContainer.add(buttonPanel);

        // Add the container panel to the left side
        leftSide.add(scrollPaneContainer, BorderLayout.CENTER);

        mainFrame.add(leftSide, BorderLayout.WEST);
        mainFrame.add(rightSideWhole, BorderLayout.EAST);
        mainFrame.setVisible(true);

        // hover effect sa confirm button mu white ig hover niya normal ig hawa
        confirmButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e)
            {
                confirmButton.setBackground(Color.WHITE);
                confirmButton.setForeground(new Color(248, 146, 137));
            }

            public void mouseExited(MouseEvent e)
            {
                confirmButton.setBackground(new Color(248, 146, 137));
                confirmButton.setForeground(Color.WHITE);
            }
        });
    }
}