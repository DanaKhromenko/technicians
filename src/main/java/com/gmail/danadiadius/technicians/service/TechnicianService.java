package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Technician;

import java.util.Optional;

public interface TechnicianService extends GenericService<Technician> {
    Optional<Technician> findByEmail(String email);

    void addPortfolioProjectToTechnician(PortfolioProject portfolioProject, Technician technician);

    void removePortfolioProjectFromTechnician(PortfolioProject portfolioProject, Technician technician);
}
