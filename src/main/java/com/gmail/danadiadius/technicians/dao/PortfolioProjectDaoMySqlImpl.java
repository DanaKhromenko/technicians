package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.exception.DataProcessingException;
import com.gmail.danadiadius.technicians.lib.Dao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Technician;
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

@Dao
public class PortfolioProjectDaoMySqlImpl implements PortfolioProjectDao {
    @Inject
    private ToolService toolService;

    @Override
    public PortfolioProject create(PortfolioProject portfolioProject) {
        String query = "INSERT INTO portfolio_projects (name, description, url, picture) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, portfolioProject.getName());
            preparedStatement.setString(2, portfolioProject.getDescription());
            preparedStatement.setString(3, portfolioProject.getUrl());
            preparedStatement.setBytes(4, portfolioProject.getPicture());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                portfolioProject.setId(resultSet.getObject(1, Long.class));
            }
            return portfolioProject;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create portfolio project " + portfolioProject, e);
        }
    }

    @Override
    public Optional<PortfolioProject> get(Long id) {
        String selectQuery = "SELECT * FROM portfolio_projects WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            PortfolioProject portfolioProject = null;
            if (resultSet.next()) {
                portfolioProject = parsePortfolioProjectFromResultSet(resultSet);
                portfolioProject.setTools(toolService.getAllToolsByPortfolioProject(id));
            }
            return Optional.ofNullable(portfolioProject);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get a portfolio project by id " + id, e);
        }
    }

    @Override
    public List<PortfolioProject> getAll() {
        String query = "SELECT * FROM portfolio_projects WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            List<PortfolioProject> portfolioProjects = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PortfolioProject portfolioProject = parsePortfolioProjectFromResultSet(resultSet);
                portfolioProject.setTools(toolService.getAllToolsByPortfolioProject(portfolioProject.getId()));
                portfolioProjects.add(portfolioProject);
            }
            return portfolioProjects;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all portfolio projects.", e);
        }
    }

    @Override
    public PortfolioProject update(PortfolioProject portfolioProject) {
        String query = "UPDATE portfolio_projects SET name = ?, description = ?, url = ?, picture = ? " +
                "WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, portfolioProject.getName());
            preparedStatement.setString(2, portfolioProject.getDescription());
            preparedStatement.setString(3, portfolioProject.getUrl());
            preparedStatement.setBytes(4, portfolioProject.getPicture());
            preparedStatement.setLong(5, portfolioProject.getId());
            preparedStatement.executeUpdate();

            toolService.updatePortfolioProjectsTools(portfolioProject);

            return portfolioProject;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update portfolio project " + portfolioProject, e);
        }
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
    public List<PortfolioProject> getAllByTechnician(Long technicianId) {
        String selectQuery = "SELECT * FROM portfolio_projects WHERE is_deleted = FALSE AND technician_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            List<PortfolioProject> portfolioProjects = new ArrayList<>();
            preparedStatement.setLong(1, technicianId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PortfolioProject portfolioProject = parsePortfolioProjectFromResultSet(resultSet);
                portfolioProject.setTools(toolService.getAllToolsByPortfolioProject(portfolioProject.getId()));
                portfolioProjects.add(portfolioProject);
            }
            return portfolioProjects;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get portfolio projects by technician id " + technicianId, e);
        }
    }

    @Override
    public void updateTechnicianPortfolioProjects(Technician technician) {
        List<PortfolioProject> portfolioProjectsCurrent = technician.getPortfolioProjects();
        List<PortfolioProject> portfolioProjectsFromDB = getAllByTechnician(technician.getId());

        // Remove from MySQL DB portfolio projects that are no longer belong to technician
        List<PortfolioProject> portfolioProjectsToRemoveFromDB = new ArrayList<>(portfolioProjectsFromDB);
        if (portfolioProjectsCurrent != null) {
            portfolioProjectsToRemoveFromDB.removeAll(portfolioProjectsCurrent);
        }
        portfolioProjectsToRemoveFromDB.forEach(pp -> delete(pp.getId()));

        // Add to MySQL DB portfolio projects that are now belong to technician
        // and add relations between technician and portfolio projects
        if (portfolioProjectsCurrent != null) {
            List<PortfolioProject> portfolioProjectsToAddToDB = new ArrayList<>(portfolioProjectsCurrent);
            portfolioProjectsToAddToDB.removeAll(portfolioProjectsFromDB);
            portfolioProjectsToAddToDB.forEach(pp -> {
                create(pp);
                addPortfolioProjectToTechnician(technician.getId(), pp.getId());
            });
        }
    }

    @Override
    public void addPortfolioProjectToTechnician(Long technicianId, Long portfolioProjectId) {
        String query = "UPDATE portfolio_projects SET technician_id = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, technicianId);
            preparedStatement.setLong(2, portfolioProjectId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not add portfolio project with id " + portfolioProjectId +
                    " to technician with id " + technicianId, e);
        }
    }

    private PortfolioProject parsePortfolioProjectFromResultSet(ResultSet resultSet) throws SQLException {
        PortfolioProject portfolioProject = new PortfolioProject();
        portfolioProject.setId(resultSet.getObject("id", Long.class));
        portfolioProject.setName(resultSet.getString("name"));
        portfolioProject.setDescription(resultSet.getString("description"));
        portfolioProject.setUrl(resultSet.getString("url"));
        portfolioProject.setPicture(resultSet.getBytes("picture"));

        return portfolioProject;
    }
}
