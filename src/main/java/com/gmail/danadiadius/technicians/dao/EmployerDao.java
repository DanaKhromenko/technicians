package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.model.Employer;

import java.util.Optional;

public interface EmployerDao extends GenericDao<Employer> {
    Optional<Employer> findByEmail(String email);
}
