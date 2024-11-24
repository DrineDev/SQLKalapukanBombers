package org.example;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NavigatorButtonManager extends JPanel {
    JToggleButton mainButton;
    JToggleButton inventoryButton;
    JToggleButton orderButton;
    JToggleButton userButton;
    JToggleButton salesButton;
    JToggleButton promotionsButton;
    private boolean isProcessingChange = false;

    NavigatorButtonManager(String whereAmI) {
        this.setPreferredSize(new Dimension(200, 420));
        this.setLayout(null);
        this.setFocusable(true);
        this.setOpaque(false);

        JPanel popupPanel = new JPanel();
        popupPanel.setBounds(0, 48, 200, 500);
        popupPanel.setOpaque(false);
        popupPanel.setVisible(false);

        ImageIcon popupBackground = new ImageIcon("pics/menu box manager.png");
        JLabel popupLabel = new JLabel(popupBackground);
        popupLabel.setLayout(null);
        popupLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        popupLabel.setBackground(null);
        popupLabel.setOpaque(false);
        popupPanel.add(popupLabel);

        // Main Button
        ImageIcon mainS = new ImageIcon("pics/main menu selected.png");
        ImageIcon mainD = new ImageIcon("pics/main menu deselected.png");
        mainButton = new JToggleButton(mainD);
        mainButton.setBounds(15, 13, 190, 56);
        setupButtonStyle(mainButton, mainS, mainD);
        mainButton.addActionListener(e -> handleButtonClick("MainMenu", MainFrameManager::new));

        // Inventory Button
        ImageIcon inventoryS = new ImageIcon("pics/inventory selected.png");
        ImageIcon inventoryD = new ImageIcon("pics/inventory deselected.png");
        inventoryButton = new JToggleButton(inventoryD);
        inventoryButton.setBounds(15, 71, 190, 56);
        setupButtonStyle(inventoryButton, inventoryS, inventoryD);
        inventoryButton.addActionListener(e -> handleButtonClick("InventoryCRUD", InventoryCRUD::new));

        // Order Button
        ImageIcon orderS = new ImageIcon("pics/order selected.png");
        ImageIcon orderD = new ImageIcon("pics/order deselected.png");
        orderButton = new JToggleButton(orderD);
        orderButton.setBounds(15, 129, 190, 56);
        setupButtonStyle(orderButton, orderS, orderD);
        orderButton.addActionListener(e -> handleButtonClick("OrderCRUD", () -> {
            // Uncomment when OrderCRUD is ready
            // new OrderCRUD();
        }));

        // User Button
        ImageIcon userS = new ImageIcon("pics/user selected.png");
        ImageIcon userD = new ImageIcon("pics/user deselected.png");
        userButton = new JToggleButton(userD);
        userButton.setBounds(15, 187, 190, 56);
        setupButtonStyle(userButton, userS, userD);
        userButton.addActionListener(e -> handleButtonClick("UserCrud", UserCRUD::new));

        // Sales Button
        ImageIcon salesS = new ImageIcon("pics/sales selected.png");
        ImageIcon salesD = new ImageIcon("pics/sales deselected.png");
        salesButton = new JToggleButton(salesD);
        salesButton.setBounds(15, 245, 190, 56);
        setupButtonStyle(salesButton, salesS, salesD);
        salesButton.addActionListener(e -> handleButtonClick("SalesCRUD", () -> {
            // Uncomment when SalesCRUD is ready
            // new SalesCRUD();
        }));

        // Promotions Button
        ImageIcon promotionsS = new ImageIcon("pics/promotions selected.png");
        ImageIcon promotionsD = new ImageIcon("pics/promotions deselected.png");
        promotionsButton = new JToggleButton(promotionsD);
        promotionsButton.setBounds(15, 303, 190, 56);
        setupButtonStyle(promotionsButton, promotionsS, promotionsD);
        promotionsButton.addActionListener(e -> handleButtonClick("PromotionsCRUD", () -> {
            // Uncomment when PromotionsCRUD is ready
            // new PromotionsCRUD();
        }));

        // Menu Button
        ImageIcon menuIcon = new ImageIcon("pics/menu button.png");
        JCheckBox menuButton = new JCheckBox(menuIcon);
        menuButton.setBounds(0, 0, 48, 48);
        menuButton.setBorder(null);
        menuButton.setFocusPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupPanel.setVisible(menuButton.isSelected());
            }
        });

        // Set initial button states
        setInitialButtonState(whereAmI);

        // Add components
        this.add(menuButton);
        popupLabel.add(mainButton);
        popupLabel.add(inventoryButton);
        popupLabel.add(orderButton);
        popupLabel.add(userButton);
        popupLabel.add(salesButton);
        popupLabel.add(promotionsButton);
        this.add(popupPanel);
        this.setVisible(true);
    }

    private void setInitialButtonState(String whereAmI) {
        mainButton.setSelected(whereAmI.equals("MainMenu"));
        inventoryButton.setSelected(whereAmI.equals("InventoryCRUD"));
        orderButton.setSelected(whereAmI.equals("OrderCRUD"));
        userButton.setSelected(whereAmI.equals("UserCrud"));
        salesButton.setSelected(whereAmI.equals("SalesCRUD"));
        promotionsButton.setSelected(whereAmI.equals("PromotionsCRUD"));
    }

    private void setupButtonStyle(JToggleButton button, ImageIcon selectedIcon, ImageIcon deselectedIcon) {
        button.setFocusPainted(false);
        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setSelectedIcon(selectedIcon);
        button.setDisabledSelectedIcon(deselectedIcon);
    }

    private void handleButtonClick(String targetLocation, Runnable frameConstructor) {
        if (isProcessingChange) return;
        isProcessingChange = true;

        Window currentWindow = SwingUtilities.getWindowAncestor(this);
        if (currentWindow != null) {
            // Create new frame first
            SwingUtilities.invokeLater(() -> {
                try {
                    frameConstructor.run();
                    // Dispose the old window after the new one is created
                    currentWindow.dispose();
                } finally {
                    isProcessingChange = false;
                }
            });
        } else {
            isProcessingChange = false;
        }
    }
}