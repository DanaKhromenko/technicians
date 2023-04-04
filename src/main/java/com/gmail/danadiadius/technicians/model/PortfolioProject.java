package com.gmail.danadiadius.technicians.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="portfolio_projects")
public class PortfolioProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Technician technician;

    @Column(nullable = false)
    @Size(min = 5, max = 100)
    private String name;

    @Column(nullable = false)
    @Size(min = 10, max = 300)
    private String shortProjectDescription;

    @Column(nullable = false)
    @Size(min = 100, max = 3000)
    private String detailedProjectDescription;

    @NotNull
    private String sourceCodeUrl;

    @NotNull
    private String interactiveResultUrl;

    private String pictureUrl;

    @AssertTrue(message = "At least one of sourceCodeUrl or interactiveResultUrl must be filled!")
    private boolean isValid() {
        return (sourceCodeUrl != null && sourceCodeUrl.trim().length() > 5)
                || (interactiveResultUrl != null && interactiveResultUrl.trim().length() > 5);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortProjectDescription() {
        return shortProjectDescription;
    }

    public void setShortProjectDescription(String shortProjectDescription) {
        this.shortProjectDescription = shortProjectDescription;
    }

    public String getDetailedProjectDescription() {
        return detailedProjectDescription;
    }

    public void setDetailedProjectDescription(String detailedProjectDescription) {
        this.detailedProjectDescription = detailedProjectDescription;
    }

    public String getSourceCodeUrl() {
        return sourceCodeUrl;
    }

    public void setSourceCodeUrl(String sourceCodeUrl) {
        this.sourceCodeUrl = sourceCodeUrl;
    }

    public String getInteractiveResultUrl() {
        return interactiveResultUrl;
    }

    public void setInteractiveResultUrl(String interactiveResultUrl) {
        this.interactiveResultUrl = interactiveResultUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Override
    public String toString() {
        return "PortfolioProject{" +
                "id=" + id +
                ", technician=" + technician +
                ", name='" + name + '\'' +
                ", shortProjectDescription='" + shortProjectDescription + '\'' +
                ", detailedProjectDescription='" + detailedProjectDescription + '\'' +
                ", sourceCodeUrl='" + sourceCodeUrl + '\'' +
                ", interactiveResultUrl='" + interactiveResultUrl + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                '}';
    }
}
