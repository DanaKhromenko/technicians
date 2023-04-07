package com.gmail.danadiadius.technicians.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="technicians")
public class Technician extends User {
    @NotBlank(message = "Desired position can't be blank!")
    @Size(min = 5, max = 100)
    private String desiredPosition;

    @OneToMany
    private List<PortfolioProject> portfolioProjects;

    public Technician(String name, String email, String password, String desiredPosition,
                      List<PortfolioProject> portfolioProjects) {
        super(name, email, password);
        this.desiredPosition = desiredPosition;
        this.portfolioProjects = portfolioProjects;
    }

    public Technician(Long id, String name, String email, String password, String desiredPosition,
                      List<PortfolioProject> portfolioProjects) {
        this(name, email, password, desiredPosition, portfolioProjects);
        setId(id);
    }

    @Override
    public String toString() {
        return "Technician{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", desiredPosition='" + desiredPosition + '\'' +
                ", portfolioProjects=" + portfolioProjects +
                '}';
    }
}
