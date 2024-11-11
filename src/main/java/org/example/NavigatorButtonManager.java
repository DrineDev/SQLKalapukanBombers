package org.example;

import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


public class NavigatorButtonManager extends JPanel
{

    public NavigatorButtonManager()
    {
        this.setPreferredSize(new Dimension(200,350));
        this.setLayout(null);
        //this.setBackground(null);
        this.setFocusable(true);
        this.setOpaque(false);


        JPanel popupPanel = new JPanel();
        popupPanel.setBounds(0,48, 200,500);
        popupPanel.setOpaque(false);
        popupPanel.setVisible(false);

        ImageIcon popupBackground = new ImageIcon("pics/menu box manager.png");
        JLabel popupLabel = new JLabel(popupBackground);
        popupLabel.setLayout(null);
        popupLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        popupLabel.setBackground(null);
        popupLabel.setOpaque(false);
        popupPanel.add(popupLabel);


        ImageIcon mainS = new ImageIcon("pics/main menu selected.png");
        ImageIcon mainD = new ImageIcon("pics/main menu deselected.png");
        JRadioButton mainButton = new JRadioButton(mainD);
        mainButton.setBounds(15,13,190,56);
        mainButton.setFocusPainted(false);
        mainButton.setBorder(null);
        mainButton.setSelectedIcon(mainS);
        mainButton.setDisabledSelectedIcon(mainD);
        mainButton.setSelected(true);

        // orderButton.addActionListener(new ActionListener()
        // {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         if (orderButton.isSelected())
        //         {
        //             orderButton.setSelected(true);
        //         }
        //         else
        //         {
        //             //change to order crud
        //         }
        //     }
        // });

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
                    Window window = SwingUtilities.getWindowAncestor(NavigatorButtonManager.this);
                    SwingUtilities.invokeLater(InventoryCRUD::new);
                    window.dispose();
                }
                else
                {

                    //change to inventory crud
                }
            }
        });

        ImageIcon orderS = new ImageIcon("pics/order selected.png");
        ImageIcon orderD = new ImageIcon("pics/order deselected.png");
        JRadioButton orderButton = new JRadioButton(orderD);
        orderButton.setBounds(15, 129, 190, 56);
        orderButton.setFocusPainted(false);
        orderButton.setBorder(null);
        orderButton.setSelectedIcon(orderS);
        orderButton.setDisabledSelectedIcon(orderD);
        orderButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(orderButton.isSelected())
                {
                    Window window = SwingUtilities.getWindowAncestor(NavigatorButtonManager.this);
                    SwingUtilities.invokeLater(OrderCrud::new);
                    window.dispose();
                }
                else
                {
                    //do something
                }
            }
        });

        ImageIcon salesS = new ImageIcon("pics/sales selected.png");
        ImageIcon salesD = new ImageIcon("pics/sales deselected.png");
        JRadioButton salesButton = new JRadioButton(salesD);
        salesButton.setBounds(15,187,190,56);
        salesButton.setFocusPainted(false);
        salesButton.setBorder(null);
        salesButton.setSelectedIcon(salesS);
        salesButton.setDisabledSelectedIcon(salesD);
        salesButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(salesButton.isSelected())
                {
                    // DO THIS WHEN SALES CRUD IS DONE!!!!!!!!!!

                    // Window window = SwingUtilities.getWindowAncestor(NavigatorButtonManager.this);
                    // SwingUtilities.invokeLater(OrderCrud::new);
                    // window.dispose();
                }
                else
                {
                    //do something
                }
            }
        });

        ImageIcon promotionsS = new ImageIcon("pics/promotions selected.png");
        ImageIcon promotionsD = new ImageIcon("pics/promotions deselected.png");
        JRadioButton promotionsButton = new JRadioButton(promotionsD);
        promotionsButton.setBounds(15,245,190,56);
        promotionsButton.setFocusPainted(false);
        promotionsButton.setBorder(null);
        promotionsButton.setSelectedIcon(promotionsS);
        promotionsButton.setDisabledSelectedIcon(promotionsD);
        promotionsButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (promotionsButton.isSelected())
                {
                    // ALSO DO THIS AFTER PROMOTIONS CRUD IS DONE NIGGA!!!!!!!

                    // Window window = SwingUtilities.getWindowAncestor(NavigatorButtonManager.this);
                    // SwingUtilities.invokeLater(OrderCrud::new);
                    // window.dispose();
                }
                else
                {
                    //do something napud
                }
            }
        });

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
        popupLabel.add(mainButton);
        popupLabel.add(inventoryButton);
        popupLabel.add(orderButton);
        popupLabel.add(salesButton);
        popupLabel.add(promotionsButton);
        this.add(popupPanel);
        this.setVisible(true);
    }

}
