package com.example.demo.repositories;


import com.example.demo.entities.MusicRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRoomRepository extends JpaRepository<MusicRoom,Integer> {
}
