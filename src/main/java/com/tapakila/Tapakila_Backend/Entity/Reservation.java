package com.tapakila.Tapakila_Backend.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    private String reservationID;
    private String ticketID;
    private String userID;
    private LocalDateTime reservationDate;
}
