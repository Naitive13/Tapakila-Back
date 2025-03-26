package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Enum.EventType;
import com.tapakila.Tapakila_Backend.Entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EventDAOImpl implements EventDAO {
    private final Datasource datasource;

    @Autowired
    public EventDAOImpl(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT event_id::TEXT, event_title, event_type, event_date FROM events";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getString("event_id"));
                event.setEventTitle(rs.getString("event_title"));
                event.setEventType(EventType.valueOf(rs.getString("event_type")));
                event.setEventDate(rs.getTimestamp("event_date").toLocalDateTime());
                events.add(event);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving events",e);
        }
        return events;
    }

    @Override
    public Event getById(String eventId) {
        Event event = null;
        String sql = "SELECT event_id::TEXT, event_title, event_type, event_date FROM events WHERE event_id = ?::UUID";
        try(Connection conn = datasource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    event = new Event();
                    event.setEventId(rs.getString("event_id"));
                    event.setEventTitle(rs.getString("event_title"));
                    event.setEventType(EventType.valueOf(rs.getString("event_type")));
                    event.setEventDate(rs.getTimestamp("event_date").toLocalDateTime());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving an event",e);
        }
        return event;
    }

    @Override
    public void save(Event event) {
        String sql = "INSERT INTO events (event_id, event_title, event_type, event_date) VALUES (?::UUID, ?, ?::event_type, ?)";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getEventId());
            ps.setString(2, event.getEventTitle());
            ps.setString(3, event.getEventType().toString());
            ps.setObject(4, event.getEventDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Event creation error", e);
        }
    }

    @Override
    public void update(Event event) {
        String sql = "UPDATE events SET event_title = ?, event_type = ?::event_type, event_date = ? WHERE event_id = ?::UUID";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getEventTitle());
            ps.setString(2, event.getEventType().toString());
            ps.setObject(3, event.getEventDate());
            ps.setString(4, event.getEventId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating event", e);
        }
    }

    @Override
    public void delete(String eventId) {
        String sql = "DELETE FROM events WHERE event_id = ?::UUID";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, eventId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting event", e);
        }
    }
}
