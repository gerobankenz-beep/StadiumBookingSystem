package com.stadium.ui;

import com.stadium.dao.BookingDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MyBookingsFrame extends JFrame {

    private int customerId;
    private JTable bookingsTable;
    private DefaultTableModel tableModel;

    public MyBookingsFrame(int customerId) {
        this.customerId = customerId;

        setTitle("My Bookings");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        StadiumTheme.prepareFrame(this);

        // Table model
        tableModel = new DefaultTableModel(
                new String[]{
                        "Booking ID",
                        "Booking Reference",
                        "Event",
                        "Event Date",
                        "Status",
                        "Total"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        bookingsTable = new JTable(tableModel);
        StadiumTheme.styleTable(bookingsTable);

        // Refresh button
        JButton refreshButton = StadiumTheme.button("Refresh", false);


        JButton cancelButton = StadiumTheme.button("Cancel Booking", true);

        cancelButton.addActionListener(e -> {
            int selectedRow = bookingsTable.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a booking to cancel."
                );
                return;
            }

            int bookingId = (int) tableModel.getValueAt(selectedRow, 0);
            String status = tableModel.getValueAt(selectedRow, 4).toString();

            if (!"CONFIRMED".equalsIgnoreCase(status)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Only confirmed bookings can be cancelled."
                );
                return;
            }

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to cancel this booking?",
                    "Confirm Cancellation",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            BookingDAO bookingDAO = new BookingDAO();

            boolean success = bookingDAO.cancelBooking(
                    bookingId, customerId
            );

            if (success) {
                JOptionPane.showMessageDialog(
                        this,
                        "Booking cancelled successfully."
                );
                loadBookings();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Cancellation failed. The event may be within two hours, "
                                + "or the booking may no longer be active."
                );
            }
        });

        refreshButton.addActionListener(e -> loadBookings());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        topPanel.setOpaque(false);
        topPanel.add(refreshButton);
        topPanel.add(cancelButton);

        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Load bookings when frame opens
        loadBookings();
    }

    private void loadBookings() {

        tableModel.setRowCount(0);

        BookingDAO bookingDAO = new BookingDAO();

        List<Object[]> bookings =
                bookingDAO.getCustomerBookings(customerId);

        for (Object[] booking : bookings) {
            tableModel.addRow(booking);
        }
    }

}