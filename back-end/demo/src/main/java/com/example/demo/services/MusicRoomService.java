package com.example.demo.services;

import com.example.demo.entities.MusicRoom;
import com.example.demo.repositories.MusicRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MusicRoomService {
    @Autowired
    private MusicRoomRepository musicRoomRepository;

    public MusicRoom getMusicRoomById(Integer musicRoomId) {
        return musicRoomRepository.findById(musicRoomId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "MusicRoom not found with ID " + musicRoomId
                )
        );
    }


}
