package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.Reservation;
import com.tapakila.Tapakila_Backend.Repository.ReservationDAOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationDAOImpl reservationDAO = new ReservationDAOImpl();

    // Get all reservations (Only accessible by ADMIN)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reservation>> getAll() {
        List<Reservation> reservationList = reservationDAO.getAll();
        return ResponseEntity.ok(reservationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable String id) {
        Reservation reservation = reservationDAO.getReservationById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reservation);
    }

    @PostMapping
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation toCreate) {
        Reservation reservation = reservationDAO.createReservation(toCreate);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Reservation> updateReservation(@PathVariable String id, @RequestBody Reservation toUpdate) {
        try {
            Reservation updatedReservation = reservationDAO.update(id, toUpdate);
            return ResponseEntity.ok(updatedReservation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) {
        try {
            reservationDAO.deleteReservation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
