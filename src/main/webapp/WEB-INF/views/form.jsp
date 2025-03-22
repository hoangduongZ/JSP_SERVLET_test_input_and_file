<%--
  Created by IntelliJ IDEA.
  User: hoang
  Date: 3/21/2025
  Time: 4:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Form Input Test</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
  <h2 class="text-center mb-4">Test Input Form</h2>
  <div class="card shadow p-4">
    <form action="${pageContext.request.contextPath}/create" method="post" enctype="multipart/form-data">
      <div class="mb-3">
        <label class="form-label">Text Input</label>
        <input type="text" class="form-control" name="text" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Number Input</label>
        <input type="number" class="form-control" name="number" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Date Input</label>
        <input type="date" class="form-control" name="date" required>
      </div>

      <div class="mb-3">
        <label class="form-label">Gender</label><br>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="gender" value="0" required>
          <label class="form-check-label">Male</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="gender" value="1">
          <label class="form-check-label">Female</label>
        </div>
      </div>

      <div class="mb-3">
        <label class="form-label">Hobbies</label><br>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="checkbox" name="hobbies" value="Reading">
          <label class="form-check-label">Reading</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="checkbox" name="hobbies" value="Gaming">
          <label class="form-check-label">Gaming</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="checkbox" name="hobbies" value="Sports">
          <label class="form-check-label">Sports</label>
        </div>
      </div>

      <div class="mb-3">
        <label class="form-label">Country</label>
        <select class="form-select" name="country">
          <option value="Vietnam">Vietnam</option>
          <option value="USA">USA</option>
          <option value="Japan">Japan</option>
        </select>
      </div>

      <div class="mb-3">
        <label class="form-label">Upload File</label>
        <input type="file" class="form-control" name="file">
      </div>

      <button type="submit" class="btn btn-primary w-100">Submit</button>
    </form>
  </div>
  <div class="text-center mt-3">
    <a href="TestInputServlet?action=list" class="btn btn-secondary">View Data</a>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
