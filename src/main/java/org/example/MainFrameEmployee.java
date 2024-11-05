package org.example;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.EmptyBorder;

public class MainFrameEmployee extends JFrame {
    private JFrame mainFrame;
    private JButton exitButton;

    public MainFrameEmployee() {
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

        // Panel for exit button
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        exitPanel.setBackground(Color.white);
        exitPanel.add(exitButton);
        rightSideWhole.add(exitPanel, BorderLayout.NORTH);

        // Bottom area of right side
        JPanel rightSideBottom = new JPanel();
        rightSideBottom.setLayout(new BoxLayout(rightSideBottom, BoxLayout.Y_AXIS)); // Use BoxLayout for stacking

        // Scrollable panel for food items
        JPanel foodItemsPanel = new JPanel();
        foodItemsPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns with spacing
        foodItemsPanel.setBackground(Color.lightGray);

        // Add food items
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/ginaling.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/ginaling.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));
//        foodItemsPanel.add(new AddFood(new ImageIcon("pics/foods/bicol.png"), new ImageIcon("pics/foods/bicol.png")));


        JScrollPane scrollPane = new JScrollPane(foodItemsPanel);
        scrollPane.setPreferredSize(new Dimension(680, 500));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        rightSideBottom.add(scrollPane);
        rightSideWhole.add(rightSideBottom, BorderLayout.CENTER);




        // Left side
        ImageIcon leftSideArea = new ImageIcon("pics/checkout area.png");
        JLabel leftSide = new JLabel();
        leftSide.setIcon(leftSideArea);
        leftSide.setLayout(null);

        ImageIcon defaultCheckbox = new ImageIcon("pics/checkbox default.png");
        ImageIcon selectedCheckbox = new ImageIcon("pics/check_box.png");
        ImageIcon categoryArea = new ImageIcon("pics/category area.png");
        JLabel leftSideCategory = new JLabel();
        JRadioButton vegetarianButton = new JRadioButton("Vegetarian");
        vegetarianButton.setFocusPainted(false);
        vegetarianButton.setContentAreaFilled(false);
        vegetarianButton.setBorder(new EmptyBorder(0, 15,0, 0));
        vegetarianButton.setIcon(defaultCheckbox);
        vegetarianButton.setSelectedIcon(selectedCheckbox);
        vegetarianButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //cardLayout.next(rightFoodArea);

            }
        });
        vegetarianButton.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

            }
        });


        JRadioButton non_VegetariaButton =  new JRadioButton("Non-Vegetarian");
        non_VegetariaButton.setFocusPainted(false);
        non_VegetariaButton.setContentAreaFilled(false);
        non_VegetariaButton.setBorder(new EmptyBorder(0, 15,0, 0));
        non_VegetariaButton.setIcon(defaultCheckbox);
        non_VegetariaButton.setSelectedIcon(selectedCheckbox);
        non_VegetariaButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });

        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(vegetarianButton);
        bGroup.add(non_VegetariaButton);

        JCheckBox spicyButton = new JCheckBox("Spicy");
        spicyButton.setFocusPainted(false);
        spicyButton.setBorderPainted(false);
        spicyButton.setContentAreaFilled(false);
        spicyButton.setBorder(new EmptyBorder(0, 25,0, 0));
        spicyButton.setIcon(defaultCheckbox);
        spicyButton.setSelectedIcon(selectedCheckbox);
        spicyButton.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {

            }

        });

        leftSideCategory.setIcon(categoryArea);
        leftSideCategory.setLayout(new GridLayout(2, 2));
        leftSideCategory.setBounds(35,65, 250, 100);
        leftSideCategory.add(vegetarianButton);
        leftSideCategory.add(spicyButton);
        leftSideCategory.add(non_VegetariaButton);

        ImageIcon checkoutArea = new ImageIcon("pics/checkout box.png");
        JLabel leftSideCheckout = new JLabel();
        leftSideCheckout.setIcon(checkoutArea);
        leftSideCheckout.setBounds(35, 195, 250,320);
        //maybe add another jscrollpane because if order is too much, you ahve to scroll it

        ImageIcon checkoutButtonImage = new ImageIcon("pics/checkout button.png");
        JButton checkoutButton = new JButton();
        checkoutButton.setIcon(checkoutButtonImage);
        checkoutButton.setBounds(35, 535, 110, 30);
        checkoutButton.setContentAreaFilled(false);
        checkoutButton.setFocusPainted(false);
        checkoutButton.setBorder(null);
        checkoutButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                showImageFrame("pics/pop up frame.png");
            }
        });










        leftSide.add(leftSideCategory);
        leftSide.add(leftSideCheckout);
        leftSide.add(checkoutButton);
        mainFrame.add(leftSide, BorderLayout.WEST);
        mainFrame.add(rightSideWhole, BorderLayout.EAST);


        mainFrame.setVisible(true);
    }

    //shit below is the same w/ login page popup after login (fadein adn fadeout)
    private void showImageFrame(String imagePath)
    {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();

        JWindow imageWindow = new JWindow();
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        imageWindow.setSize(width, height);
        imageWindow.setLocationRelativeTo(mainFrame);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));
        imageWindow.setContentPane(panel);

        Shape shape = new RoundRectangle2D.Float(0, 0, width, height, 17, 17);
        imageWindow.setShape(shape);

        imageWindow.setOpacity(0.0f);
        imageWindow.setVisible(true);

        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(new ActionListener()
        {
            float opacity = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeInTimer.stop();
                    new Timer(1000, new ActionListener()
                    {
                        @Override
                        public void actionPerformed(ActionEvent evt)
                        {
                            startFadeOut(imageWindow);
                        }
                    }).start();
                }
                imageWindow.setOpacity(opacity);
                imageWindow.repaint();
            }
        });
        fadeInTimer.start();
    }
    private void startFadeOut(JWindow imageWindow)
    {
        Timer fadeOutTimer = new Timer(20, null);
        fadeOutTimer.addActionListener(new ActionListener()
        {
            float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e)
            {
                opacity -= 0.05f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    imageWindow.dispose();
                    fadeOutTimer.stop();
                }
                imageWindow.setOpacity(opacity);
                imageWindow.repaint();
            }
        });
        fadeOutTimer.start();
    }
}