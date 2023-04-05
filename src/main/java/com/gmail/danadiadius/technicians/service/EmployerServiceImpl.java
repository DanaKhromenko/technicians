package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.dao.EmployerDao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.Employer;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerServiceImpl implements EmployerService {
    @Inject
    private EmployerDao employerDao;

    @Override
    public Optional<Employer> findByEmail(String email) {
        return employerDao.findByEmail(email);
    }

    @Override
    public Employer create(Employer employer) {
        return employerDao.create(employer);
    }

    @Override
    public Employer get(Long id) {
        return employerDao.get(id);
    }

    @Override
    public List<Employer> getAll() {
        return employerDao.getAll();
    }

    @Override
    public Employer update(Employer employer) {
        return employerDao.update(employer);
    }

    @Override
    public boolean delete(Long id) {
        return employerDao.delete(id);
    }
}
