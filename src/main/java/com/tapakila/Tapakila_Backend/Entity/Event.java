package com.tapakila.Tapakila_Backend.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private String eventId;
    private String eventTitle;
    private EventType eventType;
    private LocalDateTime eventDate;
}
