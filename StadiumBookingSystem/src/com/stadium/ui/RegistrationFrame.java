package com.stadium.ui;

import com.stadium.dao.CustomerDAO;

import javax.swing.*;
import java.awt.*;

public class RegistrationFrame extends JFrame {

    private JTextField nameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField contactField;
    private JTextField countryField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegistrationFrame() {

        setTitle("Customer Registration");
        setSize(560, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        StadiumTheme.prepareFrame(this);

        JPanel page = new JPanel(new GridBagLayout());
        page.setOpaque(false);
        page.setBorder(BorderFactory.createEmptyBorder(24, 34, 28, 34));

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StadiumTheme.BORDER),
                BorderFactory.createEmptyBorder(22, 28, 24, 28)
        ));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.gridwidth = 2;

        JLabel titleLabel = StadiumTheme.title("Create your account");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 2, 0);
        card.add(titleLabel, constraints);

        JLabel subtitleLabel = new JLabel(
                "Register for easy access to stadium events and bookings.",
                SwingConstants.CENTER
        );
        subtitleLabel.setFont(new Font("Sans-serif", Font.PLAIN, 13));
        subtitleLabel.setForeground(StadiumTheme.MUTED);
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 18, 0);
        card.add(subtitleLabel, constraints);

        nameField = StadiumTheme.field();
        addressField = StadiumTheme.field();
        emailField = StadiumTheme.field();
        contactField = StadiumTheme.field();
        countryField = StadiumTheme.field();
        usernameField = StadiumTheme.field();
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Sans-serif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(230, 36));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(StadiumTheme.BORDER),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        addField(card, constraints, 2, "Name", nameField);
        addField(card, constraints, 3, "Address", addressField);
        addField(card, constraints, 4, "Email", emailField);
        addField(card, constraints, 5, "Contact", contactField);
        addField(card, constraints, 6, "Country", countryField);
        addField(card, constraints, 7, "Username", usernameField);
        addField(card, constraints, 8, "Password", passwordField);

        JButton registerButton = StadiumTheme.button("Register", true);
        JButton cancelButton = StadiumTheme.button("Cancel", false);

        constraints.gridwidth = 1;
        constraints.gridy = 9;
        constraints.gridx = 0;
        constraints.insets = new Insets(16, 0, 0, 8);
        card.add(registerButton, constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(16, 8, 0, 0);
        card.add(cancelButton, constraints);

        page.add(card);
        add(page);

        // Register
        registerButton.addActionListener(e -> registerCustomer());

        // Cancel
        cancelButton.addActionListener(e -> dispose());
    }

    private void addField(
            JPanel panel,
            GridBagConstraints constraints,
            int row,
            String labelText,
            JComponent field
    ) {
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.insets = new Insets(4, 0, 3, 8);
        panel.add(StadiumTheme.formLabel(labelText), constraints);

        constraints.gridx = 1;
        constraints.insets = new Insets(4, 8, 3, 0);
        panel.add(field, constraints);
    }

    private void registerCustomer() {

        String name = nameField.getText().trim();
        String address = addressField.getText().trim();
        String email = emailField.getText().trim();
        String contact = contactField.getText().trim();
        String country = countryField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() ||
                address.isEmpty() ||
                email.isEmpty() ||
                contact.isEmpty() ||
                country.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields."
            );
            return;
        }


        CustomerDAO customerDAO = new CustomerDAO();

        try {
            customerDAO.addCustomer(
                    name,
                    address,
                    email,
                    contact,
                    country,
                    username,
                    password
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Registration request processed. "
                            + "Try logging in with your new account."
            );

            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Registration failed: " + ex.getMessage(),
                    "Registration Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }



    }
}