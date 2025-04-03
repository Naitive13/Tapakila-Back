package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Enum.ReservationStatus;
import com.tapakila.Tapakila_Backend.Entity.Enum.TicketType;
import com.tapakila.Tapakila_Backend.Entity.Ticket;

import java.util.List;

public interface TicketDAO {

    public Ticket createTicket(Ticket toCreate);

    public List<Ticket> getTicketsList();

    public void deleteTicket(String id);

    public Ticket updateTicket(String id, Ticket toUpdate);

    public String updateTicketStatus(String id, ReservationStatus status);

    public Ticket getTicketsById(String ticketID);

    public Integer countAvailableTicket(TicketType ticket);
}
