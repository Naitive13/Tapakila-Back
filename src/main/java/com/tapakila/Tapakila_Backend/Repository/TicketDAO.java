package com.tapakila.Tapakila_Backend.Repository;

import com.tapakila.Tapakila_Backend.Entity.Ticket;

import java.util.List;

public interface TicketDAO {

    public Ticket createTicket(Ticket toCreate);

    public List<Ticket> getTicketsList();
}
