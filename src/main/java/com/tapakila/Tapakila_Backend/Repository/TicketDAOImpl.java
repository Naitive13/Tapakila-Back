package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Enum.ReservationStatus;
import com.tapakila.Tapakila_Backend.Entity.Enum.TIcketType;
import com.tapakila.Tapakila_Backend.Entity.Reservation;
import com.tapakila.Tapakila_Backend.Entity.Ticket;
import org.springframework.security.access.prepost.PreAuthorize;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class TicketDAOImpl implements TicketDAO {
    private final Datasource datasource;

    public TicketDAOImpl(Datasource datasource) {
        this.datasource = datasource;
    }

//
//    CREATE TABLE tickets(
//            ticket_id UUID PRIMARY KEY ,
//            event_id UUID references events(event_id) ON DELETE CASCADE,
//    reservation_status reservation_status,
//    ticket_type ticket_type
//);

//    private String ticket_id;
//    private String event_id;
//    private ReservationStatus reservationStatus;
//    private TIcketType tIcketType;


    @Override
    public Ticket createTicket(Ticket toCreate) {
        String sql = """
                INSERT INTO tickets(ticket_id, event_id, reservation_status, ticket_type) 
                VALUES(?, ?, ?::reservation_status, ?::ticket_type)
                """;

        try (Connection connection = datasource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, toCreate.getTicket_id());
            ps.setString(2, toCreate.getEvent_id());
            ps.setString(3, toCreate.getReservationStatus().name());
            ps.setString(4, toCreate.getTicketType().name());

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted == 0) {
                throw new RuntimeException("Ticket creation failed :< !!! , no rows affected !!!!");
            }

            return toCreate;
        } catch (Exception e) {
            throw new RuntimeException("Error while creating ticket T _ T", e);
        }
    }
}
