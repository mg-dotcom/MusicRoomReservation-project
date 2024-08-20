package com.example.demo.repositories;


import com.example.demo.entities.MusicRoom;
import com.example.demo.entities.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot,Integer>{

}
