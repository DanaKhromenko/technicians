package com.gmail.danadiadius.technicians.controller;

import com.gmail.danadiadius.technicians.exception.AuthenticationException;
import com.gmail.danadiadius.technicians.lib.Injector;
import com.gmail.danadiadius.technicians.model.User;
import com.gmail.danadiadius.technicians.service.AuthenticationService;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.gmail.danadiadius.technicians");
    private static final AuthenticationService authenticationService
            = (AuthenticationService) injector.getInstance(AuthenticationService.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = authenticationService.login(email, password);
            HttpSession session = request.getSession();
            session.setAttribute("user_id", user.getId());
            response.sendRedirect("/index");
        } catch (AuthenticationException e) {
            request.setAttribute("errorMessage", "Authentication failed: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
