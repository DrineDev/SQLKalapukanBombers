package org.example;

import org.example.Classes.Order;
import org.example.Classes.OrderItem;
import org.example.SQLQueries.SQLOrder;
import org.example.SQLQueries.SQLOrderItems;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderCrud extends JFrame {
    private final JLabel confirmationTextLabel2;
    private final JLabel confirmationTextLabel;
    private final JTextField orderIdTextfield;
    private final JTextField orderDateTextfield;
    private final JTextField statusTextfield;
    private final JTextField totalAmountTextfield;
    private final JPanel warningPanel;
    private final JLabel warningText;
    private final JFrame deletePopUpFrame;
    private final JButton noButton;
    private final JButton yesButton;
    private final JPanel rightSideBottom;
    private JFrame Frame;
    private JButton exitButton, addButton, deleteButton, orderIdButton, confirmButton, cancelButton, statusButton;
    private JPanel topLeftPanel, rightSidePanel, confirmationPanel, orderItemsPanel, orderPanel, orderDatePanel, statusPanel, totalAmountPanel;
    private JLabel orderDateLabel, orderLabel, rightSideLineLabel, orderIdLabel, totalAmountLabel, statusLabel, totalAmountValue;
    private NavigatorButtonOrder navButton;
    private JScrollPane orderItemsScrollPane;
    private JRadioButton mainMenuButton, inventoryButton, orderButton, salesButton, promotionsButton;
    private JPopupMenu popupMenu;
    private ButtonGroup buttonGroup;
    private JScrollPane scrollPane;

    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_WIDTH = 150;
    private static final int VERTICAL_SPACING = 10;
    private static final int VERTICAL_PADDING = 20;
    private static final String DB_URL = "jdbc:sqlite:SQL/database.db";

    public OrderCrud() {
        //Frame Initialization
        Frame = new JFrame();
        Frame.setSize(1000, 600);
        Frame.setUndecorated(true);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setLayout(null);
        Frame.setLocationRelativeTo(null);
        Frame.getContentPane().setBackground(Color.PINK);
        exitButton = createExitButton();

        navButton = new NavigatorButtonOrder();
        navButton.setBounds(10, 10, 48, 48);

        ImageIcon orderImage = new ImageIcon("pics/Orders.png");
        orderLabel = new JLabel(orderImage);
        orderLabel.setBounds(307, 12, 51, 13);

        ImageIcon rightSideLine = new ImageIcon("pics/Line 1.png");
        rightSideLineLabel = new JLabel(rightSideLine);
        rightSideLineLabel.setBounds(24, 35, 613, 3);

        ImageIcon orderIdImage = new ImageIcon("pics/Order_ID (3).png");
        orderIdLabel = new JLabel(orderIdImage);
        orderIdLabel.setBounds(46, 45, 67, 14);

        ImageIcon orderDateImage = new ImageIcon("pics/Order_Date (2).png");
        orderDateLabel = new JLabel(orderDateImage);
        orderDateLabel.setBounds(232, 45, 84, 14);

        ImageIcon statusImage = new ImageIcon("pics/Status (2).png");
        statusLabel = new JLabel(statusImage);
        statusLabel.setBounds(427, 45, 54, 14);

        ImageIcon totalAmountImage = new ImageIcon("pics/Total_Amount (2).png");
        totalAmountLabel = new JLabel(totalAmountImage);
        totalAmountLabel.setBounds(538, 45, 97, 14);

        orderItemsScrollPane = createOrderItemsScrollPane();

        // RIGHT SIDE PANEL INIT (WITH LABELS AND PANELS)
        rightSidePanel = createRightSidePanel();

        // GET ORDERS
        List<Order> orders = SQLOrder.getAllOrders();
        int maxHeight = 419;
        for (Order order : orders) {
            // Order ID Panel
            orderIdButton = new JButton(String.valueOf(order.getOrderId()));
            orderIdButton.setBackground(Color.WHITE);
            orderIdButton.setBorderPainted(false);
            orderIdButton.setContentAreaFilled(false);
            orderIdButton.setRequestFocusEnabled(false);
            orderIdButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            orderIdButton.setMaximumSize(new Dimension(70, 20));
            orderIdButton.setPreferredSize(new Dimension(70, 20));
            orderPanel.add(orderIdButton);
            List<OrderItem> items = SQLOrderItems.getOrderItems(Integer.parseInt(String.valueOf(order.getOrderId())));

            orderIdButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Frame.add(orderItemsScrollPane);
                    displayOrderItems(Integer.parseInt(String.valueOf(order.getOrderId())), orderItemsPanel, orderItemsScrollPane);
                }
            });

            // Order Date Panel
            JLabel orderDateLabel = new JLabel(order.getOrderDate());
            orderDateLabel.setMaximumSize(new Dimension(250, 20));
            orderDateLabel.setPreferredSize(new Dimension(250, 20));
            orderDatePanel.add(orderDateLabel);
            orderDatePanel.setBorder(null);
            orderDatePanel.setBackground(Color.WHITE);

            // Status Panel
            statusButton = new JButton(order.getStatus());
            statusButton.setBackground(Color.WHITE);
            statusButton.setBorderPainted(false);
            statusButton.setContentAreaFilled(false);
            statusButton.setRequestFocusEnabled(false);
            statusButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            statusButton.setMaximumSize(new Dimension(100, 20));
            statusButton.setPreferredSize(new Dimension(100, 20));
            statusButton.addActionListener(e -> {
                // Show a popup with options 'Pending' and 'Delivered'
                String[] options = {"Pending", "Delivered"};
                int choice = JOptionPane.showOptionDialog(null,
                        "Choose Status",
                        "Select Status",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                // Handle the selected option
                if (choice == 0) {
                    SQLOrder.setStatus(order.getOrderId(), "Pending");
                } else if (choice == 1) {
                    // If 'Delivered' is selected
                    SQLOrder.setStatus(order.getOrderId(), "Delivered");
                }
                // Optionally, you can update the button text to reflect the selected status
                statusButton.setText(order.getStatus());
                Frame.dispose();
                new OrderCrud();
            });
            statusPanel.add(statusButton);

            // Total Amount Panel
            totalAmountValue = new JLabel(String.format("%.2f", order.getTotalAmount()));
            totalAmountValue.setMaximumSize(new Dimension(109, 20));
            totalAmountValue.setPreferredSize(new Dimension(109, 20));
            totalAmountValue.setFont(new Font("Inter", Font.BOLD, 14));
            totalAmountValue.setAlignmentX(Component.LEFT_ALIGNMENT);
            totalAmountPanel.add(totalAmountValue);
        }

        rightSidePanel.setPreferredSize(new Dimension(660, maxHeight + 100));

        //Add Order button for addorderpanel
        ImageIcon addButtonImage = new ImageIcon("pics/Add order.png");
        addButton = new JButton(addButtonImage);
        addButton.setPreferredSize(new Dimension(130, 40));
        addButton.setBackground(Color.pink);
        addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmationPanel.setVisible(true);
            }
        });

        // CONFIRMATION TEXTS
        ImageIcon ConfirmationPanelText1 = new ImageIcon("pics/Are you sure you want to.png");
        confirmationTextLabel = new JLabel(ConfirmationPanelText1);
        confirmationTextLabel.setBounds(52, 9, 145, 15);
        ImageIcon ConfirmationPanelText2 = new ImageIcon("pics/you to go to the Main Menu_.png");
        confirmationTextLabel2 = new JLabel(ConfirmationPanelText2);
        confirmationTextLabel2.setBounds(27, 30, 195, 15);

        // CANCEL BUTTON
        ImageIcon CancelImageIcon = new ImageIcon("pics/Group 1.png");
        cancelButton = new JButton(CancelImageIcon);
        cancelButton.setBounds(61, 123, 120, 32);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmationPanel.setVisible(false);
            }
        });

        // CONFIRM BUTTON
        ImageIcon ConfirmImageIcon = new ImageIcon("pics/Group 3.png");
        confirmButton = new JButton(ConfirmImageIcon);
        confirmButton.setBounds(61, 72, 120, 30);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setBorderPainted(false);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(MainFrameManager::new);
                Frame.dispose();
            }
        });

        // CONFIRMATION PANEL
        confirmationPanel = createConfirmationPanel();

        orderIdTextfield = new JTextField();
        orderIdTextfield.setBounds(22, 24, 195, 26);
        orderIdTextfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        orderDateTextfield = new JTextField();
        orderDateTextfield.setBounds(22, 75, 195, 26);
        orderDateTextfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        statusTextfield = new JTextField();
        statusTextfield.setBounds(22, 126, 195, 26);
        statusTextfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        totalAmountTextfield = new JTextField();
        totalAmountTextfield.setBounds(22, 177, 195, 26);
        totalAmountTextfield.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        JTextField deleteIdTextfield = new JTextField();
        deleteIdTextfield.setFont(new Font("Arial", Font.PLAIN, 12));
        deleteIdTextfield.setBounds(55, 80, 190, 30); // Set explicit bounds for the text field
        deleteIdTextfield.setBackground(Color.WHITE);
        deleteIdTextfield.setBorder(BorderFactory.createLineBorder(Color.PINK,2,false));

        JLabel DeleteidTextfieldLabel = new JLabel();
        DeleteidTextfieldLabel.setBounds(55, 32, 190, 32);
        DeleteidTextfieldLabel.setBackground(Color.WHITE);

        // WARNING PANEL
        ImageIcon WarningTextImage = new ImageIcon("pics/warningtext.png");
        warningText = new JLabel(WarningTextImage);
        warningText.setBounds(55, 32, 190, 32);
        warningPanel = new JPanel();
        warningPanel.setBackground(Color.WHITE);
        warningPanel.setBounds(300, 45, 660, 620);
        warningPanel.setBorder(new LineBorder(new Color(208, 108, 108, 255)));
        warningPanel.setLayout(null);
        warningPanel.add(DeleteidTextfieldLabel);
        warningPanel.add(deleteIdTextfield);
        warningPanel.add(warningText);

        ImageIcon DeleteButtonImage = new ImageIcon("pics/delete order.png");
        deleteButton = new JButton(DeleteButtonImage);
        deleteButton.setPreferredSize(new Dimension(130, 40));
        deleteButton.setBackground(Color.red);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setBorderPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletePopUpFrame.setVisible(true);
                warningPanel.add(noButton);
                warningPanel.add(yesButton);
            }
        });

        // DELETE POP UP FRAME
        deletePopUpFrame = new JFrame();
        deletePopUpFrame.setBounds(300, 45, 300, 200);
        deletePopUpFrame.setUndecorated(true);
        deletePopUpFrame.setLocationRelativeTo(null);
        deletePopUpFrame.add(warningPanel);

        // NO BUTTON
        ImageIcon NoImage = new ImageIcon("pics/no.png");
        noButton = new JButton(NoImage);
        noButton.setContentAreaFilled(false);
        noButton.setBorderPainted(false);
        noButton.setBounds(40, 129, 90, 30);
        noButton.addActionListener(e -> deletePopUpFrame.setVisible(false));

        ImageIcon YesImage = new ImageIcon("pics/yes.png");
        yesButton = new JButton(YesImage);
        yesButton.setContentAreaFilled(false);
        yesButton.setBorderPainted(false);
        yesButton.setBounds(170, 129, 90, 30);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int OrderId = Integer.parseInt(deleteIdTextfield.getText());

                SQLOrder.deleteOrder(OrderId);
                deletePopUpFrame.setVisible(false);

                Frame.dispose();
                new OrderCrud();
            }
        });

        topLeftPanel = createTopLeftPanel();
        rightSideBottom = new JPanel();
        rightSideBottom.setLayout(new BoxLayout(rightSideBottom, BoxLayout.Y_AXIS));

        // Create and initialize the MenuButton
        createPopupMenu();
        mainMenuButton.addActionListener(e -> {
            if (mainMenuButton.isSelected()) {
                Window window = SwingUtilities.getWindowAncestor(OrderCrud.this);
                SwingUtilities.invokeLater(MainFrameManager::new);
                window.dispose();
            }
        });

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

        Frame.add(navButton);
        Frame.add(confirmationPanel);
        Frame.add(exitButton);
        Frame.add(rightSideBottom);
        Frame.add(topLeftPanel);
        Frame.add(rightSidePanel);
        Frame.setVisible(true);
    }

    private JPanel createConfirmationPanel() {
        JPanel confirmationPanelTemp = new JPanel();
        confirmationPanelTemp.setLayout(null);
        confirmationPanelTemp.setBounds(505, 235, 250, 200);
        confirmationPanelTemp.setBorder(new LineBorder(Color.PINK, 2, false));
        confirmationPanelTemp.add(cancelButton);
        confirmationPanelTemp.add(confirmButton);
        confirmationPanelTemp.add(confirmationTextLabel);
        confirmationPanelTemp.add(confirmationTextLabel2);
        confirmationPanelTemp.setVisible(false);

        return confirmationPanelTemp;
    }

    private JPanel createTopLeftPanel() {
        JPanel TopLeftPanel = new JPanel();
        TopLeftPanel.setBounds(33, 54, 240, 204);
        TopLeftPanel.setBackground(Color.WHITE);
        TopLeftPanel.setLayout(new BoxLayout(TopLeftPanel, BoxLayout.Y_AXIS));
        TopLeftPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));
        TopLeftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        TopLeftPanel.add(addButton);
        TopLeftPanel.add(deleteButton);

        return TopLeftPanel;
    }


    private JPanel createRightSidePanel() {
        // Initialize the panels with their layouts
        orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        orderPanel.setBackground(Color.WHITE);

        orderDatePanel = new JPanel();
        orderDatePanel.setLayout(new BoxLayout(orderDatePanel, BoxLayout.Y_AXIS));
        orderDatePanel.setBackground(Color.WHITE);

        statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBackground(Color.WHITE);

        totalAmountPanel = new JPanel();
        totalAmountPanel.setLayout(new BoxLayout(totalAmountPanel, BoxLayout.Y_AXIS));
        totalAmountPanel.setBackground(Color.WHITE);

        // Create a container panel for the scrollable content
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(null); // Using absolute positioning
        scrollContent.setBackground(Color.WHITE);

        // Add panels to scroll content with bounds
        scrollContent.add(orderPanel);
        scrollContent.add(orderDatePanel);
        scrollContent.add(statusPanel);
        scrollContent.add(totalAmountPanel);

        // Set bounds for the panels
        orderPanel.setBounds(24, 0, 80, 500);
        orderDatePanel.setBounds(135, 0, 250, 500);
        statusPanel.setBounds(400, 0, 100, 500);
        totalAmountPanel.setBounds(527, 0, 109, 500);

        // Important: Set preferred size for scroll content
        scrollContent.setPreferredSize(new Dimension(660, 500));

        // Create the scroll pane
        scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBounds(0, 65, 660, 455); // Adjusted to account for header space
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());

        // Create the main right-side panel
        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(null);
        rightSidePanel.setBounds(302, 45, 660, 520);
        rightSidePanel.setBackground(Color.WHITE);

        // Add header labels (uncommented and adjusted positions)
