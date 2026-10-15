package com.stadium.dao;

import com.stadium.model.Event;
import com.stadium.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {

    public List<Event> getAllEvents() {

        List<Event> events = new ArrayList<>();

        String sql = """
            SELECT event_id,
                   event_type,
                   event_name,
                   event_date,
                   event_time
            FROM events
            ORDER BY event_date, event_time
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet =
                     statement.executeQuery()) {

            while (resultSet.next()) {

                Event event = new Event(
                        resultSet.getInt("event_id"),
                        resultSet.getString("event_type"),
                        resultSet.getString("event_name"),
                        resultSet.getString("event_date"),
                        resultSet.getString("event_time")
                );

                events.add(event);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }


}