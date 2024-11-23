package org.example;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.example.SQLQueries.SQLUser;

public class UserCRUD extends JFrame {
    public static final String DB_URL = "jdbc:sqlite:SQL/database.db";
    private JLabel crudBigArea;
    JPanel userIDLogPanel;
    JPanel usernameLogPanel;
    JPanel passwordLogPanel;
    JPanel roleLogPanel;
    JScrollPane logScrollPane;

    public UserCRUD() {
        this.setSize(new Dimension(1000, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setLayout(null);

        // Background panel
        JPanel bg = new JPanel();
        bg.setBounds(0, 0, 1000, 600);
        bg.setBackground(new Color(248, 146, 137, 255));

        // Left side CRUD box
        ImageIcon crudBG = new ImageIcon("pics/crud box.png");
        JLabel crudLabel = new JLabel(crudBG);
        crudLabel.setBounds(33, 35, 240, 250);
        crudLabel.setLayout(null);

        // Add user section
        setupAddUserSection();

        // Edit user section
        setupEditUserSection();

        // Delete user section
        setupDeleteUserSection();

        // CRUD Buttons
        setupCRUDButtons(crudLabel);

        // Logging area (right side)
        ImageIcon crudBig = new ImageIcon("pics/crud big box.png");
        crudBigArea = new JLabel(crudBig);
        crudBigArea.setBounds(300, 45, 660, 520);
        crudBigArea.setLayout(null);

        // Setup logging area
        setupLoggingArea();

        // Navigation buttons
        NavigatorButtonManager navButton = new NavigatorButtonManager();
        navButton.setBounds(5, 5, 206, 360);

        // Exit button
        setupExitButton();

        this.add(navButton);
        this.add(crudLabel);
        this.add(crudBottomAddUser);
        this.add(crudBottomEditUser);
        this.add(crudBottomDeleteUser);
        this.add(crudBigArea);
        this.add(exitButton);
        this.add(bg);
        this.setVisible(true);
    }

    private JLabel crudBottomAddUser;
    private JLabel crudBottomEditUser;
    private JLabel crudBottomDeleteUser;
    private JButton exitButton;

    private void setupAddUserSection() {
        ImageIcon leftBottom = new ImageIcon("pics/crud bottom box.png");
        crudBottomAddUser = new JLabel(leftBottom);
        crudBottomAddUser.setBounds(33, 260, 240, 320);
        crudBottomAddUser.setVisible(false);
        crudBottomAddUser.setLayout(null);

        JTextField usernameAdd = new JTextField();
        usernameAdd.setBounds(23, 40, 195, 26);
        JLabel username1 = new JLabel("Username");
        username1.setBounds(90, 70, 100, 11);

        JTextField passwordAdd = new JTextField();
        passwordAdd.setBounds(23, 100, 195, 26);
        JLabel password1 = new JLabel("Password");
        password1.setBounds(90, 130, 120, 11);

        JTextField roleAdd = new JTextField();
        roleAdd.setBounds(23, 160, 195, 26);
        JLabel role1 = new JLabel("Role");
        role1.setBounds(105, 190, 100, 11);

        ImageIcon adduserPop = new ImageIcon("pics/add user button crud.png");
        JCheckBox addUserButtonPop = new JCheckBox(adduserPop);
        addUserButtonPop.setBounds(65, 250, 110, 30);
        addUserButtonPop.setContentAreaFilled(false);
        addUserButtonPop.setBorderPainted(false);
        addUserButtonPop.addItemListener(createAddUserListener(usernameAdd, passwordAdd, roleAdd));

        crudBottomAddUser.add(usernameAdd);
        crudBottomAddUser.add(username1);
        crudBottomAddUser.add(passwordAdd);
        crudBottomAddUser.add(password1);
        crudBottomAddUser.add(roleAdd);
        crudBottomAddUser.add(role1);
        crudBottomAddUser.add(addUserButtonPop);
    }

    private void setupEditUserSection() {
        ImageIcon leftBottom = new ImageIcon("pics/crud bottom box.png");
        crudBottomEditUser = new JLabel(leftBottom);
        crudBottomEditUser.setBounds(33, 260, 240, 320);
        crudBottomEditUser.setVisible(false);
        crudBottomEditUser.setLayout(null);

        JTextField userID = new JTextField();
        userID.setBounds(23, 40, 195, 26);
        JLabel ID = new JLabel("User_ID*");
        ID.setBounds(95, 70, 100, 11);

        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(23, 90, 195, 26);
        JLabel username2 = new JLabel("Username");
        username2.setBounds(90, 120, 120, 11);

        JTextField passwordTextField = new JTextField();
        passwordTextField.setBounds(23, 140, 195, 26);
        JLabel password2 = new JLabel("Password");
        password2.setBounds(90, 170, 100, 11);

        JTextField roleTextField = new JTextField();
        roleTextField.setBounds(23, 190, 195, 26);
        JLabel role2 = new JLabel("Role");
        role2.setBounds(105, 220, 100, 11);

        ImageIcon edituserPop = new ImageIcon("pics/edit user button crud.png");
        JCheckBox editUserButtonPop = new JCheckBox(edituserPop);
        editUserButtonPop.setBounds(65, 250, 110, 35);
        editUserButtonPop.setContentAreaFilled(false);
        editUserButtonPop.setBorderPainted(false);
        editUserButtonPop.addItemListener(createEditUserListener(userID, usernameTextField, passwordTextField, roleTextField));

        crudBottomEditUser.add(userID);
        crudBottomEditUser.add(ID);
        crudBottomEditUser.add(usernameTextField);
        crudBottomEditUser.add(username2);
        crudBottomEditUser.add(passwordTextField);
        crudBottomEditUser.add(password2);
        crudBottomEditUser.add(roleTextField);
        crudBottomEditUser.add(role2);
        crudBottomEditUser.add(editUserButtonPop);
    }

    private void setupDeleteUserSection() {
        ImageIcon deleteBG = new ImageIcon("pics/crud bottom area small.png");
        crudBottomDeleteUser = new JLabel(deleteBG);
        crudBottomDeleteUser.setBounds(33, 290, 240, 150);
        crudBottomDeleteUser.setVisible(false);
        crudBottomDeleteUser.setLayout(null);

        JTextField userID2 = new JTextField();
        userID2.setBounds(23, 40, 195, 26);
        JLabel ID2 = new JLabel("User_ID");
        ID2.setBounds(96, 70, 100, 11);

        ImageIcon delete = new ImageIcon("pics/delete user button crud.png");
        JCheckBox deleteButtonPop = new JCheckBox(delete);
        deleteButtonPop.setBounds(65, 100, 110, 30);
        deleteButtonPop.setContentAreaFilled(false);
        deleteButtonPop.setBorderPainted(false);
        deleteButtonPop.addItemListener(createDeleteUserListener(userID2));

        crudBottomDeleteUser.add(userID2);
        crudBottomDeleteUser.add(ID2);
        crudBottomDeleteUser.add(deleteButtonPop);
    }

    private void setupCRUDButtons(JLabel crudLabel) {
        ImageIcon addUser = new ImageIcon("pics/add user.png");
        ImageIcon addSelected = new ImageIcon("pics/add user selected.png");
        JCheckBox addUserButton = new JCheckBox(addUser);
        addUserButton.setBorderPainted(false);
        addUserButton.setContentAreaFilled(false);
        addUserButton.setBounds(50, 65, 135, 35);
        addUserButton.setSelectedIcon(addSelected);

        ImageIcon updateUser = new ImageIcon("pics/edit user .png");
        ImageIcon updateSelected = new ImageIcon("pics/edit user sselected.png");
        JCheckBox updateUserButton = new JCheckBox(updateUser);
        updateUserButton.setBorderPainted(false);
        updateUserButton.setContentAreaFilled(false);
        updateUserButton.setBounds(50, 110, 135, 35);
        updateUserButton.setSelectedIcon(updateSelected);

        ImageIcon deleteUser = new ImageIcon("pics/delete user.png");
        ImageIcon deleteSelected = new ImageIcon("pics/delete user selected.png");
        JCheckBox deleteUserButton = new JCheckBox(deleteUser);
        deleteUserButton.setBorderPainted(false);
        deleteUserButton.setContentAreaFilled(false);
        deleteUserButton.setBounds(50, 155, 135, 34);
        deleteUserButton.setSelectedIcon(deleteSelected);

        addUserButton.addActionListener(createAddButtonListener(updateUserButton, deleteUserButton));
        updateUserButton.addItemListener(createUpdateButtonListener(addUserButton, deleteUserButton));
        deleteUserButton.addItemListener(createDeleteButtonListener(addUserButton, updateUserButton));

        crudLabel.add(addUserButton);
        crudLabel.add(updateUserButton);
        crudLabel.add(deleteUserButton);
    }

    private void setupExitButton() {
        ImageIcon exitImageIcon = new ImageIcon("pics/exit button2.png");
        exitButton = new JButton();
        exitButton.setIcon(exitImageIcon);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.setBounds(970, 10, 20, 20);
        exitButton.addActionListener(e -> System.exit(0));
    }

    private JPanel createLogPanel(List<String> items) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 0, 5));
        
