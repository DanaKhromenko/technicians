package com.gmail.danadiadius.technicians.controller;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.Employer;
import com.gmail.danadiadius.technicians.model.Technician;
import com.gmail.danadiadius.technicians.model.User;
import com.gmail.danadiadius.technicians.service.EmployerService;
import com.gmail.danadiadius.technicians.service.TechnicianService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Currency;

public class RegistrationController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final EmployerService employerService = (EmployerService) injector.getInstance(EmployerService.class);
    private final TechnicianService technicianService = (TechnicianService) injector.getInstance(TechnicianService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName(req.getParameter("name"));
        user.setEmail(req.getParameter("email"));
        user.setPassword(req.getParameter("password"));
        user.setDescription(req.getParameter("description"));
        user.setCountry(req.getParameter("country"));
        user.setCity(req.getParameter("city"));
        user.setPhone(req.getParameter("phone"));

        String type = req.getParameter("type");
        if (type.equals("employer")) {
            Employer employer = new Employer(user);

            employer.setHiring(getBooleanFromFlag(req.getParameter("is_hiring")));
            employer.setCompanyName(req.getParameter("company_name"));
            employer.setCurrentPosition(req.getParameter("current_position"));

            employerService.create(employer);
        } else {
            Technician technician = new Technician(user);

            technician.setOpenToWork(getBooleanFromFlag(req.getParameter("open_to_work")));
            technician.setDesiredPosition(req.getParameter("desired_position"));

            String desiredAnnualSalaryStr = req.getParameter("desired_annual_salary");
            int desiredAnnualSalary;
            try {
                desiredAnnualSalary = Integer.parseInt(desiredAnnualSalaryStr);
            } catch (Exception e) {
                desiredAnnualSalary = 0;
            }
            technician.setDesiredAnnualSalary(desiredAnnualSalary);

            String annualSalaryCurrencyStr = req.getParameter("annual_salary_currency");
            Currency annualSalaryCurrency = null;
            try {
                annualSalaryCurrency = Currency.getInstance(annualSalaryCurrencyStr);
            } catch (Exception e) { }
            technician.setAnnualSalaryCurrency(annualSalaryCurrency);

            technicianService.create(technician);
        }
        resp.sendRedirect("/login");
    }

    private boolean getBooleanFromFlag(String value) {
        return value != null && value.equals("on");
    }
}
