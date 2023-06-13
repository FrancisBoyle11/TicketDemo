package com.example.demo.repository;

import com.example.demo.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends JpaRepository<Train, String> {
    @Query("select t from Train t")
    Optional<Train> findByTrainNumber();
}
