package com.stadium.service;

import com.stadium.dao.TicketDAO;
import com.stadium.dao.BookingDAO;
import com.stadium.dao.BookingSeatDAO;
import com.stadium.dao.SeatDAO;
import com.stadium.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookingService {

    private final BookingDAO bookingDAO;
    private final BookingSeatDAO bookingSeatDAO;
    private final SeatDAO seatDAO;
    private final TicketDAO ticketDAO;
    private int lastBookingId = -1;

    public BookingService() {
        bookingDAO = new BookingDAO();
        bookingSeatDAO = new BookingSeatDAO();
        seatDAO = new SeatDAO();
        ticketDAO = new TicketDAO();
    }

    public boolean bookSeats(
            String bookingReference,
            int customerId,
            int eventId,
            List<Integer> selectedSeatIds,
            double subtotal,
            double discount,
            double total) {

        if (selectedSeatIds == null || selectedSeatIds.isEmpty()) {
            return false;
        }

        if (selectedSeatIds.size() > 5) {
            System.out.println("Maximum of 5 seats allowed.");
            return false;
        }

        try (Connection connection =
                     DatabaseConnection.getConnection()) {

            connection.setAutoCommit(false);

            // Check all seats first
            for (int seatId : selectedSeatIds) {

                if (!seatDAO.isSeatAvailable(seatId)) {

                    System.out.println(
                            "Seat " + seatId + " is already booked."
                    );

                    connection.rollback();
                    return false;
                }
            }

            // Create booking
            int bookingId = bookingDAO.createBooking(
                    connection,
                    bookingReference,
                    customerId,
                    eventId,
                    subtotal,
                    discount,
                    total
            );

            lastBookingId = bookingId;

            if (bookingId == -1) {
                connection.rollback();
                return false;
            }

            // Add seats to booking
            for (int seatId : selectedSeatIds) {

                boolean added =
                        bookingSeatDAO.addBookingSeat(
                                connection,
                                bookingId,
                                seatId
                        );

                if (!added) {
                    connection.rollback();
                    return false;
                }
            }

            // Mark seats as booked
            for (int seatId : selectedSeatIds) {

                boolean booked =
                        seatDAO.bookSeat(
                                connection,
                                seatId
                        );

                if (!booked) {
                    connection.rollback();
                    return false;
                }
            }

            // Create ticket
            String ticketNumber = "TKT-"+ bookingReference;

            boolean ticketCreated = ticketDAO.createTicket(
                    connection,
                    ticketNumber,
                    bookingId
            );

            if(!ticketCreated){
                connection.rollback();
                return false;
            }

            //Everything succeeded
            connection.commit();

            System.out.println(
                    "Booking completed: "+ticketNumber
            );

            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        }
    }

    public int getLastBookingId() {
        return lastBookingId;
    }
}