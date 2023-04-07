package com.gmail.danadiadius.technicians.controller.tool;

import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.service.ToolService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetToolController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private final ToolService toolService = (ToolService) injector.getInstance(ToolService.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("tools", toolService.getAll());
        req.getRequestDispatcher("/WEB-INF/views/tools/all.jsp").forward(req, resp);
    }
}
