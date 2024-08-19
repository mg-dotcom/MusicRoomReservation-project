package com.example.demo.entities;

import com.example.demo.entities.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "reservationId")
    private Integer id;
    private String phoneNumber;
    private Date date;
    @CreationTimestamp
    @Column(nullable = false, updatable = false ,insertable = false)
    private ZonedDateTime createAt;
    @ManyToOne
    @JoinColumn(name = "timeSlotId", nullable = false)
    private TimeSlot timeSlot;
    @ManyToOne
    @JoinColumn(name = "musicRoomId", nullable = false)
    private MusicRoom musicRoom;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
