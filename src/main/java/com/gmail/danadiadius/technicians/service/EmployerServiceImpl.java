package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.dao.EmployerDao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.Employer;
import com.gmail.danadiadius.technicians.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService {
    @Inject
    private EmployerDao employerDao;

    @Inject
    private UserService userService;

    @Override
    public Employer create(Employer employer) {
        User user = userService.create(employer);
        employer.setId(user.getId());
        return employerDao.create(employer);
    }

    @Override
    public Employer get(Long id) {
        return employerDao.get(id).orElseThrow();
    }

    @Override
    public List<Employer> getAll() {
        return employerDao.getAll();
    }

    @Override
    public Employer update(Employer employer) {
        User user = userService.update(employer);
        employer.setPassword(user.getPassword());
        return employerDao.update(employer);
    }

    @Override
    public boolean delete(Long id) {
        return userService.delete(id);
    }

    @Override
    public Optional<Employer> findByEmail(String email) {
        return employerDao.findByEmail(email);
    }
}
