package org.example;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class NavigatorButtonInventory extends JToggleButton {
    private JPopupMenu popupMenu;
    private JRadioButton orderButton;
    private JRadioButton inventoryButton;
    private JRadioButton mainMenuButton;
    private JRadioButton promotionsButton;
    private JRadioButton salesButton;
    private JRadioButton usersButton;
    private ButtonGroup buttonGroup;

    // Constants for sizing
    private static final int BUTTON_WIDTH = 190;
    private static final int BUTTON_HEIGHT = 56;
    private static final int VERTICAL_SPACING = 2;
    private static final int VERTICAL_PADDING = 15;

    public NavigatorButtonInventory() {
        ImageIcon menuIcon = new ImageIcon("pics/menu button.png");
        setIcon(menuIcon);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(48, 48));

        createPopupMenu();

        addActionListener(e -> {
            if (isSelected()) {
                Point p = getLocation();
                popupMenu.show(this, 0, getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventory.this);
                if (window != null) {
                    window.addWindowFocusListener(new WindowAdapter() {
                        @Override
                        public void windowLostFocus(WindowEvent e) {
                            setSelected(false);
                            popupMenu.setVisible(false);
                        }
                    });

                    JRootPane rootPane = SwingUtilities.getRootPane(NavigatorButtonInventory.this);
                    if (rootPane != null) {
                        rootPane.getLayeredPane().setLayer(popupMenu, JLayeredPane.POPUP_LAYER);
                    }

                    removeComponentListener(this);
                }
            }
        });
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

        // Calculate total height for 6 buttons
        int totalHeight = (BUTTON_HEIGHT * 6) + (VERTICAL_SPACING * 5) + (VERTICAL_PADDING * 2);

        // Create panel with fixed size - removed horizontal padding
        JPanel buttonPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BUTTON_WIDTH, totalHeight);
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
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventory.this);
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
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventory.this);
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
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventory.this);
                SwingUtilities.invokeLater(OrderCrud::new);
                window.dispose();
            }
        });

        ImageIcon userDeselected = new ImageIcon("pics/user deselected.png");
        ImageIcon userSelected = new ImageIcon("pics/user selected.png");
        usersButton = createRadioButton(userDeselected, userSelected);
        usersButton.addActionListener(e -> {
            if (usersButton.isSelected()) {
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventory.this);
                SwingUtilities.invokeLater(UserCRUD::new);
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
        buttonGroup.add(usersButton);
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
        buttonPanel.add(usersButton);
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
                setSelected(false);
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                setSelected(false);
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

    public void addOrderButtonListener(ActionListener listener) {
        orderButton.addActionListener(listener);
    }
    public void addUsersButtonListener(ActionListener listener) {usersButton.addActionListener(listener);}
    public void addInventoryButtonListener(ActionListener listener) {
        inventoryButton.addActionListener(listener);
    }
}