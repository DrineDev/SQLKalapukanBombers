package org.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLClientInfoException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.example.SQLQueries.SQLUser;

public class ConfirmationFrameUserCrud extends JFrame
{
    ConfirmationFrameUserCrud(UserCRUD user, String username, String password, String role)
    {
        JFrame confirmFrame = new JFrame();
        confirmFrame.setSize(300,200);
        confirmFrame.setLocationRelativeTo(null);
        confirmFrame.setUndecorated(true);
        confirmFrame.setLayout(null);

        ImageIcon bg = new ImageIcon("pics/confirmation frame.png");
        JLabel bgPanel = new JLabel(bg);
        bgPanel.setBounds(0,0,300,200);
        
        ImageIcon no = new ImageIcon("pics/no crud.png");
        JButton noButton = new JButton(no);
        noButton.setBounds(40,140,90,30);
        noButton.setBorderPainted(false);
        noButton.setContentAreaFilled(false);

        ImageIcon yes = new ImageIcon("pics/yes crud.png");
        JButton yesButton = new JButton(yes);
        yesButton.setBounds(170,140,90,30);
        yesButton.setBorderPainted(false);
        yesButton.setContentAreaFilled(false);

        noButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                confirmFrame.dispose();
            }
            
        });

        yesButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //sql here
                SQLUser.addUser(username, password, role);
                SwingUtilities.invokeLater(() -> user.refreshLogPanels());
                confirmFrame.dispose();
            }
        });

        bgPanel.add(noButton);
        bgPanel.add(yesButton);
        confirmFrame.add(bgPanel);
        confirmFrame.setVisible(true);
    } 

    ConfirmationFrameUserCrud(UserCRUD user, String id, String username, String password, String role)
    {
        JFrame confirmFrame = new JFrame();
        confirmFrame.setSize(300,200);
        confirmFrame.setLocationRelativeTo(null);
        confirmFrame.setUndecorated(true);
        confirmFrame.setLayout(null);

        ImageIcon bg = new ImageIcon("pics/confirmation frame.png");
        JLabel bgPanel = new JLabel(bg);
        bgPanel.setBounds(0,0,300,200);
        
        ImageIcon no = new ImageIcon("pics/no crud.png");
        JButton noButton = new JButton(no);
        noButton.setBounds(40,140,90,30);
        noButton.setBorderPainted(false);
        noButton.setContentAreaFilled(false);

        ImageIcon yes = new ImageIcon("pics/yes crud.png");
        JButton yesButton = new JButton(yes);
        yesButton.setBounds(170,140,90,30);
        yesButton.setBorderPainted(false);
        yesButton.setContentAreaFilled(false);

        noButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                confirmFrame.dispose();
            }
            
        });

        yesButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //sql here
                int idInput = Integer.parseInt(id);
                if(!(username.isBlank()))
                {
                    SQLUser.setUsername(idInput, username);
                }
                if (!(password.isBlank()))
                {
                    SQLUser.setPassword(idInput, password);
                }
                if (!(role.isBlank()))
                {
                    SQLUser.setRole(idInput, role);
                }
                SwingUtilities.invokeLater(() -> user.refreshLogPanels());
                confirmFrame.dispose();
            }
        });

        bgPanel.add(noButton);
        bgPanel.add(yesButton);
        confirmFrame.add(bgPanel);
        confirmFrame.setVisible(true);
    }

    ConfirmationFrameUserCrud(UserCRUD user, String id)
    {
        JFrame confirmFrame = new JFrame();
        confirmFrame.setSize(300,200);
        confirmFrame.setLocationRelativeTo(null);
        confirmFrame.setUndecorated(true);
        confirmFrame.setLayout(null);

        ImageIcon bg = new ImageIcon("pics/confirmation frame.png");
        JLabel bgPanel = new JLabel(bg);
        bgPanel.setBounds(0,0,300,200);
        
        ImageIcon no = new ImageIcon("pics/no crud.png");
        JButton noButton = new JButton(no);
        noButton.setBounds(40,140,90,30);
        noButton.setBorderPainted(false);
        noButton.setContentAreaFilled(false);

        ImageIcon yes = new ImageIcon("pics/yes crud.png");
        JButton yesButton = new JButton(yes);
        yesButton.setBounds(170,140,90,30);
        yesButton.setBorderPainted(false);
        yesButton.setContentAreaFilled(false);

        noButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                confirmFrame.dispose();
            }
                
        });

        yesButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //sql here
                int idInput = Integer.parseInt(id);
                SQLUser.deleteUser(idInput);
                SwingUtilities.invokeLater(() -> user.refreshLogPanels());
                confirmFrame.dispose();
            }
        });

        bgPanel.add(noButton);
        bgPanel.add(yesButton);
        confirmFrame.add(bgPanel);
        confirmFrame.setVisible(true);
    }

}