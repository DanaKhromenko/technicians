package com.gmail.danadiadius.technicians.controller.portfolioProject;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.PortfolioProject;
import com.gmail.danadiadius.technicians.model.Tool;
import com.gmail.danadiadius.technicians.service.PortfolioProjectService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
        String name = req.getParameter("name");
        String shortProjectDescription = req.getParameter("short_project_description");
        String detailedProjectDescription = req.getParameter("detailed_project_description");
        String sourceCodeUrl = req.getParameter("source_code_url");
        String interactiveResultUrl = req.getParameter("interactive_result_url");
        String pictureUrl = req.getParameter("picture_url");
        PortfolioProject portfolioProject = new PortfolioProject(name, shortProjectDescription,
                detailedProjectDescription, sourceCodeUrl, interactiveResultUrl, pictureUrl, Collections.emptyList());
        // TODO: add TOOLS
        portfolioProjectService.create(portfolioProject);
        resp.sendRedirect("/portfolio_projects/add");
    }
}
