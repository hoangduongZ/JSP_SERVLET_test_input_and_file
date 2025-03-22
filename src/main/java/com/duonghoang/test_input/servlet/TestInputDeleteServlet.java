package com.duonghoang.test_input.servlet;

import com.duonghoang.test_input.dao.TestInputDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete")
public class TestInputDeleteServlet extends HttpServlet {
    TestInputDAO testInputDAO = new TestInputDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        testInputDAO.deleteById(id);
        resp.sendRedirect(req.getContextPath());
    }
}
