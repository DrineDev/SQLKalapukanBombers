package org.example;

import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class MainFrame extends JFrame
{
    JFrame mainFrame;
    JButton exitButton;
    public MainFrame()
    {
        mainFrame = new JFrame(); // Initialize mainFrame
        mainFrame.setSize(1000, 600);
        mainFrame.setUndecorated(true);
        mainFrame.setLayout(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        //placeholder contents
        JLabel burger = new JLabel();
        burger.setText("burger yummy!");
        burger.setBounds(100,100, 600, 600);
        burger.setBackground(Color.BLACK);


        ImageIcon exitImageIcon = new ImageIcon("exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setBounds(960, 15, 20, 20);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);

        exitButton.addActionListener((ActionEvent e) ->
        {
            System.exit(0);
        });

        mainFrame.add(burger);
        mainFrame.add(exitButton);
        mainFrame.setVisible(true);

    }

}
