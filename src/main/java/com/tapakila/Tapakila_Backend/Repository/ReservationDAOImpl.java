package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Repository
public class ReservationDAOImpl implements ReservationDAO {

    @Autowired
    private Datasource datasource;


    @Override
    public List<Reservation> getAll() {
        String sql = """
                SELECT reservation_id, user_id, ticket_id, reservation_date from reservation_details
                """;
        List<Reservation> reservationList = new ArrayList<>();

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                reservationList.add(new Reservation(rs.getString("reservation_id"),
                        rs.getString("user_id"), rs.getString("ticket_id"),
                        rs.getTimestamp("reservation_date").toLocalDateTime()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }


    @Override
    public Reservation createReservation(Reservation toCreate) {
        String sql = """
                INSERT INTO reservations(reservation_id, user_id, ticket_id, reservation_date) 
                VALUES(?::UUID, ?::UUID, ?::UUID, ?)
                """;

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            // Generate UUID if not already set
            String reservationId = toCreate.getReservationID();
            if (reservationId == null || reservationId.isEmpty()) {
                reservationId = UUID.randomUUID().toString();
                toCreate.setReservationID(reservationId);
            }

            ps.setString(1, reservationId);
            ps.setString(2, toCreate.getUserID());
            ps.setString(3, toCreate.getTicketID());
            ps.setTimestamp(4, Timestamp.valueOf(toCreate.getReservationDate()));

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return toCreate;
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
    public Reservation update(String reservationId, Reservation toUpdate) {
        String sql = """
                UPDATE reservations SET reservation_date = ? WHERE reservation_id = ?
                """;

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(toUpdate.getReservationDate()));
            ps.setString(2, reservationId);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("No reservation with ID : " + reservationId);
            }

            return toUpdate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reservation getReservationById(String id) {
        String sql = """
                SELECT reservation_id, user_id, ticket_id, reservation_date from reservation_details WHERE reservation_id = ?::UUID;
                """;
        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                            rs.getString("reservation_id"),
                            rs.getString("user_id"),
                            rs.getString("ticket_id"),
                            rs.getTimestamp("reservation_date").toLocalDateTime());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("No reservation found with the ID: " + id, e);
        }
        return null;
    }

    @Override
    public void deleteReservation(String id) {
        String sql = """
                DELETE FROM Reservations WHERE reservation_id = ?
                """;

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException("No reservation found with the ID: " + id);
            }
        } catch (
                Exception e) {
            throw new RuntimeException(e);
        }
    }
}
