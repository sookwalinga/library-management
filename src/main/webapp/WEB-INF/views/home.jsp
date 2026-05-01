<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Management System</title>
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
        <div class="welcome-banner">
            <h1>Welcome to the Library</h1>
            <p>Manage your books and authors efficiently with our system.</p>
        </div>

        <div class="dashboard">
            <div class="card">
                <h3>Total Books</h3>
                <div class="number">${totalBooks}</div>
                <p style="margin-top: 15px;">
                    <a href="${pageContext.request.contextPath}/books"
                       class="btn btn-warning">View Books</a>
                </p>
            </div>

            <div class="card">
                <h3>Total Authors</h3>
                <div class="number">${totalAuthors}</div>
                <p style="margin-top: 15px;">
                    <a href="${pageContext.request.contextPath}/authors"
                       class="btn btn-warning">View Authors</a>
                </p>
            </div>

            <div class="card">
                <h3>Quick Actions</h3>
                <p style="margin: 15px 0;">
                    <a href="${pageContext.request.contextPath}/books/new"
                       class="btn btn-warning">Add Book</a>
                </p>
                <p>
                    <a href="${pageContext.request.contextPath}/authors/new"
                       class="btn btn-warning">Add Author</a>
                </p>
            </div>
        </div>
    </div>
</body>
</html>
