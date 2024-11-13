package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderCrud extends JFrame {
    private JFrame Frame;
    private JButton ExitButton;

    public OrderCrud() {
        //Exit Button
        ImageIcon ExitImageIcon = new ImageIcon("pics/exit button.png");
        ExitButton = new JButton();
        ExitButton.setIcon(ExitImageIcon);
        ExitButton.setContentAreaFilled(false);
        ExitButton.setFocusPainted(false);
        ExitButton.setBorderPainted(false);
        ExitButton.setBounds(962,15,20,20);
        ExitButton.addActionListener(e -> System.exit(0));
        ExitButton.setVisible(true);



        //Frame Initialization
        Frame = new JFrame();
        Frame.setSize(1000, 600);
        Frame.setUndecorated(true);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setLayout(null);
        Frame.setLocationRelativeTo(null);
        Frame.setBackground(Color.PINK);

        JPanel RightSide = new JPanel(new GridLayout(2,2,10,10));
        RightSide.setBounds(290, 20, 650, 550); // Adjusted x, width, and height
        RightSide.setBackground(Color.WHITE);
        RightSide.setBorder(BorderFactory.createLineBorder(Color.GRAY));





        //Initialize Bottom Left buttons
        ImageIcon BottomLeftImage = new ImageIcon("pics/rectangle 10.png");
        JTextField Order_ID_Text = new JTextField();
        JTextField Meal_ID_Text = new JTextField();
        JTextField Meal_Quantity_Text = new JTextField();
        JTextField Date_Text = new JTextField();
        Order_ID_Text.setPreferredSize(new Dimension(195,30));
        Order_ID_Text.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Meal_ID_Text.setPreferredSize(new Dimension(195,30));
        Meal_ID_Text.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Meal_Quantity_Text.setPreferredSize(new Dimension(195,30));
        Meal_Quantity_Text.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Date_Text.setPreferredSize(new Dimension(195,30));
        Date_Text.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        Order_ID_Text.setBackground(Color.GRAY);
        Meal_ID_Text.setBackground(Color.GRAY);
        Meal_Quantity_Text.setBackground(Color.GRAY);
        Date_Text.setBackground(Color.GRAY);

        ImageIcon BottomLeftAddButtonImage = new ImageIcon("pics/rectangle 11.png");
        JButton BottomLeftAddButton = new JButton(BottomLeftAddButtonImage);
        BottomLeftAddButton.setBounds(103,593,100,40);
        BottomLeftAddButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        BottomLeftAddButton.setContentAreaFilled(false);
        BottomLeftAddButton.setBorderPainted(false);
        ImageIcon AddOrderText = new ImageIcon("Add.png");
        JButton AddOrderButton = new JButton(AddOrderText);
        AddOrderButton.setBounds(139,604, 28,17);
        AddOrderButton.setContentAreaFilled(false);
        AddOrderButton.setBorderPainted(false);

//        //Initialize Bottom Left Panel that pops up after clicking Add Order
//        JPanel BottomLeftPanel = new JPanel();
//        BottomLeftPanel.setBackground(Color.WHITE);
//        BottomLeftPanel.setBounds(33,327,240,270);
//        BottomLeftPanel.setLayout(new BoxLayout(BottomLeftPanel, BoxLayout.Y_AXIS));
//        BottomLeftPanel.add(Order_ID_Text);
//        BottomLeftPanel.add(Meal_ID_Text);
//        BottomLeftPanel.add(Meal_Quantity_Text);
//        BottomLeftPanel.add(Date_Text);
//        BottomLeftPanel.add(BottomLeftAddButton);
//        BottomLeftPanel.add(AddOrderButton);

        JPanel BottomLeftPanel = new JPanel();
        BottomLeftPanel.setBackground(Color.WHITE);
        BottomLeftPanel.setBounds(33, 327, 240, 270);
        BottomLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Adds padding between components

// Add components with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        BottomLeftPanel.add(Order_ID_Text, gbc);

        gbc.gridy = 1;
        BottomLeftPanel.add(Meal_ID_Text, gbc);

        gbc.gridy = 2;
        BottomLeftPanel.add(Meal_Quantity_Text, gbc);

        gbc.gridy = 3;
        BottomLeftPanel.add(Date_Text, gbc);

        gbc.gridy = 4;
        BottomLeftPanel.add(BottomLeftAddButton, gbc);

        gbc.gridy = 5;
        BottomLeftPanel.add(AddOrderButton, gbc);



////        JPanel BottomLeftPopUpPanel = new JPanel();
//        BottomLeftPopUpPanel.setBackground(Color.PINK);
//        BottomLeftPopUpPanel.setBounds(103,593, 100, 40);
//        BottomLeftPopUpPanel.setLayout(new BoxLayout(BottomLeftPopUpPanel, BoxLayout.Y_AXIS));
//        BottomLeftPanel.add(Order_ID_Text);
//        BottomLeftPanel.add(Meal_ID_Text);
//        BottomLeftPanel.add(Meal_Quantity_Text);
//        BottomLeftPanel.add(Date_Text);
//        BottomLeftPanel.add(BottomLeftPopUpPanel);

        Frame.add(BottomLeftPanel);
        BottomLeftPanel.setVisible(false);

        ImageIcon AddButtonImage = new ImageIcon("pics/Add order.png");
        JButton AddButton = new JButton(AddButtonImage);
        AddButton.setBackground(Color.GRAY);
        AddButton.addActionListener(e -> BottomLeftPanel.setVisible(true));


        ImageIcon UpdateButtonImage = new ImageIcon("pics/update order.png");
        JButton UpdateButton = new JButton(UpdateButtonImage);
        UpdateButton.setPreferredSize(new Dimension(130,40));
        ImageIcon DeleteButtonImage = new ImageIcon("pics/delete order.png");
        JButton DeleteButton = new JButton(DeleteButtonImage);
        DeleteButton.setPreferredSize(new Dimension(130,40));


        //Warning Panel after pressing Delete Button
        JPanel WarningPanel = new JPanel();
        ImageIcon WarningTextImage = new ImageIcon("pics/warningtext.png");
        JLabel WarningText = new JLabel(WarningTextImage);
        WarningText.setBounds(55,32,190,32);
        WarningPanel.setBackground(Color.WHITE);
        WarningPanel.setBounds(300,45,660,620);
        WarningPanel.setBorder(new LineBorder(new Color(208,108,108,255)));
        WarningPanel.setLayout(null);
        WarningPanel.add(WarningText);

        //new Frame for delete pop up
        JFrame DeletePopUpFrame = new JFrame();
        DeletePopUpFrame.setBounds(300,45,300,200);
        DeletePopUpFrame.setUndecorated(true);
        DeletePopUpFrame.setLocationRelativeTo(null);
        DeletePopUpFrame.add(WarningPanel);

        ImageIcon NoImage = new ImageIcon("pics/no.png");
        JButton NoButton = new JButton(NoImage);
        NoButton.setContentAreaFilled(false);
        NoButton.setBorderPainted(false);
        NoButton.setBounds(40,129,90,30);
        NoButton.addActionListener(e -> DeletePopUpFrame.setVisible(false));
        ImageIcon YesImage = new ImageIcon("pics/yes.png");
        JButton YesButton = new JButton(YesImage);
        YesButton.setContentAreaFilled(false);
        YesButton.setBorderPainted(false);
        YesButton.setBounds(170,129,90,30);
        YesButton.addActionListener(e -> DeletePopUpFrame.setVisible(false));




        //Delete Button Function to warn user of deleting meal order
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeletePopUpFrame.setVisible(true);
                WarningPanel.add(NoButton);
                WarningPanel.add(YesButton);
            }
        });



        AddButton.setBackground(Color.pink);
        AddButton.setContentAreaFilled(false);
        AddButton.setBorderPainted(false);
        UpdateButton.setBackground(Color.pink);
        UpdateButton.setContentAreaFilled(false);
        UpdateButton.setBorderPainted(false);
        DeleteButton.setBackground(Color.red);
        DeleteButton.setContentAreaFilled(false);
        DeleteButton.setBorderPainted(false);


        //Top Left Panel for Order options
        JPanel TopLeftPanel = new JPanel();
        TopLeftPanel.setBounds(33, 54,240,254);
        TopLeftPanel.setBackground(Color.WHITE);
        TopLeftPanel.setLayout(new BoxLayout(TopLeftPanel, BoxLayout.Y_AXIS));
        TopLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        TopLeftPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds vertical spacing
        TopLeftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        TopLeftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        AddButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        UpdateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        DeleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        TopLeftPanel.add(AddButton);
        TopLeftPanel.add(UpdateButton);
        TopLeftPanel.add(DeleteButton);




        JPanel RightSideBottom = new JPanel();
        RightSideBottom.setLayout(new BoxLayout(RightSideBottom, BoxLayout.Y_AXIS));



        Frame.add(ExitButton);
        Frame.add(RightSideBottom);
        Frame.add(TopLeftPanel);
        Frame.add(RightSide);
        Frame.setVisible(true);
    }
}