        for (String item : items) {
            JLabel label = new JLabel(item);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label);
        }
        
        int itemHeight = 30;
        int totalHeight = Math.max(420, (items.size() * itemHeight) + (items.size() * 5));
        panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, totalHeight));
        
        return panel;
    }

    private void setupLoggingArea() {
        JPanel mainLogArea = new JPanel();
        mainLogArea.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        mainLogArea.setBackground(Color.WHITE);

        // Fetch data from database
        List<String> userIDs = getUserIDsFromDatabase();
        List<String> usernames = getUsernamesFromDatabase();
        List<String> passwords = getPasswordsFromDatabase();
        List<String> roles = getRolesFromDatabase();

        // Create panels with dynamic heights
        userIDLogPanel = createLogPanel(userIDs);
        userIDLogPanel.setPreferredSize(new Dimension(70, userIDLogPanel.getPreferredSize().height));
        userIDLogPanel.setBackground(new Color(245, 245, 245));

        usernameLogPanel = createLogPanel(usernames);
        usernameLogPanel.setPreferredSize(new Dimension(220, usernameLogPanel.getPreferredSize().height));
        usernameLogPanel.setBackground(Color.WHITE);

        passwordLogPanel = createLogPanel(passwords);
        passwordLogPanel.setPreferredSize(new Dimension(220, passwordLogPanel.getPreferredSize().height));
        passwordLogPanel.setBackground(new Color(245, 245, 245));

        roleLogPanel = createLogPanel(roles);
        roleLogPanel.setPreferredSize(new Dimension(100, roleLogPanel.getPreferredSize().height));
        roleLogPanel.setBackground(Color.WHITE);

        // Add panels to main area
        mainLogArea.add(userIDLogPanel);
        mainLogArea.add(usernameLogPanel);
        mainLogArea.add(passwordLogPanel);
        mainLogArea.add(roleLogPanel);
        
        // Scroll pane
        logScrollPane = new JScrollPane();
        logScrollPane.setBounds(25, 70, 620, 420);
        logScrollPane.setBorder(new EmptyBorder(0,0,0,0));
        logScrollPane.setViewportView(mainLogArea);
        logScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        logScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        logScrollPane.getVerticalScrollBar().setUI(new customScrollBarUI());
        logScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        logScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        crudBigArea.add(logScrollPane);
    }

    // Helper methods for database operations
    private List<String> getUserIDsFromDatabase() {
        List<String> ids = new ArrayList<>();
        String query = "SELECT id FROM USERS ORDER BY id";
        
        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                ids.add(String.valueOf(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    private List<String> getUsernamesFromDatabase() {
        List<String> usernames = new ArrayList<>();
        String query = "SELECT username FROM USERS ORDER BY id";
        
        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                usernames.add(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }

    private List<String> getPasswordsFromDatabase() {
        List<String> passwords = new ArrayList<>();
        String query = "SELECT password FROM USERS ORDER BY id";
        
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                passwords.add(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwords;
    }

    private List<String> getRolesFromDatabase() {
        List<String> roles = new ArrayList<>();
        String query = "SELECT role FROM USERS ORDER BY id";
        
        try (Connection connection = DriverManager.getConnection(DB_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery()) {
            
            while (resultSet.next()) {
                roles.add(resultSet.getString("role"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    // Event Listeners
    private ItemListener createAddUserListener(JTextField username, JTextField password, JTextField role) {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String usernameInput = username.getText();
                String passwordInput = password.getText();
                String roleInput = role.getText();

                if (usernameInput.isBlank() || passwordInput.isBlank() || roleInput.isBlank()) {
                    showImageFrame("pics/fill in all error.png");
                    username.setText(null);
                    password.setText(null);
                    role.setText(null);
                } else {
                    ConfirmationFrameUserCrud confirm = new ConfirmationFrameUserCrud(UserCRUD.this,usernameInput, passwordInput, roleInput);
                    username.setText(null);
                    password.setText(null);
                    role.setText(null);
                    refreshLogPanels(); // Refresh after adding user
                }
            }
        };
    }

    private ItemListener createEditUserListener(JTextField id, JTextField username, JTextField password, JTextField role) {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String idInput = id.getText();
                String usernameInput = username.getText();
                String passwordInput = password.getText();
                String roleInput = role.getText();

                if (idInput.isBlank() || usernameInput.isBlank() || passwordInput.isBlank() || roleInput.isBlank()) {
                    showImageFrame("pics/fill in all error.png");
                    showImageFrame("pics/user id wrong.png");
                } else {
                    ConfirmationFrameUserCrud confirm = new ConfirmationFrameUserCrud(UserCRUD.this, idInput, usernameInput, passwordInput, roleInput);
                    refreshLogPanels(); // Refresh after editing user
                }
            }
        };
    }

    private ItemListener createDeleteUserListener(JTextField id) {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String idInput = id.getText();

                if (idInput.isBlank()) {
                    showImageFrame("pics/user id wrong.png");
                } else {
                    ConfirmationFrameUserCrud confirm = new ConfirmationFrameUserCrud(UserCRUD.this, idInput);
                    refreshLogPanels(); // Refresh after deleting user
                }
            }
        };
    }

    private ActionListener createAddButtonListener(JCheckBox updateButton, JCheckBox deleteButton) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                if (source.isSelected()) {
                    crudBottomEditUser.setVisible(false);
                    crudBottomDeleteUser.setVisible(false);
                    crudBottomAddUser.setVisible(true);

                    updateButton.setSelected(false);
                    deleteButton.setSelected(false);
                } else {
                    crudBottomAddUser.setVisible(false);
                }
            }
        };
    }

    private ItemListener createUpdateButtonListener(JCheckBox addButton, JCheckBox deleteButton) {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    crudBottomAddUser.setVisible(false);
                    crudBottomDeleteUser.setVisible(false);
                    crudBottomEditUser.setVisible(true);

                    addButton.setSelected(false);
                    deleteButton.setSelected(false);
                } else {
                    crudBottomEditUser.setVisible(false);
                }
            }
        };
    }

    private ItemListener createDeleteButtonListener(JCheckBox addButton, JCheckBox updateButton) {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    crudBottomAddUser.setVisible(false);
                    crudBottomEditUser.setVisible(false);
                    crudBottomDeleteUser.setVisible(true);

                    addButton.setSelected(false);
                    updateButton.setSelected(false);
                } else {
                    crudBottomDeleteUser.setVisible(false);
                }
            }
        };
    }

    // Method to refresh the log panels
    public void refreshLogPanels() {
        // Get fresh data from database
        List<String> userIDs = getUserIDsFromDatabase();
        List<String> usernames = getUsernamesFromDatabase();
        List<String> passwords = getPasswordsFromDatabase();
        List<String> roles = getRolesFromDatabase();
    
        // Clear existing panels
        userIDLogPanel.removeAll();
        usernameLogPanel.removeAll();
        passwordLogPanel.removeAll();
        roleLogPanel.removeAll();
    
        // Update panels with new data
        updateLogPanel(userIDLogPanel, userIDs);
        updateLogPanel(usernameLogPanel, usernames);
        updateLogPanel(passwordLogPanel, passwords);
        updateLogPanel(roleLogPanel, roles);
    
        // Update panel sizes
        int maxItems = Math.max(
            Math.max(userIDs.size(), usernames.size()),
            Math.max(passwords.size(), roles.size())
        );
        int itemHeight = 30;
        int totalHeight = Math.max(420, (maxItems * itemHeight) + (maxItems * 5));
    
        userIDLogPanel.setPreferredSize(new Dimension(70, totalHeight));
        usernameLogPanel.setPreferredSize(new Dimension(220, totalHeight));
        passwordLogPanel.setPreferredSize(new Dimension(220, totalHeight));
        roleLogPanel.setPreferredSize(new Dimension(100, totalHeight));
    
        // Revalidate and repaint all components
        userIDLogPanel.revalidate();
        usernameLogPanel.revalidate();
        passwordLogPanel.revalidate();
        roleLogPanel.revalidate();
        
        logScrollPane.revalidate();
        
        userIDLogPanel.repaint();
        usernameLogPanel.repaint();
        passwordLogPanel.repaint();
        roleLogPanel.repaint();
        
        logScrollPane.repaint();
        crudBigArea.repaint();
    }
    private void updateLogPanel(JPanel panel, List<String> items) {
        panel.setLayout(new GridLayout(0, 1, 0, 5));
        
        for (String item : items) {
            JLabel label = new JLabel(item);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label);
        }
    }

    private void showImageFrame(String imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();

        JWindow imageWindow = new JWindow();
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        imageWindow.setSize(width, height);
        imageWindow.setLocationRelativeTo(this);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, this);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));
        imageWindow.setContentPane(panel);

        imageWindow.setOpacity(0.0f);
        imageWindow.setVisible(true);

        Timer fadeInTimer = new Timer(20, null);
        fadeInTimer.addActionListener(new ActionListener() {
            float opacity = 0.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity += 0.05f;
                if (opacity >= 1.0f) {
                    opacity = 1.0f;
                    fadeInTimer.stop();
                    new Timer(1500, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            startFadeOut(imageWindow);
                        }
                    }).start();
                }
                imageWindow.setOpacity(opacity);
                imageWindow.repaint();
            }
        });
        fadeInTimer.start();
    }

    private void startFadeOut(JWindow imageWindow) {
        Timer fadeOutTimer = new Timer(20, null);
        fadeOutTimer.addActionListener(new ActionListener() {
            float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                if (opacity <= 0.0f) {
                    opacity = 0.0f;
                    imageWindow.dispose();
                    fadeOutTimer.stop();
                }
                imageWindow.setOpacity(opacity);
                imageWindow.repaint();
            }
        });
        fadeOutTimer.start();
    }
}