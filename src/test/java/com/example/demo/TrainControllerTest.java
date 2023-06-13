package com.example.demo;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import com.example.demo.controller.TrainController;
import com.example.demo.model.Train;
import com.example.demo.service.TrainCRUDOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;

class TrainControllerTest {
    @Mock
    private TrainCRUDOperations crudOperations;

    @InjectMocks
    private TrainController trainController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTrain_ValidTrain_ReturnsCreatedTrain() {
        Train train = new Train("T123", "Station A", "Station B");
        when(crudOperations.addTrain(any(Train.class))).thenReturn(train);

        ResponseEntity<Train> response = trainController.createTrain(train);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(train, response.getBody());
        verify(crudOperations, times(1)).addTrain(any(Train.class));
    }

    @Test
    void getTrainByNumber_ExistingTrain_ReturnsTrain() {
        String trainNumber = "T123";
        Train train = new Train("T123", "Station A", "Station B");
        when(crudOperations.getTrainByNumber(trainNumber)).thenReturn(train);

        ResponseEntity<Train> response = trainController.getTrainByNumber(trainNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(train, response.getBody());
        verify(crudOperations, times(1)).getTrainByNumber(trainNumber);
    }

    @Test
    void getTrainByNumber_NonExistentTrain_ReturnsNotFound() {
        String trainNumber = "T123";
        when(crudOperations.getTrainByNumber(trainNumber)).thenReturn(null);

        ResponseEntity<Train> response = trainController.getTrainByNumber(trainNumber);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getTrainByNumber(trainNumber);
    }

    @Test
    void updateTrain_ExistingTrain_ReturnsUpdatedTrain() {
        String trainNumber = "T123";
        Train existingTrain = new Train("T123", "Station A", "Station B");
        Train updatedTrain = new Train("T123", "Station C", "Station D");
        when(crudOperations.getTrainByNumber(trainNumber)).thenReturn(existingTrain);
        when(crudOperations.updateTrain(any(Train.class))).thenReturn(updatedTrain);

        ResponseEntity<Train> response = trainController.updateTrain(trainNumber, updatedTrain);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTrain, response.getBody());
        verify(crudOperations, times(1)).getTrainByNumber(trainNumber);
        verify(crudOperations, times(1)).updateTrain(updatedTrain);
    }

    @Test
    void updateTrain_NonExistentTrain_ReturnsNotFound() {
        String trainNumber = "T123";
        Train updatedTrain = new Train("T123", "Station C", "Station D");
        when(crudOperations.getTrainByNumber(trainNumber)).thenReturn(null);

        ResponseEntity<Train> response = trainController.updateTrain(trainNumber, updatedTrain);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getTrainByNumber(trainNumber);
        verify(crudOperations, never()).updateTrain(any(Train.class));
    }

    @Test
    void deleteTrain_ExistingTrain_ReturnsNoContent() {
        String trainNumber = "T123";
        Train existingTrain = new Train("T123", "Station A", "Station B");
        when(crudOperations.getTrainByNumber(trainNumber)).thenReturn(existingTrain);

        ResponseEntity<Void> response = trainController.deleteTrain(trainNumber);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getTrainByNumber(trainNumber);
        verify(crudOperations, times(1)).deleteTrain(trainNumber);
    }

    @Test
    void deleteTrain_NonExistentTrain_ReturnsNotFound() {
        String trainNumber = "T123";
        when(crudOperations.getTrainByNumber(trainNumber)).thenReturn(null);

        ResponseEntity<Void> response = trainController.deleteTrain(trainNumber);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(crudOperations, times(1)).getTrainByNumber(trainNumber);
        verify(crudOperations, never()).deleteTrain(trainNumber);
    }
}
