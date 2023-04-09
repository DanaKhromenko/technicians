package com.gmail.danadiadius.technicians.controller.technician;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.Technician;
import com.gmail.danadiadius.technicians.service.TechnicianService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Currency;

public class AddTechnicianController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final TechnicianService technicianService = (TechnicianService) injector.getInstance(TechnicianService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/technicians/add.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String description = req.getParameter("description");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String phone = req.getParameter("phone");

        String openToWorkStr = req.getParameter("open_to_work");
        boolean openToWork = Boolean.parseBoolean(openToWorkStr != null ? openToWorkStr : "false");
        String desiredPosition = req.getParameter("desired_position");
        String desiredAnnualSalaryStr = req.getParameter("desired_annual_salary");
        int desired_annual_salary = Integer.parseInt(desiredAnnualSalaryStr != null &&
                desiredAnnualSalaryStr.matches("\\d+") ? desiredAnnualSalaryStr : "0");
        String annualSalaryCurrencyStr = req.getParameter("annual_salary_currency");
        Currency annual_salary_currency = null;
        if (annualSalaryCurrencyStr != null) {
            try {
                annual_salary_currency = Currency.getInstance(annualSalaryCurrencyStr);
            } catch (IllegalArgumentException e) {}
        }

        Technician technician = new Technician(openToWork, desiredPosition, desired_annual_salary,
                annual_salary_currency, Collections.emptyList());
        technician.setName(name);
        technician.setEmail(email);
        technician.setPassword(password);
        technician.setDescription(description);
        technician.setCountry(country);
        technician.setCity(city);
        technician.setPhone(phone);

        technicianService.create(technician);
        resp.sendRedirect("/technicians/add");
    }
}
