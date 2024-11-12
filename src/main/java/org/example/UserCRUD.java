package org.example;

import javax.swing.*;
import java.awt.*;

public class UserCRUD extends JFrame
{
    public UserCRUD()
    {
        this.setSize(new Dimension(1000, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true); // No title bar or window controls
        this.setLayout(null); // Absolute positioning for components

        // Background panel
        JPanel bg = new JPanel();
        bg.setBounds(0, 0, 1000, 600);
        bg.setBackground(new Color(248, 146, 137, 255));

        ImageIcon crudBG = new ImageIcon("pics/crud box.png");
        JLabel crudLabel = new JLabel(crudBG);
        crudLabel.setBounds(33,35,240,250);
        crudLabel.setLayout(null);

        ImageIcon crudBig = new ImageIcon("pics/crud big box.png");
        JLabel crudBigArea = new JLabel(crudBig);
        crudBigArea.setBounds(300,45, 660,520);

        ImageIcon addUser = new ImageIcon("pics/add user.png");
        JCheckBox addUserButton = new JCheckBox(addUser);
        addUserButton.setBounds(50, 65,135, 35);

        ImageIcon updateUser = new ImageIcon("pics/update user.png");
        JCheckBox updateUserButton = new JCheckBox(updateUser);
        updateUserButton.setBounds(50,110, 135,35);

        ImageIcon deleteUser = new ImageIcon("pics/delete user.png");
        JCheckBox deleteUserButton = new JCheckBox(deleteUser);
        deleteUserButton.setBounds(50,155,135,34);


        crudLabel.add(addUserButton);
        crudLabel.add(updateUserButton);
        crudLabel.add(deleteUserButton);

        ImageIcon leftBottom = new ImageIcon("pics/crud bottom box.png");
        JLabel crudBottom = new JLabel(leftBottom);
        crudBottom.setBounds(33, 260, 240, 320);

        

        //navigation shits
        NavigatorButtonManager navButton = new NavigatorButtonManager();
        navButton.setBounds(10,10,206,360);

        //exit
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button2.png");
        JButton exitButton = new JButton();
        exitButton.setIcon(exitImageIcon); // Set the image icon
        exitButton.setContentAreaFilled(false); // Remove button background
        exitButton.setFocusPainted(false); // Remove focus effect
        exitButton.setBorderPainted(false);
        exitButton.setBounds(970, 10, 20, 20);
        exitButton.addActionListener(e -> System.exit(0));


        this.add(navButton);
        this.add(crudLabel);
        this.add(crudBottom);
        this.add(crudBigArea);
        this.add(exitButton);
        this.add(bg);
        this.setVisible(true);
    }
}
