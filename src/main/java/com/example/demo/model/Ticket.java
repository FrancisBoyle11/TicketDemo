package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    @ManyToOne
    @JoinColumn(name = "passengerId")
    private Passenger passenger;

    @ManyToOne
    @JoinColumn(name = "trainNumber")
    private Train train;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    public Ticket() {
    }

    public Ticket(Passenger passenger, Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.passenger = passenger;
        this.train = train;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
