package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.dao.PortfolioProjectDao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.PortfolioProject;

import java.util.List;

@Service
public class PortfolioProjectServiceImpl implements PortfolioProjectService {
    @Inject
    private PortfolioProjectDao portfolioProjectDao;

    @Override
    public List<PortfolioProject> getAllByTechnician(Long technicianId) {
        return portfolioProjectDao.getAllByTechnician(technicianId);
    }

    @Override
    public PortfolioProject create(PortfolioProject portfolioProject) {
        return portfolioProjectDao.create(portfolioProject);
    }

    @Override
    public PortfolioProject get(Long id) {
        return portfolioProjectDao.get(id).get();
    }

    @Override
    public List<PortfolioProject> getAll() {
        return portfolioProjectDao.getAll();
    }

    @Override
    public PortfolioProject update(PortfolioProject portfolioProject) {
        return portfolioProjectDao.update(portfolioProject);
    }

    @Override
    public boolean delete(Long id) {
        return portfolioProjectDao.delete(id);
    }
}
