package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Technician;

import java.util.List;

public interface PortfolioProjectService extends GenericService<PortfolioProject> {
    List<PortfolioProject> getAllByTechnician(Long technicianId);

    void updateTechnicianPortfolioProjects(Technician technician);

    void addPortfolioProjectToTechnician(Long technicianId, Long portfolioProjectId);
}
