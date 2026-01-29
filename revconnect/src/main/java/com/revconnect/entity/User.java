package com.revconnect.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String bio;
    private String location;
    private String website;

    @Enumerated(EnumType.STRING)
    private Role role; // PERSONAL, CREATOR, BUSINESS
}
