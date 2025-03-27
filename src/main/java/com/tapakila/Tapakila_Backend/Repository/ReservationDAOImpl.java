package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAOImpl implements ReservationDAO {
    private Datasource datasource;

    @Override
    public List<Reservation> getAll() {
        String sql = """
                SELECT reservation_id, user_id, ticket_id, reservation_date from reservation_details;
                """;

        List<Reservation> reservationList = new ArrayList<>();

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                reservationList.add(new Reservation(rs.getString("reservation_id"), rs.getString("user_id"), rs.getString("ticket_id"), rs.getTimestamp("reservation_date").toLocalDateTime()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }

//
//    CREATE TABLE reservations(
//            reservation_id UUID PRIMARY KEY ,
//            event_id UUID references events(event_id) ON DELETE CASCADE,
//    user_id UUID references users(user_id) ON DELETE CASCADE,
//    ticket_id UUID references tickets(ticket_id) ON DELETE CASCADE,
//    reservation_date TIMESTAMP without TIME ZONE
//);

    @Override
    public Reservation createReservation(Reservation toCreate) {

        String sql = """
                INSERT INTO reservations(reservation_id, user_id, ticket_id, reservation_date) 
                VALUES(?, ?, ?, ?)
                """;

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, toCreate.getReservationID());
            ps.setString(2, toCreate.getUserID());
            ps.setString(3, toCreate.getTicketID());
            ps.setTimestamp(4, Timestamp.valueOf(toCreate.getReservationDate()));

            ps.executeUpdate();
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
        return new Reservation();
    }
}
