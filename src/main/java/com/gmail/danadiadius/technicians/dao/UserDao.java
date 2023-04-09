package com.gmail.danadiadius.technicians.dao;

import com.gmail.danadiadius.technicians.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {
    Optional<User> findByEmail(String email);
}
