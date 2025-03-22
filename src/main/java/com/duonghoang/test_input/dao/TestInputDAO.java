package com.duonghoang.test_input.dao;

import com.duonghoang.test_input.model.TestInput;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.duonghoang.test_input.servlet.TestInputCreateServlet.UPLOAD_DIRECTORY;
import static com.duonghoang.test_input.util.CommonUtil.checkTypeFile;

public class TestInputDAO extends BaseDAO {
    public TestInputDAO() {
    }

    public Integer save(TestInput testInput) throws SQLException {
        return executeUpdateWithPreparedStatement(
                "INSERT INTO test_input (text_input, number_input, date_input, gender, hobbies, country, file_path) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?);",
                testInput.getTextInput(), testInput.getNumberInput(),
                testInput.getDateInput(), testInput.getGender(), testInput.getHobbies(),
                testInput.getCountry(), testInput.getFilePath());
    }

    public List<TestInput> getAll() {
        try {
            return executeQueryPrepareStatementFetchList(
                    "SELECT * FROM test_input", this::mapRsToTestInput
            );
        } catch (SQLException e) {
            System.err.println("Error when fetch data: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private TestInput mapRsToTestInput(ResultSet rs) {
        try {
            return TestInput.builder().
                    id(rs.getLong("id")).textInput(rs.getString("text_input"))
                    .numberInput(rs.getInt("number_input"))
                    .dateInput(LocalDate.parse(rs.getString("date_input")
                            , DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .gender(rs.getBoolean("gender"))
                    .hobbies(rs.getString("hobbies"))
                    .country(rs.getString("country"))
                    .filePath(rs.getString("file_path"))
                    .build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public TestInput findById(Long id) {
        try {
            return executeQueryPrepareStatementFetchOne("SELECT * FROM test_input WHERE id=?"
                    , this::mapRsToTestInput, id);
        } catch (SQLException e) {
            System.err.println("Error when fetch data: " + e.getMessage());
            return null;
        }
    }

    public void update(TestInput testInput, Part filePart, ServletContext servletContext) {
        TestInput testInputExisting = findById(testInput.getId());
        handleFile(testInput, testInputExisting, filePart, servletContext);
        String sql = "UPDATE test_input SET text_input = ?, number_input = ?, date_input = ?, " +
                "gender = ?, hobbies = ?, country = ?, file_path = ? WHERE id = ?";
        try {
            executeUpdateWithPreparedStatement(sql
                    , objectParams(testInput, testInputExisting));
        } catch (SQLException e) {
            System.err.println("Error when fetch data: " + e.getMessage());
        }
    }

    private Object[] objectParams(TestInput testInput, TestInput testInputExisting) {
        if (testInput.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null.");
        }

        return new Object[]{
                testInput.getTextInput() != null ? testInput.getTextInput() : testInputExisting.getTextInput(),
                testInput.getNumberInput() != null ? testInput.getNumberInput() : testInputExisting.getNumberInput(),
                testInput.getDateInput() != null ? Date.valueOf(testInput.getDateInput()) : Date.valueOf(testInputExisting.getDateInput()),
                testInput.getGender() != null ? testInput.getGender() : testInputExisting.getGender(),
                testInput.getHobbies() != null ? testInput.getHobbies() : testInputExisting.getHobbies(),
                testInput.getCountry() != null ? testInput.getCountry() : testInputExisting.getCountry(),
                testInput.getFilePath() != null ? testInput.getFilePath() : testInputExisting.getFilePath(),
                testInput.getId()
        };
    }

    private void handleFile(TestInput testInput, TestInput testInputExisting, Part filePart, ServletContext servletContext) {
        if (filePart == null || filePart.getSize() == 0) {
            return;
        }

        if (!checkTypeFile(filePart, "application/pdf")) {
            throw new IllegalArgumentException("File type not allowed. Only PDFs are accepted.");
        }

        try {
            String uploadPath = servletContext.getRealPath("") + File.separator + UPLOAD_DIRECTORY;
            Files.createDirectories(Paths.get(uploadPath));

            String fileName = UUID.randomUUID() + "_" + Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String fullPath = uploadPath + File.separator + fileName;
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(fullPath), StandardCopyOption.REPLACE_EXISTING);
            }
            String deleteNotify = deleteOldFile(testInputExisting,servletContext) ? "Deleted success!" : "Deleted fail!";
//            Can log deleteNotify
            testInput.setFilePath(UPLOAD_DIRECTORY + File.separator + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file: " + e.getMessage(), e);
        }
    }

    private Boolean deleteOldFile(TestInput testInputExisting,ServletContext servletContext) throws IOException {
        if (testInputExisting.getFilePath() == null || testInputExisting.getFilePath().isEmpty()) {
            return false;
        }

        String realPath = servletContext.getRealPath("");
        if (realPath == null) {
            throw new IOException("Real path is not available.");
        }

        Path path = Paths.get(realPath, testInputExisting.getFilePath());
        return Files.deleteIfExists(path);
    }


    public void deleteById(Long id) {
        String sql = "DELETE FROM test_input WHERE id = ?";
        try {
            executeUpdateWithPreparedStatement(sql,id);
        } catch (SQLException e) {
            System.err.println("Error when delete record id=" + id + " :" + e.getMessage());
        }
    }
}
