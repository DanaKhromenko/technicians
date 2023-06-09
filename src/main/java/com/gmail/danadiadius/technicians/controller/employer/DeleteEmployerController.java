package com.gmail.danadiadius.technicians.controller.employer;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.service.EmployerService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteEmployerController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final EmployerService employerService = (EmployerService) injector.getInstance(EmployerService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        employerService.delete(Long.parseLong(req.getParameter("id")));
        resp.sendRedirect("employers/all");
    }
}
