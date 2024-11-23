package org.example;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.example.SQLQueries.SQLUser;
//import org.example.SQLQueries;


public class LoginPage extends JFrame{
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";
    JFrame loginFrame;
    JButton exitButton;
    JButton loginButton;
    LoginPage()
    {
        //make frame for login
        loginFrame = new JFrame("Kalapukan Bombers Foods - Login");
        loginFrame.setSize(1000, 600);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setUndecorated(true);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setLocationRelativeTo(null);
        loginFrame.getContentPane().setBackground(new Color(255, 255, 255, 100));

        //make custom button to exit the program
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setBounds(565, 15, 20, 20);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);

        exitButton.addActionListener((ActionEvent e) ->
        {
            System.exit(0);
        });

        //left side of panel
        JPanel leftSide = new JPanel();
        leftSide.setPreferredSize(new Dimension(400,600));
        leftSide.setBackground(Color.white);
        leftSide.setLayout(null);

        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setBounds(0,0,400,600);
        leftSidePanel.setBackground(Color.white);
        leftSidePanel.setLayout(null);

        JLabel usernameText = new JLabel();
        JLabel passwordText = new JLabel();
        usernameText.setText("Username");
        usernameText.setBounds(90, 240, 60,15);
        passwordText.setText("Password");
        passwordText.setBounds(90, 320, 60, 15);

        JTextField userTextField = new JTextField();
        JPasswordField passTextField = new JPasswordField();
        userTextField.setBounds(90, 210, 220, 30);
        passTextField.setBounds(90, 290, 220, 30);

        //left side panel for sign up
        JPanel leftSideSignUp = new JPanel();
        leftSideSignUp.setBounds(0,0,400,600);
        leftSideSignUp.setBackground(Color.white);
        leftSideSignUp.setVisible(false);
        leftSideSignUp.setLayout(null);

        ImageIcon U = new ImageIcon("pics/username.png");
        JLabel user = new JLabel(U);
        user.setBounds(90,180,68,15);

        ImageIcon P = new ImageIcon("pics/passsword.png");
        JLabel pass = new JLabel(P);
        pass.setBounds(90,240,67,15);

        ImageIcon CP = new ImageIcon("pics/confirm pass.png");
        JLabel confirmPass = new JLabel(CP);
        confirmPass.setBounds(90,300,112,15);

        ImageIcon M = new ImageIcon("pics/Manager code (if available).png");
        JLabel manager = new JLabel(M);
        manager.setBounds(90,360,150,15);

        JTextField userText = new JTextField();
        userText.setBounds(90,150,220,30);
        JPasswordField passText = new JPasswordField();
        passText.setBounds(90,210,220,30);
        JPasswordField confirmPassText = new JPasswordField();
        confirmPassText.setBounds(90,270,220,30);
        JTextField managerText = new JTextField();
        managerText.setBounds(90,330,220,30);

        //right side of panel
        ImageIcon rightImage = new ImageIcon("pics/right.png");
        JLabel rightBackground = new JLabel();
        rightBackground.setIcon(rightImage);
        rightBackground.setBounds(0,0, 600, 600);
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setPreferredSize(new Dimension(600,600));
        rightSidePanel.setLayout(null);

        //error texts
        ImageIcon errorPic = new ImageIcon("pics/Incorrect error.png");
        JLabel errorText = new JLabel();
        errorText.setBounds(90, 175, 220, 15);
        errorText.setIcon(errorPic);
        errorText.setVisible(false);

        ImageIcon fillUp = new ImageIcon("pics/fill up error.png");
        JLabel fillUpError = new JLabel(fillUp);
        fillUpError.setBounds(92,110,215,15);
        fillUpError.setVisible(false);

        ImageIcon confirm = new ImageIcon("pics/confirm pass error.png");
        JLabel confirmPassError = new JLabel(confirm);
        confirmPassError.setBounds(102,110,196,15);
        confirmPassError.setVisible(false);

        //signup success
        ImageIcon succes = new ImageIcon("pics/sign up success.png");
        JLabel successMessage = new JLabel(succes);
        successMessage.setBounds(50,138,300,17);
        successMessage.setVisible(false);

        //login button
        ImageIcon loginButtonImage = new ImageIcon("pics/login button.png");
        loginButton = new JButton();
        loginButton.setBounds(140, 370, 120, 30);
        loginButton.setIcon(loginButtonImage);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                String userInput = userTextField.getText();
                String passInput = new String(passTextField.getPassword());

