package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.dao.UserDao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User create(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        return userDao.create(user);
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).orElseThrow();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        if (user.getPassword().length() < 64) {
            user.setPassword(hashPassword(user.getPassword()));
        }
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Could not encode user's password ", e);
        }
    }
}
