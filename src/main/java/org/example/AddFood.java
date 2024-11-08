package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

import org.example.SQLQueries.SQLMeal;

public class AddFood extends JPanel {
    private final ImageIcon foodImage;
    private final String nutritionFact;
    private final Timer hoverTimer;
    private static final int HOVER_DELAY = 500;
    private int quantity;

    public AddFood(int mealID) {
        foodImage = new ImageIcon(SQLMeal.getImage(mealID));
        nutritionFact = SQLMeal.getNutritionFact(mealID);
        hoverTimer = new Timer(HOVER_DELAY, e -> {});
        
        initializeUI();
    }

    private void initializeUI() {
        setPreferredSize(new Dimension(300, 300));
        setBackground(Color.white);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel foodBg = createFoodLabel();
        JLabel nutritionLabel = createNutritionLabel();
        JPanel bottomArea = createBottomArea();

        foodBg.add(nutritionLabel);
        foodBg.addMouseListener(createHoverListener(nutritionLabel));
        
        this.add(foodBg);
        this.add(bottomArea);
    }

    private JLabel createFoodLabel() {
        JLabel foodBg = new JLabel(foodImage);
        foodBg.setAlignmentX(CENTER_ALIGNMENT);
        return foodBg;
    }

    private JLabel createNutritionLabel() {
        JLabel label = new JLabel("<html>" + nutritionFact.replace("\n", "<br>") + "</html>");
        label.setBounds(10, 10, 280, 230);
        label.setForeground(Color.BLACK);
        label.setBackground(new Color(255, 255, 255, 200));
        label.setOpaque(true);
        label.setVisible(false);
        return label;
    }

    private MouseAdapter createHoverListener(JLabel nutritionLabel) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hoverTimer.stop();
                hoverTimer.setInitialDelay(HOVER_DELAY);
                hoverTimer.addActionListener(evt -> nutritionLabel.setVisible(true));
                hoverTimer.setRepeats(false);
                hoverTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoverTimer.stop();
                nutritionLabel.setVisible(false);
            }
        };
    }

    private JPanel createBottomArea() {
        JPanel bottomArea = new JPanel();
        bottomArea.setBackground(Color.white);
        bottomArea.setLayout(null);

        JLabel bottomLabel = new JLabel(new ImageIcon("pics/addfood bottom.png"));
        JButton incrementButton = createButton("pics/more.png", e -> incrementQuantity());
        JButton decrementButton = createButton("pics/less.png", e -> decrementQuantity());
        JButton orderButton = createButton("pics/order button.png", e -> showOrderPopup());
        JLabel amountTextField = createAmountLabel();

        bottomLabel.add(incrementButton);
        bottomLabel.add(amountTextField);
        bottomLabel.add(decrementButton);
        bottomLabel.add(orderButton);
        
        bottomArea.add(bottomLabel);
        return bottomArea;
    }

    private JButton createButton(String iconPath, ActionListener action) {
        JButton button = new JButton(new ImageIcon(iconPath));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(action);
        return button;
    }

    private JLabel createAmountLabel() {
        JLabel label = new JLabel(String.valueOf(quantity));
        label.setBounds(33, 15, 23, 25);
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    private void incrementQuantity() {
        quantity++;
    }

    private void decrementQuantity() {
        if (quantity > 0) quantity--;
    }

    private void showOrderPopup() {
        showImageFrame("pics/pop up frame order.png");
        quantity = 0;
    }

    private void showImageFrame(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        JWindow imageWindow = createImageWindow(width, height);
        imageWindow.setContentPane(createImagePanel(image, width, height));
        imageWindow.setShape(new RoundRectangle2D.Float(0, 0, width, height, 17, 17));
        imageWindow.setOpacity(0.0f);
        imageWindow.setVisible(true);

        fadeIn(imageWindow);
    }

    private JWindow createImageWindow(int width, int height) {
        JWindow window = new JWindow();
        window.setSize(width, height);
        window.setLocationRelativeTo(null);
        return window;
    }

    private JPanel createImagePanel(Image image, int width, int height) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));
        return panel;
    }

    private void fadeIn(JWindow window) {
        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(new ActionListener() {
            float opacity = 0.0f;
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    fadeInTimer.stop();
                    startFadeOut(window);
                }
                window.setOpacity(opacity);
                window.repaint();
            }
        });
        fadeInTimer.start();
    }

    private void startFadeOut(JWindow window) {
        Timer fadeOutTimer = new Timer(20, null);
        fadeOutTimer.addActionListener(new ActionListener() {
            float opacity = 1.0f;
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.0f) {
                    window.dispose();
                    fadeOutTimer.stop();
                }
                window.setOpacity(opacity);
                window.repaint();
            }
        });
        fadeOutTimer.start();
    }
}
  
