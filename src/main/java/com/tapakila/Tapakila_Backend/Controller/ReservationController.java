package com.tapakila.Tapakila_Backend.Controller;


import com.tapakila.Tapakila_Backend.Entity.Reservation;
import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Repository.ReservationDAOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {
    private ReservationDAOImpl reservationDAO = new ReservationDAOImpl();

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getAll() {
        List<Reservation> reservationList = reservationDAO.getAll();
        return ResponseEntity.ok(reservationList);
    }
}
