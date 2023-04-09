package com.gmail.danadiadius.technicians.controller.employer;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.Employer;
import com.gmail.danadiadius.technicians.service.EmployerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddEmployerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final EmployerService employerService = (EmployerService) injector.getInstance(EmployerService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/employers/add.jsp").forward(req, resp);
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

        String isHiringStr = req.getParameter("is_hiring");
        boolean isHiring = Boolean.parseBoolean(isHiringStr != null ? isHiringStr : "false");
        String companyName = req.getParameter("company_name");
        String currentPosition = req.getParameter("current_position");

        Employer employer = new Employer(isHiring, companyName, currentPosition);
        employer.setName(name);
        employer.setEmail(email);
        employer.setPassword(password);
        employer.setDescription(description);
        employer.setCountry(country);
        employer.setCity(city);
        employer.setPhone(phone);

        employerService.create(employer);
        resp.sendRedirect("/employers/add");
    }
}
