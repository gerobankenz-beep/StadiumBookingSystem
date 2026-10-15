package com.stadium.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TicketDAO {

    public boolean createTicket(
            Connection connection,
            String ticketNumber,
            int bookingId) {

        String sql = """
                INSERT INTO tickets
                (ticket_number, booking_id)
                VALUES (?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, ticketNumber);
            statement.setInt(2, bookingId);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}