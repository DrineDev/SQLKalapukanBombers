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
        Frame.getContentPane().setBackground(Color.PINK);

        JPanel RightSidePanel = new JPanel(new GridLayout(20,4,10,10));
        RightSidePanel.setBounds(290, 20, 650, 550); // Adjusted x, width, and height
        RightSidePanel.setBackground(Color.WHITE);
        RightSidePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        RightSidePanel.setBorder(new LineBorder(Color.BLACK, 2, true));

        ImageIcon OrderImage = new ImageIcon("pics/Order.png");
        ImageIcon RightSideLine = new ImageIcon("pics/line 1.png");
        ImageIcon RightSideOrderImage = new ImageIcon("pics/OrderID.png");
        ImageIcon RightSideMealIDImage = new ImageIcon("pics/Meal_ID (1).png");
        ImageIcon RightSideMealQuantityImage = new ImageIcon("pics/Meal_Quantity.png");
        ImageIcon RightSideDateImage = new ImageIcon("pics/Date (1).png");

        JLabel OrderLabel = new JLabel(OrderImage);
        JLabel RightSideLineLabel = new JLabel(RightSideLine);
        JLabel RightSideOrderLabel = new JLabel(RightSideOrderImage);
        JLabel RightSideMealIDLabel = new JLabel(RightSideMealIDImage);
        JLabel RightSideMealQuantityLabel = new JLabel(RightSideMealQuantityImage);
        JLabel RightSideDateLabel = new JLabel(RightSideDateImage);


//        RightSidePanel.add(OrderLabel);
//        RightSidePanel.add(RightSideLineLabel);

        RightSidePanel.setLayout(new GridBagLayout());
        GridBagConstraints Rightsidegbc = new GridBagConstraints();

        Rightsidegbc.weightx = 1.0;
        Rightsidegbc.anchor = GridBagConstraints.NORTH;
        Rightsidegbc.fill = GridBagConstraints.HORIZONTAL;
        Rightsidegbc.insets = new Insets(10,10,10,10);

        Rightsidegbc.gridy = 0;
        Rightsidegbc.gridx = 0;
        Rightsidegbc.gridwidth = 4;
        RightSidePanel.add(OrderLabel, Rightsidegbc);

        Rightsidegbc.gridy = 1;
        Rightsidegbc.gridwidth = 4;
        RightSidePanel.add(RightSideLineLabel, Rightsidegbc);

        Rightsidegbc.gridy = 2;
        Rightsidegbc.gridwidth = 1;
        RightSidePanel.add(RightSideOrderLabel,Rightsidegbc);

        Rightsidegbc.gridx = 1;
        RightSidePanel.add(RightSideMealIDLabel, Rightsidegbc);

        Rightsidegbc.gridx = 2;
        RightSidePanel.add(RightSideMealQuantityLabel,Rightsidegbc);

        Rightsidegbc.gridx = 3;
        RightSidePanel.add(RightSideDateLabel, Rightsidegbc);

        Rightsidegbc.gridwidth = 4;
        Rightsidegbc.weighty = 1.0; // Give extra space to this "filler" component
        RightSidePanel.add(Box.createVerticalGlue(), Rightsidegbc);





        //Initialize Bottom Left buttons
