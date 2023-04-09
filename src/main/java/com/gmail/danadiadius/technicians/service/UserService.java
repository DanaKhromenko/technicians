package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.model.User;

import java.util.Optional;

public interface UserService extends GenericService<User> {
    Optional<User> findByEmail(String email);
}
