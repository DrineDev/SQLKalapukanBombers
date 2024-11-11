package org.example;

import org.example.SQLQueries.SQLUser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
//import org.example.SQLQueries;


public class LoginPage extends JFrame{
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";
    JFrame loginFrame;
    JButton exitButton;
    JButton loginButton;
    LoginPage()
    {
        //make frame for login
        loginFrame = new JFrame();
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
        JPanel leftSidePanel = new JPanel();
        leftSidePanel.setPreferredSize(new Dimension(400,600));
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

        //right side of panel
        ImageIcon rightImage = new ImageIcon("pics/right.png");
        JLabel rightBackground = new JLabel();
        rightBackground.setIcon(rightImage);
        rightBackground.setBounds(0,0, 600, 600);
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setPreferredSize(new Dimension(600,600));
        rightSidePanel.setLayout(null);

        //error text
        ImageIcon errorPic = new ImageIcon("pics/Incorrect error.png");
        JLabel errorText = new JLabel();
        errorText.setBounds(90, 175, 220, 15);
        errorText.setIcon(errorPic);
        errorText.setForeground(Color.red);
        errorText.setVisible(false);


        //login button
        ImageIcon loginButtonImage = new ImageIcon("pics/login button.png");
        loginButton = new JButton();
        loginButton.setBounds(90, 390, 120, 30);
        loginButton.setIcon(loginButtonImage);
        loginButton.setContentAreaFilled(false);
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


        //add shits
        loginFrame.add(leftSidePanel, BorderLayout.WEST);
        loginFrame.add(rightSidePanel, BorderLayout.EAST);

        leftSidePanel.add(errorText);
        leftSidePanel.add(userTextField);
        leftSidePanel.add(usernameText);
        leftSidePanel.add(passTextField);
        leftSidePanel.add(passwordText);
        leftSidePanel.add(loginButton);

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