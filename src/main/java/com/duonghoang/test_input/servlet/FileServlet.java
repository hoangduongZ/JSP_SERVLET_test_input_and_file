package com.duonghoang.test_input.servlet;

import com.duonghoang.test_input.dao.TestInputDAO;
import com.duonghoang.test_input.model.TestInput;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/files/*")
public class FileServlet extends HttpServlet {
    private TestInputDAO testInputDAO = new TestInputDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getPathInfo().substring(1);
        String filePath = getServletContext().getRealPath("") + File.separator + fileName;
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "inline; filename=" + fileName);
        resp.setContentLength((int) Files.size(path));

        try (InputStream inputStream = Files.newInputStream(path);
             OutputStream outputStream = resp.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead); // avoid redundant writing when coming end file
            }
        }
    }

    public TestInputDAO getTestInputDAO() {
        return testInputDAO;
    }

    public void setTestInputDAO(TestInputDAO testInputDAO) {
        this.testInputDAO = testInputDAO;
    }
}
