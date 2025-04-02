package com.tapakila.Tapakila_Backend.Entity;


import com.tapakila.Tapakila_Backend.Entity.Enum.ReservationStatus;
import com.tapakila.Tapakila_Backend.Entity.Enum.TicketType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Ticket {
    private String ticket_id;
    private String event_id;
    private ReservationStatus reservationStatus;
    private TicketType ticketType;
}
