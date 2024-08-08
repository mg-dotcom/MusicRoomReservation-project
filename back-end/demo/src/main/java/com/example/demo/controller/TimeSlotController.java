package com.example.demo.controller;

import com.example.demo.entities.TimeSlot;
import com.example.demo.repositories.TimeSlotRepository;
import com.example.demo.services.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping("")
    private List<TimeSlot> getAllTimeSlots() {
        return timeSlotService.getAllTimeSlot();
    }

    @GetMapping("/{timeSlotId}")
    public ResponseEntity<TimeSlot> getTimeSlotById(@PathVariable Integer timeSlotId) {
        TimeSlot timeSlot = timeSlotService.getTimeSlotById(timeSlotId);
        return ResponseEntity.ok(timeSlot);
    }
}
