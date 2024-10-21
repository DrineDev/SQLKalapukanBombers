package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.example.SQLQueries;

public class SQLEditorGUI extends JFrame
{


    SQLEditorGUI()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(6, 2,10,10));

        JLabel userIDText = new JLabel("ID:");
        JLabel username = new JLabel("Username:");
        JLabel password = new JLabel("Password:");
        JLabel role = new JLabel("Role:");
        JTextField userID = new JTextField();
        JTextField userInput = new JTextField();
        JTextField roleInput = new JTextField();
        JPasswordField passwordInput = new JPasswordField();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton editButton = new JButton("Edit");
        JButton showList = new JButton("Show List");

        addButton.addActionListener((ActionEvent e) ->
        {
            String user_name = username.getText();
            String passInput = new String(passwordInput.getPassword());
            String role_name = role.getText();
            if (user_name.isEmpty()&&passInput.isEmpty()&&role_name.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "Please input all fields!");
                SQLediterUser.addUser(user_name,passInput,role_name);
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Nice one!");
                SQLediterUser.addUser(user_name,passInput,role_name);
            }

        });

        showList.addActionListener((ActionEvent e) ->
        {
            SQLediterUser.listUsers();

        });

        this.add(userIDText);
        this.add(userID);
        this.add(username);
        this.add(userInput);
        this.add(password);
        this.add(passwordInput);
        this.add(role);
        this.add(roleInput);
        this.add(addButton);
        this.add(deleteButton);
        this.add(editButton);
        this.add(showList);
        this.setLocationRelativeTo(null);
        this.setSize(300, 300);
        this.setVisible(true);
    }
}
