package com.stadium.dao;

import com.stadium.database.DatabaseConnection;

import com.stadium.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public int createBooking(
            Connection connection,
            String bookingReference,
            int customerId,
            int eventId,
            double subtotal,
            double discount,
            double total) {

        String sql = """
            INSERT INTO bookings
            (booking_reference, customer_id, event_id,
             subtotal, discount, total, status)
            VALUES (?, ?, ?, ?, ?, ?, 'CONFIRMED')
            RETURNING booking_id
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bookingReference);
            statement.setInt(2, customerId);
            statement.setInt(3, eventId);
            statement.setDouble(4, subtotal);
            statement.setDouble(5, discount);
            statement.setDouble(6, total);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("booking_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public List<Object[]> getCustomerBookings(int customerId) {
        List<Object[]> bookings = new ArrayList<>();

        String sql = """
        SELECT b.booking_id,
               b.booking_reference,
               e.event_name,
               e.event_date,
               b.status,
               b.total
        FROM bookings b
        JOIN events e ON b.event_id = e.event_id
        WHERE b.customer_id = ?
        ORDER BY b.checked_at DESC
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Object[] row = {
                            resultSet.getInt("booking_id"),
                            resultSet.getString("booking_reference"),
                            resultSet.getString("event_name"),
                            resultSet.getString("event_date"),
                            resultSet.getString("status"),
                            resultSet.getDouble("total")
                    };

                    bookings.add(row);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }


    public boolean cancelBooking(int bookingId, int customerId) {
        String checkSql = """
        SELECT b.status, e.event_date, e.event_time
        FROM bookings b
        JOIN events e ON b.event_id = e.event_id
        WHERE b.booking_id = ?
          AND b.customer_id = ?
        FOR UPDATE OF b
        """;

        String cancelSql = """
        UPDATE bookings
        SET status = 'CANCELLED'
        WHERE booking_id = ?
          AND customer_id = ?
          AND status = 'CONFIRMED'
        """;

        String releaseSeatsSql = """
        UPDATE seats
        SET status = 'AVAILABLE'
        WHERE seat_id IN (
            SELECT seat_id
            FROM booking_seats
            WHERE booking_id = ?
        )
        """;

        try (Connection connection = DatabaseConnection.getConnection()) {
            connection.setAutoCommit(false);

            try {
                java.time.LocalDateTime eventDateTime;

                try (PreparedStatement check =
                             connection.prepareStatement(checkSql)) {
                    check.setInt(1, bookingId);
                    check.setInt(2, customerId);

                    try (ResultSet rs = check.executeQuery()) {
                        if (!rs.next()
                                || !"CONFIRMED".equalsIgnoreCase(
                                rs.getString("status"))) {
                            connection.rollback();
                            return false;
                        }

                        eventDateTime = java.time.LocalDateTime.of(
                                rs.getDate("event_date").toLocalDate(),
                                rs.getTime("event_time").toLocalTime()
                        );
                    }
                }

                if (!java.time.LocalDateTime.now()
                        .isBefore(eventDateTime.minusHours(2))) {
                    connection.rollback();
                    return false;
                }

                try (PreparedStatement cancel =
                             connection.prepareStatement(cancelSql)) {
                    cancel.setInt(1, bookingId);
                    cancel.setInt(2, customerId);

                    if (cancel.executeUpdate() != 1) {
                        connection.rollback();
                        return false;
                    }
                }

                try (PreparedStatement release =
                             connection.prepareStatement(releaseSeatsSql)) {
                    release.setInt(1, bookingId);
                    release.executeUpdate();
                }

                connection.commit();
                return true;

            } catch (Exception e) {
                connection.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}