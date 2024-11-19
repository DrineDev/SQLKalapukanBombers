package org.example;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;
import org.w3c.dom.Text;

public class AddFood extends JPanel {
    private ImageIcon foodImage;
    private int x;
    private String nutritionFact;
    private JButton orderButton;
    private Timer hoverTimer;
    private final int HOVER_DELAY = 500; // Delay in milliseconds

    public AddFood(int mealID, JPanel loggerText, JPanel loggerPrice, MainFrameManager mainFrame)
    {
        // Retrieve food image and nutrition facts from database
        this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));
        this.nutritionFact = SQLMeal.getNutritionFact(mealID);
    
        this.setPreferredSize(new Dimension(300, 300));
        this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    
        // Food background label
        JLabel foodBg = new JLabel();
        foodBg.setIcon(foodImage);

        // TODO
    
        // Nutrition fact label (displayed on hover)
        JLabel nutritionLabel = createNutritionLabel();
    
        // Add nutrition label as a child of foodBg for better overlay management
        foodBg.add(nutritionLabel);
        foodBg.addMouseListener(createHoverListener(nutritionLabel)); // FOR HOVERING ACTIONS
    
        // Bottom area for buttons and other elements
        ImageIcon bottom = new ImageIcon("pics/addfood bottom.png");
        JLabel bottomLabel = createBottomLabel(bottom);
        JPanel bottomArea = createBottomArea();

        // Quantity label
        ImageIcon amountField = new ImageIcon("pics/amount field.png");
        JLabel amountTextField = createAmountTextField(amountField);
    
        // Increment button
        ImageIcon incre = new ImageIcon("pics/more.png");
        JButton increment = createIncrementButton(incre);
        increment.addActionListener(createIncrementListener(amountTextField)); // FOR ADDING ORDER
    
        // Decrement button
        ImageIcon decre = new ImageIcon("pics/less.png");
        JButton decrement = createDecrementButton(decre);
        decrement.addActionListener(createDecrementListener(amountTextField));

        // Order button
        ImageIcon order = new ImageIcon("pics/order button.png");
        JButton orderButton = createOrderButton(order);
        orderButton.addActionListener(createOrderListener(amountTextField, loggerText, loggerPrice, mealID, mainFrame));
        
        // Add components to the panel
        this.add(foodBg); // Add foodBg with nutritionLabel inside
        bottomLabel.add(increment);
        bottomLabel.add(amountTextField);
        bottomLabel.add(decrement);
        bottomLabel.add(orderButton);
        bottomArea.add(bottomLabel);
        this.add(bottomArea);
    }

    public AddFood(int mealID, JPanel loggerText, JPanel loggerPrice, MainFrameEmployee mainFrame)
    {
        // Retrieve food image and nutrition facts from database
        this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));
        this.nutritionFact = SQLMeal.getNutritionFact(mealID);

        this.setPreferredSize(new Dimension(300, 300));
        this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Food background label
        JLabel foodBg = new JLabel();
        foodBg.setIcon(foodImage);

        // TODO

        // Nutrition fact label (displayed on hover)
        JLabel nutritionLabel = createNutritionLabel();

        // Add nutrition label as a child of foodBg for better overlay management
        foodBg.add(nutritionLabel);
        foodBg.addMouseListener(createHoverListener(nutritionLabel)); // FOR HOVERING ACTIONS

        // Bottom area for buttons and other elements
        ImageIcon bottom = new ImageIcon("pics/addfood bottom.png");
        JLabel bottomLabel = createBottomLabel(bottom);
        JPanel bottomArea = createBottomArea();

        // Quantity label
        ImageIcon amountField = new ImageIcon("pics/amount field.png");
        JLabel amountTextField = createAmountTextField(amountField);

        // Increment button
        ImageIcon incre = new ImageIcon("pics/more.png");
        JButton increment = createIncrementButton(incre);
        increment.addActionListener(createIncrementListener(amountTextField)); // FOR ADDING ORDER

        // Decrement button
        ImageIcon decre = new ImageIcon("pics/less.png");
        JButton decrement = createDecrementButton(decre);
        decrement.addActionListener(createDecrementListener(amountTextField));

        // Order button
        ImageIcon order = new ImageIcon("pics/order button.png");
        JButton orderButton = createOrderButton(order);
        orderButton.addActionListener(createOrderListener(amountTextField, loggerText, loggerPrice, mealID, mainFrame));

        // Add components to the panel
        this.add(foodBg); // Add foodBg with nutritionLabel inside
        bottomLabel.add(increment);
        bottomLabel.add(amountTextField);
        bottomLabel.add(decrement);
        bottomLabel.add(orderButton);
        bottomArea.add(bottomLabel);
        this.add(bottomArea);
    }

    private void showImageFrame(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();

        int height = image.getHeight(null);
        int width = image.getWidth(null);
        JWindow imageWindow = createImageWindow(image, width, height);

        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(createFadeInListener(imageWindow, fadeInTimer));
        fadeInTimer.start();
    }

    private void startFadeOut(JWindow imageWindow)
    {
        Timer fadeOutTimer = new Timer(20, null);
        fadeOutTimer.addActionListener(createFadeOutListener(imageWindow, fadeOutTimer));
        fadeOutTimer.start();
    }

    private JLabel createNutritionLabel() {
        JLabel nutritionLabel = new JLabel("<html>" + nutritionFact.replace("\n", "<br>") + "</html>");
        nutritionLabel.setBounds(10, 10, 280, 230);  // Position within the bounds of food label
        nutritionLabel.setForeground(Color.BLACK);    // Set text color
        nutritionLabel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent background
        nutritionLabel.setOpaque(true);
        nutritionLabel.setVisible(false); // Initially hidden
        return nutritionLabel;
    }

    private JLabel createBottomLabel(ImageIcon bottom) {
        JLabel bottomLabel = new JLabel(bottom);
        bottomLabel.setBounds(0,0,300,50);
        bottomLabel.setBackground(Color.white);
        return bottomLabel;
    }

    private MouseAdapter createHoverListener(JLabel nutritionLabel) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                if (hoverTimer != null && hoverTimer.isRunning()) {
                    hoverTimer.stop();
                }
                hoverTimer = new Timer(HOVER_DELAY, e -> 
                    SwingUtilities.invokeLater(() -> nutritionLabel.setVisible(true))
                );
                hoverTimer.setRepeats(false);
                hoverTimer.start();
            }
    
            @Override
            public void mouseExited(MouseEvent evt) {
                if (hoverTimer != null && hoverTimer.isRunning()) {
                    hoverTimer.stop();
                }
                SwingUtilities.invokeLater(() -> nutritionLabel.setVisible(false));
            }
        };
    }

    private JPanel createBottomArea() {
        JPanel bottomArea = new JPanel();
        bottomArea.setBackground(Color.white);
        bottomArea.setLayout(null);
        return bottomArea;
    }

    private JLabel createAmountTextField(ImageIcon amountField) {
        JLabel amountTextField = new JLabel(amountField);
        amountTextField.setText("" + 0);
        amountTextField.setBounds(33, 15, 23, 25);
        amountTextField.setHorizontalTextPosition(JLabel.CENTER);
        amountTextField.setVerticalTextPosition(JLabel.CENTER);
        return amountTextField;
    }

    private JButton createIncrementButton(ImageIcon incre) {
        JButton increment = new JButton(incre);
        increment.setBounds(61,20,11, 14);
        increment.setBorderPainted(false);
        increment.setFocusPainted(false);
        increment.setContentAreaFilled(false);
        return increment;
    }

    private ActionListener createIncrementListener(JLabel amountTextField) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO : ADD FUNCTIONALITY HERE
                x += 1;
                amountTextField.setText("" + x);
            }
        };
    }

    private JButton createDecrementButton(ImageIcon decre) {
        JButton decrement = new JButton(decre);
        decrement.setBounds(15, 20, 11, 14);
        decrement.setBorderPainted(false);
        decrement.setFocusPainted(false);
        decrement.setContentAreaFilled(false);

        return decrement;
    }

    private ActionListener createDecrementListener(JLabel amountTextField) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (x > 0) {
                    // TODO : ADD FUNCTIONALITY HERE
                    x -= 1;
                    amountTextField.setText("" + x);
                }   
            }
        };
    }
    
    private JButton createOrderButton(ImageIcon order) {
        orderButton = new JButton(order);
        orderButton.setBounds(210, 15, 80, 25);
        orderButton.setBorderPainted(false);
        orderButton.setFocusPainted(false);
        orderButton.setContentAreaFilled(false);
        return orderButton;
    }
    
    private ActionListener createOrderListener(JLabel amountTextField, JPanel logger, JPanel loggerPrice, int mealID, MainFrameManager mainFrame) {
    return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // TODO : ADD FUNCTIONALITY SOON
                String foodName = SQLMeal.getName(mealID);
                String quantityStr = amountTextField.getText();
                int quantity = Integer.parseInt(quantityStr);

                if(quantity != 0)
                {
                    float priceValue = SQLInventory.getPrice(mealID);
                    float totalPrice = priceValue * quantity;

                    String price = String.format("₱%.2f", totalPrice);
                    String logEntry = quantity + " x " + foodName;
    
                    JLabel logText = new JLabel(logEntry);
                    JLabel logPrice = new JLabel(price);

                    mainFrame.updateTotalPrice(totalPrice);
                    logger.add(logText);
                    loggerPrice.add(logPrice);
                    x = 0;
                    amountTextField.setText("" + x + " ");
                    showImageFrame("pics/pop up frame order.png");

                    logger.revalidate();
                    logger.repaint();
                    loggerPrice.revalidate();
                    loggerPrice.repaint();
                }
            }
        };
    }

    private ActionListener createOrderListener(JLabel amountTextField, JPanel logger, JPanel loggerPrice, int mealID, MainFrameEmployee mainFrame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // TODO : ADD FUNCTIONALITY SOON
                String foodName = SQLMeal.getName(mealID);
                String quantityStr = amountTextField.getText();
                int quantity = Integer.parseInt(quantityStr);

                if(quantity != 0)
                {
                    float priceValue = SQLInventory.getPrice(mealID);
                    float totalPrice = priceValue * quantity;

                    String price = String.format("₱%.2f", totalPrice);
                    String logEntry = quantity + " x " + foodName;

                    JLabel logText = new JLabel(logEntry);
                    JLabel logPrice = new JLabel(price);

                    mainFrame.updateTotalPrice(totalPrice);
                    logger.add(logText);
                    loggerPrice.add(logPrice);
                    x = 0;
                    amountTextField.setText("" + x + " ");
                    showImageFrame("pics/pop up frame order.png");

                    logger.revalidate();
                    logger.repaint();
                    loggerPrice.revalidate();
                    loggerPrice.repaint();
                }
            }
        };
    }

    private JWindow createImageWindow(Image image, int width, int height) {
        JWindow imageWindow = new JWindow();
        imageWindow.setSize(width, height);
        imageWindow.setLocationRelativeTo(null);
         JPanel panel = createPanel(image);
        panel.setPreferredSize(new Dimension(width, height));
        imageWindow.setContentPane(panel);
        Float shape = new RoundRectangle2D.Float(0, 0, width, height, 17, 17);
        imageWindow.setShape(shape);
        imageWindow.setOpacity(0.0f);
        imageWindow.setVisible(true);
        return imageWindow;
    }

    private JPanel createPanel(Image image) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        };
    }

    private ActionListener createFadeInListener(JWindow imageWindow, Timer fadeInTimer) {
        return new ActionListener() {
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
        };
    }

    private ActionListener createFadeOutListener(JWindow imageWindow, Timer fadeOutTimer) {
    return new ActionListener() {
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
        };
    }
}  
