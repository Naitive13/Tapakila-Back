package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Reservation;

import java.util.List;

public interface ReservationDAO {
    List<Reservation> getAll();

    public Reservation createReservation(Reservation toCreate);
}
