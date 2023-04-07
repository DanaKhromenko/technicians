package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.exception.DataProcessingException;
import com.gmail.danadiadius.technicians.lib.Dao;
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
public class ToolDaoMySQLImpl implements ToolDao {
    @Override
    public Tool create(Tool tool) {
        String query = "INSERT INTO tools (name) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            setUpdate(preparedStatement, tool).executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                tool.setId(resultSet.getObject(1, Long.class));
            }
            return tool;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create tool. " + tool, e);
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
             Statement statement = connection.createStatement()) {
            List<Tool> tools = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                tools.add(setTool(resultSet));
            }
            return tools;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get a list of tools from tools table. ", e);
        }
    }

    @Override
    public Tool update(Tool tool) {
        String query = "UPDATE tools SET name = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = setUpdate(connection.prepareStatement(query), tool)) {
            preparedStatement.setLong(2, tool.getId());
            preparedStatement.executeUpdate();
            return tool;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update a tool " + tool, e);
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
            throw new DataProcessingException("Could not delete a tool by id " + id, e);
        }
    }

    @Override
    public List<Tool> getAllByPortfolioProject(Long portfolioProjectId) {
        String selectQuery = "SELECT t.id as id, "
                + "t.name as name "
                + "FROM tools t"
                + " INNER JOIN portfolio_projects_tools ppt on t.id ppt.tool_id"
                + "WHERE ppt.is_deleted = false and ppt.portfolio_project_id = ?";
        List<Tool> tools = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setLong(1, portfolioProjectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tools.add(new Tool(resultSet.getObject("id", Long.class), resultSet.getNString("name")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not get all tools", e);
        }
        return tools;
    }

    private Tool setTool(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        Tool tool = new Tool(name);
        tool.setId(id);
        return tool;
    }

    private PreparedStatement setUpdate(PreparedStatement statement,
                                        Tool tool) throws SQLException {
        statement.setString(1, tool.getName());
        return statement;
    }
}
