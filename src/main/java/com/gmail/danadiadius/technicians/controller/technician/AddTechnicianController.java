package com.gmail.danadiadius.technicians.controller.technician;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.Technician;
import com.gmail.danadiadius.technicians.service.TechnicianService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        Technician technician = new Technician();

        // User attributes
        technician.setId(Long.valueOf(req.getParameter("id")));
        technician.setName(req.getParameter("name"));
        technician.setEmail(req.getParameter("email"));
        technician.setPassword(req.getParameter("password"));
        technician.setDescription(req.getParameter("description"));
        technician.setCountry(req.getParameter("country"));
        technician.setCity(req.getParameter("city"));
        technician.setPhone(req.getParameter("phone"));
        /* technician.setPicture(req.getParameter("picture")); TODO: update with the real user picture data */

        // Technician attributes
        technician.setOpenToWork(Boolean.parseBoolean(req.getParameter("open_to_work")));
        technician.setDesiredPosition(req.getParameter("desired_position"));
        String desiredAnnualSalary = req.getParameter("desired_annual_salary");
        technician.setDesiredAnnualSalary(desiredAnnualSalary.isEmpty() ? 0 : Integer.parseInt(desiredAnnualSalary));
        technician.setAnnualSalaryCurrency(Currency.getInstance(req.getParameter("annual_salary_currency")));

        technicianService.create(technician);

        resp.sendRedirect("/technicians/add");
    }
}
