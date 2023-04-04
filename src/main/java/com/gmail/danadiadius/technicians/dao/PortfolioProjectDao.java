package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.model.PortfolioProject;

import java.util.List;

public interface PortfolioProjectDao extends GenericDao<PortfolioProject> {
    List<PortfolioProject> getAllByTechnician(Long TechnicianId);
}
