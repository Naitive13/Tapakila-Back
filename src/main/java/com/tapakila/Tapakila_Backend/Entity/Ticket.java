package com.tapakila.Tapakila_Backend.Entity;


import com.tapakila.Tapakila_Backend.Entity.Enum.ReservationStatus;
import com.tapakila.Tapakila_Backend.Entity.Enum.TicketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private String ticket_id;
    private String event_id;
    private ReservationStatus reservationStatus;
    private TicketType ticketType;
}
