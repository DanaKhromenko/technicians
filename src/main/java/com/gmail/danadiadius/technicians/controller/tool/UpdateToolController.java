package com.gmail.danadiadius.technicians.controller.tool;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.Tool;
import com.gmail.danadiadius.technicians.service.ToolService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateToolController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final ToolService toolService = (ToolService) injector.getInstance(ToolService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");

        req.setAttribute("id", id);
        req.setAttribute("name", name);
        req.getRequestDispatcher("/WEB-INF/tools/update.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        Tool tool = new Tool(id, name);
        toolService.update(tool);

        resp.sendRedirect("tools/");
    }
}
