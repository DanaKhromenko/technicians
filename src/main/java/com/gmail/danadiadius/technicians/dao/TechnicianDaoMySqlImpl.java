package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.exception.DataProcessingException;
import com.gmail.danadiadius.technicians.lib.Dao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Technician;
import com.gmail.danadiadius.technicians.model.User;
import com.gmail.danadiadius.technicians.service.PortfolioProjectService;
import com.gmail.danadiadius.technicians.service.UserService;
import com.gmail.danadiadius.technicians.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class TechnicianDaoMySqlImpl implements TechnicianDao {
    @Inject
    private PortfolioProjectService portfolioProjectService;

    @Inject
    private UserService userService;

    private static final int ZERO_PLACEHOLDER = 0;
    private static final int PARAMETER_SHIFT = 2;

    @Override
    public Technician create(Technician technician) {
        String query = "INSERT INTO technicians (user_id, open_to_work, desired_position, desired_annual_salary, " +
                "annual_salary_currency) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Store technician to DB
            preparedStatement.setLong(1, technician.getId());
            preparedStatement.setBoolean(2, technician.isOpenToWork());
            preparedStatement.setString(3, technician.getDesiredPosition());
            preparedStatement.setInt(4, technician.getDesiredAnnualSalary());
            preparedStatement.setString(5, technician.getAnnualSalaryCurrency().getCurrencyCode());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                technician.setId(resultSet.getObject(1, Long.class));
            }

            // Store technician's portfolio projects to DB
            for (PortfolioProject portfolioProject : technician.getPortfolioProjects()) {
                PortfolioProject portfolioProjectFromDB = portfolioProjectService.create(portfolioProject);
                portfolioProject.setId(portfolioProjectFromDB.getId());
            }

            return technician;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create technician " + technician, e);
        }
    }

    @Override
    public Optional<Technician> get(Long id) {
        String query = "SELECT * FROM technicians t INNER JOIN users u ON t.user_id = u.id WHERE u.id = ? AND " +
                "u.is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Technician technician = null;
            if (resultSet.next()) {
                technician = getTechnician(resultSet);
            }
            return Optional.ofNullable(technician);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get technician by id " + id, e);
        }
    }

    @Override
    public List<Technician> getAll() {
        List<Technician> technicians = new ArrayList<>();
        String query = "SELECT * FROM technicians t INNER JOIN users u ON t.user_id = u.id WHERE u.is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Technician technician = getTechnician(resultSet);
                technician.setPortfolioProjects(portfolioProjectService.getAllByTechnician(technician.getId()));
                technicians.add(technician);
            }
            return technicians;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all technicians.", e);
        }
    }

    @Override
    public Technician update(Technician technician) {
        User user = userService.update(technician);
        technician.setPassword(user.getPassword());

        String query = "UPDATE technicians SET open_to_work = ?, desired_position = ?, desired_annual_salary = ?, " +
                "annual_salary_currency = ? WHERE user_id = ? ";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = getPreparedStatementWithTechnicianData(connection.prepareStatement(query), technician)) {
            preparedStatement.executeUpdate();

            deleteAllPortfolioProjectsExceptList(technician);
            insertAllPortfolioProjects(technician);

            return technician;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update technician " + technician, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return userService.delete(id);
    }

    private Technician getTechnician(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String description = resultSet.getString("description");

        boolean openToWork = resultSet.getBoolean("open_to_work");
        String desiredPosition = resultSet.getString("desired_position");
        int desiredAnnualSalary = resultSet.getInt("desired_annual_salary");
        String strAnnualSalaryCurrency = resultSet.getString("annual_salary_currency");
        Currency annualSalaryCurrency = null;
        if (!strAnnualSalaryCurrency.isEmpty()) {
            annualSalaryCurrency = Currency.getInstance(strAnnualSalaryCurrency);
        }

        Technician technician = new Technician(openToWork, desiredPosition, desiredAnnualSalary, annualSalaryCurrency,
                Collections.emptyList());

        technician.setId(id);
        technician.setName(name);
        technician.setEmail(email);
        technician.setPassword(password);
        technician.setDescription(description);

        return technician;
    }

    private PreparedStatement getPreparedStatementWithTechnicianData(PreparedStatement preparedStatement,
                                                                   Technician technician) throws SQLException {
        preparedStatement.setBoolean(1, technician.isOpenToWork());
        preparedStatement.setString(2, technician.getDesiredPosition());
        preparedStatement.setLong(3, technician.getDesiredAnnualSalary());
        preparedStatement.setString(4, technician.getAnnualSalaryCurrency().getCurrencyCode());
        preparedStatement.setLong(5, technician.getId());
        return preparedStatement;
    }

    private void deleteAllPortfolioProjectsExceptList(Technician technician) {
        List<PortfolioProject> exceptions = technician.getPortfolioProjects();
        int size = exceptions.size();
        String insertQuery = "DELETE FROM technician_portfolio_project WHERE technician_id = ? "
                + "AND NOT technician_portfolio_project.portfolio_project_id IN (" + ZERO_PLACEHOLDER
                + ", ?".repeat(size) + ");";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setLong(1, technician.getId());
            for (int i = 0; i < size; i++) {
                PortfolioProject portfolioProject = exceptions.get(i);
                preparedStatement.setLong((i) + PARAMETER_SHIFT, portfolioProject.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete portfolio projects.", e);
        }
    }

    private void insertAllPortfolioProjects(Technician technician) {
        Long technicianId = technician.getId();
        List<PortfolioProject> portfolioProjects = technician.getPortfolioProjects();
        if (portfolioProjects.size() == 0) {
            return;
        }
        String insertQuery = "INSERT INTO technician_portfolio_project (technician_id, portfolio_project_id) VALUES "
                + portfolioProjects.stream()
                .map(portfolioProject -> "(?, ?)")
                .collect(Collectors.joining(", "))
                + " ON DUPLICATE KEY UPDATE technician_id = technician_id";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            for (int i = 0; i < portfolioProjects.size(); i++) {
                PortfolioProject portfolioProject = portfolioProjects.get(i);
                preparedStatement.setLong((i * PARAMETER_SHIFT) + 1, technicianId);
                preparedStatement.setLong((i * PARAMETER_SHIFT) + 2, portfolioProject.getId());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not insert portfolio projects " + portfolioProjects, e);
        }
    }

    @Override
    public Optional<Technician> findByEmail(String email) {
        String query = "SELECT * FROM technicians t INNER JOIN users u ON t.user_id = u.id WHERE u.email = ? AND " +
                "u.is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            Technician technician = null;
            if (resultSet.next()) {
                technician = getTechnician(resultSet);
            }
            return Optional.ofNullable(technician);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get technician by e-mail " + email, e);
        }
    }
}
