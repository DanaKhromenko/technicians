package com.gmail.danadiadius.technicians.model;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name="/employers")
public class Employer extends User {
    public Employer(String name, String email, String password) {
        super(name, email, password);
    }

    public Employer(Long id, String name, String email, String password) {
        this(name, email, password);
        setId(id);
    }

    @Override
    public String toString() {
        return "Employer{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
