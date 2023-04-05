package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Tool;

import java.util.List;

public interface PortfolioProjectService extends GenericService<PortfolioProject> {
    List<PortfolioProject> getAllByTechnician(Long technicianId);

    void addToolToPortfolioProject(Tool tool, PortfolioProject portfolioProject);

    void removeToolFromPortfolioProject(Tool tool, PortfolioProject portfolioProject);
}
