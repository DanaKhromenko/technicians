package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.exception.DataProcessingException;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.model.Employer;
import com.gmail.danadiadius.technicians.model.User;
import com.gmail.danadiadius.technicians.service.UserService;
import com.gmail.danadiadius.technicians.util.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployerDaoMySqlImpl implements EmployerDao {
    @Inject
    private UserService userService;

    @Override
    public Employer create(Employer employer) {
        User user = userService.create(employer);

        String query = "INSERT INTO employers (user_id, is_hiring, current_position, company_name) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setBoolean(2, employer.isHiring());
            preparedStatement.setString(3, employer.getCurrentPosition());
            preparedStatement.setString(4, employer.getCompanyName());
            preparedStatement.executeUpdate();
            return employer;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create employer " + employer, e);
        }
    }

    @Override
    public Optional<Employer> get(Long id) {
        String query = "SELECT * FROM employers e INNER JOIN users u ON e.user_id = u.id WHERE u.id = ? AND " +
                "u.is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Employer employer = null;
            if (resultSet.next()) {
                employer = getEmployer(resultSet);
            }
            return Optional.ofNullable(employer);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get employer by id " + id, e);
        }
    }

    @Override
    public List<Employer> getAll() {
        List<Employer> employers = new ArrayList<>();
        String query = "SELECT * FROM employers e INNER JOIN users u ON e.user_id = u.id WHERE u.is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employers.add(getEmployer(resultSet));
            }
            return employers;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all employers.", e);
        }
    }

    @Override
    public Employer update(Employer employer) {
        User user = userService.update(employer);
        employer.setPassword(user.getPassword());

        String query = "UPDATE employers SET is_hiring = ?, current_position = ?, company_name = ? WHERE user_id = ? ";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = getPreparedStatementWithEmployerData(connection.prepareStatement(query), employer)) {
            preparedStatement.executeUpdate();
            return employer;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update employer " + employer, e);
        }
    }

    @Override
    public boolean delete(Long id) {
        return userService.delete(id);
    }

    private Employer getEmployer(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String description = resultSet.getString("description");

        boolean isHiring = resultSet.getBoolean("is_hiring");
        String companyName = resultSet.getString("company_name");
        String currentPosition = resultSet.getString("current_position");

        Employer employer = new Employer(isHiring, companyName, currentPosition);

        employer.setId(id);
        employer.setName(name);
        employer.setEmail(email);
        employer.setPassword(password);
        employer.setDescription(description);

        return employer;
    }

    private PreparedStatement getPreparedStatementWithEmployerData(PreparedStatement preparedStatement,
                                                                   Employer employer) throws SQLException {
        preparedStatement.setBoolean(1, employer.isHiring());
        preparedStatement.setString(2, employer.getCurrentPosition());
        preparedStatement.setString(3, employer.getCompanyName());
        preparedStatement.setLong(4, employer.getId());
        return preparedStatement;
    }

    @Override
    public Optional<Employer> findByEmail(String email) {
        String query = "SELECT * FROM employers e INNER JOIN users u ON e.user_id = u.id WHERE u.email = ? AND " +
                "u.is_deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            Employer employer = null;
            if (resultSet.next()) {
                employer = getEmployer(resultSet);
            }
            return Optional.ofNullable(employer);
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get employer by e-mail " + email, e);
        }
    }
}
