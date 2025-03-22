<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Test Input List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Test Input List</h2>
    <div class="card shadow p-4">
        <table class="table table-striped">
            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Text</th>
                <th>Number</th>
                <th>Date</th>
                <th>Gender</th>
                <th>Hobbies</th>
                <th>Country</th>
                <th>File</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="t" items="${testInputs}">
                <tr>
                    <td>${t.id}</td>
                    <td>${t.textInput}</td>
                    <td>${t.numberInput}</td>
                    <td>${t.dateInput}</td>
                    <td>${t.gender ? "Female" : "Male"}</td>
                    <td>${t.hobbies}</td>
                    <td>${t.country}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/files/${t.filePath}" target="_blank">View File</a>
                    </td>
                    <td><a href="${pageContext.request.contextPath}/update?id=${t.id}" class="btn btn-primary">Update</a>
                        <a onclick="return confirm('Do you want to delete one ?')" href="${pageContext.request.contextPath}/delete?id=${t.id}" class="btn btn-danger">Delete</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="text-center mt-3">
        <a href="${pageContext.request.contextPath}/create" class="btn btn-primary">Add New Entry</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
