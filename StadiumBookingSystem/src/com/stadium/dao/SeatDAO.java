package com.stadium.dao;
import com.stadium.database.DatabaseConnection;
import com.stadium.model.Seat;
import com.stadium.model.SeatCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {


    public List<Seat> getAvailableSeats() {

        List<Seat> seats = new ArrayList<>();

        String sql = """
            SELECT s.seat_id,
                   s.seat_number,
                   s.status,
                   sc.category_id,
                   sc.category_name,
                   sc.price
            FROM seats s
            JOIN seat_categories sc
              ON s.category_id = sc.category_id
            WHERE s.status = 'AVAILABLE'
            ORDER BY s.seat_id
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                SeatCategory category = new SeatCategory(
                        resultSet.getInt("category_id"),
                        resultSet.getString("category_name"),
                        resultSet.getDouble("price")
                );

                Seat seat = new Seat(
                        resultSet.getInt("seat_id"),
                        resultSet.getString("seat_number"),
                        resultSet.getString("status"),
                        category
                );

                seats.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    public boolean bookSeat(
            Connection connection,
            int seatId) {

        String sql = """
            UPDATE seats
            SET status = 'BOOKED'
            WHERE seat_id = ?
              AND status = 'AVAILABLE'
            """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, seatId);

            int rowsUpdated = statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isSeatAvailable(int seatId) {

        String sql = """
            SELECT status
            FROM seats
            WHERE seat_id = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, seatId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("status").equals("AVAILABLE");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
