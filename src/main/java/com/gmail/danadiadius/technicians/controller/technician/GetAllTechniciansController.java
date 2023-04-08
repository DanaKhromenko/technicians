package com.gmail.danadiadius.technicians.controller.technician;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.Technician;
import com.gmail.danadiadius.technicians.service.TechnicianService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllTechniciansController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final TechnicianService technicianService = (TechnicianService) injector.getInstance(TechnicianService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Technician> allTechnicians = technicianService.getAll();
        req.setAttribute("technicians", allTechnicians);
        req.getRequestDispatcher("/WEB-INF/technicians/all.jsp").forward(req, resp);
    }
}
