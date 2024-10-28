package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainFrameEmployee extends JFrame {
    private JFrame mainFrame;
    private JButton exitButton;
    public MainFrameEmployee() {
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

        // Create food items, change this pls, dapat connectted ni sa database so we can know its food details
        AddFood[] foods = new AddFood[6];
        for (int i = 0; i < foods.length; i++)
        {
            foods[i] = new AddFood(fIcon, fIconHover);
//            BufferedImage image = ImageIO.read(new File("pics/fried chicken.png"));
        }
        AddFood[] foods2 = new AddFood[6];
        for (int i = 0; i < foods.length; i++)
        {
            foods2[i] = new AddFood(fIcon2, fIconHover2);
//            BufferedImage image = ImageIO.read(new File("pics/fried chicken.png"));
        }

        // Frame initialization
        mainFrame = new JFrame();
        mainFrame.setSize(1000, 600);
        mainFrame.setUndecorated(true);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);



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
        rightSideComponents.setLayout(new GridLayout(0, 2, 0, 0)); // Adjust gap
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


        JPanel rightSideComponents2 = new JPanel();
        rightSideComponents2.setLayout(new GridLayout(0, 2, 0, 0)); // Adjust gap
        rightSideComponents2.setPreferredSize(new Dimension(680, 1000));
        rightSideComponents2.setBackground(Color.white);
        for (AddFood food : foods)
        {
            rightSideComponents2.add(food);
        }
        JScrollPane rightSideScrollPane2 = new JScrollPane(rightSideComponents);
        rightSideScrollPane2.setPreferredSize(new Dimension(680, 3000));
        rightSideScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rightSideScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        rightSideScrollPane2.setBorder(null);

        rightSideScrollPane2.getViewport().setViewPosition(new java.awt.Point(0, 20));

        //right food area scrooll
        JPanel rightFoodArea = new JPanel();
        CardLayout cardLayout = new CardLayout();
        rightFoodArea.setLayout(cardLayout);
        rightFoodArea.setPreferredSize(new Dimension(680,1000));
        rightFoodArea.add(rightSideScrollPane, "1");
        rightFoodArea.add(rightSideScrollPane2, "2");


        cardLayout.show(rightFoodArea, "1");


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
                cardLayout.next(rightFoodArea);

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
                System.out.println("non v");

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
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    //put here addfood pero spicy category only, we ahve to amke meals pa......
                    System.out.println("fuckyou");
                }
                else
                {
                    //its still gonna do something
                }
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

        // custom scrolling shit
        rightSideScrollPane.addMouseWheelListener(new MouseWheelListener() 
        {
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
        rightSideWhole.add(rightFoodArea, BorderLayout.CENTER);
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

