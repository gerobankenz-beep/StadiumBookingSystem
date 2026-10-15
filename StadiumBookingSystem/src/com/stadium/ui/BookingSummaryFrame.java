package com.stadium.ui;

import com.stadium.model.Seat;
import com.stadium.service.BookingService;
import com.stadium.model.Discount;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.stadium.dao.BookingSeatDAO;

public class BookingSummaryFrame extends JFrame {
    private int eventId;
    private int customerId;
    private String eventName;

    public BookingSummaryFrame(int eventId, int customerId, String eventName, List<Seat> selectedSeats) {
        this.eventId = eventId;
        this.customerId = customerId;

        setTitle("Booking Summary");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        StadiumTheme.prepareFrame(this);

        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = StadiumTheme.title("Booking Summary");

        add(titleLabel, BorderLayout.NORTH);

        JTextArea summaryArea = new JTextArea();

        summaryArea.setEditable(false);
        summaryArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        summaryArea.setForeground(StadiumTheme.NAVY);
        summaryArea.setBackground(Color.WHITE);
        summaryArea.setBorder(BorderFactory.createEmptyBorder(16, 18, 16, 18));

        double subtotal = 0;

        for (Seat seat : selectedSeats) {

            double price = seat.getCategory().getPrice();

            subtotal += price;

            summaryArea.append(
                    "Seat: "
                            + seat.getSeatNumber()
                            + " | "
                            + seat.getCategory().getCategoryName()
                            + " | UGX "
                            + price
                            + "\n"
            );
        }

        summaryArea.append("\nSubtotal: UGX " + subtotal);

        JScrollPane summaryScrollPane = new JScrollPane(summaryArea);
        summaryScrollPane.setBorder(BorderFactory.createLineBorder(StadiumTheme.BORDER));
        add(summaryScrollPane, BorderLayout.CENTER);

        JButton confirmButton = StadiumTheme.button("Confirm Booking", true);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        actionPanel.setOpaque(false);
        actionPanel.add(confirmButton);
        add(actionPanel, BorderLayout.SOUTH);

        confirmButton.addActionListener(e -> {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Confirm this booking?",
                    "Confirm Booking",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice != JOptionPane.YES_OPTION) {
                return;
            }

            double bookingSubtotal = 0;

            List<Integer> selectedSeatIds = new ArrayList<>();

            for (Seat seat : selectedSeats) {
                bookingSubtotal += seat.getCategory().getPrice();
                selectedSeatIds.add(seat.getSeatId());
            }

            // Discount policy still needs a defined bulk threshold.

            double discountAmount = 0;

            if (selectedSeats.size() >= 3) {
                Discount discount = new Discount(
                        1,
                        "Bulk Booking",
                        10
                );

                discountAmount = discount.calculateDiscount(bookingSubtotal);
            }

            double total = bookingSubtotal - discountAmount;

            String bookingReference =
                    "BK-" + UUID.randomUUID()
                            .toString()
                            .substring(0, 8)
                            .toUpperCase();

            BookingService bookingService = new BookingService();

            boolean success = bookingService.bookSeats(
                    bookingReference,
                    customerId,
                    eventId,
                    selectedSeatIds,
                    bookingSubtotal,
                    discountAmount,
                    total
            );

            if (success) {

                int bookingId = bookingService.getLastBookingId();

                BookingSeatDAO bookingSeatDAO = new BookingSeatDAO();

                List<Object[]> bookedSeats = bookingSeatDAO.getSeatsByBookingId(bookingId);

                StringBuilder ticket = new StringBuilder();

                ticket.append("================================\n");
                ticket.append("        MT SPORTS ARENA         \n");
                ticket.append("================================\n\n");

                ticket.append("Event: ").append(eventName).append("\n\n");

                ticket.append("Booking Reference: ").append(bookingReference).append("\n\n");

                ticket.append("BOOKED SEATS:\n");
                ticket.append("--------------------------------\n");

                for (Object[] seat : bookedSeats) {

                    String seatNumber = (String) seat[0];
                    String category = (String) seat[1];
                    double price = ((Number) seat[2]).doubleValue();

                    ticket.append(seatNumber)
                            .append(" - ")
                            .append(category)
                            .append(" - UGX ")
                            .append(String.format("%.0f", price))
                            .append("\n");
                }

                ticket.append("--------------------------------\n");
                ticket.append(String.format(
                        "Subtotal: UGX %.0f%n", bookingSubtotal));

                ticket.append(String.format(
                        "Discount: UGX %.0f%n", discountAmount));

                ticket.append(String.format(
                        "TOTAL: UGX %.0f%n", total));

                ticket.append("\n================================");

                JOptionPane.showMessageDialog(
                        this,
                        ticket.toString(),
                        "Booking Ticket",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Booking failed. Please check seat availability and try again."
                );
            }
            EventsFrame frame = new EventsFrame(customerId);

            //CustomerDashboard dashboard = new CustomerDashboard(customerId);
            //dashboard.setVisible(true);
        });

        setVisible(true);
    }
}