package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.model.Employer;
import com.gmail.danadiadius.technicians.service.GenericService;

import java.util.Optional;

public interface EmployerDao extends GenericService<Employer> {
    Optional<Employer> findByEmail(String email);
}
