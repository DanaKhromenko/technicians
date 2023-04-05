package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.model.Tool;

import java.util.List;

public interface ToolService extends GenericService<Tool> {
    List<Tool> getAllByPortfolioProject(Long portfolioProjectId);
}
