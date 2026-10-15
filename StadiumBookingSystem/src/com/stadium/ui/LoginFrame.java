package com.stadium.ui;

import com.stadium.dao.CustomerDAO;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        ImageIcon image = new ImageIcon("logo.jpg");
        setIconImage(image.getImage());
        setTitle("GMT Sports Arena");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        Color backgroundColor = new Color(238, 242, 247);
        Color navyColor = new Color(24, 48, 83);
        Color accentColor = new Color(35, 117, 194);
        Color borderColor = new Color(205, 214, 225);

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(backgroundColor);

        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(Color.WHITE);
        loginCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(24, 34, 28, 34)
        ));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 0, 5, 0);
        constraints.weightx = 1;

        JLabel titleLabel = new JLabel("Welcome back", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Sans-serif", Font.BOLD, 24));
        titleLabel.setForeground(navyColor);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(0, 0, 2, 0);
        loginCard.add(titleLabel, constraints);

        JLabel subtitleLabel = new JLabel("Sign in to continue to GMT Sports Arena", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Sans-serif", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(95, 108, 125));
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 18, 0);
        loginCard.add(subtitleLabel, constraints);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Sans-serif", Font.BOLD, 13));
        usernameLabel.setForeground(navyColor);
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.insets = new Insets(4, 0, 3, 10);
        loginCard.add(usernameLabel, constraints);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Sans-serif", Font.BOLD, 13));
        passwordLabel.setForeground(navyColor);
        constraints.gridx = 1;
        constraints.insets = new Insets(4, 10, 3, 0);
        loginCard.add(passwordLabel, constraints);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(190, 34));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(5, 9, 5, 9)
        ));
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.insets = new Insets(0, 0, 12, 10);
        loginCard.add(usernameField, constraints);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(190, 34));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor),
                BorderFactory.createEmptyBorder(5, 9, 5, 9)
        ));
        constraints.gridx = 1;
        constraints.insets = new Insets(0, 10, 12, 0);
        loginCard.add(passwordField, constraints);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        loginButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(accentColor);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));

        registerButton.setFont(new Font("Sans-serif", Font.BOLD, 14));
        registerButton.setForeground(navyColor);
        registerButton.setBackground(new Color(232, 238, 246));
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));

        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.insets = new Insets(2, 0, 0, 10);
        loginCard.add(loginButton, constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(2, 10, 0, 0);
        loginCard.add(registerButton, constraints);

        backgroundPanel.add(loginCard);
        add(backgroundPanel);

        registerButton.addActionListener(e -> {
            RegistrationFrame registrationFrame = new RegistrationFrame();
            registrationFrame.setVisible(true);
        });

        loginButton.addActionListener(e -> {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter username and password."
                );

            } else {

                CustomerDAO customerDAO = new CustomerDAO();

                boolean loggedIn = customerDAO.loginCustomer(username, password);

                if (loggedIn) {

                    int customerId = customerDAO.getCustomerId(username, password);

                    new CustomerDashboard(customerId);
                    dispose();
                    new CustomerDashboard(customerId);

                } else {

                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid username or password."
                    );
                }

            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}