package org.example;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MainFrameEmployee extends JFrame
{
    private JFrame mainFrame;
    private JButton exitButton;

    public MainFrameEmployee() {
        // Food icons
        ImageIcon fIcon = new ImageIcon("Rectangle 8.png");
        ImageIcon fIconHover = new ImageIcon("Rectangle 8 (1).png");
        
        // Create food items
        AddFood[] foods = new AddFood[6];
        for (int i = 0; i < foods.length; i++) {
            foods[i] = new AddFood(fIcon, fIconHover);
        }

        // Frame initialization
        mainFrame = new JFrame();
        mainFrame.setSize(1000, 600);
        mainFrame.setUndecorated(true);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        // Left side
        ImageIcon checkoutArea = new ImageIcon("checkout area.png");
        JLabel leftSide = new JLabel();
        leftSide.setIcon(checkoutArea);
        leftSide.setLayout(null);

        // Right side with scroll pane
        JPanel rightSideComponents = new JPanel();
        rightSideComponents.setLayout(null);
        rightSideComponents.setPreferredSize(new Dimension(680, 1000));
        
        JScrollPane rightSide = new JScrollPane(rightSideComponents);
        rightSide.getVerticalScrollBar().setUnitIncrement(16);
        rightSide.setPreferredSize(new Dimension(680, 1000));
        rightSide.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightSide.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Exit button
        ImageIcon exitImageIcon = new ImageIcon("exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setBounds(640, 15, 20, 20); // Position based on null layout

        exitButton.addActionListener((ActionEvent e) -> 
        {
            System.exit(0);
        });

        // Add components to right side panel
        rightSideComponents.add(exitButton);
        int yOffset = 55; 
        for (int i = 0; i < foods.length; i++) {
            foods[i].setBounds(20 + (i % 2) * 330, yOffset, 300, 250); 
            rightSideComponents.add(foods[i]);
            if (i % 2 == 1) {
                yOffset += 275; 
            }
        }

        mainFrame.add(leftSide, BorderLayout.WEST);
        mainFrame.add(rightSide, BorderLayout.EAST);
        
        mainFrame.setVisible(true);
    }
}


