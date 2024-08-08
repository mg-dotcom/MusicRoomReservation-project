package com.example.demo.controller;

import com.example.demo.entities.MusicRoom;
import com.example.demo.services.MusicRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController

@RequestMapping("/rooms")
public class MusicRoomController {
    @Autowired
    private MusicRoomService musicRoomService;
    @GetMapping("")
    private List<MusicRoom> getAllMusicRoom() {
        return musicRoomService.getAllMusicRoom();
    }

    @GetMapping("/{musicRoomId}")
    public ResponseEntity<MusicRoom> getMusicRoomById(@PathVariable Integer musicRoomId) {
        MusicRoom musicRoom = musicRoomService.getMusicRoomById(musicRoomId);
        return ResponseEntity.ok(musicRoom);
    }

}