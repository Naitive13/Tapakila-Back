package com.tapakila.Tapakila_Backend.Controller;


import com.tapakila.Tapakila_Backend.Entity.Reservation;
import com.tapakila.Tapakila_Backend.Entity.User;
import com.tapakila.Tapakila_Backend.Repository.ReservationDAOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private ReservationDAOImpl reservationDAO = new ReservationDAOImpl();

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getAll() {
        List<Reservation> reservationList = reservationDAO.getAll();
        return ResponseEntity.ok(reservationList);
    }


    @PostMapping
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation toCreate) {
        Reservation reservation = reservationDAO.createReservation(toCreate);
        return ResponseEntity.ok(reservation);
    }
}
