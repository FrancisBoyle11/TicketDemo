package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.service.TicketCRUDOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketCRUDOperations crudOperations;

    @Autowired
    public TicketController(TicketCRUDOperations crudOperations) {
        this.crudOperations = crudOperations;
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket createdTicket = crudOperations.addTicket(ticket);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) {
        Ticket ticket = crudOperations.getTicketById(ticketId);
        if (ticket != null) {
            return ResponseEntity.ok(ticket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{ticketId}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long ticketId, @RequestBody Ticket ticket) {
        Ticket existingTicket = crudOperations.getTicketById(ticketId);
        if (existingTicket != null) {
            ticket.setTicketId(ticketId);
            Ticket updatedTicket = crudOperations.updateTicket(ticket);
            return ResponseEntity.ok(updatedTicket);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId) {
        Ticket existingTicket = crudOperations.getTicketById(ticketId);
        if (existingTicket != null) {
            crudOperations.deleteTicket(ticketId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

