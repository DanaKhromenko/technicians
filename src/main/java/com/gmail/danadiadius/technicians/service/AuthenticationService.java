package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.exception.AuthenticationException;
import com.gmail.danadiadius.technicians.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;
}
