package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDAO {
    List<Event> getAll(String status, String dateFilter);
    Event getById(String eventId);
    void save(Event event);
    void update(Event event);
    void delete(String eventId);
    void updateStatus(String eventId, String status);
}
