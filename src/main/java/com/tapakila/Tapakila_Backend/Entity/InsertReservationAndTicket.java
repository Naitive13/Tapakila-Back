package com.tapakila.Tapakila_Backend.Entity;

import com.tapakila.Tapakila_Backend.Entity.Enum.ReservationStatus;
import com.tapakila.Tapakila_Backend.Entity.Enum.TicketType;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertReservationAndTicket {
    private String userID;
    private LocalDateTime reservationDate;
    private String event_id;
    private ReservationStatus reservationStatus;
    private TicketType ticketType;
}
