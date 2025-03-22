package com.duonghoang.test_input.servlet;

import com.duonghoang.test_input.dao.TestInputDAO;
import com.duonghoang.test_input.model.TestInput;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/update")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)   	// 100 MB
public class TestInputUpdateServlet extends HttpServlet {
    private TestInputDAO testInputDAO = new TestInputDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        TestInput testInput = testInputDAO.findById(Long.parseLong(id));
        req.setAttribute("testInput", testInput);
        req.getRequestDispatcher("/WEB-INF/views/form_update.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String textInput = req.getParameter("text");
            int numberInput = Integer.parseInt(req.getParameter("number"));
            LocalDate dateInput = LocalDate.parse(req.getParameter("date"));
            boolean gender = "1".equals(req.getParameter("gender"));
            String country = req.getParameter("country");

            String[] hobbiesArr = req.getParameterValues("hobbies");
            String hobbies = (hobbiesArr != null) ? String.join(",", hobbiesArr) : "";

            Part filePart = req.getPart("file");

            TestInput testInput = TestInput.builder()
                    .id(id)
                    .textInput(textInput)
                    .numberInput(numberInput)
                    .dateInput(dateInput)
                    .gender(gender)
                    .hobbies(hobbies)
                    .country(country)
                    .build();

            testInputDAO.update(testInput, filePart, getServletContext());

            resp.sendRedirect(req.getContextPath());
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }

}