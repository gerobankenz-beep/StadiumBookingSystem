package com.stadium.dao;

import com.stadium.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingSeatDAO {

    public boolean addBookingSeat(
            Connection connection,
            int bookingId,
            int seatId) {

        String sql = """
            INSERT INTO booking_seats
            (booking_id, seat_id)
            VALUES (?, ?)
            """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);
            statement.setInt(2, seatId);

            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> getSeatsByBookingId(int bookingId) {

        List<Object[]> seats = new ArrayList<>();

        String sql = """
        SELECT
            s.seat_number,
            sc.category_name,
            sc.price
        FROM booking_seats bs
        JOIN seats s
            ON bs.seat_id = s.seat_id
        JOIN seat_categories sc
            ON s.category_id = sc.category_id
        WHERE bs.booking_id = ?
        ORDER BY s.seat_number
        """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, bookingId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Object[] seat = {
                        resultSet.getString("seat_number"),
                        resultSet.getString("category_name"),
                        resultSet.getDouble("price")
                };

                seats.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }


}