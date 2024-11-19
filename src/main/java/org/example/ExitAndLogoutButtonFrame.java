package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

public class ExitAndLogoutButtonFrame extends JFrame
{
    ExitAndLogoutButtonFrame(JFrame parent)
    {
        this.setSize(250,200);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ImageIcon bgPic = new ImageIcon("pics/logout or exit___.png");
        JLabel bg = new JLabel(bgPic);
        bg.setIcon(bgPic);
        bg.setBounds(0,0,250,200);
        bg.setLayout(null);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(250,200));
        panel.setLayout(null);

        ImageIcon logout = new ImageIcon("pics/logout button.png");
        JButton logoutButton = new JButton(logout);
        logoutButton.setBounds(65,55,120,30);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setBorderPainted(false);

        ImageIcon exit = new ImageIcon("pics/exit button real.png");
        JButton exitButton = new JButton(exit);
        exitButton.setBounds(65,100,120,30);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);

        ImageIcon cancel = new ImageIcon("pics/cancel button.png");
        JButton cancelButton = new JButton(cancel);
        cancelButton.setBounds(65,145,120,30);
        cancelButton.setContentAreaFilled(false);
        cancelButton.setBorderPainted(false);

        logoutButton.addActionListener(new ActionListener()
        {
//            @Override
//            public void actionPerformed(ActionEvent e)
//            {
//                SwingUtilities.invokeLater(LoginPage::new);
//                ExitAndLogoutButtonFrame.this.dispose();
//                Window window = SwingUtilities.windowForComponent(parent);
//                window.dispose();
//            }
            @Override
            public void actionPerformed(ActionEvent e) {
                // Disable all components first
                parent.setEnabled(false);
                ExitAndLogoutButtonFrame.this.setEnabled(false);

                // Remove all components from parent
                parent.getContentPane().removeAll();
                parent.setVisible(false);
                parent.dispose();

                ExitAndLogoutButtonFrame.this.dispose();

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new LoginPage();
                    }
                });
            }
        });

        exitButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ExitAndLogoutButtonFrame.this.dispose();
                System.exit(0);
            }
        });

        cancelButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ExitAndLogoutButtonFrame.this.dispose();
            }
        });
        panel.add(logoutButton);
        panel.add(exitButton);
        panel.add(cancelButton);
        panel.add(bg);

        this.add(panel);
        this.setVisible(true);
    }
}
