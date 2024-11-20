package org.example;

import org.example.Classes.Order;
import org.example.SQLQueries.SQLOrder;

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





        //Initialize Add Order panel buttons
        JTextField Order_ID_Textfield = new JTextField();
        JTextField Meal_ID_Textfield = new JTextField();
        JTextField Meal_Quantity_Textfield = new JTextField();
        JTextField Date_Textfield = new JTextField();
        Order_ID_Textfield.setPreferredSize(new Dimension(195,25));
        Order_ID_Textfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Order_ID_Textfield.setBackground(Color.GRAY);
        Meal_ID_Textfield.setPreferredSize(new Dimension(195,25));
        Meal_ID_Textfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Meal_Quantity_Textfield.setPreferredSize(new Dimension(195,25));
        Meal_Quantity_Textfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Date_Textfield.setPreferredSize(new Dimension(195,25));
        Date_Textfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));


        JLabel spacer = new JLabel();
        spacer.setPreferredSize(new Dimension(84,10));
        JLabel spacer2 = new JLabel();
        spacer2.setPreferredSize(new Dimension(84,10));
        Order_ID_Textfield.setBackground(Color.WHITE);
        Meal_ID_Textfield.setBackground(Color.WHITE);
        Meal_Quantity_Textfield.setBackground(Color.WHITE);
        Date_Textfield.setBackground(Color.WHITE);

        //Add Order button for addorderpanel
        ImageIcon BottomLeftAddButtonImage = new ImageIcon("pics/BottomLeftAddButton.png");
        JButton BottomLeftAddButton = new JButton(BottomLeftAddButtonImage);
        BottomLeftAddButton.setPreferredSize(new Dimension(100,40));


//        ImageIcon TextFieldBackgroundImage = new ImageIcon ("pics/TextfieldBackground.png");
        ImageIcon Order_ID_Text = new ImageIcon("pics/Order_ID (2).png");
        ImageIcon Meal_ID_Text = new ImageIcon("pics/Meal_ID (2).png");
        ImageIcon Meal_Quantity_Text = new ImageIcon("pics/Meal Quantity (2).png");
        ImageIcon Date_Text = new ImageIcon("pics/Date (2).png");

