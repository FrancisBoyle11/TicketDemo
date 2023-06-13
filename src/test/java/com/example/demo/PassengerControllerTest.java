package com.example.demo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.demo.controller.PassengerController;
import com.example.demo.model.Passenger;
import com.example.demo.service.PassengerCRUDOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

class PassengerControllerTest {
    @Mock
    private PassengerCRUDOperations crudOperations;

    @InjectMocks
    private PassengerController passengerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPassenger_ValidPassenger_ReturnsCreatedPassenger() {
        Passenger passenger = new Passenger("John Doe");
        when(crudOperations.addPassenger(any(Passenger.class))).thenReturn(passenger);

        ResponseEntity<Passenger> response = passengerController.createPassenger(passenger);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(passenger, response.getBody());
        verify(crudOperations, times(1)).addPassenger(any(Passenger.class));
    }



    @Test
    void getPassengerById_ExistingPassenger_ReturnsPassenger() {
        Long passengerId = 1L;
        Passenger passenger = new Passenger("John Doe");
        when(crudOperations.getPassengerById(passengerId)).thenReturn(passenger);

        ResponseEntity<Passenger> response = passengerController.getPassengerById(passengerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(passenger, response.getBody());
        verify(crudOperations, times(1)).getPassengerById(passengerId);
    }

    @Test
    void getPassengerById_NonExistentPassenger_ReturnsNotFound() {
        Long passengerId = 1L;
        when(crudOperations.getPassengerById(passengerId)).thenReturn(null);

        ResponseEntity<Passenger> response = passengerController.getPassengerById(passengerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getPassengerById(passengerId);
    }

    @Test
    void updatePassenger_ExistingPassenger_ReturnsUpdatedPassenger() {
        Long passengerId = 1L;
        Passenger existingPassenger = new Passenger("John Doe");
        Passenger updatedPassenger = new Passenger("Jane Smith");
        when(crudOperations.getPassengerById(passengerId)).thenReturn(existingPassenger);
        when(crudOperations.updatePassenger(any(Passenger.class))).thenReturn(updatedPassenger);

        ResponseEntity<Passenger> response = passengerController.updatePassenger(passengerId, updatedPassenger);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPassenger, response.getBody());
        verify(crudOperations, times(1)).getPassengerById(passengerId);
        verify(crudOperations, times(1)).updatePassenger(updatedPassenger);
    }

    @Test
    void updatePassenger_NonExistentPassenger_ReturnsNotFound() {
        Long passengerId = 1L;
        Passenger updatedPassenger = new Passenger("Jane Smith");
        when(crudOperations.getPassengerById(passengerId)).thenReturn(null);

        ResponseEntity<Passenger> response = passengerController.updatePassenger(passengerId, updatedPassenger);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getPassengerById(passengerId);
        verify(crudOperations, never()).updatePassenger(any(Passenger.class));
    }

    @Test
    void deletePassenger_ExistingPassenger_ReturnsNoContent() {
        Long passengerId = 1L;
        Passenger existingPassenger = new Passenger("John Doe");
        when(crudOperations.getPassengerById(passengerId)).thenReturn(existingPassenger);

        ResponseEntity<Void> response = passengerController.deletePassenger(passengerId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getPassengerById(passengerId);
        verify(crudOperations, times(1)).deletePassenger(passengerId);
    }

    @Test
    void deletePassenger_NonExistentPassenger_ReturnsNotFound() {
        Long passengerId = 1L;
        when(crudOperations.getPassengerById(passengerId)).thenReturn(null);

        ResponseEntity<Void> response = passengerController.deletePassenger(passengerId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getPassengerById(passengerId);
        verify(crudOperations, never()).deletePassenger(passengerId);
    }
}
