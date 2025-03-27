package com.tapakila.Tapakila_Backend.Entity;


import com.tapakila.Tapakila_Backend.Entity.Enum.ReservationStatus;
import com.tapakila.Tapakila_Backend.Entity.Enum.TIcketType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {
    private String ticket_id;
    private String event_id;
    private ReservationStatus reservationStatus;
    private TIcketType tIcketType;
}
