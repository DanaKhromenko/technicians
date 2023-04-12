package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.exception.DataProcessingException;
import com.gmail.danadiadius.technicians.lib.Dao;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Tool;
import com.gmail.danadiadius.technicians.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ToolDaoMySqlImpl implements ToolDao {
    @Override
    public Tool create(Tool tool) {
        String query = "INSERT INTO tools (name) VALUES (?) ON DUPLICATE KEY UPDATE name = name, is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            getPreparedStatementWithToolData(preparedStatement, tool).executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                tool.setId(resultSet.getObject(1, Long.class));
            }
            return tool;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create tool " + tool, e);
        }
    }

    @Override
    public Optional<Tool> get(Long id) {
        String query = "SELECT * FROM tools WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Tool tool = null;
            if (resultSet.next()) {
                tool = setTool(resultSet);
            }
            return Optional.ofNullable(tool);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get tool by id " + id, e);
        }
    }

    @Override
    public List<Tool> getAll() {
        String query = "SELECT * FROM tools WHERE is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            List<Tool> tools = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                tools.add(setTool(resultSet));
            }
            return tools;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get list of all tools.", e);
        }
    }

    @Override
    public Tool update(Tool tool) {
        String query = "UPDATE tools SET name = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = getPreparedStatementWithToolData(connection.prepareStatement(query), tool)) {
            preparedStatement.setLong(2, tool.getId());
            preparedStatement.executeUpdate();
            return tool;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update tool " + tool, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE tools SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete tool by id " + id, e);
        }
    }

    @Override
    public List<Tool> getAllByPortfolioProject(Long portfolioProjectId) {
        String selectQuery = "SELECT DISTINCT t.* " +
                "FROM portfolio_projects_tools ppt " +
                "INNER JOIN tools t on ppt.tool_id = t.id " +
                "WHERE portfolio_project_id = ? AND t.is_deleted = FALSE; ";
        List<Tool> tools = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, portfolioProjectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tools.add(new Tool(resultSet.getObject("id", Long.class), resultSet.getNString("name")));
            }
            return tools;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all tools by portfolio project id " + portfolioProjectId, e);
        }
    }

    @Override
    public void updatePortfolioProjectsTools(PortfolioProject portfolioProject) {
        List<Tool> toolsCurrent = portfolioProject.getTools();
        List<Tool> toolsFromDB = getAllByPortfolioProject(portfolioProject.getId());

        // Remove from MySQL DB relation between portfolio project and tools that are no longer belong to it
        List<Tool> toolsToRemoveFromDB = new ArrayList<>(toolsFromDB);
        toolsToRemoveFromDB.removeAll(toolsCurrent);
        toolsToRemoveFromDB.forEach(t -> removeToolFromPortfolioProject(portfolioProject.getId(), t.getId()));

        // Add to MySQL DB tools that are now belong to portfolio project
        // and add relations between portfolio project and tools
        List<Tool> toolsToAddToDB = new ArrayList<>(toolsCurrent);
        toolsToAddToDB.removeAll(toolsFromDB);
        toolsToAddToDB.forEach(t -> {
            create(t);
            addToolToPortfolioProject(portfolioProject.getId(), t.getId());
        });
    }

    private void removeToolFromPortfolioProject(Long portfolioProjectId, Long toolId) {
        String query = "DELETE FROM portfolio_projects_tools WHERE portfolio_project_id = ? AND tool_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, portfolioProjectId);
            preparedStatement.setLong(2, toolId);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not remove tool with id " + toolId +
                    " from portfolio project with id " + portfolioProjectId, e);
        }
    }

    private void addToolToPortfolioProject(Long portfolioProjectId, Long toolId) {
        String query = "INSERT INTO portfolio_projects_tools (portfolio_project_id, tool_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, portfolioProjectId);
            preparedStatement.setLong(2, toolId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not add tool with id " + toolId +
                    " to portfolio project with id " + portfolioProjectId, e);
        }
    }

    private Tool setTool(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        return new Tool(id, name);
    }

    private PreparedStatement getPreparedStatementWithToolData(PreparedStatement preparedStatement, Tool tool)
            throws SQLException {
        preparedStatement.setString(1, tool.getName());
        return preparedStatement;
    }
}
