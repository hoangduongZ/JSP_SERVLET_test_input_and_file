<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Test Input List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
          integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
</head>
<body class="bg-light">
<div class="container mt-5">
    <h2 class="text-center mb-4">Test Input List</h2>
    <form class="p-4 bg-light rounded">
        <div class="row g-2">
            <div class="col-md-3">
                <input type="text" class="form-control" placeholder="Text">
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" placeholder="Number">
            </div>
            <div class="col-md-3">
                <select name="" id="" class="form-control">
                    <option value="0">Male</option>
                    <option value="1">Female</option>
                    <option value=""></option>
                </select>
            </div>
            <div class="col-md-3">
                <input type="text" class="form-control" placeholder="Country">
            </div>
        </div>
        <div class="d-flex justify-content-center mt-3">
            <button type="submit" class="btn btn-primary px-4">Search Now</button>
        </div>
    </form>
    <div class="card shadow p-4">
        <div class="d-flex justify-content-between" >
            <nav aria-label="...">
                <ul class="pagination">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1"><i class="fa-solid fa-chevron-left"></i></a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="#"><i class="fa-solid fa-chevron-right"></i></a>
                    </li>
                    <input type="text" style="width: 37.6px; margin-left: 5px; margin-right: 5px;">
                    <li class="page-item">
                        <a class="page-link" href="#">GO</a>
                    </li>
                </ul>
            </nav>
            <div><a href="#" class="btn btn-warning"> Imports CSV </a></div>
        </div>

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
                    <td><a href="${pageContext.request.contextPath}/update?id=${t.id}"
                           class="btn btn-primary">Update</a>
                        <a onclick="return confirm('Do you want to delete one ?')"
                           href="${pageContext.request.contextPath}/delete?id=${t.id}" class="btn btn-danger">Delete</a>
                    </td>
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
