package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

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

        ImageIcon leftBottom = new ImageIcon("pics/crud bottom box.png");
        JLabel crudBottom = new JLabel(leftBottom);
        crudBottom.setBounds(33, 260, 240, 320);
        crudBottom.setVisible(true);
        crudBottom.setLayout(new BoxLayout(crudBottom, BoxLayout.Y_AXIS));
        crudBottom.setBorder(new EmptyBorder(10, 10, 10, 0));

        JTextField userID = new JTextField();
        userID.setSize(195, 10);   
        JLabel ID = new JLabel("User_ID");

        JTextField username = new JTextField();
        username.setPreferredSize(new Dimension(195,10));
        JLabel user = new JLabel("Username");

        JTextField password = new JTextField();
        password.setPreferredSize(new Dimension(195,10));
        JLabel pass = new JLabel("Password");

        JTextField role = new JTextField();
        role.setPreferredSize(new Dimension(195,25));
        JLabel rolee = new JLabel("Role");

        crudBottom.add(userID);
        crudBottom.add(ID);
        crudBottom.add(username);
        crudBottom.add(user);
        crudBottom.add(password);
        crudBottom.add(pass);
        crudBottom.add(role);
        crudBottom.add(rolee);



        ImageIcon crudBig = new ImageIcon("pics/crud big box.png");
        JLabel crudBigArea = new JLabel(crudBig);
        crudBigArea.setBounds(300,45, 660,520);

        ImageIcon addUser = new ImageIcon("pics/add user.png");
        JCheckBox addUserButton = new JCheckBox(addUser);
        addUserButton.setBorderPainted(false);
        addUserButton.setContentAreaFilled(false);
        addUserButton.setBounds(50, 65,135, 35);
        addUserButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                   
            }
        });

        ImageIcon updateUser = new ImageIcon("pics/update user.png");
        JCheckBox updateUserButton = new JCheckBox(updateUser);
        updateUserButton.setBorderPainted(false);
        updateUserButton.setContentAreaFilled(false);
        updateUserButton.setBounds(50,110, 135,35);

        ImageIcon deleteUser = new ImageIcon("pics/delete user.png");
        JCheckBox deleteUserButton = new JCheckBox(deleteUser);
        deleteUserButton.setBorderPainted(false);
        deleteUserButton.setContentAreaFilled(false);
        deleteUserButton.setBounds(50,155,135,34);


        crudLabel.add(addUserButton);
        crudLabel.add(updateUserButton);
        crudLabel.add(deleteUserButton);

    

        //navigation shits
        NavigatorButtonManager navButton = new NavigatorButtonManager();
        navButton.setBounds(5,5,206,360);

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
