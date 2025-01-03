package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class NavigatorButtonEmployee extends JPanel
{

    public NavigatorButtonEmployee()
    {
        this.setPreferredSize(new Dimension(200,175));
        this.setLayout(null);
        //this.setBackground(null);
        this.setFocusable(true);
        this.setOpaque(false);


        JPanel popupPanel = new JPanel();
        popupPanel.setBounds(0,48, 200,148);
        popupPanel.setOpaque(false);
        popupPanel.setVisible(false);

        ImageIcon popupBackground = new ImageIcon("pics/menu box.png");
        JLabel popupLabel = new JLabel(popupBackground);
        popupLabel.setLayout(null);
        popupLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        popupLabel.setBackground(null);
        popupLabel.setOpaque(false);
        popupPanel.add(popupLabel);


        ImageIcon mainMenuS = new ImageIcon("pics/main menu selected.png");
        ImageIcon mainMenuD = new ImageIcon("pics/main menu deselected.png");
        JRadioButton mainMenuButton = new JRadioButton(mainMenuD);
        mainMenuButton.setBounds(15,13,190,56);
        mainMenuButton.setFocusPainted(false);
        mainMenuButton.setBorder(null);
        mainMenuButton.setSelectedIcon(mainMenuS);
        mainMenuButton.setDisabledSelectedIcon(mainMenuD);
        mainMenuButton.setSelected(true);

        mainMenuButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainMenuButton.isSelected())
                {
                    mainMenuButton.setSelected(true);
                }
                else
                {
                    //change to order crud
                }
            }
        });

        ImageIcon inventoryS = new ImageIcon("pics/inventory selected.png");
        ImageIcon inventoryD = new ImageIcon("pics/inventory deselected.png");
        JRadioButton inventoryButton = new JRadioButton(inventoryD);
        inventoryButton.setBounds(15,71, 190,56);
        inventoryButton.setFocusPainted(false);
        inventoryButton.setBorder(null);
        inventoryButton.setSelectedIcon(inventoryS);
        inventoryButton.setDisabledSelectedIcon(inventoryD);
        inventoryButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (inventoryButton.isSelected())
                {
                    Window window = SwingUtilities.getWindowAncestor(NavigatorButtonEmployee.this);
                    SwingUtilities.invokeLater(InventoryCRUDEmployee::new);
                    window.dispose();
                }
                else
                {

                    //change to inventory crud
                }
            }
        });

        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(mainMenuButton);
        bGroup.add(inventoryButton);

        ImageIcon menuIcon = new ImageIcon("pics/menu button.png");
        JCheckBox menuButton = new JCheckBox(menuIcon);
        menuButton.setBounds(0, 0, 48,48);
        menuButton.setBorder(null);
        menuButton.setFocusPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (menuButton.isSelected()) {
                    popupPanel.setVisible(true);
                } else {
                    popupPanel.setVisible(false);
                }
            }
        });

        //add everything
        this.add(menuButton);
        popupLabel.add(mainMenuButton);
        popupLabel.add(inventoryButton);
        this.add(popupPanel);
        this.setVisible(true);
    }

}
