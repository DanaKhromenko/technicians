package com.gmail.danadiadius.technicians.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 5, max = 100)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @ToString.Exclude
    private String password;

    @Size(max = 1000)
    private String description;

    @Size(max = 50)
    private String country;

    @Size(max = 50)
    private String city;

    @Column(unique = true, nullable = false)
    @Size(max = 20)
    private String phone;

    private byte[] picture;
}
