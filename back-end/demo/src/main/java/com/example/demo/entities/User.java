package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user")
public class User {
    @Id
    @Column(name="userId")
    private Integer id;
    private String email;
    private String password;
    @Column(name="is_admin")
    private boolean isAdmin;
}
