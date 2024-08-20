package com.example.demo.services;

import com.example.demo.entities.MusicRoom;
import com.example.demo.entities.TimeSlot;
import com.example.demo.repositories.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TimeSlotService {
   @Autowired
   public TimeSlotRepository timeSlotRepository;

    public List<TimeSlot> getAllTimeSlot(){
        return timeSlotRepository.findAll();
    }

    public TimeSlot getTimeSlotById(Integer timeSlotId) {
        return timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() ->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "MusicRoom not found with ID " + timeSlotId
                ));
    }

}
