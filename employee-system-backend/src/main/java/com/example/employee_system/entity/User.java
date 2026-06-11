package com.example.employee_system.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id ;
    public String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String role;


}
