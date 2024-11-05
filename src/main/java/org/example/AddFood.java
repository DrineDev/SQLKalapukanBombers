package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

import org.example.SQLQueries.SQLMeal;

// TODO: Pick final AddFood function
// TODO: Refactor

public class AddFood extends JPanel {
  private ImageIcon foodImage;
  private int x;
  private ImageIcon hoverImage;
  private String nutritionFact;
  private Timer hoverTimer;
  private final int HOVER_DELAY = 500; // Delay in milliseconds

  private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

  public AddFood(int mealID) {
    // Retrieve food image and nutrition facts from database
    this.foodImage = new ImageIcon(SQLMeal.getImage(mealID));
    this.nutritionFact = SQLMeal.getNutritionFact(mealID);

    this.setPreferredSize(new Dimension(300, 300));
    this.setBackground(Color.white);
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // Food background label
    JLabel foodBg = new JLabel();
    foodBg.setIcon(foodImage);

    // Nutrition fact label (displayed on hover)
    JLabel nutritionLabel = new JLabel("<html>" + nutritionFact.replace("\n", "<br>") + "</html>");
    nutritionLabel.setBounds(10, 10, 280, 230);  // Position within the bounds of food label
    nutritionLabel.setForeground(Color.BLACK);    // Set text color
    nutritionLabel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent background
    nutritionLabel.setOpaque(true);
    nutritionLabel.setVisible(false); // Initially hidden

    // Add nutrition label as a child of foodBg for better overlay management
    foodBg.add(nutritionLabel);

    foodBg.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent evt) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
          hoverTimer.stop();
        }
        hoverTimer = new Timer(HOVER_DELAY, e -> nutritionLabel.setVisible(true));
        hoverTimer.setRepeats(false);
        hoverTimer.start();
      }

      @Override
      public void mouseExited(MouseEvent evt) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
          hoverTimer.stop(); 
        }
        nutritionLabel.setVisible(false);
      }
    });

    // Bottom area for buttons and other elements
    ImageIcon bottom = new ImageIcon("pics/addfood bottom.png");
    JLabel bottomLabel = new JLabel(bottom);
    bottomLabel.setBounds(0,0,300,50);
    bottomLabel.setBackground(Color.white);
    JPanel bottomArea = new JPanel();
    //bottomArea.setSize(300, 50);
    bottomArea.setBackground(Color.white);
    bottomArea.setLayout(null);
    

    
    // Quantity label
    ImageIcon amountField = new ImageIcon("pics/amount field.png");
    JLabel amountTextField = new JLabel(amountField);
    x = 0;
    amountTextField.setText("" + x);
    amountTextField.setBounds(33, 15, 23, 25);
    amountTextField.setHorizontalTextPosition(JLabel.CENTER);
    amountTextField.setVerticalTextPosition(JLabel.CENTER);

    // Increment button
    ImageIcon incre = new ImageIcon("pics/more.png");
    JButton increment = new JButton(incre);
    increment.setBounds(61,20,11, 14);
    increment.setBorderPainted(false);
    increment.setFocusPainted(false);
    increment.setContentAreaFilled(false);
    increment.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
          x += 1;
          amountTextField.setText("" + x);
        }
    });

    // Decrement button
    ImageIcon decre = new ImageIcon("pics/less.png");
    JButton decrement = new JButton(decre);
    decrement.setBounds(15, 20, 11, 14);
    decrement.setBorderPainted(false);
    decrement.setFocusPainted(false);
    decrement.setContentAreaFilled(false);
    decrement.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
          if (x > 0)
          {
            x -= 1;
            amountTextField.setText("" + x);
          }
            
        }
    });
    // Order button
    ImageIcon order = new ImageIcon("pics/order button.png");
    JButton orderButton = new JButton(order);
    orderButton.setBounds(210, 15, 80, 25);
    orderButton.setBorderPainted(false);
    orderButton.setFocusPainted(false);
    orderButton.setContentAreaFilled(false);
    orderButton.addActionListener(new ActionListener() 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            //ADD FUNCTIONALITY SOON
            showImageFrame("pics/pop up frame order.png");
            x = 0;
            amountTextField.setText(""+ x);
        }
    });
    
    // Add components to the panel
    this.add(foodBg); // Add foodBg with nutritionLabel inside
    bottomLabel.add(increment);
    bottomLabel.add(amountTextField);
    bottomLabel.add(decrement);
    bottomLabel.add(orderButton);
    bottomArea.add(bottomLabel);
    this.add(bottomArea);

  }
  private void showImageFrame(String imagePath)
    {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();

        JWindow imageWindow = new JWindow();
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        imageWindow.setSize(width, height);
        imageWindow.setLocationRelativeTo(null);

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

        Float shape = new RoundRectangle2D.Float(0, 0, width, height, 17, 17);
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
    
  public AddFood(ImageIcon foodImage, ImageIcon hoverImage) {
    this.foodImage = foodImage;
    this.hoverImage = hoverImage;

    this.setPreferredSize(new Dimension(300, 300));
    this.setLayout(null);

    // Food background label
    JLabel foodBg = new JLabel();
    foodBg.setBounds(0, 0, 300, 250);
    foodBg.setIcon(foodImage);

    // Nutrition fact panel
    JPanel nutritionPanel = new JPanel();
    nutritionPanel.setBounds(0, 0, 300, 250);
    nutritionPanel.setBackground(new Color(255, 255, 255, 200)); // Semi-transparent background
    nutritionPanel.setVisible(false);

    foodBg.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent evt) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
          hoverTimer.stop();
        }
        hoverTimer = new Timer(HOVER_DELAY, e -> foodBg.setIcon(hoverImage));
        hoverTimer.setRepeats(false);
        hoverTimer.start();
      }

      @Override
      public void mouseExited(MouseEvent evt) {
        if (hoverTimer != null && hoverTimer.isRunning()) {
          hoverTimer.stop(); // Stop the timer if it's running
        }
        foodBg.setIcon(foodImage);
      }
    });

    ImageIcon bottom = new ImageIcon("pics/Rectangle 16.png");
    JLabel bottomPic = new JLabel(bottom);
    JPanel bottomArea = new JPanel();
    bottomArea.setBounds(0,250,300,50);
    bottomArea.add(bottomPic);

    ImageIcon incre = new ImageIcon("pics/less.png");
    JButton increment = new JButton(incre);
    increment.setBounds(0, 280, 11, 14);
    increment.setBorderPainted(false);
    increment.setFocusPainted(false);
    increment.setContentAreaFilled(false);

    ImageIcon decre = new ImageIcon("pics/more.png");
    JButton decrement = new JButton(decre);
    decrement.setBounds(50, 280, 11, 14);
    decrement.setBorderPainted(false);
    decrement.setFocusPainted(false);
    decrement.setContentAreaFilled(false);

    ImageIcon order = new ImageIcon("pics/order button.png");
    JButton orderButton = new JButton(order);
    orderButton.setBounds(200, 260, 80, 25);
    orderButton.setBorderPainted(false);
    orderButton.setFocusPainted(false);
    orderButton.setContentAreaFilled(false);

    JLabel amountTextField = new JLabel();
    ImageIcon amountField = new ImageIcon();
    int x = 0;
    amountTextField.setText("" + x);

    this.add(foodBg);
    bottomArea.add(increment);
    bottomArea.add(amountTextField);
    bottomArea.add(decrement);
    bottomArea.add(orderButton);
    this.add(bottomArea);
//    this.add(decrement, 3);
//    this.add(amountTextField, 3);
//    this.add(decrement, 1);
//    this.add(orderButton, 1);

  }

}