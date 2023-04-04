package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.model.Technician;

import java.util.Optional;

public interface TechnicianDao extends GenericDao<Technician> {
    Optional<Technician> findByEmail(String email);
}
