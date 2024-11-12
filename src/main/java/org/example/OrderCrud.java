package org.example;
import org.example.Classes.Meal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

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
        ImageIcon BottomLeftImage = new ImageIcon("pics/rectanglebtmleftorder.png");
        JButton Order_ID_Button = new JButton(BottomLeftImage);
        Order_ID_Button.setBounds(195,30,195,30);
        JButton Meal_ID_Button = new JButton(BottomLeftImage);
        Meal_ID_Button.setBounds(195,30,195,30);
        JButton Meal_Quantity_Button = new JButton(BottomLeftImage);
        Meal_Quantity_Button.setBounds(195,30,195,30);
        JButton Date_Button = new JButton(BottomLeftImage);
        Date_Button.setBounds(195,30,195,30);

        ImageIcon BottomLeftAddButtonImage = new ImageIcon("pics/rectangle 11.png");
        JButton BottomLeftAddButton = new JButton(BottomLeftAddButtonImage);

        //Initialize Bottom Left Panel that pops up after clicking Add Order
        JPanel BottomLeftPanel = new JPanel();
        BottomLeftPanel.setBackground(Color.WHITE);
        BottomLeftPanel.setBounds(33,327,240,324);
        BottomLeftPanel.setLayout(new BoxLayout(BottomLeftPanel, BoxLayout.Y_AXIS));
        BottomLeftPanel.add(Order_ID_Button);
        BottomLeftPanel.add(Meal_ID_Button);
        BottomLeftPanel.add(Meal_Quantity_Button);
        BottomLeftPanel.add(Date_Button);
        BottomLeftPanel.add(BottomLeftAddButton);


//        JPanel BottomLeftAddButton = new JPanel();
//        BottomLeftAddButton.setBackground(Color.PINK);
//        BottomLeftAddButton.setBounds(103,593, 100, 40);
//        BottomLeftAddButton.setLayout(new BoxLayout(BottomLeftAddButton, BoxLayout.Y_AXIS));
//        BottomLeftPanel.add(Order_ID_Button);
//        BottomLeftPanel.add(Meal_ID_Button);
//        BottomLeftPanel.add(Meal_Quantity_Button);
//        BottomLeftPanel.add(Date_Button);
//        BottomLeftPanel.add(BottomLeftAddButton);

        Frame.add(BottomLeftPanel);
        BottomLeftPanel.setVisible(false);

        ImageIcon AddButtonImage = new ImageIcon("pics/add order.png");
        JButton AddButton = new JButton(AddButtonImage);
        AddButton.setBackground(Color.GRAY);
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BottomLeftPanel.setVisible(true);
            }
        });


        ImageIcon UpdateButtonImage = new ImageIcon("pics/update.png");
        JButton UpdateButton = new JButton(UpdateButtonImage);
        UpdateButton.setPreferredSize(new Dimension(130,40));
        ImageIcon DeleteButtonImage = new ImageIcon("pics/deleteorder.png");
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
        YesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeletePopUpFrame.setVisible(false);
                System.out.print("Order Deleted");
            }
        });



        //Delete Button Function to warn user of deleting meal order
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeletePopUpFrame.setVisible(true);
                System.out.println("Are you Sure?");
                System.out.println("(This action cannot be undone)");
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
        TopLeftPanel.setBounds(33, 59,240,254);
        TopLeftPanel.setBackground(Color.WHITE);
        TopLeftPanel.setBorder(new EmptyBorder(59, 33, 0, 0));
        TopLeftPanel.setLayout(new BoxLayout(TopLeftPanel, BoxLayout.Y_AXIS));
        TopLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
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
