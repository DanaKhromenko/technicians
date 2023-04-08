package com.gmail.danadiadius.technicians.controller.portfolioProject;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.service.PortfolioProjectService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetAllPortfolioProjectsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final PortfolioProjectService portfolioProjectService =
            (PortfolioProjectService) injector.getInstance(PortfolioProjectService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("portfolio_projects", portfolioProjectService.getAll());
        req.getRequestDispatcher("/WEB-INF/portfolio_projects/all.jsp").forward(req, resp);
    }
}
