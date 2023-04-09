package com.gmail.danadiadius.technicians.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="employers")
public class Employer extends User {
    private boolean isHiring;

    @Size(max = 50)
    private String companyName;

    @Size(max = 100)
    private String currentPosition;

    public Employer(User user) {
        setName(user.getName());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setDescription(user.getDescription());
        setCountry(user.getCountry());
        setCity(user.getCity());
        setPhone(user.getPhone());
    }
}
