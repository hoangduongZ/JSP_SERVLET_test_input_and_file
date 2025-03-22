package com.duonghoang.test_input.servlet;

import com.duonghoang.test_input.dao.TestInputDAO;
import com.duonghoang.test_input.model.TestInput;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class TestInputIndexServlet extends HttpServlet {
    private TestInputDAO testInputDAO = new TestInputDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TestInput> testInputs = testInputDAO.getAll();
        req.setAttribute("testInputs", testInputs);
        req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
