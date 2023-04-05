package com.gmail.danadiadius.technicians.controller;

import com.gmail.danadiadius.technicians.model.Employer;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Technician;
import com.gmail.danadiadius.technicians.model.Tool;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DummyController {

    @GetMapping("/")
    public String greeting() {
        return getDummyTechnician() + "\n" + getDummyEmployer();
    }

    private Technician getDummyTechnician() {
        List<PortfolioProject> portfolioProjects = new ArrayList<>();
        portfolioProjects.add(getDummyPortfolioProject());

        return new Technician("Dana Khromenko", "danadiadius@gmail.com", "MyPassword!",
                "Java Developer", portfolioProjects);
    }

    private PortfolioProject getDummyPortfolioProject() {
        return new PortfolioProject("Taxi Service", "The taxi service web application, where " +
                "drivers and cars can be managed.", "The taxi service, that is built within Java" +
                " and Spring framework. It is a web application, where drivers and cars can be managed",
                "https://github.com/DanaKhromenko/technicians",
                "localhost:8082", "www.DanaKhromenkoPic.com",
                getDummyTools());
    }

    private List<Tool> getDummyTools() {
        List<Tool> tools = new ArrayList<>();
        tools.add(new Tool("Java"));
        tools.add(new Tool("Maven"));
        tools.add(new Tool("SQL"));
        tools.add(new Tool("Lombok"));
        return tools;
    }

    private Employer getDummyEmployer() {
        return new Employer("Jane Doe", "jane.doe@dummy.com", "Jane_Doe11");
    }
}
