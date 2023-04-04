package com.gmail.danadiadius.technicians.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name="technicians")
public class Technician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Desired position can't be blank!")
    private String desiredPosition;

    @OneToMany
    private List<PortfolioProject> portfolioProjects;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDesiredPosition() {
        return desiredPosition;
    }

    public void setDesiredPosition(String desiredPosition) {
        this.desiredPosition = desiredPosition;
    }

    public List<PortfolioProject> getPortfolioProjects() {
        return portfolioProjects;
    }

    public void setPortfolioProjects(List<PortfolioProject> portfolioProjects) {
        this.portfolioProjects = portfolioProjects;
    }

    @Override
    public String toString() {
        return "Technician{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", desiredPosition='" + desiredPosition + '\'' +
                ", portfolioProjects=" + portfolioProjects +
                '}';
    }
}
