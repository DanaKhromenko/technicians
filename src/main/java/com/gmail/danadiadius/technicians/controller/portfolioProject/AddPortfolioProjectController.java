package com.gmail.danadiadius.technicians.controller.portfolioProject;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.service.PortfolioProjectService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddPortfolioProjectController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final PortfolioProjectService portfolioProjectService =
            (PortfolioProjectService) injector.getInstance(PortfolioProjectService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/portfolio_projects/add.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PortfolioProject portfolioProject = new PortfolioProject();
        portfolioProject.setName(req.getParameter("name"));
        portfolioProject.setDescription(req.getParameter("description"));
        portfolioProject.setUrl(req.getParameter("url"));
        /* portfolioProject.setPicture(new byte[]{}); TODO: update with a real picture data*/

        portfolioProject = portfolioProjectService.create(portfolioProject);
        portfolioProjectService.addPortfolioProjectToTechnician(Long.parseLong(req.getParameter("technician_id")),
                portfolioProject.getId());

        resp.sendRedirect("/portfolio_projects/all");
    }
}
