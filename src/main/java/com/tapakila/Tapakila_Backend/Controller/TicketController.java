package com.tapakila.Tapakila_Backend.Controller;

import com.tapakila.Tapakila_Backend.Entity.Enum.TicketType;
import com.tapakila.Tapakila_Backend.Entity.Ticket;
import com.tapakila.Tapakila_Backend.Repository.TicketDAO;
import com.tapakila.Tapakila_Backend.Repository.TicketDAOImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketDAOImpl ticketDAO;

    public TicketController(TicketDAOImpl ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketDAO.getTicketsList());
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketDAO.createTicket(ticket));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String ticketId) {
        Ticket ticket = ticketDAO.getTicketsById(ticketId);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable String ticketId, @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketDAO.updateTicket(ticketId, ticket));
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable String ticketId) {
        ticketDAO.deleteTicket(ticketId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getTicketCount(@RequestParam TicketType ticketType) {
        return ResponseEntity.ok(ticketDAO.countAvailableTicket(ticketType));
    }
}
