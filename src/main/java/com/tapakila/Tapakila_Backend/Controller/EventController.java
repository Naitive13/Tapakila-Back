package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.Event;
import com.tapakila.Tapakila_Backend.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "date", required = false) String dateFilter) {
        return ResponseEntity.ok(eventService.getAllEvents(status, dateFilter));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") String eventId) {
        Event event = eventService.getEventById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") // Reserved for admins
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Event> updateEvent(@PathVariable("id") String eventId, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(eventId, event);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Event> deleteEvent(@PathVariable("id") String eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> updateEventStatus(@PathVariable("id") String eventId, @RequestBody EventStatusRequest statusRequest) {
        eventService.updateEventStatus(eventId, statusRequest.getEventStatus());
        return ResponseEntity.ok().build();
    }

    // Class for status update request
    class EventStatusRequest {
        private String eventStatus;

        public String getEventStatus() {
            return eventStatus;
        }

        public void setEventStatus(String eventStatus) {
            this.eventStatus = eventStatus;
        }
    }
}

