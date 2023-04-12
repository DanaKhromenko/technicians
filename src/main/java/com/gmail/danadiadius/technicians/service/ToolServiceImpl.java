package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.dao.ToolDao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Tool;

import java.util.List;

@Service
public class ToolServiceImpl implements ToolService {
    @Inject
    private ToolDao toolDao;

    @Override
    public Tool create(Tool tool) {
        standardizeName(tool);
        return toolDao.create(tool);
    }

    @Override
    public Tool get(Long id) {
        return toolDao.get(id).orElseThrow();
    }

    @Override
    public List<Tool> getAll() {
        return toolDao.getAll();
    }

    @Override
    public Tool update(Tool tool) {
        standardizeName(tool);
        return toolDao.update(tool);
    }

    @Override
    public boolean delete(Long id) {
        return toolDao.delete(id);
    }

    private void standardizeName(Tool tool) {
        tool.setName(tool.getName().toUpperCase());
    }

    @Override
    public List<Tool> getAllToolsByPortfolioProject(Long portfolioProjectId) {
        return toolDao.getAllByPortfolioProject(portfolioProjectId);
    }

    @Override
    public void updatePortfolioProjectsTools(PortfolioProject portfolioProject) {
        toolDao.updatePortfolioProjectsTools(portfolioProject);
    }
}
