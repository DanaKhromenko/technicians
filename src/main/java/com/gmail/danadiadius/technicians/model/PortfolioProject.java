package com.gmail.danadiadius.technicians.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="portfolio_projects")
public class PortfolioProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @OneToMany
    @Size(max = 50)
    private List<Tool> tools;

    @AssertTrue(message = "At least one of sourceCodeUrl or interactiveResultUrl must be filled!")
    private boolean isValid() {
        return (sourceCodeUrl != null && sourceCodeUrl.trim().length() > 5)
                || (interactiveResultUrl != null && interactiveResultUrl.trim().length() > 5);
    }

    public PortfolioProject(String name, String shortProjectDescription, String detailedProjectDescription,
                            String sourceCodeUrl, String interactiveResultUrl, String pictureUrl, List<Tool> tools) {
        this.name = name;
        this.shortProjectDescription = shortProjectDescription;
        this.detailedProjectDescription = detailedProjectDescription;
        this.sourceCodeUrl = sourceCodeUrl;
        this.interactiveResultUrl = interactiveResultUrl;
        this.pictureUrl = pictureUrl;
        this.tools = tools;
    }
}
