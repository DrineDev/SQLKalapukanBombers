package org.example;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class NavigatorButtonInventoryEmployee extends JToggleButton {
    private JPopupMenu popupMenu;
    private JRadioButton orderButton;
    private JRadioButton inventoryButton;
    private ButtonGroup buttonGroup;

    // Constants for sizing
    private static final int BUTTON_WIDTH = 190;
    private static final int BUTTON_HEIGHT = 56;
    private static final int VERTICAL_SPACING = 2;
    private static final int VERTICAL_PADDING = 15;

    public NavigatorButtonInventoryEmployee() {
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
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventoryEmployee.this);
                if (window != null) {
                    window.addWindowFocusListener(new WindowAdapter() {
                        @Override
                        public void windowLostFocus(WindowEvent e) {
                            setSelected(false);
                            popupMenu.setVisible(false);
                        }
                    });

                    JRootPane rootPane = SwingUtilities.getRootPane(NavigatorButtonInventoryEmployee.this);
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

        // Calculate total height for 2 buttons
        int totalHeight = (BUTTON_HEIGHT * 2) + (VERTICAL_SPACING * 1) + (VERTICAL_PADDING * 2);

        // Create panel with fixed size
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

        // Inventory Button
        ImageIcon inventoryDeselected = new ImageIcon("pics/inventory deselected.png");
        ImageIcon inventorySelected = new ImageIcon("pics/inventory selected.png");
        inventoryButton = createRadioButton(inventoryDeselected, inventorySelected);
        inventoryButton.setSelected(true);
        inventoryButton.addActionListener(e -> {
            if (inventoryButton.isSelected()) {
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventoryEmployee.this);
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
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventoryEmployee.this);
                SwingUtilities.invokeLater(OrderCrud::new);
                window.dispose();
            }
        });

        // Group buttons
        buttonGroup = new ButtonGroup();
        buttonGroup.add(inventoryButton);
        buttonGroup.add(orderButton);

        // Add buttons to panel with proper spacing
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_PADDING));
        buttonPanel.add(inventoryButton);
        buttonPanel.add(Box.createVerticalStrut(VERTICAL_SPACING));
        buttonPanel.add(orderButton);
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
    public void addInventoryButtonListener(ActionListener listener) {
        inventoryButton.addActionListener(listener);
    }
}