package com.gmail.danadiadius.technicians.controller.employer;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.Employer;
import com.gmail.danadiadius.technicians.service.EmployerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllEmployersController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final EmployerService employerService = (EmployerService) injector.getInstance(EmployerService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Employer> allEmployers = employerService.getAll();
        req.setAttribute("employers", allEmployers);
        req.getRequestDispatcher("/WEB-INF/employers/all.jsp").forward(req, resp);
    }
}
