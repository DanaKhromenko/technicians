package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.exception.DataProcessingException;
import com.gmail.danadiadius.technicians.lib.Dao;
import com.gmail.danadiadius.technicians.lib.Inject;
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
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Dao
public class TechnicianDaoMySqlImpl implements TechnicianDao {
    @Inject
    private PortfolioProjectService portfolioProjectService;

    @Inject
    private UserService userService;

    @Override
    public Technician create(Technician technician) {
        String query = "INSERT INTO technicians (user_id, open_to_work, desired_position, desired_annual_salary, " +
                "annual_salary_currency) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, technician.getId());
            preparedStatement.setBoolean(2, technician.isOpenToWork());
            preparedStatement.setString(3, technician.getDesiredPosition());
            preparedStatement.setInt(4, technician.getDesiredAnnualSalary());
            Currency currency = technician.getAnnualSalaryCurrency();
            if (currency != null) {
                preparedStatement.setString(5, currency.getCurrencyCode());
            }
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                technician.setId(resultSet.getObject(1, Long.class));
            }

            portfolioProjectService.updateTechnicianPortfolioProjects(technician);
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
                technician = parseTechnicianFromResultSet(resultSet);
                technician.setPortfolioProjects(portfolioProjectService.getAllByTechnician(id));
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
                Technician technician = parseTechnicianFromResultSet(resultSet);
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

            portfolioProjectService.updateTechnicianPortfolioProjects(technician);

            return technician;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update technician " + technician, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        // Due to soft delete strategy there is no need to delete data from `technicians` MySQL table
        return userService.delete(id);
    }

    private Technician parseTechnicianFromResultSet(ResultSet resultSet) throws SQLException {
        Technician technician = new Technician();

        // User attributes
        technician.setId(resultSet.getObject("id", Long.class));
        technician.setName(resultSet.getString("name"));
        technician.setEmail(resultSet.getString("email"));
        technician.setPassword(resultSet.getString("password"));
        technician.setDescription(resultSet.getString("description"));
        technician.setCountry(resultSet.getString("country"));
        technician.setCity(resultSet.getString("city"));
        technician.setPhone(resultSet.getString("phone"));
        /* technician.setPicture(new byte[]{}); TODO: update with the real user picture data */

        // Technician attributes
        technician.setOpenToWork(resultSet.getBoolean("open_to_work"));
        technician.setDesiredPosition(resultSet.getString("desired_position"));
        technician.setDesiredAnnualSalary(resultSet.getInt("desired_annual_salary"));
        technician.setAnnualSalaryCurrency(Currency.getInstance(resultSet.getString("annual_salary_currency")));

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
}
