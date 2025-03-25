package com.tapakila.Tapakila_Backend.Entity;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Reservation {
    private String reservationID;
    private String ticketID;
    private String userID;
    private LocalDateTime reservationDate;
}
