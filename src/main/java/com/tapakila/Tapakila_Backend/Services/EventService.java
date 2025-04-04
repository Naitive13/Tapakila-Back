package com.tapakila.Tapakila_Backend.Services;

import com.tapakila.Tapakila_Backend.Entity.Enum.EventStatus;
import com.tapakila.Tapakila_Backend.Entity.Event;
import com.tapakila.Tapakila_Backend.Repository.EventDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventDAO eventDAO;

    @Autowired
    public EventService(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public List<Event> getAllEvents(String status, String dateFilter, String title) {
        return eventDAO.getAll(status, dateFilter, title);
    }

    public Event getEventById(String eventId) {
        return eventDAO.getById(eventId);
    }

    public Event createEvent(Event event) {
        event.setEventId(UUID.randomUUID().toString()); // UUID generation
        if (event.getEventStatus() == null) {
            event.setEventStatus(EventStatus.UPCOMING); // Default Status
        }
        validateEventPoster(event.getEventPoster());
        eventDAO.save(event);
        return event;
    }

    public Event updateEvent(String eventId, Event event) {
        Event existingEvent = eventDAO.getById(eventId);
        if (existingEvent == null) {
            throw new RuntimeException("Event with id " + eventId + " not found");
        }
        event.setEventId(eventId);
        validateEventPoster(event.getEventPoster());
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

    public void updateEventStatus(String eventId, String status) {
        Event existingEvent = eventDAO.getById(eventId);
        if (existingEvent == null) {
            throw new RuntimeException("Event with id " + eventId + " not found");
        }
        eventDAO.updateStatus(eventId, status);
    }

    private void validateEventPoster(String eventPoster) {
        if (eventPoster != null && !eventPoster.isEmpty()) {
            try {
                new URL(eventPoster); // Checks if it's a valid URL
            } catch (MalformedURLException e) {
                throw new RuntimeException("Event image URL is invalid", e);
            }
        }
    }
}
