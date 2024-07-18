package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "music_room")
public class MusicRoom {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "musicRoomId")
    private Integer id;
    private String status;
    private String name;
    private Integer floor;
    private String building;
    private String musicalInstrument;
    private Integer minCapacity;
    private Integer maxCapacity;
    private String feature;
}
