package com.stadium.ui;

import com.stadium.dao.SeatDAO;
import com.stadium.model.Seat;
import com.stadium.model.SeatCategory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFrame extends JFrame {

    private List<Seat> selectedSeats;
    private int eventId;
    private int customerId;
    private String eventName;
    //String seatCat = new SeatCategory(SeatCategory());

    public SeatSelectionFrame(int eventId, int customerId, String eventName) {

        this.eventId = eventId;
        this.customerId = customerId;
        this.eventName = eventName;
        selectedSeats = new ArrayList<>();

        setTitle("Select Seats");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        StadiumTheme.prepareFrame(this);

        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = StadiumTheme.title("Select Your Seats");

        add(titleLabel, BorderLayout.NORTH);

        JPanel seatsPanel = new JPanel(new GridLayout(0, 5, 12, 12));
        seatsPanel.setBackground(Color.WHITE);
        seatsPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        Color availableSeatColor = Color.decode("#E5E7E9");
        Color selectedSeatColor = new Color(35, 117, 194);

        SeatDAO seatDAO = new SeatDAO();

        List<Seat> seats = seatDAO.getAvailableSeats();


        for (Seat seat : seats) {

            JButton seatButton = new JButton(seat.getSeatNumber());
            seatButton.setBackground(availableSeatColor);
            seatButton.setOpaque(true);
            seatButton.setFocusPainted(false);
            seatButton.setFont(new Font("Sans-serif", Font.BOLD, 13));

            seatButton.addActionListener(e -> {


                if (selectedSeats.contains(seat)) {

                    selectedSeats.remove(seat);

                    seatButton.setSelected(false);
                    seatButton.setBackground(availableSeatColor);
                    seatButton.setForeground(Color.BLACK);

                } else {

                    if (selectedSeats.size() >= 5) {

                        JOptionPane.showMessageDialog(
                                this,
                                "You can select a maximum of 5 seats."
                        );

                        return;
                    }

                    selectedSeats.add(seat);

                    seatButton.setSelected(true);
                    seatButton.setBackground(selectedSeatColor);
                    seatButton.setForeground(Color.WHITE);
                }
            });

            seatsPanel.add(seatButton);
        }

        JScrollPane scrollPane = new JScrollPane(seatsPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(StadiumTheme.BORDER));

        add(scrollPane, BorderLayout.CENTER);


        JButton continueButton = StadiumTheme.button("Continue", true);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        actionPanel.setOpaque(false);
        actionPanel.add(continueButton);
        add(actionPanel, BorderLayout.SOUTH);

        continueButton.addActionListener(e -> {

            if (selectedSeats.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select at least one seat."
                );
                new BookingSummaryFrame(eventId, customerId,eventName,selectedSeats);
                return;
            }

            double subtotal = 0;

            for (Seat seat : selectedSeats) {

                subtotal += seat.getCategory().getPrice();
            }

            new BookingSummaryFrame(eventId, customerId,eventName,selectedSeats);
        });

        setVisible(true);
    }
}