//        ImageIcon BottomLeftImage = new ImageIcon("pics/rectangle 10.png");
        JTextField Order_ID_Textfield = new JTextField();
        JTextField Meal_ID_Textfield = new JTextField();
        JTextField Meal_Quantity_Textfield = new JTextField();
        JTextField Date_Textfield = new JTextField();
        Order_ID_Textfield.setPreferredSize(new Dimension(195,30));
        Order_ID_Textfield.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        Meal_ID_Textfield.setPreferredSize(new Dimension(195,30));
        Meal_ID_Textfield.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        Meal_Quantity_Textfield.setPreferredSize(new Dimension(195,30));
        Meal_Quantity_Textfield.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        Date_Textfield.setPreferredSize(new Dimension(195,30));
        Date_Textfield.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        Order_ID_Textfield.setBackground(Color.WHITE);
        Meal_ID_Textfield.setBackground(Color.WHITE);
        Meal_Quantity_Textfield.setBackground(Color.WHITE);
        Date_Textfield.setBackground(Color.WHITE);

        ImageIcon BottomLeftAddButtonImage = new ImageIcon("pics/rectangle 11.png");
        JButton BottomLeftAddButton = new JButton(BottomLeftAddButtonImage);
        BottomLeftAddButton.setContentAreaFilled(false);
        BottomLeftAddButton.setBorderPainted(false);
        ImageIcon AddOrderText = new ImageIcon("Add.png");
        JLabel AddOrderButtonLabel = new JLabel(AddOrderText);
        AddOrderButtonLabel.setVisible(true);
        BottomLeftAddButton.setContentAreaFilled(false);
        BottomLeftAddButton.setBorderPainted(false);

        JLayeredPane BottomLeftButtonLayer = new JLayeredPane();
        BottomLeftButtonLayer.setPreferredSize(new Dimension(100,40));
        BottomLeftButtonLayer.setLayout(null);
        BottomLeftAddButton.setBounds(0,0,100,40);
        BottomLeftButtonLayer.add(BottomLeftAddButton, Integer.valueOf(0));
        BottomLeftButtonLayer.add(AddOrderButtonLabel, Integer.valueOf(1));



        ImageIcon Order_ID_Text = new ImageIcon("pics/Date (2).png");
        ImageIcon Meal_ID_Text = new ImageIcon("pics/Meal_ID (2).png");
        ImageIcon Meal_Quantity_Text = new ImageIcon("pics/Meal Quantity (2).png");
        ImageIcon Date_Text = new ImageIcon("pics/Date (2).png");

        JLabel Order_ID_Label = new JLabel(Order_ID_Text);
        JLabel Meal_ID_Label = new JLabel(Meal_ID_Text);
        JLabel Meal_Quantity_Label = new JLabel(Meal_Quantity_Text);
        JLabel Date_Label = new JLabel(Date_Text);


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

        //BottomLeftPanel Options
        JPanel BottomLeftPanel = new JPanel();
        BottomLeftPanel.setBackground(Color.WHITE);
        BottomLeftPanel.setBounds(33, 267, 240, 290);
        BottomLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        BottomLeftPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5); // Adds padding between components

        gbc.gridx = 0;
        gbc.gridy = 0;
        BottomLeftPanel.add(Order_ID_Textfield, gbc);

        gbc.gridy = 1;
        BottomLeftPanel.add(Order_ID_Label,gbc);

        gbc.gridy = 2;
        BottomLeftPanel.add(Meal_ID_Textfield, gbc);

        gbc.gridy = 3;
        BottomLeftPanel.add(Meal_ID_Label,gbc);

        gbc.gridy = 4;
        BottomLeftPanel.add(Meal_Quantity_Textfield, gbc);

        gbc.gridy = 5;
        BottomLeftPanel.add(Meal_Quantity_Label,gbc);

        gbc.gridy = 6;
        BottomLeftPanel.add(Date_Textfield, gbc);

        gbc.gridy = 7;
        BottomLeftPanel.add(Date_Label,gbc);

        gbc.gridy = 9;
        BottomLeftPanel.add(BottomLeftButtonLayer, gbc);

        //shuts off bottomleftpanel after adding an order
        BottomLeftAddButton.addActionListener(ActionListener -> BottomLeftPanel.setVisible(false));


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
        YesButton.addActionListener(ActionListener -> DeletePopUpFrame.setVisible(false));

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
        TopLeftPanel.setBounds(33, 54,240,204);
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


        //drop down menu
        JPanel MenuPanel = new JPanel();



        // Create and initialize the MenuButton
        ImageIcon MenuButtonImage = new ImageIcon("pics/menu.png");
        JButton MenuButton = new JButton(MenuButtonImage);
        MenuButton.setBounds(10, 10, 50, 40);
        MenuButton.setBorderPainted(false);
        MenuButton.setContentAreaFilled(false);
        MenuButton.setBackground(Color.PINK);

        //Drops down menu to go to Inventory, Order
//        MenuButton.addActionListener(ActionListener -> MenuPanel.setVisible(true));




        Frame.add(MenuButton);
        Frame.add(ExitButton);
        Frame.add(RightSideBottom);
        Frame.add(TopLeftPanel);
        Frame.add(RightSidePanel);
        Frame.setVisible(true);
    }
}
