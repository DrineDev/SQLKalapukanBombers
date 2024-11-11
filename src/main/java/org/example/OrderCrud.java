package org.example;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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



        //Initialize Bottom Left Panel that pops up after clicking Add Order
        JPanel BottomLeftPanel = new JPanel();
        BottomLeftPanel.setBounds(33,327,240,324);
        BottomLeftPanel.setBackground(Color.WHITE);
        BottomLeftPanel.setLayout(new BoxLayout(BottomLeftPanel, BoxLayout.Y_AXIS));

        //Initialize Bottom Left buttons
        JButton Order_ID_Button = new JButton("Order_ID");
        JButton Meal_ID_Button = new JButton("Meal_ID");
        JButton Meal_Quantity_Button = new JButton("Meal_Quantity");
        JButton Date_Button = new JButton("Date");

        JPanel BottomLeftAddButton = new JPanel();
        BottomLeftAddButton.setBackground(Color.PINK);
        BottomLeftAddButton.setBounds(103,593, 100, 40);
        BottomLeftAddButton.setLayout(new BoxLayout(BottomLeftAddButton, BoxLayout.Y_AXIS));
        BottomLeftPanel.add(Order_ID_Button);
        BottomLeftPanel.add(Meal_ID_Button);
        BottomLeftPanel.add(Meal_Quantity_Button);
        BottomLeftPanel.add(Date_Button);
        BottomLeftPanel.add(BottomLeftAddButton);

        JButton AddButton = new JButton("Add Order");
        AddButton.setBackground(Color.GRAY);
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Frame.add(BottomLeftPanel);
                BottomLeftPanel.setVisible(true);
            }
        });


        JButton EditButton = new JButton("Update Order");
        EditButton.setBackground(new Color(0xF89289));
        JButton DeleteButton = new JButton("Delete Order");


        //Warning Panel after pressing Delete Button
        JPanel WarningPanel = new JPanel();
        WarningPanel.setBounds(300,45,660,620);
        WarningPanel.setBackground(Color.WHITE);
        WarningPanel.setLayout(null);

        //new Frame for delete pop up
        JFrame DeletePopUpFrame = new JFrame();
        DeletePopUpFrame.setBounds(300,45,300,200);
        DeletePopUpFrame.setBackground(Color.WHITE);
        DeletePopUpFrame.add(WarningPanel, BorderLayout.CENTER);
        DeletePopUpFrame.setLocationRelativeTo(null);


        JButton NoButton = new JButton("No");
        NoButton.setBounds(40,129,90,30);
        NoButton.addActionListener(e -> DeletePopUpFrame.setVisible(false));
        JButton YesButton = new JButton("Yes");
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
        EditButton.setBackground(Color.pink);
        DeleteButton.setBackground(Color.red);



        //Top Left Panel for Order options
        JPanel TopLeftPanel = new JPanel();
        TopLeftPanel.setBounds(33, 59,240,254);
        TopLeftPanel.setBackground(Color.WHITE);
        TopLeftPanel.setBorder(new EmptyBorder(0, 15, 0, 0));
        TopLeftPanel.setLayout(new BoxLayout(TopLeftPanel, BoxLayout.Y_AXIS));
        TopLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        AddButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        EditButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        DeleteButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        TopLeftPanel.add(AddButton);
        TopLeftPanel.add(EditButton);
        TopLeftPanel.add(DeleteButton);




        JPanel RightSideBottom = new JPanel();
        RightSideBottom.setLayout(new BoxLayout(RightSideBottom, BoxLayout.Y_AXIS));


        Frame.add(RightSide);
        Frame.add(TopLeftPanel);
        Frame.add(RightSideBottom);
        Frame.add(ExitButton);
        Frame.setVisible(true);

    }
}