//        orderLabel.setBounds(307, 12, 51, 13);
//        rightSideLineLabel.setBounds(24, 35, 613, 3);
//        orderIdLabel.setBounds(46, 45, 67, 14);
//        orderDateLabel.setBounds(232, 45, 84, 14);
//        statusLabel.setBounds(427, 45, 54, 14);
//        totalAmountLabel.setBounds(538, 45, 97, 14);

        // Add components to the rightSidePanel
        rightSidePanel.add(orderLabel);
        rightSidePanel.add(rightSideLineLabel);
        rightSidePanel.add(orderIdLabel);
        rightSidePanel.add(orderDateLabel);
        rightSidePanel.add(statusLabel);
        rightSidePanel.add(totalAmountLabel);
        rightSidePanel.add(scrollPane);

        return rightSidePanel;
    }

    private JButton createExitButton() {
        ImageIcon ExitImageIcon = new ImageIcon("pics/exit button.png");
        JButton exitButton = new JButton();
        exitButton.setIcon(ExitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setBounds(962, 15, 20, 20);
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setVisible(true);

        return exitButton;
    }

    private JScrollPane createOrderItemsScrollPane() {
        orderItemsPanel = new JPanel();
        orderItemsPanel.setLayout(null);
        orderItemsPanel.setBackground(Color.WHITE);

        JLabel mealIdHeader = new JLabel("Meal ID");
        JLabel quantityHeader = new JLabel("Quantity");
        JLabel priceHeader = new JLabel("Price");
        JLabel subtotalHeader = new JLabel("Subtotal");

        mealIdHeader.setBounds(10, 10, 50, 20);
        quantityHeader.setBounds(70, 10, 50, 20);
        priceHeader.setBounds(130, 10, 50, 20);
        subtotalHeader.setBounds(180, 10, 50, 20);

        orderItemsPanel.add(mealIdHeader);
        orderItemsPanel.add(quantityHeader);
        orderItemsPanel.add(priceHeader);
        orderItemsPanel.add(subtotalHeader);

        JScrollPane scrollPane = new JScrollPane(orderItemsPanel);
        scrollPane.setBounds(33, 267, 240, 290); // Adjust bounds for the scroll pane
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        return scrollPane;
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
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

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

    private void displayScrollPane() {

    }

    private void displayOrderItems(int orderId, JPanel orderItemsPanel, JScrollPane scrollPane) {
        // Clear existing components except headers
        Component[] components = orderItemsPanel.getComponents();
        for (int i = 4; i < components.length; i++) {
            orderItemsPanel.remove(components[i]);
        }

        // Get order items
        List<OrderItem> items = SQLOrderItems.getOrderItems(orderId);

        // Store updated quantities
        Map<Integer, Integer> updatedQuantities = new HashMap<>();

        // Display items
        int yOffset = 40;
        for (OrderItem item : items) {
            JLabel mealId = new JLabel(String.valueOf(item.getMealId()));
            JTextField quantity = new JTextField(String.valueOf(item.getQuantity()));
            JLabel price = new JLabel(String.format("%.2f", item.getUnitPrice()));
            JLabel subtotal = new JLabel(String.format("%.2f", item.getSubtotal()));

            // Track initial quantity
            updatedQuantities.put(item.getMealId(), item.getQuantity());

            mealId.setBounds(10, yOffset, 50, 20);
            quantity.setBounds(70, yOffset, 50, 20);
            price.setBounds(130, yOffset, 50, 20);
            subtotal.setBounds(180, yOffset, 50, 20);

            // Add a DocumentListener to track changes in real-time
            quantity.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateQuantity();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateQuantity();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateQuantity();
                }

                private void updateQuantity() {
                    try {
                        int newQuantity = Integer.parseInt(quantity.getText());
                        updatedQuantities.put(item.getMealId(), newQuantity);
                    } catch (NumberFormatException ex) {
                        // Handle invalid input gracefully
                        updatedQuantities.put(item.getMealId(), 0);
                    }
                }
            });

            orderItemsPanel.add(mealId);
            orderItemsPanel.add(quantity);
            orderItemsPanel.add(price);
            orderItemsPanel.add(subtotal);

            yOffset += 25;
        }

        // Add the single Confirm button at the bottom
        JButton confirmEdit = new JButton("Confirm");
        confirmEdit.setBounds(10, yOffset + 10, 100, 30);
        confirmEdit.setBackground(Color.PINK);
        confirmEdit.setForeground(Color.WHITE);

        confirmEdit.addActionListener(e -> {
            for (OrderItem item : items) {
                int mealId = item.getMealId();
                int newQuantity = updatedQuantities.getOrDefault(mealId, item.getQuantity());

                if (newQuantity == 0) {
                    SQLOrderItems.deleteOrderItem(item.getOrderItemId());
                } else if (newQuantity != item.getQuantity()) {
                    SQLOrderItems.updateMealQuantity(orderId, mealId, newQuantity);
                }
            }

            // Recreate the entire frame or trigger a full refresh
            SwingUtilities.invokeLater(() -> {
                Frame.dispose();
                new OrderCrud();
            });
        });

        orderItemsPanel.add(confirmEdit);

        // Adjust panel size for scroll
        orderItemsPanel.setPreferredSize(new Dimension(orderItemsPanel.getWidth(), yOffset + 60));
        scrollPane.revalidate();
        scrollPane.repaint();
    }
}
