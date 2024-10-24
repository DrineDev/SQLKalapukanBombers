package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class MainFrameEmployee extends JFrame {
    private JFrame mainFrame;
    private JButton exitButton;

    public MainFrameEmployee() {
        // Food icons
        ImageIcon fIcon = new ImageIcon("Rectangle 8.png");
        ImageIcon fIconHover = new ImageIcon("Rectangle 8 (1).png");
        
        // Exit button
        ImageIcon exitImageIcon = new ImageIcon("exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener((ActionEvent e) -> 
        {
            System.exit(0);
        });

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

        // Right side
        JPanel rightSideWhole = new JPanel();
        rightSideWhole.setLayout(new BorderLayout());
        rightSideWhole.setPreferredSize(new Dimension(680, 2000));
        rightSideWhole.setBackground(Color.white);
        rightSideWhole.setBorder(new EmptyBorder(0, 20, 0, 0)); // Add left padding

        // Panel for exit button
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(Color.white);
        exitPanel.add(exitButton);

        // Bottom area of right side
        JPanel rightSideComponents = new JPanel();
        rightSideComponents.setLayout(new GridLayout(0, 2, 0, 10)); // Adjust gap
        rightSideComponents.setPreferredSize(new Dimension(680, 1000));
        rightSideComponents.setBackground(Color.white);
        for (AddFood food : foods) 
        {
            rightSideComponents.add(food); 
        }

        JScrollPane rightSideScrollPane = new JScrollPane(rightSideComponents);
        rightSideScrollPane.setPreferredSize(new Dimension(680, 3000));
        rightSideScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightSideScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        rightSideScrollPane.setBorder(null);
        
        rightSideScrollPane.getViewport().setViewPosition(new java.awt.Point(0, 20));

        // custom scrolling shit
        rightSideScrollPane.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int currentY = rightSideScrollPane.getViewport().getViewPosition().y;
                int newY = currentY + e.getWheelRotation() * 70; 

                if (newY < 20) {
                    newY = 20;
                }
                
                rightSideScrollPane.getViewport().setViewPosition(
                    new java.awt.Point(
                        rightSideScrollPane.getViewport().getViewPosition().x,
                        newY
                    )
                );
            }
        });

        rightSideWhole.add(exitPanel, BorderLayout.NORTH);
        rightSideWhole.add(rightSideScrollPane, BorderLayout.CENTER);
        
        mainFrame.add(leftSide, BorderLayout.WEST);
        mainFrame.add(rightSideWhole, BorderLayout.EAST);
        
        mainFrame.setVisible(true);
    }
}