package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.model.PortfolioProject;

import java.util.List;

public interface PortfolioProjectService extends GenericService<PortfolioProject> {
    List<PortfolioProject> getAllByTechnician(Long TechnicianId);
}
