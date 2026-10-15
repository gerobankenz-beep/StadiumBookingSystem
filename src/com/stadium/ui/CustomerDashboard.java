package com.stadium.ui;

import javax.swing.*;
import java.awt.*;

public class CustomerDashboard extends JFrame {
    private int customerId;

    public CustomerDashboard(int customerId) {

        this.customerId = customerId;

        setTitle("Customer Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        StadiumTheme.prepareFrame(this);

        JPanel content = new JPanel(new BorderLayout(20, 20));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(30, 70, 35, 70));

        JLabel titleLabel = StadiumTheme.title("Stadium Seat Booking System");
        content.add(titleLabel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 0, 14));
        menuPanel.setOpaque(false);

        JButton eventsButton = StadiumTheme.button("View Events", true);
        menuPanel.add(eventsButton);
        eventsButton.addActionListener(e -> {
            new EventsFrame(customerId);
        });

        JButton bookingsButton = StadiumTheme.button("My Bookings", false);
        bookingsButton.addActionListener(e->{
            MyBookingsFrame frame = new MyBookingsFrame(customerId);
            frame.setVisible(true);
        });
        menuPanel.add(bookingsButton);

        JButton logoutButton = StadiumTheme.button("Logout", false);
        menuPanel.add(logoutButton);
        logoutButton.addActionListener(e->{
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });

        content.add(menuPanel, BorderLayout.CENTER);
        add(content);
        setVisible(true);
    }

    public static void main(String[] args){

    }
}