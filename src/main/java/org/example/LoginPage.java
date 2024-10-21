package org.example;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

public class LoginPage {

    private static final String DB_URL = "jdbc:sqlite:C:/Users/c202301062/IdeaProjects/javaworks2/SQL/database.db";

    public static void main(String[] args) {

        LoadLoginPage();
    }
    public static void LoadLoginPage() {
        JFrame frame = new JFrame();

        JButton button = new JButton("Login");

        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();

        button.setBounds(150, 300, 100, 25);

        JLabel userLabel = new JLabel("Username: ");
        userLabel.setBounds(150, 70, 150, 25);
        userLabel.setVisible(true);
        JLabel passLabel = new JLabel("Password: ");
        passLabel.setBounds(150, 170, 150, 25);
        passLabel.setVisible(true);

        username.setBounds(150, 100, 150, 25);
        password.setBounds(150, 200, 150, 25);
        frame.add(username);
        frame.add(password);
        frame.add(userLabel);
        frame.add(passLabel);

        frame.add(button);
        frame.setResizable(false);
        frame.setSize(500, 600);
        frame.setLayout(null);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = username.getText();
                String passwordInput = new String(password.getPassword());
                System.out.println(usernameInput);
                System.out.println(passwordInput);
                    if (SQLQueries.authenticateUser(usernameInput, passwordInput)) {
                        JButton confirm = new JButton("Okay boss");
                        JFrame loginSuccessful = new JFrame();
                        loginSuccessful.add(confirm);
                        loginSuccessful.setSize(75, 150);
                        loginSuccessful.setVisible(true);
                    } else {
                        JFrame loginFailed = new JFrame();
                        JButton failed = new JButton("Oh no boss");
                        loginFailed.add(failed);
                        loginFailed.setSize(75, 150);
                        loginFailed.setVisible(true);
                    }
                }
            }
        );
    }
}
