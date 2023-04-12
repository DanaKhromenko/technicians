package com.gmail.danadiadius.technicians.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Currency;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="technicians")
public class Technician extends User {
    private boolean openToWork;

    @Size(min = 5, max = 100)
    private String desiredPosition;

    private int desiredAnnualSalary;

    private Currency annualSalaryCurrency;

    @OneToMany
    private List<PortfolioProject> portfolioProjects;

    public Technician(User user) {
        setName(user.getName());
        setEmail(user.getEmail());
        setPassword(user.getPassword());
        setDescription(user.getDescription());
        setCountry(user.getCountry());
        setCity(user.getCity());
        setPhone(user.getPhone());
    }
}
