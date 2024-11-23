package org.example;

import org.example.Classes.Order;
import org.example.Classes.OrderItem;
import org.example.SQLQueries.SQLOrder;
import org.example.SQLQueries.SQLOrderItems;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class OrderCrud extends JFrame {
    private JFrame Frame;
    private JButton ExitButton;
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;
    private JRadioButton mainMenuButton, inventoryButton, orderButton, salesButton, promotionsButton;
    private ButtonGroup buttonGroup;

    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_WIDTH = 150;
    private static final int VERTICAL_SPACING = 10;
    private static final int VERTICAL_PADDING = 20;
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
        Order_IDLabel.setBounds(46,45,67,14);
        Order_DateLabel.setBounds(232,45,84,14);
        StatusLabel.setBounds(457, 45,54,14);
        Total_AmountLabel.setBounds(538,45,97,14);

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


        JPanel OrderItemsPanel = new JPanel();
        OrderItemsPanel.setLayout(null);
        OrderItemsPanel.setBackground(Color.WHITE);
        OrderItemsPanel.setBounds(33, 267, 240, 290);
        OrderItemsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY,2,true));

        JLabel mealIdHeader = new JLabel("Meal ID");
        JLabel quantityHeader = new JLabel("Quantity");
        JLabel priceHeader = new JLabel("Price");
        JLabel subtotalHeader = new JLabel("Subtotal");

        mealIdHeader.setBounds(10, 10, 50, 20);
        quantityHeader.setBounds(70, 10, 50, 20);
        priceHeader.setBounds(130, 10, 50, 20);
        subtotalHeader.setBounds(180, 10, 50, 20);

        OrderItemsPanel.add(mealIdHeader);
        OrderItemsPanel.add(quantityHeader);
        OrderItemsPanel.add(priceHeader);
        OrderItemsPanel.add(subtotalHeader);



        JPanel OrderPanel = new JPanel();
        OrderPanel.setLayout(new BoxLayout(OrderPanel,BoxLayout.Y_AXIS));
        OrderPanel.setBounds(24,72,113,419);
        OrderPanel.setBorder(new LineBorder(Color.GRAY,2,false));

        JPanel Order_Date_Panel = new JPanel();
        Order_Date_Panel.setLayout(new BoxLayout(Order_Date_Panel,BoxLayout.Y_AXIS));
        Order_Date_Panel.setBounds(107,72,330,419);
        Order_Date_Panel.setBorder(new LineBorder(Color.GRAY,2,false));

        JPanel Status_Panel = new JPanel();
        Status_Panel.setLayout(new BoxLayout(Status_Panel,BoxLayout.Y_AXIS));
        Status_Panel.setBounds(418,72,120,419);
        Status_Panel.setBorder(new LineBorder(Color.GRAY,2,false));

        JPanel Total_Amount_Panel = new JPanel();
        Total_Amount_Panel.setLayout(new BoxLayout(Total_Amount_Panel,BoxLayout.Y_AXIS));
        Total_Amount_Panel.setBounds(537, 72, 99,419);
        Total_Amount_Panel.setBorder(new LineBorder(Color.GRAY,2,false));


        List<Order> orders = SQLOrder.getAllOrders();

        for (Order order : orders) {
            // Order ID Panel
            JButton orderIDButton = new JButton(String.valueOf(order.getOrderId()));
            OrderPanel.add(orderIDButton);
            List<OrderItem> items = SQLOrderItems.getOrderItems(Integer.parseInt(String.valueOf(order.getOrderId())));

            orderIDButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayOrderItems(Integer.parseInt(String.valueOf(order.getOrderId())), OrderItemsPanel);
                }
            });

            // Order Date Panel
            JLabel orderDateLabel = new JLabel(order.getOrderDate());
            Order_Date_Panel.add(orderDateLabel);

            // Status Panel
            JLabel statusLabel = new JLabel(order.getStatus());
            Status_Panel.add(statusLabel);

            // Total Amount Panel
            JLabel totalAmountLabel = new JLabel(String.format("%.2f", order.getTotalAmount()));
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


        ImageIcon ConfirmationPanelText1 = new ImageIcon("pics/Are you sure you want to.png");
        ImageIcon ConfirmationPanelText2 = new ImageIcon("pics/you to go to the Main Menu_.png");
        JLabel ConfirmationTextLabel = new JLabel(ConfirmationPanelText1);
        JLabel ConfirmationTextLabel2 = new JLabel(ConfirmationPanelText2);

        ConfirmationTextLabel.setBounds(52,9,145,15);
        ConfirmationTextLabel2.setBounds(27,30,195,15);
        ImageIcon CancelImageIcon = new ImageIcon("pics/Group 1.png");
        ImageIcon ConfirmImageIcon = new ImageIcon("pics/Group 3.png");
        JButton CancelButton = new JButton(CancelImageIcon);
        JButton ConfirmButton = new JButton(ConfirmImageIcon);

        CancelButton.setBounds(61,123, 120,32);
        CancelButton.setContentAreaFilled(false);
        CancelButton.setBorderPainted(false);
        ConfirmButton.setBounds(61,72,120,30);
        ConfirmButton.setContentAreaFilled(false);
        ConfirmButton.setBorderPainted(false);
        JPanel ConfirmationPanel = new JPanel();
        ConfirmationPanel.setLayout(null);
        ConfirmationPanel.setBounds(505, 235, 250,200);
        ConfirmationPanel.setBorder(new LineBorder(Color.PINK,2,false));
        ConfirmationPanel.add(CancelButton);
        ConfirmationPanel.add(ConfirmButton);
        ConfirmationPanel.add(ConfirmationTextLabel);
        ConfirmationPanel.add(ConfirmationTextLabel2);
        ConfirmationPanel.setVisible(false);

       AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmationPanel.setVisible(true);
            }
        });

        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfirmationPanel.setVisible(false);
            }
        });

        ConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(MainFrameManager::new);
                Frame.dispose();
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

                    int Order_ID = Integer.parseInt(Order_ID_Textfield2.getText());
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
                int OrderId = Integer.parseInt(Order_ID_Textfield2.getText());

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
        createPopupMenu();

