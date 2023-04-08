package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.exception.DataProcessingException;
import com.gmail.danadiadius.technicians.lib.Dao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Tool;
import com.gmail.danadiadius.technicians.service.ToolService;
import com.gmail.danadiadius.technicians.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class PortfolioProjectDaoMySqlImpl implements PortfolioProjectDao {
    @Inject
    private ToolService toolService;

    private static final int PARAMETER_SHIFT = 2; // helps to calculate the query parameter number

    @Override
    public PortfolioProject create(PortfolioProject portfolioProject) {
        String query = "INSERT INTO portfolio_projects (name, short_project_description, " +
                "detailed_project_description, source_code_url, interactive_result_url, picture_url) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, portfolioProject.getName());
            preparedStatement.setString(2, portfolioProject.getShortProjectDescription());
            preparedStatement.setString(3, portfolioProject.getDetailedProjectDescription());
            preparedStatement.setString(4, portfolioProject.getSourceCodeUrl());
            preparedStatement.setString(5, portfolioProject.getInteractiveResultUrl());
            preparedStatement.setString(6, portfolioProject.getPictureUrl());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                portfolioProject.setId(resultSet.getObject(1, Long.class));
            }

            addPortfolioProjectAndToolsRelations(portfolioProject);
            return portfolioProject;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create portfolio project " + portfolioProject, e);
        }
    }

    @Override
    public Optional<PortfolioProject> get(Long id) {
        String selectQuery = "SELECT * FROM portfolio_projects WHERE id = ? AND is_deleted = FALSE";
        PortfolioProject portfolioProject = null;
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                portfolioProject = parsePortfolioProjectFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get a portfolio project by id: " + id, e);
        }
        if (portfolioProject != null) {
            portfolioProject.setTools(toolService.getAllByPortfolioProject(portfolioProject.getId()));
        }
        return Optional.ofNullable(portfolioProject);
    }

    @Override
    public List<PortfolioProject> getAll() {
        String query = "SELECT * FROM portfolio_projects WHERE is_deleted = FALSE";
        List<PortfolioProject> portfolioProjects = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                portfolioProjects.add(parsePortfolioProjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all portfolio projects.", e);
        }

        // Add tools to the Portfolio Projects
        portfolioProjects.forEach(portfolioProject -> portfolioProject.setTools(
                toolService.getAllByPortfolioProject(portfolioProject.getId())));
        return portfolioProjects;
    }

    @Override
    public PortfolioProject update(PortfolioProject portfolioProject) {
        String selectQuery = "UPDATE portfolio_projects SET name = ?, short_project_description = ?, " +
                "detailed_project_description = ?, source_code_url = ?, interactive_result_url = ?, picture_url = ? " +
                "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, portfolioProject.getName());
            preparedStatement.setString(2, portfolioProject.getShortProjectDescription());
            preparedStatement.setString(3, portfolioProject.getDetailedProjectDescription());
            preparedStatement.setString(4, portfolioProject.getSourceCodeUrl());
            preparedStatement.setString(5, portfolioProject.getInteractiveResultUrl());
            preparedStatement.setString(6, portfolioProject.getPictureUrl());
            preparedStatement.setLong(7, portfolioProject.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update portfolio project " + portfolioProject, e);
        }

        // Update Portfolio Project's Tools
        removePortfolioProjectAndToolsOutdatedRelations(portfolioProject);
        addPortfolioProjectAndToolsRelations(portfolioProject);

        return portfolioProject;
    }

    @Override
    public boolean delete(Long id) {
        String selectQuery = "UPDATE portfolio_projects SET is_deleted = TRUE WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete portfolio project by id " + id, e);
        }
    }

    @Override
    public List<PortfolioProject> getAllByTechnician(Long TechnicianId) {
        String query = "SELECT pp.* " +
                "FROM portfolio_projects pp " +
                " INNER JOIN technicians t on pp.technician_id = t.id " +
                "WHERE t.id = ? AND pp.is_deleted = FALSE;";
        List<PortfolioProject> portfolioProjects = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                portfolioProjects.add(parsePortfolioProjectFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all portfolio projects.", e);
        }

        // Add tools to the Portfolio Projects
        portfolioProjects.forEach(portfolioProject -> portfolioProject.setTools(
                toolService.getAllByPortfolioProject(portfolioProject.getId())));
        return portfolioProjects;
    }

    private void addPortfolioProjectAndToolsRelations(PortfolioProject portfolioProject) {
        Long portfolioProjectId = portfolioProject.getId();
        List<Tool> tools = portfolioProject.getTools();
        if (tools.size() == 0) {
            return;
        }

        String query = "INSERT INTO portfolio_projects_tools (portfolio_project_id, tool_id) VALUES "
                // Note: instead of drivers add "(?, ?), " string to set IDs in the loop below
                + tools.stream().map(tool -> "(?, ?)").collect(Collectors.joining(", "))
                + " ON DUPLICATE KEY UPDATE portfolio_project_id = portfolio_project_id";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < tools.size(); i++) {
                Tool tool = tools.get(i);
                preparedStatement.setLong((i * PARAMETER_SHIFT) + 1, portfolioProjectId);
                preparedStatement.setLong((i * PARAMETER_SHIFT) + 2, tool.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not store relations between PortfolioProject " + portfolioProject +
                    " and Tools " + tools, e);
        }
    }

    private void removePortfolioProjectAndToolsOutdatedRelations(PortfolioProject portfolioProject) {
        Long portfolioProjectId = portfolioProject.getId();
        List<Tool> toolExceptions = portfolioProject.getTools();
        int size = toolExceptions.size();
        Long dummyIdForRepeatMethod = 0L;
        String insertQuery = "DELETE FROM portfolio_projects_tools WHERE portfolio_project_id = ? "
                + "AND NOT portfolio_projects_tools.tool_id IN (" + dummyIdForRepeatMethod + ", ?".repeat(size) + ");";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setLong(1, portfolioProjectId);
            for (int i = 0; i < size; i++) {
                preparedStatement.setLong((i) + PARAMETER_SHIFT, toolExceptions.get(i).getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not remove outdated tools from Portfolio Project " +
                    portfolioProject, e);
        }
    }

    private PortfolioProject parsePortfolioProjectFromResultSet(ResultSet resultSet) throws SQLException {
        Long portfolioProjectId = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String shortProjectDescription = resultSet.getString("short_project_description");
        String detailedProjectDescription = resultSet.getString("detailed_project_description");
        String sourceCodeUrl = resultSet.getString("source_code_url");
        String interactiveResultUrl = resultSet.getString("interactive_result_url");
        String pictureUrl = resultSet.getString("picture_url");

        return new PortfolioProject(portfolioProjectId, name, shortProjectDescription,
                detailedProjectDescription, sourceCodeUrl, interactiveResultUrl, pictureUrl, null);
    }
}
