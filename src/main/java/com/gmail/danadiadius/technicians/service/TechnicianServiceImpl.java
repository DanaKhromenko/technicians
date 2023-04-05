package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.dao.TechnicianDao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Technician;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicianServiceImpl implements TechnicianService {
    @Inject
    private TechnicianDao technicianDao;

    @Override
    public Optional<Technician> findByEmail(String email) {
        return technicianDao.findByEmail(email);
    }

    @Override
    public void addPortfolioProjectToTechnician(PortfolioProject portfolioProject, Technician technician) {
        technician.getPortfolioProjects().add(portfolioProject);
        technicianDao.update(technician);
    }

    @Override
    public void removePortfolioProjectFromTechnician(PortfolioProject portfolioProject, Technician technician) {
        technician.getPortfolioProjects().remove(portfolioProject);
        technicianDao.update(technician);
    }

    @Override
    public Technician create(Technician technician) {
        return technicianDao.create(technician);
    }

    @Override
    public Technician get(Long id) {
        return technicianDao.get(id).get();
    }

    @Override
    public List<Technician> getAll() {
        return technicianDao.getAll();
    }

    @Override
    public Technician update(Technician technician) {
        return technicianDao.update(technician);
    }

    @Override
    public boolean delete(Long id) {
        return technicianDao.delete(id);
    }
}