                if(SQLUser.authenticateUser(userInput, passInput))
                {
                    String userName = userInput;
                    int userID = SQLUser.getUserId(userName);
                    if("Manager".equals(SQLUser.getRole(userID)))
                    {
                        fadeInNewFrameManager();
                    }
                    else
                    {
                        fadeInNewFrameEmployee();
                    }

                }
                else
                {
                    errorText.setVisible(true);
                }

            }
        });

        //sign up shits
        ImageIcon signup = new ImageIcon("pics/signup button.png");
        JButton signupButton = new JButton(signup);
        signupButton.setBounds(140,420,120,30);
        signupButton.setContentAreaFilled(false);
        signupButton.setBorderPainted(false);
        signupButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String userInput = userText.getText();
                String passInput = passText.getText();
                String confirmInput = confirmPassText.getText();
                String managerInput = managerText.getText();

                if(userInput.isBlank() || passInput.isEmpty() || confirmInput.isEmpty())
                {
                    confirmPassError.setVisible(false);
                    fillUpError.setVisible(true);
                }
                else if (passInput.equals(confirmInput) == false)
                {
                    confirmPassError.setVisible(true);
                    fillUpError.setVisible(false);
                }
                else
                {
                    // TODO : add user to the sql

                    confirmPassError.setVisible(false);
                    fillUpError.setVisible(false);
                    System.out.println("nice one!");
                    leftSideSignUp.setVisible(false);
                    leftSidePanel.setVisible(true);
                    successMessage.setVisible(true);
                    userText.setText(null);
                    passText.setText(null);
                    confirmPassText.setText(null);
                    managerText.setText(null);
                    fillUpError.setVisible(false);
                    confirmPassError.setVisible(false);
                }

            }
        });

        //i dont have an account
        ImageIcon noAccount = new ImageIcon("pics/I don’t have an account.png");
        JButton iDontHaveAccount = new JButton(noAccount);
        iDontHaveAccount.setBounds(134,410,132,20);
        iDontHaveAccount.setContentAreaFilled(false);
        iDontHaveAccount.setBorderPainted(false);
        iDontHaveAccount.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                leftSidePanel.setVisible(false);
                leftSideSignUp.setVisible(true);
                errorText.setVisible(false);
                successMessage.setVisible(false);
                userTextField.setText(null);
                passTextField.setText(null);

            }
        });

        //i change my mind
        ImageIcon change = new ImageIcon("pics/I changed my mind, log me in.png");
        JButton iChangedMyMind = new JButton(change);
        iChangedMyMind.setBounds(117,462,170,15);
        iChangedMyMind.setContentAreaFilled(false);
        iChangedMyMind.setBorderPainted(false);
        iChangedMyMind.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                leftSideSignUp.setVisible(false);
                leftSidePanel.setVisible(true);
                userText.setText(null);
                passText.setText(null);
                confirmPassText.setText(null);
                managerText.setText(null);
                fillUpError.setVisible(false);
                confirmPassError.setVisible(false);
            }
        });

        //add shits
        leftSidePanel.add(successMessage);
        leftSidePanel.add(errorText);
        leftSidePanel.add(userTextField);
        leftSidePanel.add(usernameText);
        leftSidePanel.add(passTextField);
        leftSidePanel.add(passwordText);
        leftSidePanel.add(loginButton);
        leftSidePanel.add(iDontHaveAccount);

        leftSideSignUp.add(fillUpError);
        leftSideSignUp.add(confirmPassError);
        leftSideSignUp.add(userText);
        leftSideSignUp.add(user);
        leftSideSignUp.add(passText);
        leftSideSignUp.add(pass);
        leftSideSignUp.add(confirmPassText);
        leftSideSignUp.add(confirmPass);
        leftSideSignUp.add(managerText);
        leftSideSignUp.add(manager);
        leftSideSignUp.add(signupButton);
        leftSideSignUp.add(iChangedMyMind);

        leftSide.add(leftSidePanel);
        leftSide.add(leftSideSignUp);
        loginFrame.add(leftSide, BorderLayout.WEST);
        loginFrame.add(rightSidePanel, BorderLayout.EAST);

        rightSidePanel.add(rightBackground);
        rightSidePanel.add(exitButton);

        loginFrame.setVisible(true);
    }


    //everything below makes it so that welcome window fades in (burger.png is placeholder....)
    private void fadeInNewFrameEmployee() {
        JFrame newFrame = new JFrame();
        newFrame.setSize(1000, 600);
        newFrame.setUndecorated(true);
        newFrame.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("pics/welcome splashscreen.png");
        Image image = imageIcon.getImage();

        FadePanel fadePanel = new FadePanel(image);
        newFrame.add(fadePanel);
        newFrame.setVisible(true);

        Timer fadeInTimer = new Timer(45, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                loginFrame.dispose();
                fadePanel.incrementAlpha();
                if (fadePanel.getAlpha() >= 255) {
                    ((Timer) evt.getSource()).stop();
                    newFrame.dispose();
                    SwingUtilities.invokeLater(MainFrameEmployee::new);
                }
            }
        });

        fadeInTimer.start();
    }

    private void fadeInNewFrameManager() {
        JFrame newFrame = new JFrame();
        newFrame.setSize(1000, 600);
        newFrame.setUndecorated(true);
        newFrame.setLocationRelativeTo(null);

        ImageIcon imageIcon = new ImageIcon("pics/welcome splashscreen.png");
        Image image = imageIcon.getImage();

        FadePanel fadePanel = new FadePanel(image);
        newFrame.add(fadePanel);
        newFrame.setVisible(true);

        Timer fadeInTimer = new Timer(45, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                loginFrame.dispose();
                fadePanel.incrementAlpha();
                if (fadePanel.getAlpha() >= 255) {
                    ((Timer) evt.getSource()).stop();
                    newFrame.dispose();
                    SwingUtilities.invokeLater(MainFrameManager::new);
                }
            }
        });

        fadeInTimer.start();
    }

    //custom panel for fading in things
    private class FadePanel extends JPanel
    {
        private int alpha = 0;
        private final Image image;

        public FadePanel(Image image)
        {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha / 255f));
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }

        public void incrementAlpha()
        {
            if (alpha < 255)
            {
                alpha += 5;
                repaint();
            }
        }
        public int getAlpha()
        {
            return alpha;
        }
    }


}