// Add a component (e.g., a button or the frame itself) to trigger the popup menu
        Frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopupMenu(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopupMenu(e);
            }

            private void showPopupMenu(MouseEvent e) {
                if (e.isPopupTrigger()) { // Detect right-click
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        ImageIcon navButtonIcon = new ImageIcon("pics/menu.png");
        JButton navButton = new JButton (navButtonIcon);

        navButton.setBounds(10,10,50,40);
        navButton.setBorderPainted(false);
        navButton.setContentAreaFilled(false);
        navButton.setFocusPainted(false);

        navButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(navButton, 0, navButton.getHeight()); // Display popup menu below the button
            }
        });

        Frame.add(ConfirmationPanel);
        Frame.add(ExitButton);
        Frame.add(RightSideBottom);
        Frame.add(TopLeftPanel);
        Frame.add(RightSidePanel);
        Frame.add(UpdateOrderPanel);
        Frame.add(RightScrollPane);
        Frame.add(navButton);
        Frame.setVisible(true);
    }

    private void createPopupMenu() {
        popupMenu = new JPopupMenu() {
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon backgroundImage = new ImageIcon("pics/menu box.png");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);

            }
        };
        popupMenu.setOpaque(false);
        popupMenu.setBorder(null);

        // Calculate total height for 5 buttons
        int totalHeight = (BUTTON_HEIGHT * 5) + (VERTICAL_SPACING * 4) + (VERTICAL_PADDING * 2);

        // Create panel with fixed size - removed horizontal padding
        JPanel buttonPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BUTTON_WIDTH + 30, totalHeight + 35);
            }

            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        // Main Menu Button
        ImageIcon mainMenuDeselected = new ImageIcon("pics/main menu deselected.png");
        ImageIcon mainMenuSelected = new ImageIcon("pics/main menu selected.png");
        mainMenuButton = createRadioButton(mainMenuDeselected, mainMenuSelected);
        mainMenuButton.addActionListener(e -> {
            if (mainMenuButton.isSelected()) {
                Window window = SwingUtilities.getWindowAncestor(OrderCrud.this);
                SwingUtilities.invokeLater(MainFrameManager::new);
                window.dispose();
            }
        });

        // Inventory Button
        ImageIcon inventoryDeselected = new ImageIcon("pics/inventory deselected.png");
        ImageIcon inventorySelected = new ImageIcon("pics/inventory selected.png");
        inventoryButton = createRadioButton(inventoryDeselected, inventorySelected);
        inventoryButton.setSelected(true);
        inventoryButton.addActionListener(e -> {
            if (inventoryButton.isSelected()) {
                Window window = SwingUtilities.getWindowAncestor(OrderCrud.this);
                SwingUtilities.invokeLater(InventoryCRUD::new);
                window.dispose();
            }
        });

        // Order Button
        ImageIcon orderDeselected = new ImageIcon("pics/order deselected.png");
        ImageIcon orderSelected = new ImageIcon("pics/order selected.png");
        orderButton = createRadioButton(orderDeselected, orderSelected);
        orderButton.addActionListener(e -> {
            if (orderButton.isSelected()) {
                Window window = SwingUtilities.getWindowAncestor(OrderCrud.this);
                SwingUtilities.invokeLater(OrderCrud::new);
                window.dispose();
            }
        });

        // Sales Button
        ImageIcon salesDeselected = new ImageIcon("pics/sales deselected.png");
        ImageIcon salesSelected = new ImageIcon("pics/sales selected.png");
        salesButton = createRadioButton(salesDeselected, salesSelected);

        // Promotions Button
        ImageIcon promotionsDeselected = new ImageIcon("pics/promotions deselected.png");
        ImageIcon promotionsSelected = new ImageIcon("pics/promotions selected.png");
        promotionsButton = createRadioButton(promotionsDeselected, promotionsSelected);

        // Group buttons
        buttonGroup = new ButtonGroup();
        buttonGroup.add(mainMenuButton);
        buttonGroup.add(inventoryButton);
        buttonGroup.add(orderButton);
        buttonGroup.add(salesButton);
        buttonGroup.add(promotionsButton);

        // Add buttons to panel with proper spacing
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_PADDING));
        buttonPanel.add(mainMenuButton);
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
        buttonPanel.add(inventoryButton);
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
        buttonPanel.add(orderButton);
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
        buttonPanel.add(salesButton);
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
        buttonPanel.add(promotionsButton);
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_PADDING));

        // Add panel to popup menu
        popupMenu.add(buttonPanel);

        // Set popup size
        popupMenu.setPopupSize(buttonPanel.getPreferredSize());

        // Add popup menu listener
        popupMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                buttonGroup.clearSelection();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                buttonGroup.clearSelection();
            }
        });
    }

    private JRadioButton createRadioButton(ImageIcon defaultIcon, ImageIcon selectedIcon) {
        JRadioButton button = new JRadioButton(defaultIcon);
        button.setFocusPainted(false);
        button.setBorder(null);
        button.setSelectedIcon(selectedIcon);
        button.setDisabledSelectedIcon(defaultIcon);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    private void displayOrderItems(int orderId, JPanel orderItemsPanel) {
        // Clear existing components except headers
        Component[] components = orderItemsPanel.getComponents();
        for (int i = 4; i < components.length; i++) {
            orderItemsPanel.remove(components[i]);
        }

        // Get order items
        List<OrderItem> items = SQLOrderItems.getOrderItems(orderId);

        // Display items
        int yOffset = 40;
        for (OrderItem item : items) {
            JLabel mealId = new JLabel(String.valueOf(item.getMealId()));
            JLabel quantity = new JLabel(String.valueOf(item.getQuantity()));
            JLabel price = new JLabel(String.format("%.2f", item.getUnitPrice()));
            JLabel subtotal = new JLabel(String.format("%.2f", item.getSubtotal()));

            mealId.setBounds(10, yOffset, 50, 20);
            quantity.setBounds(70, yOffset, 50, 20);
            price.setBounds(130, yOffset, 50, 20);
            subtotal.setBounds(180, yOffset, 50, 20);

            orderItemsPanel.add(mealId);
            orderItemsPanel.add(quantity);
            orderItemsPanel.add(price);
            orderItemsPanel.add(subtotal);

            yOffset += 25;
        }

        orderItemsPanel.revalidate();
        orderItemsPanel.repaint();
    }
}
