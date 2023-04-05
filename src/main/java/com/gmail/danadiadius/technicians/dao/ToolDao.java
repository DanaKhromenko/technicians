package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.model.Tool;

import java.util.List;

public interface ToolDao extends GenericDao<Tool> {
    List<Tool> getAllByPortfolioProject(Long portfolioProjectId);
}
