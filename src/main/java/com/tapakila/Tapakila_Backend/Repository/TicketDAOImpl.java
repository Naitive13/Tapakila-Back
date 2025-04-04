package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Enum.ReservationStatus;
import com.tapakila.Tapakila_Backend.Entity.Enum.TicketType;
import com.tapakila.Tapakila_Backend.Entity.Ticket;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
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
                VALUES(?::UUID, ?::UUID, ?::reservation_status, ?::ticket_type)
                """;

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            String ticketId = toCreate.getTicket_id();
            if (ticketId == null || ticketId.isEmpty()) {
                ticketId = UUID.randomUUID().toString();
                toCreate.setTicket_id(ticketId);
            }

            ps.setString(1, ticketId);
            ps.setString(2, toCreate.getEvent_id());
            ps.setString(3, ReservationStatus.available.name());
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


    @Override
    public List<Ticket> getTicketsList() {
        String sql = """
                    SELECT ticket_id, event_id, reservation_status, ticket_type FROM tickets
                
                """;
        List<Ticket> ticketList = new ArrayList<>();
        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ticketList.add(new Ticket(rs.getString("ticket_id"), rs.getString("event_id"), ReservationStatus.valueOf(rs.getString("reservation_status")), TicketType.valueOf(rs.getString("ticket_type"))));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving ticket list: ", e);
        }

        return ticketList;
    }

    @Override
    public void deleteTicket(String id) {
        String sql = """
                    DELETE FROM tickets WHERE ticket_id = ?
                """;

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new RuntimeException("No ticket found with the ID: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Ticket updateTicket(String id, Ticket toUpdate) {
        String sql = """
                UPDATE tickets SET reservation_status = ?, ticket_type = ? WHERE ticket_id = ?
                """;
        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, toUpdate.getReservationStatus().name());
            ps.setString(2, toUpdate.getTicketType().name());
            ps.setString(3, id);


            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No ticket found with the ID: " + id);
            }

            return toUpdate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateTicketStatus(String id, ReservationStatus status) {
        String sql = """
                UPDATE tickets SET reservation_status = ?::reservation_status WHERE ticket_id = ?::UUID
                """;
        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status.toString());
            ps.setString(2, id);


            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("No ticket found with the ID: " + id);
            }

            return "Ticked " + id + "has been reserved";
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    @Override
    public Ticket getTicketsById(String ticketID) {
        String sql = """
                                    SELECT ticket_id, event_id, reservation_status, ticket_type FROM tickets 
                WHERE ticket_id = ?::UUID   
                
                """;
        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, ticketID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Ticket(rs.getString("ticket_id"), rs.getString("event_id"), ReservationStatus.valueOf(rs.getString("reservation_status")), TicketType.valueOf(rs.getString("ticket_type")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving ticket list: ", e);
        }
        return null;
    }

    @Override
    public Integer countAvailableTicket(TicketType ticketType) {
        String sql = """
                    SELECT COUNT(*) FROM tickets WHERE ticket_type = ?::ticket_type AND reservation_status = 'available'
                """;

        try (Connection connection = datasource.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, ticketType.name());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error counting available tickets", e);
        }

        return 0;
    }
}
