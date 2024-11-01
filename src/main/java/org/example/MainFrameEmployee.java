package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainFrameEmployee extends JFrame {
    private JFrame mainFrame;
    private JButton exitButton;

    public MainFrameEmployee() 
    {
        // Food icons
        ImageIcon fIcon = new ImageIcon("pics/fried chicken.png");
        ImageIcon fIconHover = new ImageIcon("pics/Rectangle 8 (1).png");
        ImageIcon fIcon2 = new ImageIcon("pics/cordon.png");
        ImageIcon fIconHover2 = new ImageIcon("pics/Rectangle 8 (1).png");


        // Exit button
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener((ActionEvent e) -> 
        {
            System.exit(0);
        });


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

        // Bottom area of right side
        JPanel rightSideBottom = new JPanel();
        CardLayout cardLayout = new CardLayout();
        rightSideBottom.setLayout(cardLayout);
        rightSideBottom.setPreferredSize(new Dimension(680, 1000));

        JPanel rightBottomParent = new JPanel();
        rightBottomParent.setLayout(null);
        rightBottomParent.setBounds(0,0,680, 1000);
        rightBottomParent.setBackground(null);

        //add foods 
        ImageIcon burger = new ImageIcon("pics/foods/burger.png");
        ImageIcon bicol = new ImageIcon("pics/foods/bicol express.png");
        ImageIcon butong = new ImageIcon("pics/foods/butong.png");
        ImageIcon cordon = new ImageIcon("pics/foods/cordon.png");
        ImageIcon ginaling = new ImageIcon("pics/foods/ginaling.png");
        ImageIcon larang = new ImageIcon("pics/foods/larang.png");
        ImageIcon lemon = new ImageIcon("pics/foods/lemon.png");
        ImageIcon pancit = new ImageIcon("pics/foods/pancit.png");
        ImageIcon sinigang = new ImageIcon("pics/foods/sinigang.png");
        ImageIcon sweetAndSour = new ImageIcon("pics/foods/sweet and sour.png");
        ImageIcon taho = new ImageIcon("pics/foods/taho.png");
        ImageIcon tapioca = new ImageIcon("pics/foods/tapioca drink.png");

        AddFood burgerFood = new AddFood(burger, bicol);
        AddFood bicolFood = new AddFood(bicol, burger);
        AddFood butongFood = new AddFood(butong, burger);
        AddFood cordonFood = new AddFood(cordon, burger);
        AddFood ginalingFood = new AddFood(ginaling, burger);
        AddFood larangFood = new AddFood(larang, burger);
        AddFood lemonDrink = new AddFood(lemon, burger);
        AddFood pancitFood = new AddFood(pancit, burger);
        AddFood sinigangFood = new AddFood(sinigang, burger);
        AddFood sweetAndSourFood = new AddFood(sweetAndSour, burger);
        AddFood tahoFood = new AddFood(taho, burger);
        AddFood tapiocaFood = new AddFood(tapioca, burger);

        JPanel defaultPanel = new JPanel();
        defaultPanel.setLayout(new GridLayout(10, 2, 0, 0));
        defaultPanel.setPreferredSize(new Dimension(680, 3000));
        defaultPanel.setBackground(Color.white);
        defaultPanel.add(bicolFood);
        defaultPanel.add(butongFood);
        defaultPanel.add(cordonFood);
        defaultPanel.add(ginalingFood);
        defaultPanel.add(larangFood);
        defaultPanel.add(lemonDrink); 
        defaultPanel.add(pancitFood);
        defaultPanel.add(sinigangFood);
        defaultPanel.add(sweetAndSourFood);
        defaultPanel.add(tahoFood);
        defaultPanel.add(tapiocaFood);
        defaultPanel.setVisible(true);

        JPanel vegetarianPanel = new JPanel();
        vegetarianPanel.setLayout(new GridLayout(0, 2, 0, 0)); 
        vegetarianPanel.setPreferredSize(new Dimension(680, 3000));
        vegetarianPanel.setBackground(null);
        vegetarianPanel.add(lemonDrink);
        vegetarianPanel.add(tahoFood);
        vegetarianPanel.add(tapiocaFood);
        vegetarianPanel.add(butongFood);

        JPanel non_vegetarianPanel = new JPanel();
        non_vegetarianPanel.setLayout(new GridLayout(0, 2, 0, 0)); 
        non_vegetarianPanel.setPreferredSize(new Dimension(680, 3000));
        non_vegetarianPanel.setBackground(null);
        non_vegetarianPanel.setVisible(false);
        non_vegetarianPanel.add(bicolFood);
        non_vegetarianPanel.add(cordonFood);
        non_vegetarianPanel.add(ginalingFood);
        non_vegetarianPanel.add(larangFood);
        non_vegetarianPanel.add(pancitFood);
        non_vegetarianPanel.add(sinigangFood);
        non_vegetarianPanel.add(sweetAndSourFood);

        JPanel spicyPanel = new JPanel();
        spicyPanel.setLayout(new GridLayout(0, 2, 0, 0)); 
        spicyPanel.setPreferredSize(new Dimension(680, 3000));
        spicyPanel.setBackground(null);
        spicyPanel.setVisible(false);
        spicyPanel.add(larangFood);

        JPanel vegetarianSpicyPanel = new JPanel();
        vegetarianSpicyPanel.setLayout(new GridLayout(0, 2, 0, 0)); 
        vegetarianSpicyPanel.setPreferredSize(new Dimension(680, 3000));
        vegetarianSpicyPanel.setBackground(null);
        //as of now no food for this ^^^

        JPanel non_vegetarianSpicyPanel = new JPanel();
        non_vegetarianSpicyPanel.setLayout(new GridLayout(0, 2, 0, 0)); 
        non_vegetarianSpicyPanel.setPreferredSize(new Dimension(680, 3000));
        non_vegetarianSpicyPanel.setBackground(null);
        non_vegetarianSpicyPanel.add(larangFood);

        AddScrollPage dPage = new AddScrollPage(defaultPanel);
        AddScrollPage vPage = new AddScrollPage(vegetarianPanel);
        AddScrollPage nvPage = new AddScrollPage(non_vegetarianPanel);
        AddScrollPage sPage = new AddScrollPage(spicyPanel);
        AddScrollPage vsPage = new AddScrollPage(vegetarianSpicyPanel);
        AddScrollPage nvsPage = new AddScrollPage(non_vegetarianSpicyPanel);

        rightSideBottom.add("Default", dPage);
        rightSideBottom.add("Vegetarian", vPage);
        rightSideBottom.add("Non-Vegetarian", nvPage);
        rightSideBottom.add("Spicy", sPage);
        rightSideBottom.add("Spicy Vegetarian", vsPage);
        rightSideBottom.add("Spicy Non-Vegetarian", nvsPage);







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
                cardLayout.show(rightSideBottom, "Vegetarian");
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
                cardLayout.show(rightSideBottom, "Non-Vegetarian");
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
                cardLayout.show(rightSideBottom, "Spicy");
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



        //rightBottomParent.add("Default page", dPage);






        cardLayout.show(rightSideBottom, "Default page");

        rightSideWhole.add(exitPanel, BorderLayout.NORTH);
        rightSideWhole.add(rightSideBottom, BorderLayout.CENTER);
        
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

