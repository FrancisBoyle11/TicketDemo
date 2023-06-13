package com.example.demo.controller;

import com.example.demo.model.Ticket;
import com.example.demo.model.Train;
import com.example.demo.service.TrainCRUDOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/trains")
public class TrainController {
    private final TrainCRUDOperations trainCRUDOperations;

    @Autowired
    public TrainController(TrainCRUDOperations trainCRUDOperations) {
        this.trainCRUDOperations = trainCRUDOperations;
    }

    @PostMapping
    public ResponseEntity<Train> createTrain(@RequestBody Train train) {
        Train createdTrain = trainCRUDOperations.addTrain(train);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrain);
    }

    @GetMapping("/{trainNumber}")
    public ResponseEntity<Train> getTrainByNumber(@PathVariable String trainNumber) {
        Train train = trainCRUDOperations.getTrainByNumber(trainNumber);
        if (train  != null) {
            return ResponseEntity.ok(train);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{trainNumber}")
    public ResponseEntity<Train> updateTrain(@PathVariable String trainNumber, @RequestBody Train train) {
        Train existingTrain = trainCRUDOperations.getTrainByNumber(trainNumber);
        if (existingTrain != null) {
            train.setTrainNumber(trainNumber);
            Train updatedTrain = trainCRUDOperations.updateTrain(train);
            return ResponseEntity.ok(updatedTrain);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{trainNumber}")
    public ResponseEntity<Void> deleteTrain(@PathVariable String trainNumber) {
        Train existingTicket = trainCRUDOperations.getTrainByNumber(trainNumber);
        if (existingTicket != null) {
            trainCRUDOperations.deleteTrain(trainNumber);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}


