package com.example.demo.controller;

import com.example.demo.entities.MusicRoom;
import com.example.demo.services.MusicRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping("/rooms")
public class MusicRoomController {
    @Autowired
    private MusicRoomService musicRoomService;

    @GetMapping("/{musicRoomId}")
    public MusicRoom getStatusById(@PathVariable Integer musicRoomId){
        return musicRoomService.getMusicRoomById(musicRoomId);
    }
}
