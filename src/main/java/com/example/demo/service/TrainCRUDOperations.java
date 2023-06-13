package com.example.demo.service;

import com.example.demo.model.Train;
import com.example.demo.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TrainCRUDOperations {

    private final TrainRepository trainRepository;

    @Autowired
    public TrainCRUDOperations(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train createTrain(Train train) {
        return trainRepository.save(train);
    }

    public List<Train> getAllTrains() {
        return trainRepository.findAll();
    }

    public Optional<Train> getTrainById(String trainNumber) {
        return trainRepository.findById(trainNumber);
    }

    public Train updateTrain(Train train) {
        return trainRepository.save(train);
    }

    public boolean deleteTrain(String trainNumber) {
        if (trainRepository.existsById(trainNumber)) {
            trainRepository.deleteById(trainNumber);
            return true;
        }
        return false;
    }

    public Train getTrainByNumber(String trainNumber) {
        return trainRepository.findByTrainNumber().orElse(null);
    }

    public Train addTrain(Train train) {
        return trainRepository.save(train);
    }
}