//        JLabel TextFieldBackground = new JLabel (TextFieldBackgroundImage);
        JLabel Order_ID_Label = new JLabel(Order_ID_Text);
        JLabel Meal_ID_Label = new JLabel(Meal_ID_Text);
        JLabel Meal_Quantity_Label = new JLabel(Meal_Quantity_Text);
        JLabel Date_Label = new JLabel(Date_Text);


        //AddOrderPanel Options
        JPanel AddOrderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        AddOrderPanel.setBackground(Color.WHITE);
        AddOrderPanel.setBounds(33, 267, 240, 290);
        AddOrderPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        AddOrderPanel.add(Order_ID_Textfield);
        AddOrderPanel.add(Order_ID_Label);
        AddOrderPanel.add(Meal_ID_Textfield);
        AddOrderPanel.add(Meal_ID_Label);
        AddOrderPanel.add(Meal_Quantity_Textfield);
        AddOrderPanel.add(Meal_Quantity_Label);
        AddOrderPanel.add(Date_Textfield);
        AddOrderPanel.add(spacer);
        AddOrderPanel.add(Date_Label);
        AddOrderPanel.add(spacer2);
        AddOrderPanel.add(BottomLeftAddButton);

        Frame.add(AddOrderPanel);
        AddOrderPanel.setVisible(false);
        ImageIcon AddButtonImage = new ImageIcon("pics/Add order.png");
        JButton AddButton = new JButton(AddButtonImage);
        AddButton.setBackground(Color.GRAY);
        AddButton.addActionListener(e -> AddOrderPanel.setVisible(true));


        //shuts off bottomleftpanel after adding an order
        BottomLeftAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mealId = Integer.parseInt(Meal_ID_Textfield.getText());
                int mealQuantity = Integer.parseInt(Meal_Quantity_Textfield.getText());
                String Date = Date_Textfield.getText();

                SQLOrder.addOrder(mealId, mealQuantity, Date);
                AddOrderPanel.setVisible(false);
            }
        });





        ImageIcon UpdateButtonImage = new ImageIcon("pics/update order.png");
        JButton UpdateButton = new JButton(UpdateButtonImage);
        UpdateButton.setPreferredSize(new Dimension(130,40));

        ImageIcon BottomLeftUpdateButtonImage = new ImageIcon("pics/BottomLeftUpdateButton.png");
        JButton BottomLeftUpdateButton = new JButton(BottomLeftUpdateButtonImage);
        BottomLeftUpdateButton.setPreferredSize(new Dimension(100,40));


        JTextField Order_ID_Textfield2 = new JTextField();
        JTextField Meal_ID_Textfield2 = new JTextField();
        JTextField Meal_Quantity_Textfield2 = new JTextField();
        JTextField Date_Textfield2 = new JTextField();
        Order_ID_Textfield2.setPreferredSize(new Dimension(195,25));
        Order_ID_Textfield2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Meal_ID_Textfield2.setPreferredSize(new Dimension(195,25));
        Meal_ID_Textfield2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Meal_Quantity_Textfield2.setPreferredSize(new Dimension(195,25));
        Meal_Quantity_Textfield2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Date_Textfield2.setPreferredSize(new Dimension(195,25));
        Date_Textfield2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        ImageIcon Order_ID_Text2 = new ImageIcon("pics/Order_ID (2).png");
        ImageIcon Meal_ID_Text2 = new ImageIcon("pics/Meal_ID (2).png");
        ImageIcon Meal_Quantity_Text2 = new ImageIcon("pics/Meal Quantity (2).png");
        ImageIcon Date_Text2 = new ImageIcon("pics/Date (2).png");

        JLabel Order_ID_Label2 = new JLabel(Order_ID_Text2);
        JLabel Meal_ID_Label2 = new JLabel(Meal_ID_Text2);
        JLabel Meal_Quantity_Label2= new JLabel(Meal_Quantity_Text2);
        JLabel Date_Label2 = new JLabel(Date_Text2);

        JPanel UpdateOrderPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        UpdateOrderPanel.setBackground(Color.WHITE);
        UpdateOrderPanel.setBounds(33,267,240,290);
        UpdateOrderPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY,2,true));

        UpdateOrderPanel.add(Order_ID_Textfield2);
        UpdateOrderPanel.add(Order_ID_Label2);
        UpdateOrderPanel.add(Meal_ID_Textfield2);
        UpdateOrderPanel.add(Meal_ID_Label2);
        UpdateOrderPanel.add(Meal_Quantity_Textfield2);
        UpdateOrderPanel.add(Meal_Quantity_Label2);
        UpdateOrderPanel.add(Date_Textfield2);
        UpdateOrderPanel.add(spacer);
        UpdateOrderPanel.add(Date_Label2);
        UpdateOrderPanel.add(spacer2);
        UpdateOrderPanel.add(BottomLeftUpdateButton);


        Frame.add(UpdateOrderPanel);
        UpdateOrderPanel.setVisible(false);
        UpdateButton.setBackground(Color.GRAY);
        UpdateButton.addActionListener(new ActionListener() {
            @Override
        public void actionPerformed(ActionEvent e) {
            AddOrderPanel.setVisible(false);
            UpdateOrderPanel.setVisible(true);
                }
            });


                //shuts off bottomleftpanel after updating an order
                BottomLeftUpdateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int orderId = Integer.parseInt(Order_ID_Textfield.getText());
                        int mealId = Integer.parseInt(Meal_ID_Textfield.getText());
                        int mealQuantity = Integer.parseInt(Meal_Quantity_Textfield.getText());
                        String Date = Date_Textfield.getText();

                        SQLOrder.editOrder(orderId, mealId, mealQuantity, Date);
                        UpdateOrderPanel.setVisible(false);
                    }
                });



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

        YesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int OrderId = Integer.parseInt(Order_ID_Textfield.getText());

                SQLOrder.deleteOrder(OrderId);
                DeletePopUpFrame.setVisible(false);
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
