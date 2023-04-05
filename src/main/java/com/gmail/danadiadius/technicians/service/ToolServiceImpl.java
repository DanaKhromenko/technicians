package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.dao.ToolDao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.Tool;

import java.util.List;

@Service
public class ToolServiceImpl implements ToolService {
    @Inject
    private ToolDao toolDao;

    @Override
    public Tool create(Tool tool) {
        return toolDao.create(tool);
    }

    @Override
    public Tool get(Long id) {
        return toolDao.get(id).get();
    }

    @Override
    public List<Tool> getAll() {
        return toolDao.getAll();
    }

    @Override
    public Tool update(Tool tool) {
        return toolDao.update(tool);
    }

    @Override
    public boolean delete(Long id) {
        return toolDao.delete(id);
    }

    @Override
    public List<Tool> getAllByPortfolioProject(Long portfolioProjectId) {
        return toolDao.getAllByPortfolioProject(portfolioProjectId);
    }
}
