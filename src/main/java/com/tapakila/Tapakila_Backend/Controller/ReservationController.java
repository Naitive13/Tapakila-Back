package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.InsertReservationAndTicket;
import com.tapakila.Tapakila_Backend.Entity.Reservation;
import com.tapakila.Tapakila_Backend.Repository.ReservationDAO;
import com.tapakila.Tapakila_Backend.Repository.ReservationDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationDAO reservationDAO;

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

    @PostMapping("/create")
    @PreAuthorize("hasRole('STANDARD')")
    public ResponseEntity<Object> createReservation(@RequestBody InsertReservationAndTicket toCreate) {
        try {
            InsertReservationAndTicket reservation = reservationDAO.createReservationAndTicket(toCreate);
            return ResponseEntity.ok(reservation);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
