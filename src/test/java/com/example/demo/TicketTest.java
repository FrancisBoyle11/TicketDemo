package com.example.demo;

import com.example.demo.controller.TicketController;
import com.example.demo.model.Ticket;
import com.example.demo.service.CRUDOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketControllerTest {
    @Mock
    private CRUDOperations crudOperations;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTicket_ValidTicket_ReturnsCreatedTicket() {
        Ticket ticket = new Ticket();
        when(crudOperations.addTicket(any(Ticket.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.createTicket(ticket);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(crudOperations, times(1)).addTicket(any(Ticket.class));
    }

    @Test
    void getTicketById_ExistingTicket_ReturnsTicket() {
        Long ticketId = 1L;
        Ticket ticket = new Ticket();
        when(crudOperations.getTicketById(ticketId)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
        verify(crudOperations, times(1)).getTicketById(ticketId);
    }

    @Test
    void getTicketById_NonExistentTicket_ReturnsNotFound() {
        Long ticketId = 1L;
        when(crudOperations.getTicketById(ticketId)).thenReturn(null);

        ResponseEntity<Ticket> response = ticketController.getTicketById(ticketId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getTicketById(ticketId);
    }

    @Test
    void updateTicket_ExistingTicket_ReturnsUpdatedTicket() {
        Long ticketId = 1L;
        Ticket existingTicket = new Ticket();
        Ticket updatedTicket = new Ticket();
        when(crudOperations.getTicketById(ticketId)).thenReturn(existingTicket);
        when(crudOperations.updateTicket(any(Ticket.class))).thenReturn(updatedTicket);

        ResponseEntity<Ticket> response = ticketController.updateTicket(ticketId, updatedTicket);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTicket, response.getBody());
        verify(crudOperations, times(1)).getTicketById(ticketId);
        verify(crudOperations, times(1)).updateTicket(updatedTicket);
    }

    @Test
    void updateTicket_NonExistentTicket_ReturnsNotFound() {
        Long ticketId = 1L;
        Ticket updatedTicket = new Ticket();
        when(crudOperations.getTicketById(ticketId)).thenReturn(null);

        ResponseEntity<Ticket> response = ticketController.updateTicket(ticketId, updatedTicket);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getTicketById(ticketId);
        verify(crudOperations, never()).updateTicket(any(Ticket.class));
    }

    @Test
    void deleteTicket_ExistingTicket_ReturnsNoContent() {
        Long ticketId = 1L;
        Ticket existingTicket = new Ticket();
        when(crudOperations.getTicketById(ticketId)).thenReturn(existingTicket);

        ResponseEntity<Void> response = ticketController.deleteTicket(ticketId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getTicketById(ticketId);
        verify(crudOperations, times(1)).deleteTicket(ticketId);
    }

    @Test
    void deleteTicket_NonExistentTicket_ReturnsNotFound() {
        Long ticketId = 1L;
        when(crudOperations.getTicketById(ticketId)).thenReturn(null);

        ResponseEntity<Void> response = ticketController.deleteTicket(ticketId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getTicketById(ticketId);
        verify(crudOperations, never()).deleteTicket(ticketId);
    }
}

