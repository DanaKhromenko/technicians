package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.model.Employer;

import java.util.Optional;

public interface EmployerService extends GenericService<Employer> {
    Optional<Employer> findByEmail(String email);
}
