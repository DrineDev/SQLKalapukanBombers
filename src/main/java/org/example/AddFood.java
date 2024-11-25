package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.RoundRectangle2D.Float;

import javax.swing.*;

import org.example.Classes.OrderItem;
import org.example.Classes.SharedData;
import org.example.SQLQueries.SQLInventory;
import org.example.SQLQueries.SQLMeal;
import org.example.SQLQueries.SQLOrderItems;

public class AddFood extends JPanel {
    private ImageIcon foodImage;
    private int x = 0;
    private JButton orderButton;
    private Timer hoverTimer;
    private final int HOVER_DELAY = 500; // Delay in milliseconds
    private JLayeredPane layeredPane;
    private int mealId;

    public AddFood(int mealID, JPanel loggerText, JPanel loggerPrice, MainFrameManager mainFrame) {
        mealId = mealID;

        setLayout(null);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 300));
        layeredPane.setBounds(0, 0, 300, 300);

        // Create the main panel that will hold the existing components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBounds(0, 0, 300, 300);
        mainPanel.setOpaque(false);

        // Add low stock warning if applicable
        int currentStock = SQLInventory.getQuantityAvailable(mealID);
        if (currentStock < 5) {
            JLabel warningLabel = new JLabel("!");
            warningLabel.setFont(new Font("Arial", Font.BOLD, 35));
            warningLabel.setForeground(Color.RED);
            warningLabel.setBounds(10, 5, 20, 20);

            // Create a tooltip
            warningLabel.setToolTipText("Low stock: " + currentStock + " remaining");

            layeredPane.add(warningLabel, JLayeredPane.POPUP_LAYER);
        }

        // Create the info button (existing code)
        JButton topRightButton = new JButton();
        topRightButton.setIcon(new ImageIcon("pics/meal info icon.png"));
        topRightButton.setForeground(Color.WHITE);
        topRightButton.setBounds(250, 10, 42, 12);
        topRightButton.setBorderPainted(false);
        topRightButton.setContentAreaFilled(false);
        topRightButton.setFocusPainted(false);
        topRightButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MealInfoMainFrame(mealID));
        });

        // Existing components
        this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));

        // Food background label
        JLabel foodBg = new JLabel();
        foodBg.setIcon(foodImage);

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
        increment.addActionListener(createIncrementListener(amountTextField));

        // Decrement button
        ImageIcon decre = new ImageIcon("pics/less.png");
        JButton decrement = createDecrementButton(decre);
        decrement.addActionListener(createDecrementListener(amountTextField));

        // Order button
        ImageIcon order = new ImageIcon("pics/order button.png");
        JButton orderButton = createOrderButton(order);
        orderButton.addActionListener(createOrderListener(amountTextField, loggerText, loggerPrice, mealID, mainFrame));

        // Add components to the main panel
        mainPanel.add(foodBg);
        bottomLabel.add(increment);
        bottomLabel.add(amountTextField);
        bottomLabel.add(decrement);
        bottomLabel.add(orderButton);
        bottomArea.add(bottomLabel);
        mainPanel.add(bottomArea);

        // Add components to layered pane with different z-orders
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(topRightButton, JLayeredPane.POPUP_LAYER);

        // Add layered pane to this panel
        add(layeredPane);

        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.white);
    }

    public AddFood(int mealID, JPanel loggerText, JPanel loggerPrice, MainFrameEmployee mainFrame) {
        mealId = mealID;
        setLayout(null);

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 300));
        layeredPane.setBounds(0, 0, 300, 300);

        // Create the main panel that will hold the existing components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBounds(0, 0, 300, 300);
        mainPanel.setOpaque(false);

        // Add low stock warning if applicable
        int currentStock = SQLInventory.getQuantityAvailable(mealID);
        if (currentStock < 5) {
            JLabel warningLabel = new JLabel("!");
            warningLabel.setFont(new Font("Arial", Font.BOLD, 35));
            warningLabel.setForeground(Color.RED);
            warningLabel.setBounds(10, 5, 20, 20);

            // Create a tooltip
            warningLabel.setToolTipText("Low stock: " + currentStock + " remaining");

            layeredPane.add(warningLabel, JLayeredPane.POPUP_LAYER);
        }

        // Create the info button (existing code)
        JButton topRightButton = new JButton();
        topRightButton.setIcon(new ImageIcon("pics/meal info icon.png"));
        topRightButton.setForeground(Color.WHITE);
        topRightButton.setBounds(250, 10, 42, 12);
        topRightButton.setBorderPainted(false);
        topRightButton.setContentAreaFilled(false);
        topRightButton.setFocusPainted(false);
        topRightButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new MealInfoMainFrame(mealID));
        });

        // Existing components
        this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));

        // Food background label
        JLabel foodBg = new JLabel();
        foodBg.setIcon(foodImage);

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
        increment.addActionListener(createIncrementListener(amountTextField));

        // Decrement button
        ImageIcon decre = new ImageIcon("pics/less.png");
        JButton decrement = createDecrementButton(decre);
        decrement.addActionListener(createDecrementListener(amountTextField));

        // Order button
        ImageIcon order = new ImageIcon("pics/order button.png");
        JButton orderButton = createOrderButton(order);
        orderButton.addActionListener(createOrderListener(amountTextField, loggerText, loggerPrice, mealID, mainFrame));

        // Add components to the main panel
        mainPanel.add(foodBg);
        bottomLabel.add(increment);
        bottomLabel.add(amountTextField);
        bottomLabel.add(decrement);
        bottomLabel.add(orderButton);
        bottomArea.add(bottomLabel);
        mainPanel.add(bottomArea);

        // Add components to layered pane with different z-orders
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(topRightButton, JLayeredPane.POPUP_LAYER);

        // Add layered pane to this panel
        add(layeredPane);

        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.white);
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
        return e -> {
            String foodName = SQLMeal.getName(mealID);
            String quantityStr = amountTextField.getText();
            int quantity = Integer.parseInt(quantityStr);

            if (quantity != 0) {
                float priceValue = SQLInventory.getPrice(mealID);
                float totalPrice = priceValue * quantity;

                String price = String.format("₱%.2f", totalPrice);
                String logEntry = quantity + " x " + foodName;

                JLabel logText = new JLabel(logEntry);
                logText.setBackground(Color.white);
                JLabel logPrice = new JLabel(price);
                logPrice.setBackground(Color.white);

                mainFrame.updateTotalPrice(totalPrice);
                logger.add(logText);
                loggerPrice.add(logPrice);
                x = 0;
                amountTextField.setText("" + x);

                logger.revalidate();
                logger.repaint();
                loggerPrice.revalidate();
                loggerPrice.repaint();

                // Add to SharedData order (without database interaction)
                OrderItem orderItem = new OrderItem(SharedData.order.getOrderId(), mealID, quantity, priceValue);
                SharedData.order.addOrderItem(orderItem);
            }
        };
    }

    private ActionListener createOrderListener(JLabel amountTextField, JPanel logger, JPanel loggerPrice, int mealID, MainFrameEmployee mainFrame) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
