package com.example.demo.controller;

import com.example.demo.model.Passenger;
import com.example.demo.model.Ticket;
import com.example.demo.service.PassengerCRUDOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerCRUDOperations passengerCRUDOperations;

    @Autowired
    public PassengerController(PassengerCRUDOperations passengerCRUDOperations) {
        this.passengerCRUDOperations = passengerCRUDOperations;
    }

    @PostMapping
    public ResponseEntity<Passenger> createPassenger(@RequestBody Passenger passenger) {
        Passenger createdPassenger = passengerCRUDOperations.addPassenger(passenger);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPassenger);
    }


    @GetMapping("/{passengerId}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long passengerId) {
        Passenger passenger = passengerCRUDOperations.getPassengerById(passengerId);
        if (passenger != null) {
            return ResponseEntity.ok(passenger);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{passengerId}")
    public ResponseEntity<Passenger> updatePassenger(@PathVariable Long passengerId, @RequestBody Passenger passenger) {
        Passenger existingPassenger = passengerCRUDOperations.getPassengerById(passengerId);
        if (existingPassenger != null) {
            passenger.setPassengerId(passengerId);
            Passenger updatedPassenger = passengerCRUDOperations.updatePassenger(passenger);
            return ResponseEntity.ok(updatedPassenger);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{passengerId}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long passengerId) {
        Passenger existingPassenger = passengerCRUDOperations.getPassengerById(passengerId);
        if (existingPassenger != null) {
            passengerCRUDOperations.deletePassenger(passengerId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
