package com.gmail.danadiadius.technicians.service;

import com.gmail.danadiadius.technicians.dao.TechnicianDao;
import com.gmail.danadiadius.technicians.lib.Inject;
import com.gmail.danadiadius.technicians.lib.Service;
import com.gmail.danadiadius.technicians.model.Technician;
import com.gmail.danadiadius.technicians.model.User;

import java.util.List;

@Service
public class TechnicianServiceImpl implements TechnicianService {
    @Inject
    private TechnicianDao technicianDao;

    @Inject
    private UserService userService;

    @Override
    public Technician create(Technician technician) {
        User user = userService.create(technician);
        technician.setId(user.getId());
        return technicianDao.create(technician);
    }

    @Override
    public Technician get(Long id) {
        return technicianDao.get(id).orElseThrow();
    }

    @Override
    public List<Technician> getAll() {
        return technicianDao.getAll();
    }

    @Override
    public Technician update(Technician technician) {
        User user = userService.update(technician);
        technician.setPassword(user.getPassword());
        return technicianDao.update(technician);
    }

    @Override
    public boolean delete(Long id) {
        return userService.delete(id);
    }
}
