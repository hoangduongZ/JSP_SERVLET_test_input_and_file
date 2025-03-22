package com.duonghoang.test_input.servlet;

import com.duonghoang.test_input.dao.TestInputDAO;
import com.duonghoang.test_input.model.TestInput;
import com.duonghoang.test_input.util.CommonUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/create")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*50,      	// 50 MB
        maxRequestSize=1024*1024*100)   	// 100 MB
public class TestInputCreateServlet extends HttpServlet {
    private TestInputDAO testInputDAO;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        testInputDAO = new TestInputDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(req, resp);
    }

    public static final String UPLOAD_DIRECTORY = Paths.get("assets","uploads").toString();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = req.getParameter("text");
        int number = Integer.parseInt(req.getParameter("number"));
        String dateString = req.getParameter("date");
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        int gender = Integer.parseInt(req.getParameter("gender"));
        String[] hobbies = req.getParameterValues("hobbies");
        String country = req.getParameter("country");

        String hobbiesString = (hobbies != null) ? String.join(", ", hobbies) : "";


        String fullPath = null;
        String fileName;
        Part filePart = req.getPart("file");
        if(filePart.getContentType().contains("pdf") && filePart.getSize() > 0){
            String fileNameFromClient = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("") +File.separator + UPLOAD_DIRECTORY;
            Path path= Paths.get(uploadPath);
            Files.createDirectories(path);
            fileName = UUID.randomUUID()+fileNameFromClient;
            fullPath = uploadPath + File.separator + fileName;
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);
            }
        }else {
            throw new IllegalStateException("File type not image");
        }

        String relativePath = UPLOAD_DIRECTORY+ File.separator +fileName;
        TestInput testInput = TestInput.builder()
                .textInput(text)
                .numberInput(number)
                .dateInput(localDate)
                .gender(gender == 1)
                .hobbies(hobbiesString)
                .country(country)
                .filePath(relativePath)
                .build();
        try {
            testInputDAO.save(testInput);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath());
    }

}
