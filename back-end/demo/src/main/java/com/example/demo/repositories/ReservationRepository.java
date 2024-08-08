package com.example.demo.repositories;


import com.example.demo.entities.Reservation;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
