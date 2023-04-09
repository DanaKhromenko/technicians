package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.exception.AuthenticationException;
import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.User;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private static final UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email).orElse(null);
        if (user == null || !user.getPassword().equals(UserServiceImpl.hashPassword(password))) {
            throw new AuthenticationException("Could not find user by current e-mail and password.");
        }
        return user;
    }
}
