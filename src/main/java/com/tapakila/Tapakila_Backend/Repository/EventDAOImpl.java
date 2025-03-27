package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Enum.EventStatus;
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
    public List<Event> getAll(String status, String dateFilter) {
        List<Event> events = new ArrayList<>();
        StringBuilder sql =  new StringBuilder("SELECT event_id::TEXT, event_title, event_type, event_date, event_status, event_poster FROM events WHERE 1=1");

        // Filter by status
        if (status != null && !status.isEmpty()) {
            sql.append(" AND event_status = ?::event_status");
        }

        // Filter by date
        if (dateFilter != null && !dateFilter.isEmpty()) {
            switch (dateFilter.toLowerCase()) {
                case "upcoming":
                    sql.append(" AND event_date > CURRENT_TIMESTAMP");
                    break;
                case "past":
                    sql.append(" AND event_date < CURRENT_TIMESTAMP");
                    break;
                default:
                    break;
            }
        }

        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event();
                    event.setEventId(rs.getString("event_id"));
                    event.setEventTitle(rs.getString("event_title"));
                    event.setEventType(EventType.valueOf(rs.getString("event_type")));
                    event.setEventDate(rs.getTimestamp("event_date").toLocalDateTime());
                    event.setEventStatus(EventStatus.valueOf(rs.getString("event_status")));
                    event.setEventPoster(rs.getString("event_poster"));
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving events",e);
        }
        return events;
    }

    @Override
    public Event getById(String eventId) {
        Event event = null;
        String sql = "SELECT event_id::TEXT, event_title, event_type, event_date, event_status, event_poster FROM events WHERE event_id = ?::UUID";
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
                    event.setEventStatus(EventStatus.valueOf(rs.getString("event_status")));
                    event.setEventPoster(rs.getString("event_poster"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while retrieving an event",e);
        }
        return event;
    }

    @Override
    public void save(Event event) {
        String sql = "INSERT INTO events (event_id, event_title, event_type, event_date, event_status, event_poster) VALUES (?::UUID, ?, ?::event_type, ?, ?::event_status, ?)";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getEventId());
            ps.setString(2, event.getEventTitle());
            ps.setString(3, event.getEventType().toString());
            ps.setObject(4, event.getEventDate());
            ps.setString(5, event.getEventStatus() != null ? event.getEventStatus().toString() : "UPCOMING");
            ps.setString(6, event.getEventPoster());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Event creation error", e);
        }
    }

    @Override
    public void update(Event event) {
        String sql = "UPDATE events SET event_title = ?, event_type = ?::event_type, event_date = ?, event_status = ?::event_status, event_poster = ? WHERE event_id = ?::UUID";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, event.getEventTitle());
            ps.setString(2, event.getEventType().toString());
            ps.setObject(3, event.getEventDate());
            ps.setString(4, event.getEventStatus().toString());
            ps.setString(5, event.getEventPoster());
            ps.setString(6, event.getEventId());

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

    @Override
    public void updateStatus(String eventId, String status) {
        String sql = "UPDATE events SET event_status = ?::event_status WHERE event_id = ?::UUID";
        try (Connection conn = datasource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, eventId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("Event not found for status update");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating event status", e);
        }
    }
}
