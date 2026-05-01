<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Authors List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>
<body>
    <nav class="navbar">
        <h1>📚 Library Management System</h1>
        <ul>
            <li><a href="${pageContext.request.contextPath}/">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/books">Books</a></li>
            <li><a href="${pageContext.request.contextPath}/authors">Authors</a></li>
        </ul>
    </nav>

    <div class="container">
        <h2>All Authors</h2>

        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/authors/new"
               class="btn btn-success">+ Add New Author</a>
        </div>

        <c:choose>
            <c:when test="${empty authors}">
                <p>No authors found. Add some!</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Nationality</th>
                            <th>Birth Year</th>
                            <th>Biography</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="author" items="${authors}">
                            <tr>
                                <td>${author.id}</td>
                                <td><c:out value="${author.name}"/></td>
                                <td><c:out value="${author.nationality}"/></td>
                                <td>${author.birthYear}</td>
                                <td><c:out value="${author.biography}"/></td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/authors/edit/${author.id}"
                                       class="btn btn-warning">Edit</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
