package com.gmail.danadiadius.technicians.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Currency;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="technicians")
public class Technician extends User {
    private boolean openToWork;

    @NotBlank(message = "Desired position can't be blank!")
    @Size(min = 5, max = 100)
    private String desiredPosition;

    private int desiredAnnualSalary;

    private Currency annualSalaryCurrency;

    @OneToMany
    private List<PortfolioProject> portfolioProjects;
}