//                // TODO : ADD FUNCTIONALITY SOON
//                String foodName = SQLMeal.getName(mealID);
//                String quantityStr = amountTextField.getText();
//                int quantity = Integer.parseInt(quantityStr);
//
//                if(quantity != 0)
//                {
//                    float priceValue = SQLInventory.getPrice(mealID);
//                    float totalPrice = priceValue * quantity;
//
//                    String price = String.format("₱%.2f", totalPrice);
//                    String logEntry = quantity + " x " + foodName;
//
//                    JLabel logText = new JLabel(logEntry);
//                    JLabel logPrice = new JLabel(price);
//
//                    mainFrame.updateTotalPrice(totalPrice);
//                    logger.add(logText);
//                    loggerPrice.add(logPrice);
//                    x = 0;
//                    amountTextField.setText("" + x + " ");
//                    showImageFrame("pics/pop up frame order.png");
//
//                    logger.revalidate();
//                    logger.repaint();
//                    loggerPrice.revalidate();
//                    loggerPrice.repaint();
//
//                    // FOR ADDING TO ORDER
//                    OrderItem orderItem = new OrderItem(SharedData.order.getOrderId(), mealID, quantity, SQLInventory.getPrice(mealID));
//                    SharedData.order.addOrderItem(orderItem);
//                    SQLOrderItems.addOrderItem(SharedData.order.getOrderId(), mealID, quantity, SQLInventory.getPrice(mealID));
//                }
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

    public int getMealId() {
        return mealId;
    }
}
