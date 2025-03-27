package com.tapakila.Tapakila_Backend.Services;

import com.tapakila.Tapakila_Backend.Entity.Event;
import com.tapakila.Tapakila_Backend.Repository.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventDAO eventDAO;

    @Autowired
    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public List<Event> getAllEvents() {
        return eventDAO.getAll();
    }

    public Event getEventById(String eventId) {
        return eventDAO.getById(eventId);
    }

    public Event createEvent(Event event) {
        event.setEventId(UUID.randomUUID().toString()); // UUID generation
        eventDAO.save(event);
        return event;
    }

    public Event updateEvent(String eventId, Event event) {
        Event existingEvent = eventDAO.getById(eventId);
        if (existingEvent == null) {
            throw new RuntimeException("Event with id " + eventId + " not found");
        }
        event.setEventId(eventId);
        eventDAO.update(event);
        return event;
    }

    public void deleteEvent(String eventId) {
        Event existingEvent = eventDAO.getById(eventId);
        if (existingEvent == null) {
            throw new RuntimeException("Event with id " + eventId + " not found");
        }
        eventDAO.delete(eventId);
    }
}
