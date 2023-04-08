package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.exception.DataProcessingException;
import com.gmail.danadiadius.technicians.lib.Dao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Technician;
import com.gmail.danadiadius.technicians.service.PortfolioProjectService;
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
public class TechnicianDaoMySqlImpl implements TechnicianDao {
    @Inject
    private PortfolioProjectService portfolioProjectService;

    @Override
    public Technician create(Technician technician) {
        String query = "INSERT INTO technicians (name, email, password, desired_position) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Store technician to DB
            preparedStatement.setString(1, technician.getName());
            preparedStatement.setString(2, technician.getEmail());
            preparedStatement.setString(3, technician.getPassword());
            preparedStatement.setString(4, technician.getDesiredPosition());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                technician.setId(resultSet.getObject(1, Long.class));
            }

            // Store technician's portfolio projects to DB
            for (PortfolioProject portfolioProject : technician.getPortfolioProjects()) {
                PortfolioProject portfolioProjectFromDB = portfolioProjectService.create(portfolioProject);
                technician.getPortfolioProjects().add(portfolioProjectFromDB);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not create technician. " + technician, e);
        }
        return technician;
    }

    @Override
    public Optional<Technician> get(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Technician> getAll() {
        String query = "SELECT * FROM technicians WHERE is_deleted = FALSE";
        List<Technician> technicians = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()) {
                technicians.add(getTechnician(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get a list of tools from tools table. ", e);
        }
        return technicians;
    }

    @Override
    public Technician update(Technician element) {

//        try {
//            PreparedStatement statement = connection.prepareStatement("UPDATE technician SET name=? WHERE id=?");
//            statement.setString(1, technician.getName());
//            statement.setLong(2, technician.getId());
//            statement.executeUpdate();
//
//            // удаляем старые портфолио проекты
//            deleteTechnicianPortfolioProjects(technician);
//            // сохраняем новые портфолио проекты
//            saveTechnicianPortfolioProjects(technician);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
//        try {
//            // удаляем портфолио проекты, принадлежащие этому технику
//            deleteTechnicianPortfolioProjects(technician);
//
//            PreparedStatement statement = connection.prepareStatement("DELETE FROM technician WHERE id=?");
//            statement.setLong(1, technician.getId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    @Override
    public Optional<Technician> findByEmail(String email) {
//        Technician technician = null;
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT * FROM technician WHERE id=?");
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                technician = new Technician();
//                technician.setId(resultSet.getLong("id"));
//                technician.setName(resultSet.getString("name"));
//                // загружаем портфолио проекты, принадлежащие этому технику
//                technician.setPortfolioProjects(findTechnicianPortfolioProjects(technician));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return technician;
        return Optional.empty();
    }

    private Technician getTechnician(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject("id", Long.class);
        String name = resultSet.getString("name");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        String desiredPosition = resultSet.getString("desired_position");
        Technician technician = new Technician(id, name, email, password, desiredPosition, null);
        return technician;
    }

    private void saveTechnicianPortfolioProjects(Technician technician) throws SQLException {
//        for (PortfolioProject portfolioProject : technician.getPortfolioProjects()) {
//            PreparedStatement statement = connection.prepareStatement("INSERT INTO technician_portfolio_project (technician_id, portfolio_project_id) VALUES (?, ?)");
//            statement.setLong(1, technician.getId());
//            statement.setLong(2, portfolioProject.getId());
//            statement.executeUpdate();
//        }
    }

//    private void deleteTechnicianPortfolioProjects(Technician technician) throws SQLException {
//        PreparedStatement statement = connection.prepareStatement("DELETE FROM technician_portfolio_project WHERE technician_id=?");
//        statement.setLong(1, technician.getId());
//        statement.executeUpdate();
//    }

//    private List<PortfolioProject> findTechnicianPortfolioProjects(Technician technician) throws SQLException {
//        List<PortfolioProject> portfolioProjects = new ArrayList<>();
//        PreparedStatement statement = connection.prepareStatement("SELECT p.* FROM portfolio_project p, technician_portfolio_project tp WHERE p.id=tp.portfolio_project_id AND tp.technician_id=?");
//        statement.setLong(1, technician.getId());
//        ResultSet resultSet = statement.executeQuery();
//        while (resultSet.next()) {
//            PortfolioProject portfolioProject
}
