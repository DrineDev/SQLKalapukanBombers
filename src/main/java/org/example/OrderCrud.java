package org.example;

import org.example.Classes.Order;
import org.example.SQLQueries.SQLOrder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderCrud extends JFrame {
    private JFrame Frame;
    private JButton ExitButton;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public OrderCrud() {
        //Exit Button
        ImageIcon ExitImageIcon = new ImageIcon("pics/exit button.png");
        ExitButton = new JButton();
        ExitButton.setIcon(ExitImageIcon);
        ExitButton.setContentAreaFilled(false);
        ExitButton.setFocusPainted(false);
        ExitButton.setBorderPainted(false);
        ExitButton.setBounds(962, 15, 20, 20);
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

        JPanel RightSidePanel = new JPanel();
        RightSidePanel.setLayout(null);
        RightSidePanel.setBounds(302, 45, 660, 520);
        RightSidePanel.setBackground(Color.WHITE);
        RightSidePanel.setBorder(new LineBorder(Color.GRAY,2,true));


        ImageIcon OrderImage = new ImageIcon("pics/Orders.png");
        ImageIcon RightSideLine = new ImageIcon("pics/Line 1.png");
        ImageIcon Order_IDImage = new ImageIcon("pics/Order_ID (3).png");
        ImageIcon Order_DateImage = new ImageIcon("pics/Order_Date (2).png");
        ImageIcon StatusImage = new ImageIcon("pics/Status (2).png");
        ImageIcon Total_AmountImage = new ImageIcon("pics/Total_Amount (2).png");

        JLabel OrderLabel = new JLabel(OrderImage);
        JLabel RightSideLineLabel = new JLabel(RightSideLine);
        JLabel Order_IDLabel = new JLabel(Order_IDImage);
        JLabel Order_DateLabel = new JLabel(Order_DateImage);
        JLabel StatusLabel = new JLabel(StatusImage);
        JLabel Total_AmountLabel = new JLabel(Total_AmountImage);

        OrderLabel.setBounds(307,12,51,13);
        RightSideLineLabel.setBounds(24,35,613,3);
        Order_IDLabel.setBounds(32,45,67,14);
        Order_DateLabel.setBounds(168,45,84,14);
        StatusLabel.setBounds(395, 45,54,14);
        Total_AmountLabel.setBounds(524,45,97,14);

        RightSidePanel.add(OrderLabel);
        RightSidePanel.add(RightSideLineLabel);
        RightSidePanel.add(Order_IDLabel);
        RightSidePanel.add(Order_DateLabel);
        RightSidePanel.add(StatusLabel);
        RightSidePanel.add(Total_AmountLabel);

        JScrollPane RightScrollPane = new JScrollPane(RightSidePanel);
        RightScrollPane.setBounds(302, 45,660,520);
        RightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        RightScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JPanel OrderPanel = new JPanel();
        OrderPanel.setLayout(new BoxLayout(OrderPanel,BoxLayout.Y_AXIS));
        OrderPanel.setBounds(24,72,73,419);
        OrderPanel.setBorder(new LineBorder(Color.GRAY,2,false));

        JPanel Order_Date_Panel = new JPanel();
        Order_Date_Panel.setLayout(new BoxLayout(Order_Date_Panel,BoxLayout.Y_AXIS));
        Order_Date_Panel.setBounds(97,72,220,419);
        Order_Date_Panel.setBorder(new LineBorder(Color.GRAY,2,false));

        JPanel Status_Panel = new JPanel();
        Status_Panel.setLayout(new BoxLayout(Status_Panel,BoxLayout.Y_AXIS));
        Status_Panel.setBounds(317,72,220,419);
        Status_Panel.setBorder(new LineBorder(Color.GRAY,2,false));

        JPanel Total_Amount_Panel = new JPanel();
        Total_Amount_Panel.setLayout(new BoxLayout(Total_Amount_Panel,BoxLayout.Y_AXIS));
        Total_Amount_Panel.setBounds(537, 72, 99,419);
        Total_Amount_Panel.setBorder(new LineBorder(Color.GRAY,2,false));


        List<Order> orders = SQLOrder.getAllOrders();

        for (Order order : orders) {
            // Order ID Panel
            JLabel orderIDLabel = new JLabel("Order ID: " + order.getOrderId());
            OrderPanel.add(orderIDLabel);

            // Order Date Panel
            JLabel orderDateLabel = new JLabel("Date: " + order.getOrderDate());
            Order_Date_Panel.add(orderDateLabel);

            // Status Panel
            JLabel statusLabel = new JLabel("Status: " + order.getStatus());
            Status_Panel.add(statusLabel);

            // Total Amount Panel
            JLabel totalAmountLabel = new JLabel("Total: $" + String.format("%.2f", order.getTotalAmount()));
            Total_Amount_Panel.add(totalAmountLabel);
        }

        Container PanelContainer = Frame.getContentPane();
        PanelContainer.setLayout(null);
        RightSidePanel.add(OrderPanel);
        RightSidePanel.add(Order_Date_Panel);
        RightSidePanel.add(Status_Panel);
        RightSidePanel.add(Total_Amount_Panel);




        //Add Order button for addorderpanel
        ImageIcon AddButtonImage = new ImageIcon("pics/Add order.png");
        JButton AddButton = new JButton(AddButtonImage);
        AddButton.setPreferredSize(new Dimension(130,40));


        ImageIcon BottomLeftAddButtonImage = new ImageIcon("pics/BottomLeftAddButton.png");
        JButton BottomLeftAddButton = new JButton(BottomLeftAddButtonImage);
        BottomLeftAddButton.setBounds(70, 228, 100,34);
        BottomLeftAddButton.setBorderPainted(false);

        //Initialize Add Order panel buttons
        JTextField Order_ID_Textfield = new JTextField();
        JTextField Order_Date_Textfield = new JTextField();
        JTextField Status_Textfield = new JTextField();
        JTextField Date_Textfield = new JTextField();
        Order_ID_Textfield.setBounds(22, 24, 195, 26);
        Order_ID_Textfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Order_Date_Textfield.setBounds(22, 75, 195, 26);
        Order_Date_Textfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Status_Textfield.setBounds(22, 126, 195, 26);
        Status_Textfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Date_Textfield.setBounds(22, 177, 195, 26);
        Date_Textfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));


        Order_ID_Textfield.setBackground(Color.white);
        Order_Date_Textfield.setBackground(Color.white);
        Status_Textfield.setBackground(Color.white);
        Date_Textfield.setBackground(Color.white);



        ImageIcon Order_ID_Text = new ImageIcon("pics/Order_ID (1).png");
        ImageIcon Order_Date_Text = new ImageIcon("pics/Order_Date.png");
        ImageIcon Status_Text = new ImageIcon("pics/Status.png");
        ImageIcon Total_Amount_Text = new ImageIcon("pics/Total_Amount.png");


        JLabel Order_ID_Label = new JLabel(Order_ID_Text);
        Order_ID_Label.setBounds(101, 50, 47,10);
        JLabel Order_Date_Label = new JLabel(Order_Date_Text);
        Order_Date_Label.setBounds(95, 101, 59,10);
        JLabel Status_Label= new JLabel(Status_Text);
        Status_Label.setBounds(98, 152, 47,10);
        JLabel Total_Amount_Label = new JLabel(Total_Amount_Text);
        Total_Amount_Label.setBounds(83, 202, 87, 11);



        //AddOrderPanel Options
        JPanel AddOrderPanel = new JPanel();
        AddOrderPanel.setLayout(null);
        AddOrderPanel.setBackground(Color.WHITE);
        AddOrderPanel.setBounds(33, 267, 240, 290);
        AddOrderPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        AddOrderPanel.add(Order_ID_Textfield);
        AddOrderPanel.add(Order_ID_Label);
        AddOrderPanel.add(Order_Date_Textfield);
        AddOrderPanel.add(Order_Date_Label);
        AddOrderPanel.add(Status_Textfield);
        AddOrderPanel.add(Status_Label);
        AddOrderPanel.add(Date_Textfield);
        AddOrderPanel.add(Total_Amount_Label);
        AddOrderPanel.add(BottomLeftAddButton);


        AddOrderPanel.setVisible(false);
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrderPanel.setVisible(true);
                BottomLeftAddButton.setEnabled(true);
            }
        });


        //shuts off bottomleftpanel after adding an order
        BottomLeftAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Order_ID_Textfield.getText().isEmpty() ||
                            Order_Date_Textfield.getText().isEmpty() ||
                            Status_Textfield.getText().isEmpty() ||
                            Date_Textfield.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields must filled", "Input error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int Order_ID = Integer.parseInt(Order_ID_Textfield.getText());
                    String Order_Date = Order_Date_Textfield.getText();
                    String Status = Status_Textfield.getText();
                    Double Total_Amount = Double.parseDouble(Date_Textfield.getText());


                    if (Order_Date != null && Status != null && Total_Amount != 0) {
                        AddOrderPanel.setVisible(false);
                        JOptionPane.showMessageDialog(null, "Order added successfully", "Add Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add order", "Add error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Invalid number format in fields", "Input error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });





        ImageIcon UpdateButtonImage = new ImageIcon("pics/update order.png");
        JButton UpdateButton = new JButton(UpdateButtonImage);
        UpdateButton.setPreferredSize(new Dimension(130,40));

        ImageIcon BottomLeftUpdateButtonImage = new ImageIcon("pics/BottomLeftUpdateButton.png");
        JButton BottomLeftUpdateButton = new JButton(BottomLeftUpdateButtonImage);
        BottomLeftUpdateButton.setBounds(70, 228, 100,34);
        BottomLeftUpdateButton.setBorderPainted(false);


        JTextField Order_ID_Textfield2 = new JTextField();
        JTextField Order_Date_Textfield2 = new JTextField();
        JTextField Status_Textfield2 = new JTextField();
        JTextField Total_Amount_Textfield2 = new JTextField();
        Order_ID_Textfield2.setBounds(22, 24, 195, 26);
        Order_ID_Textfield2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Order_Date_Textfield2.setBounds(22, 75, 195, 26);
        Order_Date_Textfield2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Status_Textfield2.setBounds(22, 126, 195, 26);
        Status_Textfield2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        Total_Amount_Textfield2.setBounds(22, 177, 195, 26);
        Total_Amount_Textfield2.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        ImageIcon Order_ID_Text2 = new ImageIcon("pics/Order_ID (1).png");
        ImageIcon Order_Date_Text2 = new ImageIcon("pics/Order_Date.png");
        ImageIcon Status_Text2 = new ImageIcon("pics/Status.png");
        ImageIcon Total_Amount2 = new ImageIcon("pics/Total_Amount.png");

        JLabel Order_ID_Label2 = new JLabel(Order_ID_Text2);
        Order_ID_Label2.setBounds(101, 50, 47,10);
        JLabel Order_Date_Label2 = new JLabel(Order_Date_Text2);
        Order_Date_Label2.setBounds(95, 101, 59,10);
        JLabel Status_Label2 = new JLabel(Status_Text2);
        Status_Label2.setBounds(98, 152, 47,10);
        JLabel Total_Amount_Label2 = new JLabel(Total_Amount2);
        Total_Amount_Label2.setBounds(83, 202, 87, 11);

        JPanel UpdateOrderPanel = new JPanel();
        UpdateOrderPanel.setLayout(null);
        UpdateOrderPanel.setBackground(Color.WHITE);
        UpdateOrderPanel.setBounds(33,267,240,290);
        UpdateOrderPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY,2,true));

        UpdateOrderPanel.add(Order_ID_Textfield2);
        UpdateOrderPanel.add(Order_ID_Label2);
        UpdateOrderPanel.add(Order_Date_Textfield2);
        UpdateOrderPanel.add(Order_Date_Label2);
        UpdateOrderPanel.add(Status_Textfield2);
        UpdateOrderPanel.add(Status_Label2);
        UpdateOrderPanel.add(Total_Amount_Textfield2);
        UpdateOrderPanel.add(Total_Amount_Label2);
        UpdateOrderPanel.add(BottomLeftUpdateButton);



        UpdateOrderPanel.setVisible(false);
        UpdateButton.setBackground(Color.GRAY);
        UpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrderPanel.setVisible(false);
                BottomLeftAddButton.setEnabled(false);
                UpdateOrderPanel.setVisible(true);
            }
        });


        //shuts off bottomleftpanel after updating an order
        BottomLeftUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (Order_ID_Textfield2.getText().isEmpty() ||
                            Order_Date_Textfield2.getText().isEmpty() ||
                            Status_Textfield2.getText().isEmpty() ||
                            Total_Amount_Textfield2.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(null, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int Order_ID = Integer.parseInt(Order_ID_Textfield.getText());
                    String Order_Date = Order_Date_Textfield2.getText();
                    String Status = Status_Textfield2.getText();
                    Double Total_Amount = Double.parseDouble(Total_Amount_Textfield2.getText());

                    if(Order_ID < 0) {
                        JOptionPane.showMessageDialog(null, "All fields must have an input", "Add Order error", JOptionPane.ERROR_MESSAGE);
                    }
                    SQLOrder.editOrder(Order_ID, Order_Date, Status, Total_Amount);
                    UpdateOrderPanel.setVisible(false);

                    if (Order_Date != null || Status != null || Total_Amount > 0) {
                        UpdateOrderPanel.setVisible(false);
                        JOptionPane.showMessageDialog(null,
                                "Order updated successfully",
                                "Update Confirmation",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Failed to update order",
                                "Update Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Invalid number format in input fields",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
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




        // Create and initialize the MenuButton
        ImageIcon MenuButtonImage = new ImageIcon("pics/menu.png");
        JButton MenuButton = new JButton(MenuButtonImage);
        MenuButton.setBounds(10, 10, 50, 40);
        MenuButton.setBorderPainted(false);
        MenuButton.setContentAreaFilled(false);
        MenuButton.setBackground(Color.PINK);


        NavigatorButtonManager navButtonOrder = new NavigatorButtonManager();
        navButtonOrder.setBounds(10,10,206,360);
        navButtonOrder.setOpaque(true);
        navButtonOrder.setBackground(Color.WHITE);
        navButtonOrder.setBounds(10, 10, 206, 360);
        navButtonOrder.setVisible(false);


        //Drops down menu to go to Inventory, Order
        MenuButton.addActionListener(e -> {
            boolean isVisible = navButtonOrder.isVisible();
            navButtonOrder.setVisible(!isVisible); // Toggle visibility
            navButtonOrder.getParent().revalidate(); // Revalidate the layout
            navButtonOrder.getParent().repaint();    // Repaint to remove lingering visuals
        });

        Frame.add(navButtonOrder, Integer.valueOf(2));
        Frame.add(MenuButton);
        Frame.add(ExitButton);
        Frame.add(RightSideBottom);
        Frame.add(TopLeftPanel);
        Frame.add(RightSidePanel);
        Frame.add(AddOrderPanel);
        Frame.add(UpdateOrderPanel);
        Frame.add(RightScrollPane);
        Frame.setVisible(true);
    }

//    private void loadOrders() {
//        tableModel.setRowCount(0);
//
//        List<Order> orders = SQLCreate.createOrderTable();
//        if(orders != null) {
//            for(Order order : orders) {
//                Object[] row = {
//                        order.getOrderId(),
//                        order.getMealId();
//                order.getMealQuantity(),
//                        order.getDate()
//                };
//                tableModel.addRow(row);
//            }
//        }
//    }
}
