package com.tapakila.Tapakila_Backend.Entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket {
    private String ticket_id;
    private String user_id;
    private ReservationStatus reservationStatus;
    private TIcketType tIcketType;
}
