package org.example;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.*;

public class NavigatorButtonInventory extends JToggleButton {
    private JPopupMenu popupMenu;
    private JRadioButton orderButton;
    private JRadioButton inventoryButton;
    private ButtonGroup buttonGroup;

    // Constants for sizing
    private static final int BUTTON_WIDTH = 190;
    private static final int BUTTON_HEIGHT = 56;
    private static final int VERTICAL_SPACING = 2; // Space between buttons
    private static final int PADDING = 15; // Padding from edge of popup to buttons

    public NavigatorButtonInventory() {
        // Initialize the main menu button
        ImageIcon menuIcon = new ImageIcon("pics/menu button.png");
        setIcon(menuIcon);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(48, 48));

        // himuon ang actual pop up menu naay function anis ubos
        createPopupMenu();

        // action listener para ig click sa katong menu button mu pop up ang inventory ug order
        addActionListener(e -> {
            if (isSelected()) {
                Point p = getLocation();
                popupMenu.show(this, 0, getHeight());
            } else {
                popupMenu.setVisible(false);
            }
        });

        // Component listener sa pop up menu
        addComponentListener(new ComponentAdapter() {
            //kani if gi click na ang menu button aka nag show na ang katung order ug inventory
            @Override
            public void componentShown(ComponentEvent e) {
                Window window = SwingUtilities.getWindowAncestor(NavigatorButtonInventory.this);
                if (window != null) {
                    // window focus listener ni siya kung nag show na ang pop up niya
                    // if mu click ka sa gawas mu despawn siya
                    window.addWindowFocusListener(new WindowAdapter() {
                        @Override
                        public void windowLostFocus(WindowEvent e) {
                            setSelected(false);
                            popupMenu.setVisible(false);
                        }
                    });

                    // Ang gamit ani kay kwaon ang rootpane para ma display
                    // babaw sa other components ang pop up menu aka di matabunan
                    JRootPane rootPane = SwingUtilities.getRootPane(NavigatorButtonInventory.this);
                    if (rootPane != null) {
                        rootPane.getLayeredPane().setLayer(popupMenu, JLayeredPane.POPUP_LAYER);
                    }

                    // Remove this listener since we only need to set up once
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

        // Calculate total height needed for the panel
        int totalHeight = (BUTTON_HEIGHT * 2) + VERTICAL_SPACING + (PADDING * 2);

        // Panel nga mu hold sa buttons katong order ug inventory
        JPanel buttonPanel = new JPanel(null) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(BUTTON_WIDTH + (PADDING * 2), totalHeight);
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
        buttonPanel.setOpaque(false);

        // Create order button radio siya para di ma click ang order ug inventory same time so either or ra
        ImageIcon orderDeselected = new ImageIcon("pics/order deselected.png");
        ImageIcon orderSelected = new ImageIcon("pics/order selected.png");
        orderButton = createRadioButton(orderDeselected, orderSelected);
        orderButton.setBounds(PADDING, PADDING, BUTTON_WIDTH, BUTTON_HEIGHT);


        // Create inventory button radio siya para di ma click ang order ug inventory same time so either or ra
        ImageIcon inventoryDeselected = new ImageIcon("pics/inventory deselected.png");
        ImageIcon inventorySelected = new ImageIcon("pics/inventory selected.png");
        inventoryButton = createRadioButton(inventoryDeselected, inventorySelected);
        inventoryButton.setBounds(PADDING, PADDING + BUTTON_HEIGHT + VERTICAL_SPACING,
                BUTTON_WIDTH, BUTTON_HEIGHT);
        inventoryButton.setSelected(true);

        // e group ang duha ka buttons
        buttonGroup = new ButtonGroup();
        buttonGroup.add(orderButton);
        buttonGroup.add(inventoryButton);

        // Add action listeners
        orderButton.addActionListener(e -> {
            if (orderButton.isSelected()) {
                popupMenu.setVisible(false);
                setSelected(false);
            }
        });

        inventoryButton.addActionListener(e -> {
            if (inventoryButton.isSelected()) {
                popupMenu.setVisible(false);
                setSelected(false);
            }
        });

        // Add buttons to panel
        buttonPanel.add(orderButton);
        buttonPanel.add(inventoryButton);

        // Add panel to popup menu
        popupMenu.add(buttonPanel);

        // Force the popup menu to be exactly the size we want
        popupMenu.setPopupSize(buttonPanel.getPreferredSize());

        // Add popup menu listener to handle button state
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
    //function muhimo sa radio button 2 ka parameters ang pic nga default ug pic if e select ang icon
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

    //function imo tawagon para makaedit kas action listener sa order button
    // wa pa koy order so wala pani
    public void addOrderButtonListener(ActionListener listener) {
        orderButton.addActionListener(listener);
    }

    //function imo tawagon para makaedit kas action listener sa inventory button
    // since inventory man ko so wala ray mahitabo kay naa naman kas inventory window daan
    public void addInventoryButtonListener(ActionListener listener) {
        inventoryButton.addActionListener(listener);
    }
}
