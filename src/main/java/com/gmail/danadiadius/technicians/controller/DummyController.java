package com.gmail.danadiadius.technicians.controller;

import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Technician;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DummyController {

    @GetMapping("/")
    public String greeting() {
        return getDummyTechnician().toString();
    }

    private Technician getDummyTechnician() {
        PortfolioProject portfolioProject = new PortfolioProject();
        portfolioProject.setId(1L);
        portfolioProject.setName("Taxi Service");
        portfolioProject.setShortProjectDescription(
                "The taxi service web application, where drivers and cars can be managed.");
        portfolioProject.setDetailedProjectDescription(
                "The taxi service, that is built within Java and Spring framework. It is a web application, where drivers and cars can be managed");

        List<PortfolioProject> portfolioProjects = new ArrayList<>();
        portfolioProjects.add(portfolioProject);

        Technician technician = new Technician();
        technician.setId(1L);
        technician.setEmail("danadiadius@gmail.com");
        technician.setDesiredPosition("Java Developer");
        technician.setPortfolioProjects(portfolioProjects);

        return technician;
    }
}
